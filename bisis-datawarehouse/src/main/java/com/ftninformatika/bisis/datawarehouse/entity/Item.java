package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * The persistent class for the item database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="item", schema = "bisis_reports", catalog = "bisis")

public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	public Item(Item another){
		this.ctlgDate = another.getCtlgDate();
		this.ctlgNo = another.getCtlgNo();
		this.issueNo = another.getIssueNo();
		this.library = another.getLibrary();
		this.price = another.getPrice();
		this.accessionRegister = another.getAccessionRegister();
		this.acquisition = another.getAcquisition();
		this.bibliographicLevel = another.getBibliographicLevel();
		this.internalMark = another.getInternalMark();
		this.status = another.getStatus();
		this.library = another.getLibrary();
		this.location = another.getLocation();
		this.sublocation = another.getSublocation();
		this.serialType = another.getSerialType();
		this.record = another.getRecord();
		this.recordType = another.getRecordType();
		this.target = another.getTarget();
	}

	@Id
	@SequenceGenerator(name="ITEM_ITEMID_GENERATOR", sequenceName="bisis_reports.ITEM_ITEM_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ITEM_ITEMID_GENERATOR")
	@Column(name="item_id")
	private Integer itemId;

	@Column(name="ctlg_date")
	private LocalDateTime ctlgDate;

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
	private Country country;

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
}
