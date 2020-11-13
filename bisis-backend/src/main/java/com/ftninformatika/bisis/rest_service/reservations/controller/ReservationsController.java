package com.ftninformatika.bisis.rest_service.reservations.controller;

import com.ftninformatika.bisis.circ.dto.ConfirmReservationDTO;
import com.ftninformatika.bisis.opac2.dto.ReservationDTO;
import com.ftninformatika.bisis.opac2.dto.ReservationRequestDTO;
import com.ftninformatika.bisis.opac2.dto.ReservationResponseDTO;
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.BisisReservationsServiceInterface;
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.CreateReservationServiceInterface;
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.OpacReservationsServiceInterface;
import com.ftninformatika.util.constants.ReservationsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author marijakovacevic
 */

@RestController
@RequestMapping("/reservations")
public class ReservationsController {
    @Autowired
    private CreateReservationServiceInterface createReservationService;

    @Autowired
    private OpacReservationsServiceInterface opacReservationsService;

    @Autowired
    private BisisReservationsServiceInterface bisisReservationsService;

    /**
     * This method reserves book for the logged user.
     * If this process was successful, method will return created reservation.
     *
     * @param library               the library in which the logged user is a member
     * @param authToken             user authentication token
     * @param reservationRequestDTO the reservation request contains the book ID to be reserved and
     *                              the library location ID
     * @return created reservation
     */
    @PostMapping("/reserve")
    public ResponseEntity<?> reserveBook(@RequestHeader("Library") String library,
                                         @RequestHeader("Authorization") String authToken,
                                         @RequestBody ReservationRequestDTO reservationRequestDTO) {

        Object reservation = createReservationService.reserveBook(authToken, library, reservationRequestDTO.getRecordId(),
                reservationRequestDTO.getCoderId());

        if (reservation != null) {
            if (reservation.equals(ReservationsConstants.NORESERVATION) || reservation.equals(ReservationsConstants.LIMITEXCEEDED)
                    || reservation.equals(ReservationsConstants.ALREADYRESERVED)) {
                return new ResponseEntity<>(new ReservationResponseDTO(reservation.toString()), HttpStatus.OK);
            }
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        // todo transakcije handler
//         Object reservation = null;
//
//            reservation = reservationsService.reserveBook(authToken, library, reservationRequestDTO.getRecordId(),
//                    reservationRequestDTO.getCoderId());
//
//            if (reservation.equals(ReservationsConstants.LIMITEXCEEDED)){
//                throw new ReservationNotAllowedException();
//            }else{
//                return new ResponseEntity<>(reservation, HttpStatus.OK);
//            }
//        } catch (ReservationNotAllowedException | ReservationsLimitExceededException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(reservation, HttpStatus.FORBIDDEN);
//        } catch (InvalidRequestException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(reservation, HttpStatus.BAD_REQUEST);
//        }
    }

    /**
     * Returns all reservations of the logged user.
     *
     * @param library   the library in which the logged user is a member
     * @param authToken user authentication token
     * @return list of all reservations
     */
    @GetMapping(value = "/active-reservations")
    public ResponseEntity<?> getReservationsByUser(@RequestHeader("Library") String library,
                                                   @RequestHeader("Authorization") String authToken) {
        List<ReservationDTO> reservationDTOS = opacReservationsService.getReservationsByUser(library, authToken);
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

    /**
     * Deletes reservation that has the specified reservation ID.
     * Returns true if reservation is successfully deleted.
     *
     * @param authToken     user authentication token
     * @param reservationId the reservation ID to be deleted
     * @return true if reservation is deleted, otherwise false
     */
    @RequestMapping(value = "/delete/{reservationId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteReservation(@RequestHeader("Authorization") String authToken,
                                               @PathVariable("reservationId") String reservationId) {

        Boolean reservationDeleted = opacReservationsService.deleteReservation(authToken, reservationId);

        if (reservationDeleted) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_MODIFIED);
        }
    }


    /**
     * Returns first reservations, from queues, of each returned book to the library.
     *
     * @param library       the library in which the librarian is logged in
     * @param returnedBooks list of ctlg numbers of the books that are returned to the library
     * @return list containing first reservation of each book that is returned
     */
    @PostMapping("/reservations-for-returned-books")
    public ResponseEntity<List<ReservationDTO>> getReservationsForReturnedBooks(@RequestHeader("Library") String library,
                                                                                @RequestBody List<String> returnedBooks) {
        List<ReservationDTO> reservationDTOS = bisisReservationsService.getReservationsForReturnedBooks(returnedBooks, library);
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

    /**
     * Confirms reservation and assigns book to member.
     * Changes status of the reservation in the list of all member's reservations.
     * Deletes reservation from the record's queue.
     *
     * @param confirmReservationDTO dto contains reservation's and record's ID
     * @return true if reservation is successfully confirmed and it's status is changed, otherwise returns false
     */
    @PostMapping("/confirm-reservation")
    public ResponseEntity<Boolean> confirmReservation(@RequestBody ConfirmReservationDTO confirmReservationDTO) {
        boolean isReservationConfirmed = bisisReservationsService.confirmReservation(confirmReservationDTO.getReservation_id(),
                confirmReservationDTO.getRecord_id(), confirmReservationDTO.getCtlgNo());
        return new ResponseEntity<>(isReservationConfirmed, HttpStatus.OK);
    }

}
