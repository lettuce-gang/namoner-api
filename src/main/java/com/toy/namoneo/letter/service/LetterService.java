package com.toy.namoneo.letter.service;

import com.toy.namoneo.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoneo.letter.controller.dto.response.LetterResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LetterService {
    void send(LetterSendRequest letterSendRequest, MultipartFile image);

    List<LetterResponse> findLettersByPhone(String phone);
}
