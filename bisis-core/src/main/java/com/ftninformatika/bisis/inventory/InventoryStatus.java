package com.ftninformatika.bisis.inventory;

public enum InventoryStatus {
    IN_PREPARATION( "IN_PREPARATION"),
    IN_PROGRESS( "IN_PROGRESS"),
    FINISHED( "FINISHED");

    private String code;

    InventoryStatus(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }

}
