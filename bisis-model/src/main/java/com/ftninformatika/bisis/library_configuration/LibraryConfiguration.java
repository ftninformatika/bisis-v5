package com.ftninformatika.bisis.library_configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * Created by Petar on 6/30/2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "configs")
@JsonIgnoreProperties( ignoreUnknown = true )
public class LibraryConfiguration {

    @Id
    private String _id;
    private String libraryName;
    private String networkServerList;
    private String libraryFullName;
    private String shortName;

    private String cataloguingPrimerciModel;
    private String cataloguingGodineModel;
    private String cataloguingInvbrSubStr;
    private String cataloguingDefaultPrimerakInvKnj;
    private String cataloguingDefaultSveskaInvKnj;
    private String cataloguingDefaultGodinaInvKnj;
    private String cataloguingValidator;
    private String cataloguingReportset;

    private String bookcardsNextPage;
    private String bookcardsCurrentType;
    private String bookcardsTranslateX;
    private String bookcardsTranslateY;
    private String bookcardsFontSize;
    private String bookcardsBrRedova;
    private String bookcardsLocale;

    private String barcodeLabelFormat;
    private String barcodePort;
    private String barcodeOptionName;
    private String barcodeLibrary1;
    private Map<String, String> barcodeLibraries;
    private String barcodeLabelWidth;
    private String barcodeLabelHeight;
    private String barcodeLabelResolution;
    private String barcodeBarwidth;
    private String barcodeNarrowbar;
    private String barcodeWidebar;
    private String barcodeSigfont;
    private String barcodeLabelfont;
    private String barcodePageCode;
    private String barcodeWrap;

    private String barcodeUsersLabelWidth;
    private String barcodeUsersLabelHeight;
    private String barcodeUsersLabelResolution;
    private String barcodeUsersBarwidth;
    private String barcodeUsersNarrowbar;
    private String barcodeUsersWidebar;
    private String barcodeUsersPageCode;
    private String barcodeUsersLinelength;

    private String pincodeEnabled;
    private String pincodeLibrary;

    private String locale;
    private Boolean circLocaleLatn;

    private String locationAddress;
    private String locationZip;
    private String locationCity;
    private String websiteUrl;
    private List<Report> reports;
    private Integer maxCollectionsPerLib;
    //redosled kojim se biblioteke prikazuju u mobilnoj applikaciji
    private Integer mobileOrderNo;
    private Boolean maintenanceEnabled;
    //oznaka da li biblioteka ima podlokacije kao Beograd.
    // ako je locationLevel=1 ili ne postoji znaci da nema podlokacije ima samo lokacije (odeljenja), ako je locationLevel =2 ima podlokacije
    private  Integer locationLevel;
    private Boolean reservation;

    public String toString(){
        return libraryFullName;
    }
}
