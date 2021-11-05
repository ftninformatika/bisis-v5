package com.ftninformatika.bisis.library_configuration;

public enum EnumLocationLevel {
    LOCATION(1, "primerci.odeljenje"),
    SUB_LOCATION(2, "primerci.sigPodlokacija");

    private Integer level;
    private String primerakField;

    EnumLocationLevel(Integer level, String primerakField) {
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
