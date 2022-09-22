package com.ftninformatika.bisis.datawarehouse.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the udk database table.
 * 
 */
@Entity
@NamedQuery(name="Udk.findAll", query="SELECT u FROM Udk u")
public class Udk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="udk_id")
	private String udkId;

	private String description;

	private String library;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="udk")
	private Set<Item> items;

	//bi-directional many-to-one association to Lending
	@OneToMany(mappedBy="udk")
	private Set<Lending> lendings;

	public Udk() {
	}

	public String getUdkId() {
		return this.udkId;
	}

	public void setUdkId(String udkId) {
		this.udkId = udkId;
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
		item.setUdk(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setUdk(null);

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
		lending.setUdk(this);

		return lending;
	}

	public Lending removeLending(Lending lending) {
		getLendings().remove(lending);
		lending.setUdk(null);

		return lending;
	}

}