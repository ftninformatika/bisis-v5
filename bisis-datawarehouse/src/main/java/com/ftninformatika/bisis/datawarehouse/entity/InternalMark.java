package com.ftninformatika.bisis.datawarehouse.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


/**
 * The persistent class for the internal_mark database table.
 * 
 */
@Entity
@Table(name="internal_mark", schema = "bisis_reports")
@NamedQuery(name="InternalMark.findAll", query="SELECT i FROM InternalMark i")
public class InternalMark implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="internal_mark_id")
	private String internalMarkId;

	private String description;

	private String library;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="internalMark")
	private Set<Item> items;

	public InternalMark() {
	}

	public String getInternalMarkId() {
		return this.internalMarkId;
	}

	public void setInternalMarkId(String internalMarkId) {
		this.internalMarkId = internalMarkId;
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
		item.setInternalMark(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setInternalMark(null);

		return item;
	}

}
