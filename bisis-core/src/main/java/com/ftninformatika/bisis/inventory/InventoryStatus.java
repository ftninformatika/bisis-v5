package com.ftninformatika.bisis.inventory;

public enum InventoryStatus {
    IN_PREPARATION( 0),
    IN_PROGRESS( 1),
    FINISHED( 2);

    private int code;

    InventoryStatus(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code + "";
    }

}
