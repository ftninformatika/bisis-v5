package com.ftninformatika.bisis.inventory;

public enum EnumActionState {
    GENERATING_UNITS( "GENERATING_UNITS"),
    STATUS_CHANGING( "STATUS_CHANGING"),
    LENDING_FINDING( "LENDING_FINDING"),
    NONE("NONE");

    private String code;

    EnumActionState(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
