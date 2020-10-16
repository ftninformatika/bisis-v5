package com.ftninformatika.bisis.reservations;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_reservations")
public class Reservations {
    @Id
    private String _id;
    private String ctlgNo;
    private LinkedList<Reservation> reservations = new LinkedList<>();
}
