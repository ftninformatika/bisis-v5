package com.ftninformatika.utils.validators.security;

/**
 * @author badf00d21  7.5.19.
 */
public class PasswordValidator {

    public static final String PASS_STRENGTH_REGEX = "(?=.*[0-9])(?=.*[a-z]).{6,}";

    /**
     *
     * @param plainPass
     * @return enum value for provided password if it satisfies minimal
     * password strength requirements: 6 chars, min 1 lowercase letter,
     * min 1 digit. STRONG_ENOUGH if satisfied, NOT_STRONG_ENOUGH if not.
     */
    public static PasswordCodes validatePasswordStrength(String plainPass) {
        if (plainPass.matches(PASS_STRENGTH_REGEX))
            return PasswordCodes.STRONG_ENOUGH;
        else
            return PasswordCodes.NOT_STRONG_ENOUGH;
    }

    /**
     * For validation if 2 pass fields matches.
     * @param pass0
     * @param pass1
     * @return PASSWORDS_MATCHED if true, PASSWORDS_DONT_MATCH if not.
     */
    public static PasswordCodes comparePasswords(String pass0, String pass1) {
        if (pass0 == null || pass0.equals("")
                || pass1 == null || pass1.equals(""))
            return PasswordCodes.PASSWORDS_NULL_OR_EMPTY;
        if (pass0.equals(pass1))
            return PasswordCodes.PASSWORDS_MATCHED;
        else
            return PasswordCodes.PASSWORDS_DONT_MATCH;
    }

}