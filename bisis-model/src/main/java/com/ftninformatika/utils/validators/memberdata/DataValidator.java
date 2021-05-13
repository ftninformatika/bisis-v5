package com.ftninformatika.utils.validators.memberdata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author badf00d21  5.7.19.
 */
public class DataValidator {

    public static String EMAIL_VALIDITY_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public static DataErrors validateEmail(String email) {
        if (email == null) return DataErrors.EMAIL_FORMAT_INVALID;
        Pattern pattern = Pattern.compile(EMAIL_VALIDITY_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) return DataErrors.EMAIL_FORMAT_INVALID;
        return DataErrors.EMAIL_FORMAT_VALID;
    }
}
