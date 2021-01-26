package com.ftninformatika.bisis.nabavka.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReportItemDTO {
    String library;
    int amount;
    double price;
}


