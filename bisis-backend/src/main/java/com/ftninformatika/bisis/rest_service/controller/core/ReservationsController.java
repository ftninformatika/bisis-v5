package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.opac2.dto.ReservationRequestDTO;
import com.ftninformatika.bisis.rest_service.service.implementations.ReservationsService;
import com.ftninformatika.bisis.rest_service.service.interfaces.ReservationsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {
    @Autowired
    private ReservationsServiceInterface reservationsService;

    /**
     * This method reserves book for the logged user.
     * If this process was successful, method will return created reservation.
     *
     * @param library the library in which the logged in user is a member
     * @param authToken user authentication token
     * @param reservationRequestDTO the reservation request contains the book ID to be reserved and
     *                              the library location ID
     * @return created reservation
     */
    @PostMapping("/reserve")
    public ResponseEntity<Object> reserveBook(@RequestHeader("Library") String library,
                                                   @RequestHeader("Authorization") String authToken,
                                                   @RequestBody ReservationRequestDTO reservationRequestDTO) {
        Object reservation = reservationsService.reserveBook(authToken, library, reservationRequestDTO.getRecordId(),
                reservationRequestDTO.getCoderId());
        if (reservation == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(reservation, HttpStatus.OK);
    }
}
