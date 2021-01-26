package com.ftninformatika.bisis.nabavka.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Book {
    private String isbn;
    private String author;
    private String title;
    private String publisher;
    private double price;
    private double rebate;
    private double vat;
}
