package com.ftninformatika.bisis.inventory;

public enum EnumInvLocation {
    LOCATION(1, "primerci.odeljenje"),
    SUB_LOCATION(2, "primerci.sigPodlokacija");

    private Integer level;
    private String primerakField;

    EnumInvLocation(Integer level, String primerakField) {
        this.level = level;
        this.primerakField = primerakField;
    }

    public Integer getLevel() {
        return this.level;
    }

    public String getPrimerakField() {
        return primerakField;
    }
}
