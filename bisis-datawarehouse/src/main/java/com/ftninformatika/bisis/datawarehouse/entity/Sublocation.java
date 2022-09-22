package com.ftninformatika.bisis.datawarehouse.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the sublocation database table.
 * 
 */
@Entity
@NamedQuery(name="Sublocation.findAll", query="SELECT s FROM Sublocation s")
public class Sublocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="sublocation_id")
	private String sublocationId;

	private String description;

	private String library;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="sublocation")
	private Set<Item> items;

	public Sublocation() {
	}

	public String getSublocationId() {
		return this.sublocationId;
	}

	public void setSublocationId(String sublocationId) {
		this.sublocationId = sublocationId;
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
		item.setSublocation(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setSublocation(null);

		return item;
	}

}