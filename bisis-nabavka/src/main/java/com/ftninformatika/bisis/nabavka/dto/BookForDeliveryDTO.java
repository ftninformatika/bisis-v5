package com.ftninformatika.bisis.nabavka.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookForDeliveryDTO {
  private BookDTO book;
  private int amount;

}
