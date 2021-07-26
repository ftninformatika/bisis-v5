package com.ftninformatika.utils.validators.security;

/**
 * @author badf00d21  7.5.19.
 */
public enum PasswordCodes {

    STRONG_ENOUGH(0, "PASSWORD_STRENGTH_SATISFIED"),
    NOT_STRONG_ENOUGH(1, "PASSWORD_STRENGTH_NOT_SATISFIED"),
    PASSWORDS_MATCHED(2, "PASSWORDS_MATCHED"),
    PASSWORDS_DONT_MATCH(3, "PASSWORDS_DONT_MATCH"),
    PASSWORDS_NULL_OR_EMPTY(4, "PASSWORDS_NULL_OR_EMPTY");

    private final int code;
    private final String message;

    private PasswordCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

}