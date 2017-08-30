package com.ftninformatika.bisis.library_configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private String catalougingPrimerciModel;
    private String catalougingGodineModel;
    private String catalougingInvbrSubStr;
    private String catalougingDefaultPrimerakInvKnj;
    private String catalougingDefaultSveskaInvKnj;
    private String catalougingDefaultGodinaInvKnj;
    private String catalougingValidator;
    private String catalougingReportset;

    private String bookcardsNextPage;
    private String bookcardsCurrentType;
    private String bookcardsTranslateX;
    private String bookcardsTranslateY;
    private String bookcardsFontSize;
    private String bookcardsBrRedova;

    private String barcodePort;
    private String barcodeOptionName;
    private String barcodeLibrary1;
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


}
