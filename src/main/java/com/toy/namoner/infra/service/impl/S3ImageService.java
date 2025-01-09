package com.toy.namoner.infra.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.toy.namoner.common.AppEnvironment;
import com.toy.namoner.infra.service.ImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3ImageService implements ImageService {

	private final AppEnvironment env;
	private final AmazonS3 amazonS3;

	@Override
	public List<String> uploadFiles(String prefix, List<MultipartFile> multipartFiles) {
		ArrayList<String> fileNameList = new ArrayList<>();

		multipartFiles.forEach(file -> {
			String fileName = uploadFile(prefix, file);
			fileNameList.add(fileName);
		});

		return fileNameList;
	}

	@Override
	public String uploadFile(String prefix, MultipartFile multipartFile) {
		String fileName = prefix + "/" + createFileName(multipartFile.getOriginalFilename());
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(multipartFile.getSize());
		objectMetadata.setContentType(multipartFile.getContentType());

		try (InputStream inputStream = multipartFile.getInputStream()) {
			amazonS3.putObject(new PutObjectRequest(env.getBucket(), fileName, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
		}
		return fileName;
	}

	@Override
	public String getFileUrl(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			return null;
		}
		return "https://%s.s3.%s.amazonaws.com/%s".formatted(env.getBucket(), env.getRegion(), fileName);
	}

	private String createFileName(String fileName) {
		return UUID.randomUUID().toString().concat(getFileExtension(fileName));
	}

	private String getFileExtension(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf("."));
		} catch (StringIndexOutOfBoundsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일" + fileName + ") 입니다.");
		}
	}
}
