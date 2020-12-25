package com.ftninformatika.bisis.inventory;

public enum EnumInvLocation {
    LOCATION("location"),
    SUB_LOCATION("sublocation");

    private String text;

    EnumInvLocation(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
