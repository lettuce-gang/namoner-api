package com.toy.namoner.letter.controller;

import com.toy.namoner.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoner.letter.controller.dto.response.LetterListResponse;
import com.toy.namoner.letter.controller.dto.response.LetterResponse;
import com.toy.namoner.letter.service.LetterService;
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
    public ResponseEntity<List<LetterListResponse>> findLettersByUserId(@RequestParam("userId") String userId) {
        List<LetterListResponse> letterListResponses = letterService.findLettersByUserId(userId);

        return ResponseEntity.ok(letterListResponses);
    }

    @GetMapping("/{letterId}")
    public ResponseEntity<LetterResponse> findByLetterId(@PathVariable("letterId") String letterId) {
        LetterResponse letterResponse = letterService.getLetterResponseByLetterId(letterId);

        return ResponseEntity.ok(letterResponse);
    }
}
