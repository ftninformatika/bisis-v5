package com.ftninformatika.bisis.circ.wrappers;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberData {

    private Member member;
    private List<Lending> lendings;
    private List<ItemAvailability> books;
    private String inUseBy;
    private HashMap<String, String> booksToReserve;
    private List<ReservationOnProfile> reservationsToDelete;

}
