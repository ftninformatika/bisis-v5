package com.ftninformatika.bisis.circ.wrappers;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.WarningCounter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarningsData {

    private List<Lending> lendings;
    private List<WarningCounter> counters;

}
