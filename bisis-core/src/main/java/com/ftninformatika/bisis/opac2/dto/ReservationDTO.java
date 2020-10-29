package com.ftninformatika.bisis.opac2.dto;

import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO implements Serializable {
    private String _id;
    private String record_id;
    private boolean isBookPickedUp;
    private Date reservationDate;
    private String title;
    private List<String> authors;
    private ReservationStatus reservationStatus;
    private Date pickUpDeadline;
    private String location;

    public static ReservationDTO convertToDto(ReservationOnProfile reservation, Book book, String location){
        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.set_id(reservation.get_id());
        reservationDTO.setRecord_id(reservation.getRecord_id());
        reservationDTO.setBookPickedUp(reservation.isBookPickedUp());
        reservationDTO.setReservationDate(reservation.getReservationDate());
        reservationDTO.setReservationStatus(reservation.getReservationStatus());
        reservationDTO.setPickUpDeadline(reservation.getPickUpDeadline());
        reservationDTO.setLocation(location);
        reservationDTO.setTitle(book.getTitle());
        reservationDTO.setAuthors(book.getAuthors());

        return reservationDTO;
    }
}
