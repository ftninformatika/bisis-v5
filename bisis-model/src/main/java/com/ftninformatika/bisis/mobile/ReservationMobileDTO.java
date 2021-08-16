package com.ftninformatika.bisis.mobile;

import com.ftninformatika.bisis.reservations.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author marijakovacevic
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationMobileDTO {
    String _id;
    String record_id;
    String ctlgNo;
    Date reservationDate;
    String title;
    List<String> authors;
    ReservationStatus reservationStatus;
    Date pickUpDeadline;
    String locationDescription;

    public ReservationMobileDTO(com.ftninformatika.bisis.opac.dto.ReservationDTO r){
        this._id = r.get_id();
        this.record_id = r.getRecord_id();
        this.ctlgNo = r.getCtlgNo();
        this.reservationDate = r.getReservationDate();
        this.title = r.getTitle();
        this.authors = r.getAuthors();
        this.reservationStatus = r.getReservationStatus();
        this.pickUpDeadline = r.getPickUpDeadline();
        this.locationDescription = r.getLocationDescription();
    }
}
