package com.ftninformatika.bisis.datawarehouse.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the membership database table.
 * 
 */
@Entity
@NamedQuery(name="Membership.findAll", query="SELECT m FROM Membership m")
public class Membership implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MEMBERSHIP_MEMBERSHIPID_GENERATOR", sequenceName="MEMBERSHIP_MEMBERSHIP_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MEMBERSHIP_MEMBERSHIPID_GENERATOR")
	@Column(name="membership_id")
	private Integer membershipId;

	private Timestamp date;

	private BigDecimal fee;

	@Column(name="first_time")
	private Boolean firstTime;

	private String library;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;

	//bi-directional many-to-one association to CircLocation
	@ManyToOne
	@JoinColumn(name="location_id")
	private CircLocation circLocation;

	//bi-directional many-to-one association to Gender
	@ManyToOne
	@JoinColumn(name="gender_id")
	private Gender gender;

	//bi-directional many-to-one association to Librarian
	@ManyToOne
	@JoinColumn(name="librarian_id")
	private Librarian librarian;

	//bi-directional many-to-one association to Member
	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member;

	//bi-directional many-to-one association to MembershipType
	@ManyToOne
	@JoinColumn(name="membership_type_id")
	private MembershipType membershipType;

	public Membership() {
	}

	public Integer getMembershipId() {
		return this.membershipId;
	}

	public void setMembershipId(Integer membershipId) {
		this.membershipId = membershipId;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public BigDecimal getFee() {
		return this.fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Boolean getFirstTime() {
		return this.firstTime;
	}

	public void setFirstTime(Boolean firstTime) {
		this.firstTime = firstTime;
	}

	public String getLibrary() {
		return this.library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public CircLocation getCircLocation() {
		return this.circLocation;
	}

	public void setCircLocation(CircLocation circLocation) {
		this.circLocation = circLocation;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Librarian getLibrarian() {
		return this.librarian;
	}

	public void setLibrarian(Librarian librarian) {
		this.librarian = librarian;
	}

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public MembershipType getMembershipType() {
		return this.membershipType;
	}

	public void setMembershipType(MembershipType membershipType) {
		this.membershipType = membershipType;
	}

}