package com.ftninformatika.bisis.inventory;

public enum EnumInventoryState {
    IN_PREPARATION( "IN_PREPARATION"),
    IN_PROGRESS( "IN_PROGRESS"),
    FINISHED( "FINISHED");

    private String code;

    EnumInventoryState(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }

}
