package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="action", schema="bisis_reports", catalog="bisis")
public class Action extends Coder implements Serializable {
    private static final long serialVersionUID = 1L;

}
