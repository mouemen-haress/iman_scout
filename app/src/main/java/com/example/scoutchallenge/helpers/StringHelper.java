package com.example.scoutchallenge.helpers;

public class StringHelper {

    public static boolean isNullOrEmpty(final String string) {
        return string == null || "".equals(string) || "null".equalsIgnoreCase(string);
    }
}
