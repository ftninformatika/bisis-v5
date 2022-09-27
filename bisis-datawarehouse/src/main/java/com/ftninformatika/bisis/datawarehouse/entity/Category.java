package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="category", schema="bisis_reports", catalog="bisis")
public class Category extends Coder implements Serializable {
	private static final long serialVersionUID = 1L;

}
