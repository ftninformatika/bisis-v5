package com.ftninformatika.bisis.inventory.service.implementations;

import lombok.Getter;

@Getter
public enum InventoryUnitEnum {
        OK(0,"ok"),
        BAD_INVENTORY(1, "bad inventory id"),
        BAD_INVNUM(2, "bad inventory number"),
        DUPLICATE(3, "duplicate inventory number"),
        SERVER_ERROR (4, "general server error");

        private final int code;
        private final String description;

    InventoryUnitEnum(int code, String description) {
            this.code = code;
            this.description = description;
        }
}
