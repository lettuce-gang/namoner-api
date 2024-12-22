package com.toy.namoner.common.utils;

import io.micrometer.common.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberUtils {
    private static final String PHONE_NUMBER_REGEX =
            "^(010)(\\d{4})(\\d{4})$" +                // 01011111111
                    "|^(010)-(\\d{4})-(\\d{4})$" +             // 010-1111-1111
                    "|^\\+82-10-(\\d{4})-(\\d{4})$";           // +82-10-1111-1111
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    public static boolean isValidPhoneNumber(String number) {
        if (StringUtils.isEmpty(number)) return false;

        return PHONE_NUMBER_PATTERN.matcher(number).matches();
    }

    public static String convertPhoneNumberToNMNSpec(String number) {
        if (!isValidPhoneNumber(number))
            throw new IllegalArgumentException("Invalid phone number format: " + number);

        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(number);

        if (matcher.matches()) {
            if (matcher.group(1) != null && matcher.group(2) != null && matcher.group(3) != null) {
                // 01011111111 형태
                return matcher.group(1) + matcher.group(2) + matcher.group(3);
            } else if (matcher.group(4) != null && matcher.group(5) != null && matcher.group(6) != null) {
                // 010-1111-1111 형태
                return matcher.group(4) + matcher.group(5) + matcher.group(6);
            } else if (matcher.group(7) != null && matcher.group(8) != null) {
                // +82-10-1111-1111 형태
                return "010" + matcher.group(7) + matcher.group(8);
            }
        }

        throw new IllegalStateException("Unexpected phone number format: " + number);
    }
}
