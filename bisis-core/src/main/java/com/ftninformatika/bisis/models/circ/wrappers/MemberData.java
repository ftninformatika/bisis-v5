package com.ftninformatika.bisis.models.circ.wrappers;

import com.ftninformatika.bisis.models.circ.Lending;
import com.ftninformatika.bisis.models.circ.Member;
import com.ftninformatika.bisis.records.ItemAvailability;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

}
