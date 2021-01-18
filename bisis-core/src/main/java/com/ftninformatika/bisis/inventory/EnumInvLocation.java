package com.ftninformatika.bisis.inventory;

public enum EnumInvLocation {
    LOCATION("location", "primerci.odeljenje"),
    SUB_LOCATION("sublocation", "primerci.sigPodlokacija");

    private String text;
    private String primerakField;

    EnumInvLocation(String text, String primerakField) {
        this.text = text;
        this.primerakField = primerakField;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public String getPrimerakField() {
        return primerakField;
    }
}
