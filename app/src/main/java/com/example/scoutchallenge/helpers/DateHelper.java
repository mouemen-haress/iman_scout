package com.example.scoutchallenge.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateHelper {

    public static String convertMongoDate(String val) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            String finalStr = outputFormat.format(inputFormat.parse(val));
            return finalStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
