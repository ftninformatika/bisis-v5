package com.ftninformatika.bisis.datawarehouse.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the acquisition database table.
 * 
 */
@Entity
@NamedQuery(name="Acquisition.findAll", query="SELECT a FROM Acquisition a")
public class Acquisition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="acquisition_id")
	private String acquisitionId;

	private Integer description;

	private String library;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="acquisition")
	private Set<Item> items;

	public Acquisition() {
	}

	public String getAcquisitionId() {
		return this.acquisitionId;
	}

	public void setAcquisitionId(String acquisitionId) {
		this.acquisitionId = acquisitionId;
	}

	public Integer getDescription() {
		return this.description;
	}

	public void setDescription(Integer description) {
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
		item.setAcquisition(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setAcquisition(null);

		return item;
	}

}