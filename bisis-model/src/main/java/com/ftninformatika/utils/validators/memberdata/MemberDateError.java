package com.ftninformatika.utils.validators.memberdata;

public enum MemberDateError {
    NO_ERROR(0, "MEMEBERS_DATE_NOERROR"),
    BIRTHDAY_DATE_ERROR(1, "BIRTHDAY_DATE_ERROR"),
    CORPORATE_MEMBER_SIGN_DATE_ERROR(2, "CORPORATE_MEMBER_SIGN_DATE_ERROR"),
    SIGNINGS_SIGN_DATE_ERROR(3, "SIGNINGS_SIGN_DATE_ERROR"),
    SIGNINGS_UNTIL_DATE_ERROR(4, "SIGNINGS_UNTIL_DATE_ERROR"),
    DUPLICATE_DATE_ERROR(5, "DUPLICATE_DATE_ERROR"),
    PICTUREBOOK_DATE_ERROR(6, "PICTUREBOOK_DATE_ERROR"),
    LENDING_LEND_DATE_ERROR(7, "LENDING_LEND_DATE_ERROR"),
    LENDING_RESUME_DATE_ERROR(8, "LENDING_RESUME_DATE_ERROR"),
    LENDING_RETURN_DATE_ERROR(9, "LENDING_RETURN_DATE_ERROR"),
    LENDING_DEADLINE_DATE_ERROR(10, "LENDING_DEADLINE_DATE_ERROR");

    private final int code;
    private final String message;

    private MemberDateError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessageKey() {
        return this.message;
    }
}
