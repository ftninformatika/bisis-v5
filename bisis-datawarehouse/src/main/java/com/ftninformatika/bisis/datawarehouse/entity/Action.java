package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Action extends Coder implements Serializable {
    private static final long serialVersionUID = 1L;

}
