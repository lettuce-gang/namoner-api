package com.toy.namoneo.letter.service;

import com.toy.namoneo.config.ImageService;
import com.toy.namoneo.letter.controller.dto.LetterSendRequest;
import com.toy.namoneo.letter.domain.Letter;
import com.toy.namoneo.letter.repository.LetterRepository;
import com.toy.namoneo.user.domain.User;
import com.toy.namoneo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LetterServiceImpl implements LetterService {

    private final ImageService imageService;
    private final UserRepository userRepository;
    private final LetterRepository letterRepository;

    @Override
    public void send(LetterSendRequest letterSendRequest, MultipartFile image) {
        User recieveUser = userRepository.findByPhone(letterSendRequest.getToPhoneNumber()).orElseGet(User::craeteNotRegisteredUser);

        String imageUrl = imageService.uploadFile(ImageService.LETTER_IMAGE_DIR, image);

        Letter letter = Letter.from(letterSendRequest, recieveUser, imageUrl);

        letterRepository.save(letter);
    }
}
