package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the serial_type database table.
 * 
 */
@Entity
@Table(name="serial_type", schema = "bisis_reports", catalog = "bisis")
@Getter
@Setter
@NoArgsConstructor
public class SerialType extends Coder implements Serializable {
	private static final long serialVersionUID = 1L;

}
