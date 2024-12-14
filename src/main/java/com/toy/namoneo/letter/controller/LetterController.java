package com.toy.namoneo.letter.controller;

import com.toy.namoneo.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoneo.letter.controller.dto.response.LetterResponse;
import com.toy.namoneo.letter.service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/letters")
@RequiredArgsConstructor
public class LetterController {

    private final LetterService letterService;

    @PostMapping
    public ResponseEntity send(@RequestPart(name = "letterInfo") LetterSendRequest letterSendRequest, @RequestPart(required = false, name = "image") MultipartFile image) {
        letterService.send(letterSendRequest, image);
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<List<LetterResponse>> findLettersByPhone(@RequestParam("phone") String phone) {
        List<LetterResponse> letterResponses = letterService.findLettersByPhone(phone);

        return ResponseEntity.ok(letterResponses);
    }
}
