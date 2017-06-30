package com.ftninformatika.bisis.config_model;

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
public class LibraryConfiguration {

    @Id
    private String _id;
    private String libraryName;
    private String primerciModel;
    private String catalougingGodineModel;
    private String tcatalougingInvbrSubStr;
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

}
