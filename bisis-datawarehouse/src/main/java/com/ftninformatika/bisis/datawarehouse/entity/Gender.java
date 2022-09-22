package com.ftninformatika.bisis.datawarehouse.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the gender database table.
 * 
 */
@Entity
@NamedQuery(name="Gender.findAll", query="SELECT g FROM Gender g")
public class Gender implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="gender_id")
	private Integer genderId;

	private String description;

	private String library;

	//bi-directional many-to-one association to Lending
	@OneToMany(mappedBy="gender")
	private Set<Lending> lendings;

	//bi-directional many-to-one association to Membership
	@OneToMany(mappedBy="gender")
	private Set<Membership> memberships;

	public Gender() {
	}

	public Integer getGenderId() {
		return this.genderId;
	}

	public void setGenderId(Integer genderId) {
		this.genderId = genderId;
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
		lending.setGender(this);

		return lending;
	}

	public Lending removeLending(Lending lending) {
		getLendings().remove(lending);
		lending.setGender(null);

		return lending;
	}

	public Set<Membership> getMemberships() {
		return this.memberships;
	}

	public void setMemberships(Set<Membership> memberships) {
		this.memberships = memberships;
	}

	public Membership addMembership(Membership membership) {
		getMemberships().add(membership);
		membership.setGender(this);

		return membership;
	}

	public Membership removeMembership(Membership membership) {
		getMemberships().remove(membership);
		membership.setGender(null);

		return membership;
	}

}