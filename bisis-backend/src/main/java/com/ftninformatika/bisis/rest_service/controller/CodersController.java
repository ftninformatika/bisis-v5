package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.models.circ.EducationLvl;
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
    public List<EducationLvl> getEducation(String libName){
        return edurep.getCoders(libName);
    }

}
