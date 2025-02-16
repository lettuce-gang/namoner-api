package com.toy.namoner.domain.letter.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.toy.namoner.common.handler.NamonerResponse;
import com.toy.namoner.common.jwt.NMNAuthentication;
import com.toy.namoner.domain.auth.role.UserAuth;
import com.toy.namoner.domain.letter.controller.dto.request.LetterReplyRequest;
import com.toy.namoner.domain.letter.controller.dto.request.LetterSendRequest;
import com.toy.namoner.domain.letter.controller.dto.response.LetterListResponse;
import com.toy.namoner.domain.letter.controller.dto.response.LetterResponse;
import com.toy.namoner.domain.letter.service.LetterService;

import jakarta.validation.Valid;
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
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void send(
			NMNAuthentication authentication,
			@Valid @RequestPart(name = "letterInfo") LetterSendRequest letterSendRequest,
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
	@UserAuth
	public List<LetterListResponse> findLettersByUserId(NMNAuthentication authentication, @RequestParam("userId") String userId) {
		return letterService.findLettersByUserId(authentication, userId);
	}

	/**
	 * 내가 보낸 편지함 조회
	 *
	 * @return 편지함 목록
	 */
	@NamonerResponse
	@GetMapping("/my")
	@UserAuth
	public List<LetterListResponse> findSendLetters(NMNAuthentication authentication) {
		return letterService.findSendLetters(authentication);
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

	/**
	 * 편지 답장
	 *
	 * @param originLetterId       원본 편지 ID
	 * @param replyLetterRequest 답장 편지 정보
	 * @param image                첨부할 이미지
	 */
	@NamonerResponse
	@UserAuth
	@PostMapping(path = "/{letterId}/reply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void replyLetter(
			NMNAuthentication authentication,
			@PathVariable("letterId") String originLetterId,
			@RequestPart(name = "letterInfo") LetterReplyRequest replyLetterRequest,
			@RequestPart(required = false, name = "image") MultipartFile image) {
		letterService.reply(authentication.getUserId(), originLetterId, replyLetterRequest, image);
	}
}
