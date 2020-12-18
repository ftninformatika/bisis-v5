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
            case "INV_NO_ASC": return new Sort(new Sort.Order(Sort.Direction.ASC, "invNo"));
            case "INV_NO_DESC": return new Sort(new Sort.Order(Sort.Direction.DESC, "invNo"));
            case "AUTHOR_ASC": return new Sort(new Sort.Order(Sort.Direction.ASC, "author"));
            case "AUTHOR_DESC": return new Sort(new Sort.Order(Sort.Direction.DESC, "author"));
            case "TITLE_ASC": return new Sort(new Sort.Order(Sort.Direction.ASC, "title"));
            case "TITLE_DESC": return new Sort(new Sort.Order(Sort.Direction.DESC, "title"));
            case "SIGNATURE_ASC": return new Sort(new Sort.Order(Sort.Direction.ASC, "signature"));
            case "SIGNATURE_DESC": return new Sort(new Sort.Order(Sort.Direction.DESC, "signature"));
            case "PUBLISHER_ASC": return new Sort(new Sort.Order(Sort.Direction.ASC, "publisher"));
            case "PUBLISHER_DESC": return new Sort(new Sort.Order(Sort.Direction.DESC, "publisher"));
            case "PUB_YEAR_ASC": return new Sort(new Sort.Order(Sort.Direction.ASC, "pubYear"));
            case "PUB_YEAR_DESC": return new Sort(new Sort.Order(Sort.Direction.DESC, "pubYear"));
            case "ITEM_STATUS_ASC": return new Sort(new Sort.Order(Sort.Direction.ASC, "itemStatus"));
            case "ITEM_STATUS_DESC": return new Sort(new Sort.Order(Sort.Direction.DESC, "itemStatus"));
            case "INVENTORY_STATUS_ASC": return new Sort(new Sort.Order(Sort.Direction.ASC, "inventoryStatus"));
            case "INVENTORY_STATUS_DESC": return new Sort(new Sort.Order(Sort.Direction.DESC, "inventoryStatus"));
            case "DATE_ASC": return new Sort(new Sort.Order(Sort.Direction.ASC, "date"));
            case "DATE_DESC": return new Sort(new Sort.Order(Sort.Direction.DESC, "date"));
            default: return null;
        }
    }

    @Override
    public String toString() {
        return this.text;
    }

}
