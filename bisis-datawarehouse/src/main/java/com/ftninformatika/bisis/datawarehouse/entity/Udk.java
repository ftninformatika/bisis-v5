package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;


/**
 * The persistent class for the udk database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="udk", schema = "bisis_reports", catalog = "bisis")
public class Udk extends Coder implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToMany(mappedBy = "udks")
	Set<Item> items;

	@ManyToMany(mappedBy = "udks")
	Set<Lending> lendings;

}
