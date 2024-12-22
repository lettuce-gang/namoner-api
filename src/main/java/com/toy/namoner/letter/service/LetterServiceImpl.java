package com.toy.namoner.letter.service;

import com.toy.namoner.common.exceptions.CommonBadRequestException;
import com.toy.namoner.common.exceptions.CommonResponseCode;
import com.toy.namoner.config.ImageService;
import com.toy.namoner.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoner.letter.controller.dto.response.LetterListResponse;
import com.toy.namoner.letter.controller.dto.response.LetterResponse;
import com.toy.namoner.letter.domain.Letter;
import com.toy.namoner.letter.domain.enums.LetterType;
import com.toy.namoner.letter.repository.LetterRepository;
import com.toy.namoner.user.domain.User;
import com.toy.namoner.user.repository.UserRepository;
import com.toy.namoner.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LetterServiceImpl implements LetterService {

    private final ImageService imageService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final LetterRepository letterRepository;

    @Override
    public void send(LetterSendRequest letterSendRequest, MultipartFile image) {
        User receiver = userService.findByUserId(letterSendRequest.getUserReceiver());

        String imageUrl = image == null || image.isEmpty() ? null : imageService.uploadFile(ImageService.LETTER_IMAGE_DIR, image);

        Letter letter = switch (letterSendRequest.getLetterType()) {
            case NORMAL -> Letter.createNormalLetterType(letterSendRequest, receiver, imageUrl);
            case RESERVED -> Letter.createReservedLetterType(letterSendRequest, receiver, imageUrl);
            default -> throw new CommonBadRequestException(CommonResponseCode.LETTER_TYPE_EXCEPTION);
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
                .collect(Collectors.toList());

        List<Letter> notReadLetters = letters.stream()
                .filter(letter -> !letter.getIsRead() && LetterType.NORMAL.equals(letter.getLetterType()))
                .sorted(Comparator.comparing(Letter::getReceiveDate).reversed())
                .collect(Collectors.toList());

        List<Letter> readLetters = letters.stream()
                .filter(letter -> letter.getIsRead())
                .sorted(Comparator.comparing(Letter::getReceiveDate).reversed())
                .collect(Collectors.toList());

        List<Letter> ret = new ArrayList<>();
        ret.addAll(notReadLetters);
        ret.addAll(reservedLetters);
        ret.addAll(readLetters);

        return ret;
    }

    @Override
    public Letter findById(String letterId) {
        Letter letter = letterRepository.findById(letterId).orElseThrow(() -> {
            String detailDescription = "Letter " + letterId + " not found";
            return new CommonBadRequestException(CommonResponseCode.ENTITY_NOT_FOUND, detailDescription);
        });

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
