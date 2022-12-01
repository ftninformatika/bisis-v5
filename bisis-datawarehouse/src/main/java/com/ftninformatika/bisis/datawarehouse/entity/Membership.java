package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * The persistent class for the membership database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="membership", schema = "bisis_reports", catalog = "bisis")

public class Membership implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MEMBERSHIP_MEMBERSHIPID_GENERATOR", sequenceName="bisis_reports.MEMBERSHIP_MEMBERSHIP_ID_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MEMBERSHIP_MEMBERSHIPID_GENERATOR")
	@Column(name="membership_id")
	private Integer membershipId;

	private LocalDateTime date;

	private Double fee;

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

	//bi-directional many-to-one association to CorporateMember
	@ManyToOne
	@JoinColumn(name="corporate_member_id")
	private CorporateMember corporateMember;

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
}
