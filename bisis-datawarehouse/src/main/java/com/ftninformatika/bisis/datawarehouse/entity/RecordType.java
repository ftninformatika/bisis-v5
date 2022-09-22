package com.ftninformatika.bisis.datawarehouse.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the record_type database table.
 * 
 */
@Entity
@Table(name="record_type")
@NamedQuery(name="RecordType.findAll", query="SELECT r FROM RecordType r")
public class RecordType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="record_type_id")
	private String recordTypeId;

	private String description;

	private String library;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="recordType")
	private Set<Item> items;

	//bi-directional many-to-one association to Lending
	@OneToMany(mappedBy="recordType")
	private Set<Lending> lendings;

	public RecordType() {
	}

	public String getRecordTypeId() {
		return this.recordTypeId;
	}

	public void setRecordTypeId(String recordTypeId) {
		this.recordTypeId = recordTypeId;
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
		item.setRecordType(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setRecordType(null);

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
		lending.setRecordType(this);

		return lending;
	}

	public Lending removeLending(Lending lending) {
		getLendings().remove(lending);
		lending.setRecordType(null);

		return lending;
	}

}