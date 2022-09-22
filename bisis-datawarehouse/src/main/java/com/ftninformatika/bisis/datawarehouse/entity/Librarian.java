package com.ftninformatika.bisis.datawarehouse.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the librarian database table.
 * 
 */
@Entity
@NamedQuery(name="Librarian.findAll", query="SELECT l FROM Librarian l")
public class Librarian implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="librarian_id")
	private String librarianId;

	private String description;

	private String library;

	//bi-directional many-to-one association to Lending
	@OneToMany(mappedBy="librarian")
	private Set<Lending> lendings;

	//bi-directional many-to-one association to Membership
	@OneToMany(mappedBy="librarian")
	private Set<Membership> memberships;

	public Librarian() {
	}

	public String getLibrarianId() {
		return this.librarianId;
	}

	public void setLibrarianId(String librarianId) {
		this.librarianId = librarianId;
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
		lending.setLibrarian(this);

		return lending;
	}

	public Lending removeLending(Lending lending) {
		getLendings().remove(lending);
		lending.setLibrarian(null);

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
		membership.setLibrarian(this);

		return membership;
	}

	public Membership removeMembership(Membership membership) {
		getMemberships().remove(membership);
		membership.setLibrarian(null);

		return membership;
	}

}