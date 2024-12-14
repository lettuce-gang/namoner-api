package com.toy.namoneo.config;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    public static final String LETTER_IMAGE_DIR = "letter";

    List<String> uploadFiles(String prefix, List<MultipartFile> multipartFiles);

    String uploadFile(String prefix, MultipartFile multipartFile);

    String getFileUrl(String fileName);
}
