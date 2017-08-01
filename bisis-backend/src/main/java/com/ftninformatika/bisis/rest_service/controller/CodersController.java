package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.auth.model.User;
import com.ftninformatika.bisis.models.circ.*;
import com.ftninformatika.bisis.models.coders.*;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dboberic on 26/07/2017.
 */
@RestController
@RequestMapping("/coders")
public class CodersController {


    @Autowired
    AcquisitionRepository acqrep;

    @Autowired
    AvailabilityRepository availrep;

    @Autowired
    AccessionRegisterRepository accregrep;

    @Autowired
    BindingRepository bindrep;

    @Autowired
    FormatRepository formrep;

    @Autowired
    InternalMarkRepository intmrep;

    @Autowired
    ItemStatusRepository statrep;

    @Autowired
    LocationRepository locrep;

    @Autowired
    SublocationRepository sublocrep;

    @Autowired
    EducationLvlRepository edurep;

    @Autowired
    LanguageRepository langrep;

    @Autowired
    MembershipRepository mbrshiprep;

    @Autowired
    MembershipTypeRepository mbrtyperep;

    @Autowired
    PlaceRepository placerep;

    @Autowired
    UserCategRepository usrcategrep;

    @Autowired
    WarningTypeRepository warnrep;

    @Autowired
    OrganizationRepository orgrep;

    @RequestMapping(path = "accession_register")
    public List<AccessionRegister> getAccessionRegs(String libName){
        return accregrep.getCoders(libName);
    }

    @RequestMapping(path = "acquisiton_type")
    public List<Acquisition> getAcquisitonTypes(String libName){
        return acqrep.getCoders(libName);
    }

    @RequestMapping(path = "availability")
    public List<Availability> getAvailabilities(String libName){
        return availrep.getCoders(libName);
    }

    @RequestMapping(path = "binding")
    public List<Binding> getBindings(String libName){
        return bindrep.getCoders(libName);
    }

    @RequestMapping(path = "format")
    public List<Format> getFormats(String libName){
        return formrep.getCoders(libName);
    }

    @RequestMapping(path = "internal_mark")
    public List<InternalMark> getInterMarks(String libName){
        return intmrep.getCoders(libName);
    }

    @RequestMapping(path = "item_status")
    public List<ItemStatus> getStatuses(String libName){
        return statrep.getCoders(libName);
    }

    @RequestMapping(path = "location")
    public List<Location> getLocations(String libName){
        return locrep.getCoders(libName);
    }

    @RequestMapping(path = "sublocation")
    public List<Sublocation> getSublocations(String libName){
        return sublocrep.getCoders(libName);
    }

    //coders from circ------------------------------------

    @RequestMapping(path = "education")
    public List<EducationLvl> getEducations(String libName){
        return edurep.getCoders(libName);
    }

    @RequestMapping(path = "language")
    public List<Language> getLanguages(String libName){
        return langrep.getCoders(libName);
    }

    @RequestMapping(path = "place")
    public List<Place> getPlaces(String libName){
        return placerep.getCoders(libName);
    }

    @RequestMapping(path = "membership")
    public List<Membership> getMemberships(String libName){
        return mbrshiprep.getCoders(libName);
    }

    @RequestMapping(path = "membership_type")
    public List<MembershipType> getMembershipTyspes(String libName){
        return mbrtyperep.getCoders(libName);
    }

    @RequestMapping(path = "user_category")
    public List<UserCategory> getUserCategs(String libName){
        return usrcategrep.getCoders(libName);
    }

    @RequestMapping(path = "warning_type")
    public List<WarningType> getWarningTypes(String libName){return warnrep.getCoders(libName);}

    @RequestMapping(path = "organization")
    public List<Organization> getOrganizations(String libName){return orgrep.getCoders(libName);}

}
