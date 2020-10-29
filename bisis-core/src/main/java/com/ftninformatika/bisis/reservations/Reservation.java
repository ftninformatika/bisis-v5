package com.ftninformatika.bisis.reservations;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    private String _id;
    private Date reservationDate;
    private Date pickUpDeadline ;
    private String coderId;          // location code
}
