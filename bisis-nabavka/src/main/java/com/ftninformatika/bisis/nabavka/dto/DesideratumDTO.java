package com.ftninformatika.bisis.nabavka.dto;

import com.ftninformatika.bisis.nabavka.model.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DesideratumDTO {

    private String isbn;
    private String author;
    private String title;
    private String publisher;
    private int amountDelivered;
    private boolean deliveryConfirmed;
    private List<Location> locations;
}
