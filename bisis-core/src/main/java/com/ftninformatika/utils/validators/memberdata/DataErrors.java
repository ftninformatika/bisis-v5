package com.ftninformatika.utils.validators.memberdata;

/**
 * @author badf00d21  5.7.19.
 */
public enum  DataErrors {
    EMAIL_FORMAT_VALID(1, "EMAIL_FORMAT_VALID"),
    EMAIL_FORMAT_INVALID(-1, "EMAIL_FORMAT_INVALID");

    private final int code;
    private final String message;

    private DataErrors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessageKey() {
        return this.message;
    }
}
