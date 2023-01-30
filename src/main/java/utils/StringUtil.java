package utils;

import org.apache.commons.lang3.RandomStringUtils;

public class StringUtil {
    public static String getRandomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String removeSquareBrackets(String str) {
        return str.replaceAll("\\[", "").replaceAll("]","");
    }
}
