package com.toy.namoneo.letter.controller;

import com.toy.namoneo.letter.controller.dto.LetterSendRequest;
import com.toy.namoneo.letter.service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LetterController {

    private final LetterService letterService;

    @PostMapping
    public void send(@RequestBody LetterSendRequest letterSendRequest, MultipartFile image) {
        letterService.send(letterSendRequest, image);
    }
}
