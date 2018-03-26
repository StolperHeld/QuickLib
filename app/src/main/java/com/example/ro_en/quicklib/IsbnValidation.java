package com.example.ro_en.quicklib;

/**
 * Created by thoma on 20.03.2018.
 */

public class IsbnValidation {

    public boolean validateIsbn(String isbnAsString) {
        //remove any hyphens
        isbnAsString = isbnAsString.replaceAll("-", "");
        if (isbnAsString == null) {
            return false;
        } else if (isbnAsString.length() != 10 && isbnAsString.length() != 13) {
            return false;
        } else if (isbnAsString.length() == 10) {
            try {
                int tot = 0;
                for (int i = 0; i < 9; i++) {
                    int digit = Integer.parseInt(isbnAsString.substring(i, i + 1));
                    tot += ((10 - i) * digit);
                }

                String checksum = Integer.toString((11 - (tot % 11)) % 11);
                if ("10".equals(checksum)) {
                    checksum = "X";
                }

                return checksum.equals(isbnAsString.substring(9));
            } catch (NumberFormatException nfe) {
                //to catch invalid ISBNs that have non-numeric characters in them
                return false;
            }
        } else if (isbnAsString.length() == 13) {
            try {
                int tot = 0;
                for (int i = 0; i < 12; i++) {
                    int digit = Integer.parseInt(isbnAsString.substring(i, i + 1));
                    tot += (i % 2 == 0) ? digit * 1 : digit * 3;
                }

                //checksum must be 0-9. If calculated as 10 then = 0
                int checksum = 10 - (tot % 10);
                if (checksum == 10) {
                    checksum = 0;
                }

                return checksum == Integer.parseInt(isbnAsString.substring(12));
            } catch (NumberFormatException nfe) {
                //to catch invalid ISBNs that have non-numeric characters in them
                return false;
            }
        }
        return true;
    }
}
