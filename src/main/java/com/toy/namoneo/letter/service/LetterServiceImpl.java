package com.toy.namoneo.letter.service;

import com.toy.namoneo.common.exceptions.CommonBadRequestException;
import com.toy.namoneo.common.exceptions.CommonResponseCode;
import com.toy.namoneo.config.ImageService;
import com.toy.namoneo.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoneo.letter.controller.dto.response.LetterListResponse;
import com.toy.namoneo.letter.controller.dto.response.LetterResponse;
import com.toy.namoneo.letter.domain.Letter;
import com.toy.namoneo.letter.repository.LetterRepository;
import com.toy.namoneo.user.domain.User;
import com.toy.namoneo.user.repository.UserRepository;
import com.toy.namoneo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LetterServiceImpl implements LetterService {

    private final ImageService imageService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final LetterRepository letterRepository;

    @Override
    @Transactional
    public void send(LetterSendRequest letterSendRequest, MultipartFile image) {
//        User recieveUser = userService.findOrCreateByPhoneNumber(letterSendRequest.getReceiverPhoneNumber());

        User receiver = userService.findByUserId(letterSendRequest.getUserReceiver());

        String imageUrl = image == null || image.isEmpty() ? null : imageService.uploadFile(ImageService.LETTER_IMAGE_DIR, image);

        Letter letter = Letter.createBySend(letterSendRequest, receiver, imageUrl);

        letterRepository.save(letter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LetterListResponse> findLettersByUserId(String userId) {
        User user = userService.findByUserId(userId);

        return user.getReceiveLetters().stream().map(LetterListResponse::from).collect(Collectors.toList());
    }

    @Override
    public Letter findById(String letterId) {
        return letterRepository.findById(letterId).orElseThrow(() -> {
            String detailDescription = "Letter " + letterId + " not found";
            return new CommonBadRequestException(CommonResponseCode.ENTITY_NOT_FOUND, detailDescription);
        });
    }


    @Override
    public LetterResponse getLetterResponseByLetterId(String letterId) {
        Letter letter = findById(letterId);

        String imageUrl = imageService.getFileUrl(letter.getImageUrl());

        return LetterResponse.from(letter, imageUrl);
    }

}
