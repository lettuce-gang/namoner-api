package com.toy.namoner.infra.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public static final String LETTER_IMAGE_DIR = "letter";

    List<String> uploadFiles(String prefix, List<MultipartFile> multipartFiles);

    String uploadFile(String prefix, MultipartFile multipartFile);

    String getFileUrl(String fileName);
}
