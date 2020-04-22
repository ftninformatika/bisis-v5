package com.ftninformatika.bisis.opac2.search;

public enum FilterType {
    LOCATION(0),
    PUB_TYPE(1),
    AUTHOR(2),
    LANGUAGE(3),
    PUB_YEAR(4),
    SUB_LOCATION(5),
    SUBJECT(6);

    private int val;

    FilterType(int val) { this.val = val;}

    public int getVal() {return val;}
}
