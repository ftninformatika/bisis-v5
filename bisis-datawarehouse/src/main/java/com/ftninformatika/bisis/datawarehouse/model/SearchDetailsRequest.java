package com.ftninformatika.bisis.datawarehouse.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDetailsRequest extends SearchRequest{
    int page;
    int size;
}
