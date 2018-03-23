package com.example.ro_en.quicklib.utils;

/**
 * Created by RO_EN on 21.03.2018.
 */

public class Isbn10to13Converter {

    public static String ISBN10toISBN13( String ISBN10 ) {
        String ISBN13  = ISBN10;
        ISBN13 = "978" + ISBN13.substring(0,9);
        int d;
        int sum = 0;
        for (int i = 0; i < ISBN13.length(); i++) {
            d = ((i % 2 == 0) ? 1 : 3);
            sum += ((((int) ISBN13.charAt(i)) - 48) * d);
        }
        sum = 10 - (sum % 10);
        ISBN13 += sum;

        return ISBN13;
    }
}
