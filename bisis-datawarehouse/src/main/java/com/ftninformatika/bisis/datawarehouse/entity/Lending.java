package com.ftninformatika.bisis.datawarehouse.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the lending database table.
 * 
 */
@Entity
@NamedQuery(name="Lending.findAll", query="SELECT l FROM Lending l")
public class Lending implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LENDING_LENDINGID_GENERATOR", sequenceName="LENDING_LENDING_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LENDING_LENDINGID_GENERATOR")
	@Column(name="lending_id")
	private Integer lendingId;

	@Column(name="ctlg_no")
	private String ctlgNo;

	private Timestamp date;

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

	//bi-directional many-to-one association to County
	@ManyToOne
	@JoinColumn(name="country_id")
	private County county;

	//bi-directional many-to-one association to Gender
	@ManyToOne
	@JoinColumn(name="gender_id")
	private Gender gender;

	//bi-directional many-to-one association to Language
	@ManyToOne
	@JoinColumn(name="language_id")
	private Language language;

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
	@ManyToOne
	@JoinColumn(name="udk_id")
	private Udk udk;

	public Lending() {
	}

	public Integer getLendingId() {
		return this.lendingId;
	}

	public void setLendingId(Integer lendingId) {
		this.lendingId = lendingId;
	}

	public String getCtlgNo() {
		return this.ctlgNo;
	}

	public void setCtlgNo(String ctlgNo) {
		this.ctlgNo = ctlgNo;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getLibrary() {
		return this.library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public AccessionRegister getAccessionRegister() {
		return this.accessionRegister;
	}

	public void setAccessionRegister(AccessionRegister accessionRegister) {
		this.accessionRegister = accessionRegister;
	}

	public BibliographicLevel getBibliographicLevel() {
		return this.bibliographicLevel;
	}

	public void setBibliographicLevel(BibliographicLevel bibliographicLevel) {
		this.bibliographicLevel = bibliographicLevel;
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

	public County getCounty() {
		return this.county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public LendingAction getLendingAction() {
		return this.lendingAction;
	}

	public void setLendingAction(LendingAction lendingAction) {
		this.lendingAction = lendingAction;
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

	public RecordType getRecordType() {
		return this.recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public SerialType getSerialType() {
		return this.serialType;
	}

	public void setSerialType(SerialType serialType) {
		this.serialType = serialType;
	}

	public Target getTarget() {
		return this.target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public Udk getUdk() {
		return this.udk;
	}

	public void setUdk(Udk udk) {
		this.udk = udk;
	}

}