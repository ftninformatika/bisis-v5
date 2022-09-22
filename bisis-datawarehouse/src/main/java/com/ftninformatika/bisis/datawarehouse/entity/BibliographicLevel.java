package com.ftninformatika.bisis.datawarehouse.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the bibliographic_level database table.
 * 
 */
@Entity
@Table(name="bibliographic_level")
@NamedQuery(name="BibliographicLevel.findAll", query="SELECT b FROM BibliographicLevel b")
public class BibliographicLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="bibliographic_level_id")
	private String bibliographicLevelId;

	private String description;

	private String library;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="bibliographicLevel")
	private Set<Item> items;

	//bi-directional many-to-one association to Lending
	@OneToMany(mappedBy="bibliographicLevel")
	private Set<Lending> lendings;

	public BibliographicLevel() {
	}

	public String getBibliographicLevelId() {
		return this.bibliographicLevelId;
	}

	public void setBibliographicLevelId(String bibliographicLevelId) {
		this.bibliographicLevelId = bibliographicLevelId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLibrary() {
		return this.library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public Item addItem(Item item) {
		getItems().add(item);
		item.setBibliographicLevel(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setBibliographicLevel(null);

		return item;
	}

	public Set<Lending> getLendings() {
		return this.lendings;
	}

	public void setLendings(Set<Lending> lendings) {
		this.lendings = lendings;
	}

	public Lending addLending(Lending lending) {
		getLendings().add(lending);
		lending.setBibliographicLevel(this);

		return lending;
	}

	public Lending removeLending(Lending lending) {
		getLendings().remove(lending);
		lending.setBibliographicLevel(null);

		return lending;
	}

}