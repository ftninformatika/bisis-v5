package com.ftninformatika.bisis.acquisition.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Price {
    private double price;
    private double rebate;
    private double vat;
}
