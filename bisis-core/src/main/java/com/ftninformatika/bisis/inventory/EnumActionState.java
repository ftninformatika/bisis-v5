package com.ftninformatika.bisis.inventory;

public enum EnumActionState {
    GENERATING_UNITS( "GENERATING_UNITS"),
    STATUS_CHANGING( "STATUS_CHANGING"),
    LENDING_FINDING( "LENDING_FINDING"),
    STATUS_MAPPING_TO_BISIS( "STATUS_MAPPING_TO_BISIS"),
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
