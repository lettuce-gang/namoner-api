package com.toy.namoner.common.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PhoneNumberUtilsTest {

    @Test
    public void isValidPhoneNumberTest() {
        String[] testCases = {
                "01011111111", "010-1111-1111", "+82-10-1111-1111", " 01011111111 ", "010 1111 1111"
        };

        boolean[] expectedResult = {
                true, true, true, false, false
        };

        for (int i = 0; i < testCases.length; i++) {
            Assertions.assertThat(PhoneNumberUtils.isValidPhoneNumber(testCases[i])).isEqualTo(expectedResult[i]);
        }
    }

    @Test
    public void convertPhoneNumberToNMNSpecTest() {
        String[] testCases = {
                "01011111111", "010-1111-1111", "+82-10-1111-1111"
        };

        String expectedResult = "01011111111";

        for (int i = 0; i < testCases.length; i++) {
            Assertions.assertThat(PhoneNumberUtils.convertPhoneNumberToNMNSpec(testCases[i])).isEqualTo(expectedResult);
        }

        String errorTC1 = " 01011111111 ";
        String errorTC2 = "010 1111 1111";
        Assertions.assertThatThrownBy(() -> PhoneNumberUtils.convertPhoneNumberToNMNSpec(errorTC1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> PhoneNumberUtils.convertPhoneNumberToNMNSpec(errorTC2)).isInstanceOf(IllegalArgumentException.class);

    }

}