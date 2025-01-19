package com.toy.namoner.domain.letter.controller;

import java.security.Principal;
import java.util.List;

import com.toy.namoner.common.jwt.NMNAuthentication;
import com.toy.namoner.domain.auth.role.UserAuth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.toy.namoner.common.handler.NamonerResponse;
import com.toy.namoner.domain.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoner.domain.letter.controller.dto.response.LetterListResponse;
import com.toy.namoner.domain.letter.controller.dto.response.LetterResponse;
import com.toy.namoner.domain.letter.service.LetterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/letters")
@RequiredArgsConstructor
public class LetterController {

	private final LetterService letterService;

	/**
	 * 편지 보내기
	 *
	 * @param letterSendRequest 편지 정보
	 * @param image             첨부할 이미지
	 */
	@NamonerResponse
	@PostMapping
	public void send(
			NMNAuthentication authentication,
			@RequestPart(name = "letterInfo") LetterSendRequest letterSendRequest,
			@RequestPart(required = false, name = "image") MultipartFile image) {
		letterService.send(authentication, letterSendRequest, image);
	}

	/**
	 * 편지함 조회
	 *
	 * @param userId 사용자 ID
	 * @return 편지함 목록
	 */
	@NamonerResponse
	@GetMapping
	public List<LetterListResponse> findLettersByUserId(@RequestParam("userId") String userId) {
		return letterService.findLettersByUserId(userId);
	}

	/**
	 * 편지 단건 조회
	 *
	 * @param letterId 편지 ID
	 * @return 편지 정보
	 */
	@NamonerResponse
	@UserAuth
	@GetMapping("/{letterId}")
	public LetterResponse findByLetterId(
			NMNAuthentication authentication,
			@PathVariable("letterId") String letterId) {
		return letterService.getLetterResponseByLetterId(authentication.getUserId(), letterId);
	}
}
