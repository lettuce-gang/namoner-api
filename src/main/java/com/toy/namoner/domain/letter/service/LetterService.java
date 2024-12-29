package com.toy.namoner.domain.letter.service;

import com.toy.namoner.domain.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoner.domain.letter.controller.dto.response.LetterListResponse;
import com.toy.namoner.domain.letter.controller.dto.response.LetterResponse;
import com.toy.namoner.domain.letter.model.Letter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LetterService {
    void send(LetterSendRequest letterSendRequest, MultipartFile image);

    List<LetterListResponse> findLettersByUserId(String phone);

    Letter findById(String letterId);
    LetterResponse getLetterResponseByLetterId(String letterId);
}
