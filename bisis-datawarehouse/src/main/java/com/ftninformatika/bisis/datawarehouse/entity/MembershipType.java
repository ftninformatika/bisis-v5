package com.ftninformatika.bisis.datawarehouse.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the membership_type database table.
 * 
 */
@Entity
@Table(name="membership_type")
@NamedQuery(name="MembershipType.findAll", query="SELECT m FROM MembershipType m")
public class MembershipType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="membership_type_id")
	private Integer membershipTypeId;

	private String description;

	private String library;

	//bi-directional many-to-one association to Lending
	@OneToMany(mappedBy="membershipType")
	private Set<Lending> lendings;

	//bi-directional many-to-one association to Membership
	@OneToMany(mappedBy="membershipType")
	private Set<Membership> memberships;

	public MembershipType() {
	}

	public Integer getMembershipTypeId() {
		return this.membershipTypeId;
	}

	public void setMembershipTypeId(Integer membershipTypeId) {
		this.membershipTypeId = membershipTypeId;
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
		lending.setMembershipType(this);

		return lending;
	}

	public Lending removeLending(Lending lending) {
		getLendings().remove(lending);
		lending.setMembershipType(null);

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
		membership.setMembershipType(this);

		return membership;
	}

	public Membership removeMembership(Membership membership) {
		getMemberships().remove(membership);
		membership.setMembershipType(null);

		return membership;
	}

}