package com.ftninformatika.bisis.opac.dto;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.reservations.ReservationInQueue;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import com.ftninformatika.utils.string.LatCyrUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO implements Serializable {
    private String _id;
    private String record_id;
    private String ctlgNo;
    private boolean isBookPickedUp;
    private Date reservationDate;
    private String title;
    private List<String> authors;
    private ReservationStatus reservationStatus;
    private Date pickUpDeadline;
    private String locationDescription;
    private String locationCode;
    private String memberFirstName;
    private String memberLastName;
    private String userId;
    private String phoneNumber;
    private boolean emailSent;

    public static ReservationDTO convertToDto(ReservationOnProfile reservation, Book book,
                                              String locationDescription) {
        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.set_id(reservation.get_id());
        reservationDTO.setRecord_id(reservation.getRecord_id());
        reservationDTO.setBookPickedUp(reservation.isBookPickedUp());
        reservationDTO.setReservationDate(reservation.getReservationDate());
        reservationDTO.setReservationStatus(reservation.getReservationStatus());
        reservationDTO.setPickUpDeadline(reservation.getPickUpDeadline());
        reservationDTO.setLocationDescription(locationDescription);
        reservationDTO.setTitle(book.getTitle());
        reservationDTO.setAuthors(getAuthors(book));

        return reservationDTO;
    }

    private static List<String> getAuthors(Book book) {
        List<String> authors = new ArrayList<>();
        for (String author : book.getAuthors()){
            authors.add(author);
        }
        return authors;
    }


    public static ReservationDTO convertFirstReservationToDto(ReservationInQueue reservation, String ctlgNo, Book book,
                                                              Member member, String locationCode) {
        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.set_id(reservation.get_id());
        reservationDTO.setRecord_id(book.get_id());
        reservationDTO.setCtlgNo(ctlgNo);
        reservationDTO.setReservationDate(reservation.getReservationDate());
        reservationDTO.setPickUpDeadline(reservation.getPickUpDeadline());
        reservationDTO.setMemberFirstName(member.getFirstName());
        reservationDTO.setMemberLastName(member.getLastName());
        reservationDTO.setTitle(book.getTitle());
        reservationDTO.setAuthors(book.getAuthors());
        reservationDTO.setLocationCode(locationCode);
        reservationDTO.setReservationStatus(ReservationStatus.WAITING_IN_QUEUE);
        reservationDTO.setUserId(member.getUserId());


        return reservationDTO;
    }
}
