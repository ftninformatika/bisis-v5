package com.ftninformatika.bisis.datawarehouse.model;

import com.ftninformatika.bisis.datawarehouse.entity.Coder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public class SelectedCoder {
    CoderDefinition coder;
    List<Coder> coderValues;
    Integer index;

    @Override
    public String toString() {
        List list = coderValues.stream().map(x->x.getDescription()).collect(Collectors.toList());
        String values = String.join(",", list);
        return coder.getDisplayName() +" = " + values;
    }
}
