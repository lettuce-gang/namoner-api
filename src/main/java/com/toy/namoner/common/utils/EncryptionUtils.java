package com.toy.namoner.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

public class EncryptionUtils {
	public static String encrypt(String plainText) {
		if (StringUtils.isBlank(plainText)) {
			return plainText;
		}

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			byte[] encodedHash = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));

			// 바이트 배열을 16진수 문자열로 변환
			StringBuilder hexString = new StringBuilder();
			for (byte b : encodedHash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
