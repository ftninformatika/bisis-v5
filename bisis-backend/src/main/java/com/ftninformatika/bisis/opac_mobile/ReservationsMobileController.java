package com.ftninformatika.bisis.opac_mobile;

import com.ftninformatika.bisis.mobile.ReservationMobileDTO;
import com.ftninformatika.bisis.opac.dto.ReservationDTO;
import com.ftninformatika.bisis.reservations.service.interfaces.OpacReservationsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marijakovacevic
 */

@RestController
@RequestMapping("/mobile/reservations")
public class ReservationsMobileController {
    @Autowired
    private OpacReservationsServiceInterface opacReservationsService;

    @GetMapping(value = "/active-reservations/{memberNo}")
    public ResponseEntity<?> getReservationsByUser(@RequestHeader("Library") String library,
                                                   @PathVariable("memberNo") String memberNo) {
        List<ReservationDTO> reservations = opacReservationsService.getReservationsByUser(library, memberNo);

        List<ReservationMobileDTO> reservationDTOS = new ArrayList<>();
        for (ReservationDTO r : reservations) {
            reservationDTOS.add(new ReservationMobileDTO(r));
        }

        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

}
