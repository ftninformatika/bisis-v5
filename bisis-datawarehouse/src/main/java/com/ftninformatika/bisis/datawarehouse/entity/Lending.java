package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


/**
 * The persistent class for the lending database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="lending", schema = "bisis_reports", catalog = "bisis")

public class Lending implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LENDING_LENDINGID_GENERATOR", sequenceName="bisis_reports.LENDING_LENDING_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LENDING_LENDINGID_GENERATOR")
	@Column(name="lending_id")
	private Integer lendingId;

	@Column(name="ctlg_no")
	private String ctlgNo;

	private LocalDateTime date;

	private String library;

	//bi-directional many-to-one association to AccessionRegister
	@ManyToOne
	@JoinColumn(name="accession_register_id")
	private AccessionRegister accessionRegister;

	//bi-directional many-to-one association to BibliographicLevel
	@ManyToOne
	@JoinColumn(name="bibliographic_level_id")
	private BibliographicLevel bibliographicLevel;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;

	//bi-directional many-to-one association to CircLocation
	@ManyToOne
	@JoinColumn(name="location_id")
	private CircLocation circLocation;

	@ManyToMany
	@JoinTable(
			name = "lending_country",
			joinColumns = @JoinColumn(name = "country_id"),
			inverseJoinColumns = @JoinColumn(name = "lending_id"))
	private Set<Country> countries =new HashSet<>();

	//bi-directional many-to-one association to Gender
	@ManyToOne
	@JoinColumn(name="gender_id")
	private Gender gender;

	@ManyToMany
	@JoinTable(
			name = "lending_language",
			joinColumns = @JoinColumn(name = "language_id"),
			inverseJoinColumns = @JoinColumn(name = "lending_id"))
	private Set<Language> languages =new HashSet<>();

	//bi-directional many-to-one association to LendingAction
	@ManyToOne
	@JoinColumn(name="action_id")
	private LendingAction lendingAction;

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

	//bi-directional many-to-one association to RecordType
	@ManyToOne
	@JoinColumn(name="record_type_id")
	private RecordType recordType;

	//bi-directional many-to-one association to SerialType
	@ManyToOne
	@JoinColumn(name="serial_type_id")
	private SerialType serialType;

	//bi-directional many-to-one association to Target
	@ManyToOne
	@JoinColumn(name="target_id")
	private Target target;

	//bi-directional many-to-one association to Udk
	/*@ManyToOne
	@JoinColumn(name="udk_id")
	private Udk udk;*/

	@ManyToMany
	@JoinTable(
			name = "lending_udk",
			joinColumns = @JoinColumn(name = "udk_id"),
			inverseJoinColumns = @JoinColumn(name = "lending_id"))
	private Set<Udk> udks =new HashSet<>();

	//bi-directional many-to-one association to Status
	@ManyToOne
	@JoinColumn(name="status_id")
	private Status status;

	//bi-directional many-to-one association to InternalMark
	@ManyToOne
	@JoinColumn(name="internal_mark_id")
	private InternalMark internalMark;

	@Formula("concat(ctlg_no, date)") // <= THE TRICK
	private String ctlgNoDate;
}
