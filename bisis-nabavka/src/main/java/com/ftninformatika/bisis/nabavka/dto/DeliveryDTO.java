package com.ftninformatika.bisis.nabavka.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryDTO {
  private String location;
  private List<BookForDeliveryDTO> books = new ArrayList<>();


}

