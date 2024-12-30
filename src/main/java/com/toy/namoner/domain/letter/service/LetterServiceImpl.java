package com.toy.namoner.domain.letter.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.toy.namoner.common.error.exceptions.EntityNotFoundException;
import com.toy.namoner.common.error.exceptions.IllegalLetterTypeException;
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
public class LetterServiceImpl implements LetterService {

	private final ImageService imageService;
	private final UserService userService;
	private final LetterRepository letterRepository;

	@Override
	public void send(LetterSendRequest letterSendRequest, MultipartFile image) {
		User receiver = userService.findByUserId(letterSendRequest.getUserReceiver());

		String imageUrl =
			image == null || image.isEmpty() ? null : imageService.uploadFile(ImageService.LETTER_IMAGE_DIR, image);

		Letter letter = switch (letterSendRequest.getLetterType()) {
			case LetterType.NORMAL -> Letter.createNormalLetterType(letterSendRequest, receiver, imageUrl);
			case LetterType.RESERVED -> Letter.createReservedLetterType(letterSendRequest, receiver, imageUrl);
			default -> throw new IllegalLetterTypeException();
		};

		letterRepository.save(letter);
	}

	@Override
	@Transactional(readOnly = true)
	public List<LetterListResponse> findLettersByUserId(String userId) {
		User user = userService.findByUserId(userId);

		List<Letter> sortedLetters = sortLetter(user.getReceiveLetters());

		return sortedLetters.stream().map(LetterListResponse::from).collect(Collectors.toList());
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

	@Override
	public Letter findById(String letterId) {
		Letter letter = letterRepository.findById(letterId)
			.orElseThrow(() -> new EntityNotFoundException("Letter " + letterId + " not found"));

		letter.readLetter();
		letterRepository.save(letter);

		return letter;
	}

	@Override
	public LetterResponse getLetterResponseByLetterId(String letterId) {
		Letter letter = findById(letterId);

		String imageUrl = imageService.getFileUrl(letter.getImageUrl());

		return LetterResponse.from(letter, imageUrl);
	}

}
