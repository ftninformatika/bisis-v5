package com.ftninformatika.bisis.datawarehouse.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the serial_type database table.
 * 
 */
@Entity
@Table(name="serial_type")
@NamedQuery(name="SerialType.findAll", query="SELECT s FROM SerialType s")
public class SerialType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="serial_type_id")
	private String serialTypeId;

	private String description;

	private String library;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="serialType")
	private Set<Item> items;

	//bi-directional many-to-one association to Lending
	@OneToMany(mappedBy="serialType")
	private Set<Lending> lendings;

	public SerialType() {
	}

	public String getSerialTypeId() {
		return this.serialTypeId;
	}

	public void setSerialTypeId(String serialTypeId) {
		this.serialTypeId = serialTypeId;
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
		item.setSerialType(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setSerialType(null);

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
		lending.setSerialType(this);

		return lending;
	}

	public Lending removeLending(Lending lending) {
		getLendings().remove(lending);
		lending.setSerialType(null);

		return lending;
	}

}