package com.ftninformatika.bisis.datawarehouse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


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
