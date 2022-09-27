package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the acquisition database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="acquisition", schema="bisis_reports", catalog="bisis")

public class Acquisition extends Coder implements Serializable {
	private static final long serialVersionUID = 1L;


}
