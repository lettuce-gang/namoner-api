package com.toy.namoner.domain.letter.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.toy.namoner.common.error.exceptions.AuthorizationException;
import com.toy.namoner.common.error.exceptions.IllegalLetterTypeException;
import com.toy.namoner.common.error.exceptions.UserSenderEmptyException;
import com.toy.namoner.common.jwt.NMNAuthentication;
import com.toy.namoner.domain.letter.controller.dto.request.LetterReplyRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.toy.namoner.common.error.exceptions.CannotReadableLetterException;
import com.toy.namoner.common.error.exceptions.EntityNotFoundException;
import com.toy.namoner.domain.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoner.domain.letter.controller.dto.response.LetterListResponse;
import com.toy.namoner.domain.letter.controller.dto.response.LetterResponse;
import com.toy.namoner.domain.letter.model.Letter;
import com.toy.namoner.domain.letter.model.enums.LetterType;
import com.toy.namoner.domain.letter.repository.LetterRepository;
import com.toy.namoner.domain.user.model.User;
import com.toy.namoner.domain.user.service.UserService;
import com.toy.namoner.infra.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LetterService {

	private final ImageService imageService;
	private final UserService userService;
	private final LetterRepository letterRepository;

	public void send(NMNAuthentication authentication, LetterSendRequest letterSendRequest, MultipartFile image) {
		User userReceiver = userService.findByUserId(letterSendRequest.getUserReceiver());

		String imageUrl =
			image == null || image.isEmpty() ? null : imageService.uploadFile(ImageService.LETTER_IMAGE_DIR, image);

		User userSender =
				authentication.isGuest() ? null : userService.findByUserId(authentication.getUserId());

		Letter letter = switch (letterSendRequest.getLetterType()) {
			case LetterType.NORMAL -> Letter.createNormalLetterType(letterSendRequest, userReceiver, userSender, imageUrl);
			case LetterType.RESERVED -> Letter.createReservedLetterType(letterSendRequest, userReceiver, userSender, imageUrl);
			default -> throw new IllegalLetterTypeException();
		};

		letterRepository.save(letter);
	}

	@Transactional(readOnly = true)
	public List<LetterListResponse> findLettersByUserId(NMNAuthentication authentication, String userId) {
		User user = userService.findByUserId(userId);

		if (!authentication.verifyUser(user)) {
			throw new AuthorizationException("User " + userId + " is not authorized");
		}

		List<Letter> letters = user.getReceiveLetters();
		updateLetterTypeIfReceived(letters);
		List<Letter> sortedLetters = sortLetter(letters);

		return sortedLetters.stream().map(LetterListResponse::from).collect(Collectors.toList());
	}

	public List<LetterListResponse> findSendLetters(NMNAuthentication authentication) {
		User user = userService.findByUserId(authentication.getUserId());
		List<Letter> sendLetters = user.getSendLetters();

		updateLetterTypeIfReceived(sendLetters);

		return Stream.ofNullable(sendLetters)
			.flatMap(List::stream)
			.map(LetterListResponse::from)
			.toList();
	}

	private void updateLetterTypeIfReceived(List<Letter> letters) {
		letters.forEach(Letter::updateLetterTypeIfReceived);
	}

	private List<Letter> sortLetter(List<Letter> letters) {
		List<Letter> reservedLetters = letters.stream()
			.filter(letter -> !letter.getIsRead() && LetterType.RESERVED.equals(letter.getLetterType()))
			.sorted(Comparator.comparing(Letter::getReceiveDate))
			.toList();

		List<Letter> notReadLetters = letters.stream()
			.filter(letter -> !letter.getIsRead() && LetterType.NORMAL.equals(letter.getLetterType()))
			.sorted(Comparator.comparing(Letter::getReceiveDate).reversed())
			.toList();

		List<Letter> readLetters = letters.stream()
			.filter(Letter::getIsRead)
			.sorted(Comparator.comparing(Letter::getReceiveDate).reversed())
			.toList();

		List<Letter> ret = new ArrayList<>();
		ret.addAll(notReadLetters);
		ret.addAll(reservedLetters);
		ret.addAll(readLetters);

		return ret;
	}

	private Letter findById(String letterId) {
		Letter letter = letterRepository.findById(letterId)
			.orElseThrow(() -> new EntityNotFoundException("Letter " + letterId + " not found"));

		LocalDateTime now = LocalDateTime.now();
		if (letter.getReceiveDate() != null && now.isBefore(letter.getReceiveDate())) {
			throw new CannotReadableLetterException("Letter " + letterId + " cannot be read yet");
		}

		letter.readLetter();
		letterRepository.save(letter);

		return letter;
	}

	public LetterResponse getLetterResponseByLetterId(String userId, String letterId) {
		Letter letter = findById(letterId);

		if (!letter.isReceiver(userService.findByUserId(userId))) {
			throw new AuthorizationException("You are not authorized to view this letter.");
		};

		if (LetterType.REPLY == letter.getLetterType()) {
			return createReplyLetterResponse(letter);
		}

		return createLetterResponse(letter);
	}

	private LetterResponse createLetterResponse(Letter letter) {
		String imageUrl = imageService.getFileUrl(letter.getImageUrl());

		return LetterResponse.create(letter, imageUrl);
	}

	private LetterResponse createReplyLetterResponse(Letter replyLetter) {
		Letter originalLetter = findByReplyLetterId(replyLetter.getId());

		String originalLetterImage = imageService.getFileUrl(originalLetter.getImageUrl());
		String replyLetterImage = imageService.getFileUrl(replyLetter.getImageUrl());

		return LetterResponse.createLetterWithReply(originalLetter, originalLetterImage, replyLetter, replyLetterImage);
	}
	private Letter findByReplyLetterId(String replyLetterId) {
		Letter letter = letterRepository.findByReplyLetter_Id(replyLetterId)
				.orElseThrow(() -> new EntityNotFoundException("Letter with replyLetterId " + replyLetterId + " not found"));

		return letter;
	}

	public void reply(String userSenderId, String originLetterId, LetterReplyRequest replyLetterSendRequest, MultipartFile image) {
		Letter originLetter = letterRepository.findById(originLetterId)
				.orElseThrow(() -> new EntityNotFoundException("Letter " + originLetterId + " not found"));

		User userReceiver = originLetter.getUserSender();
		if (userReceiver == null) {
			throw new UserSenderEmptyException();
		}

		User userSender = userService.findByUserId(userSenderId);

		String imageUrl =
			image == null || image.isEmpty() ? null : imageService.uploadFile(ImageService.LETTER_IMAGE_DIR, image);

		Letter replyLetter = Letter.createReplyLetterType(replyLetterSendRequest, originLetter.getUserReceiver(), userSender, imageUrl);

		letterRepository.save(replyLetter);

		originLetter.replyLetter(replyLetter);
		letterRepository.save(originLetter);
	}
}
