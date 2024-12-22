package com.toy.namoner.letter.service;

import com.toy.namoner.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoner.letter.controller.dto.response.LetterListResponse;
import com.toy.namoner.letter.controller.dto.response.LetterResponse;
import com.toy.namoner.letter.domain.Letter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LetterService {
    void send(LetterSendRequest letterSendRequest, MultipartFile image);

    List<LetterListResponse> findLettersByUserId(String phone);

    Letter findById(String letterId);
    LetterResponse getLetterResponseByLetterId(String letterId);
}
