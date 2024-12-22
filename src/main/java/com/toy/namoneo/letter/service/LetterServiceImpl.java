package com.toy.namoneo.letter.service;

import com.toy.namoneo.config.ImageService;
import com.toy.namoneo.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoneo.letter.controller.dto.response.LetterListResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        User recieveUser = userService.findByPhoneNumber(letterSendRequest.getReceiverPhoneNumber()).orElseGet(() -> userService.craeteNotRegisteredUser(letterSendRequest.getReceiverPhoneNumber()));

        String imageUrl = image == null || image.isEmpty() ? null : imageService.uploadFile(ImageService.LETTER_IMAGE_DIR, image);

        Letter letter = Letter.from(letterSendRequest, recieveUser, imageUrl);

        letterRepository.save(letter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LetterListResponse> findLettersByPhone(String phoneNumber) {
        Optional<User> byPhone = userService.findByPhoneNumber(phoneNumber);

        if (byPhone.isEmpty()) {
            return new ArrayList<>();
        }

        User user = byPhone.get();

        return user.getReceiveLetters().stream().map(LetterListResponse::from).collect(Collectors.toList());
    }

}
