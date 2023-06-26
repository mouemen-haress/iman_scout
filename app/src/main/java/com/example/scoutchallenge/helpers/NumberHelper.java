package com.example.scoutchallenge.helpers;

public class NumberHelper {

    public static int parseStringToNumber(String value) {
        String str = value;
        try {
            int number = Integer.parseInt(str);
            return number;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return -1;
        }
    }
}
