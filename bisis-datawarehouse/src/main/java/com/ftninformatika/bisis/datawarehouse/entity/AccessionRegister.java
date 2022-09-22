package com.ftninformatika.bisis.datawarehouse.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


/**
 * The persistent class for the accession_register database table.
 * 
 */
@Entity
@Table(name="accession_register")
@NamedQuery(name="AccessionRegister.findAll", query="SELECT a FROM AccessionRegister a")
public class AccessionRegister implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="accession_register_id")
	private String accessionRegisterId;

	private String description;

	private String library;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="accessionRegister")
	private Set<Item> items;

	//bi-directional many-to-one association to Lending
	@OneToMany(mappedBy="accessionRegister")
	private Set<Lending> lendings;

	public AccessionRegister() {
	}

	public String getAccessionRegisterId() {
		return this.accessionRegisterId;
	}

	public void setAccessionRegisterId(String accessionRegisterId) {
		this.accessionRegisterId = accessionRegisterId;
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
		item.setAccessionRegister(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setAccessionRegister(null);

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
		lending.setAccessionRegister(this);

		return lending;
	}

	public Lending removeLending(Lending lending) {
		getLendings().remove(lending);
		lending.setAccessionRegister(null);

		return lending;
	}

}
