package com.ftninformatika.bisis.circ;

public enum LendingDateOption {
    LEND_DATE("lendDate"),
    RESUME_DATE("returnDate"),
    RETURN_DATE("resumeDate"),
    DEADLINE("deadline");

    private String name;

    LendingDateOption(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
