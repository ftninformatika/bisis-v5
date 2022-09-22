package com.ftninformatika.bisis.datawarehouse.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the lending_action database table.
 * 
 */
@Entity
@Table(name="lending_action")
@NamedQuery(name="LendingAction.findAll", query="SELECT l FROM LendingAction l")
public class LendingAction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="action_id")
	private Integer actionId;

	private String description;

	private String library;

	//bi-directional many-to-one association to Lending
	@OneToMany(mappedBy="lendingAction")
	private Set<Lending> lendings;

	public LendingAction() {
	}

	public Integer getActionId() {
		return this.actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
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

	public Set<Lending> getLendings() {
		return this.lendings;
	}

	public void setLendings(Set<Lending> lendings) {
		this.lendings = lendings;
	}

	public Lending addLending(Lending lending) {
		getLendings().add(lending);
		lending.setLendingAction(this);

		return lending;
	}

	public Lending removeLending(Lending lending) {
		getLendings().remove(lending);
		lending.setLendingAction(null);

		return lending;
	}

}