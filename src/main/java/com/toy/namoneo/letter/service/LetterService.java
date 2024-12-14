package com.toy.namoneo.letter.service;

import com.toy.namoneo.letter.controller.dto.LetterSendRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LetterService {
    void send(LetterSendRequest letterSendRequest, MultipartFile image);
}
