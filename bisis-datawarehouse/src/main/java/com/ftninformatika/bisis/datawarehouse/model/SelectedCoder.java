package com.ftninformatika.bisis.datawarehouse.model;

import com.ftninformatika.bisis.datawarehouse.entity.Coder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class SelectedCoder {
    CoderDefinition coder;
    List<Coder> coderValues;
    Integer index;
}
