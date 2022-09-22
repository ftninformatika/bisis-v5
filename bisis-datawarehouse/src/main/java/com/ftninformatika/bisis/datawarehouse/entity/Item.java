package com.ftninformatika.bisis.datawarehouse.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the item database table.
 * 
 */
@Entity
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ITEM_ITEMID_GENERATOR", sequenceName="ITEM_ITEM_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ITEM_ITEMID_GENERATOR")
	@Column(name="item_id")
	private Integer itemId;

	@Column(name="ctlg_date")
	private Timestamp ctlgDate;

	@Column(name="ctlg_no")
	private String ctlgNo;

	@Column(name="issue_no")
	private String issueNo;

	private String library;

	private BigDecimal price;

	//bi-directional many-to-one association to AccessionRegister
	@ManyToOne
	@JoinColumn(name="accession_register_id")
	private AccessionRegister accessionRegister;

	//bi-directional many-to-one association to Acquisition
	@ManyToOne
	@JoinColumn(name="acquisition_id")
	private Acquisition acquisition;

	//bi-directional many-to-one association to BibliographicLevel
	@ManyToOne
	@JoinColumn(name="bibliographic_level_id")
	private BibliographicLevel bibliographicLevel;

	//bi-directional many-to-one association to County
	@ManyToOne
	@JoinColumn(name="country_id")
	private County county;

	//bi-directional many-to-one association to InternalMark
	@ManyToOne
	@JoinColumn(name="internal_mark_id")
	private InternalMark internalMark;

	//bi-directional many-to-one association to Language
	@ManyToOne
	@JoinColumn(name="language_id")
	private Language language;

	//bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name="location_id")
	private Location location;

	//bi-directional many-to-one association to Record
	@ManyToOne
	@JoinColumn(name="record_id")
	private Record record;

	//bi-directional many-to-one association to RecordType
	@ManyToOne
	@JoinColumn(name="record_type_id")
	private RecordType recordType;

	//bi-directional many-to-one association to SerialType
	@ManyToOne
	@JoinColumn(name="serial_type_id")
	private SerialType serialType;

	//bi-directional many-to-one association to Status
	@ManyToOne
	@JoinColumn(name="status_id")
	private Status status;

	//bi-directional many-to-one association to Sublocation
	@ManyToOne
	@JoinColumn(name="sublocation_id")
	private Sublocation sublocation;

	//bi-directional many-to-one association to Target
	@ManyToOne
	@JoinColumn(name="target_id")
	private Target target;

	//bi-directional many-to-one association to Udk
	@ManyToOne
	@JoinColumn(name="udk_id")
	private Udk udk;

	public Item() {
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Timestamp getCtlgDate() {
		return this.ctlgDate;
	}

	public void setCtlgDate(Timestamp ctlgDate) {
		this.ctlgDate = ctlgDate;
	}

	public String getCtlgNo() {
		return this.ctlgNo;
	}

	public void setCtlgNo(String ctlgNo) {
		this.ctlgNo = ctlgNo;
	}

	public String getIssueNo() {
		return this.issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public String getLibrary() {
		return this.library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public AccessionRegister getAccessionRegister() {
		return this.accessionRegister;
	}

	public void setAccessionRegister(AccessionRegister accessionRegister) {
		this.accessionRegister = accessionRegister;
	}

	public Acquisition getAcquisition() {
		return this.acquisition;
	}

	public void setAcquisition(Acquisition acquisition) {
		this.acquisition = acquisition;
	}

	public BibliographicLevel getBibliographicLevel() {
		return this.bibliographicLevel;
	}

	public void setBibliographicLevel(BibliographicLevel bibliographicLevel) {
		this.bibliographicLevel = bibliographicLevel;
	}

	public County getCounty() {
		return this.county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	public InternalMark getInternalMark() {
		return this.internalMark;
	}

	public void setInternalMark(InternalMark internalMark) {
		this.internalMark = internalMark;
	}

	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Record getRecord() {
		return this.record;
	}

	public void setRecord(Record record) {
		this.record = record;
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

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Sublocation getSublocation() {
		return this.sublocation;
	}

	public void setSublocation(Sublocation sublocation) {
		this.sublocation = sublocation;
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
