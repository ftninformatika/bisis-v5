package com.ftninformatika.bisis.inventory;

import org.springframework.data.domain.Sort;

public enum EnumSortByInvUnit {

    INV_NO_ASC("INV_NO_ASC"),
    INV_NO_DESC("INV_NO_DESC"),
    AUTHOR_ASC("AUTHOR_ASC"),
    AUTHOR_DESC("AUTHOR_DESC"),
    TITLE_ASC("TITLE_ASC"),
    TITLE_DESC("TITLE_DESC"),
    SIGNATURE_ASC("SIGNATURE_ASC"),
    SIGNATURE_DESC("SIGNATURE_DESC"),
    PUBLISHER_ASC("PUBLISHER_ASC"),
    PUBLISHER_DESC("PUBLISHER_DESC"),
    PUB_YEAR_ASC("PUB_YEAR_ASC"),
    PUB_YEAR_DESC("PUB_YEAR_DESC"),
    ITEM_STATUS_ASC("ITEM_STATUS_ASC"),
    ITEM_STATUS_DESC("ITEM_STATUS_DESC"),
    INVENTORY_STATUS_ASC("INVENTORY_STATUS_ASC"),
    INVENTORY_STATUS_DESC("INVENTORY_STATUS_DESC"),
    DATE_ASC("DATE_ASC"),
    DATE_DESC("DATE_DESC");


    private String text;

    EnumSortByInvUnit(String text) {
        this.text = text;
    }

    public Sort getSort() {
        switch (text) {
            case "INV_NO_ASC": return Sort.by(Sort.Direction.ASC, "invNo");
            case "INV_NO_DESC": return Sort.by(Sort.Direction.DESC, "invNo");
            case "AUTHOR_ASC": return Sort.by(Sort.Direction.ASC, "author");
            case "AUTHOR_DESC": return Sort.by(Sort.Direction.DESC, "author");
            case "TITLE_ASC": return Sort.by(Sort.Direction.ASC, "title");
            case "TITLE_DESC": return Sort.by(Sort.Direction.DESC, "title");
            case "SIGNATURE_ASC": return Sort.by(Sort.Direction.ASC, "signature");
            case "SIGNATURE_DESC": return Sort.by(Sort.Direction.DESC, "signature");
            case "PUBLISHER_ASC": return Sort.by(Sort.Direction.ASC, "publisher");
            case "PUBLISHER_DESC": return Sort.by(Sort.Direction.DESC, "publisher");
            case "PUB_YEAR_ASC": return Sort.by(Sort.Direction.ASC, "pubYear");
            case "PUB_YEAR_DESC": return Sort.by(Sort.Direction.DESC, "pubYear");
            case "ITEM_STATUS_ASC": return Sort.by(Sort.Direction.ASC, "itemStatusCoderId");
            case "ITEM_STATUS_DESC": return Sort.by(Sort.Direction.DESC, "itemStatusCoderId");
            case "INVENTORY_STATUS_ASC": return Sort.by(Sort.Direction.ASC, "inventoryStatusCoderId");
            case "INVENTORY_STATUS_DESC": return Sort.by(Sort.Direction.DESC, "inventoryStatusCoderId");
            case "DATE_ASC": return Sort.by(Sort.Direction.ASC, "date");
            case "DATE_DESC": return Sort.by(Sort.Direction.DESC, "date");
            default: return null;
        }
    }

    @Override
    public String toString() {
        return this.text;
    }

}
