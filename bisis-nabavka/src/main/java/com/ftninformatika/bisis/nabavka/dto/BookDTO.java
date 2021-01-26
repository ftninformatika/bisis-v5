package com.ftninformatika.bisis.nabavka.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookDTO {

  private String title;
  private String author;
  private double price;
  private String publisher;

}
