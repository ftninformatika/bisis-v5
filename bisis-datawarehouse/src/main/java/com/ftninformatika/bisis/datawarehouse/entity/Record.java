package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


/**
 * The persistent class for the record database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="record", schema = "bisis_reports", catalog = "bisis")
@AttributeOverride(name = "description", column = @Column(name = "title", insertable = false,updatable = false))

public class Record extends Coder implements Serializable {
	private static final long serialVersionUID = 1L;

	private String rn;

	private String author;

	@Column(name="publication_year")
	private String publicationYear;

	private String publisher;

	private String title;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="record")
	private Set<Item> items;

}
