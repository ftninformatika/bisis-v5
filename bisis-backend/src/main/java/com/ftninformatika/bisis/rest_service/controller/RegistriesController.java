package com.ftninformatika.bisis.rest_service.controller;


import com.ftninformatika.bisis.registry.*;
import com.ftninformatika.bisis.rest_service.repository.mongo.registries.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/registries")
public class RegistriesController {

    @Autowired RegPrOdRepository odrednicaRepo;
    @Autowired RegPrPodRepository pododrednicaRepo;
    @Autowired RegKolOdrRepository kolektivnaOdrRepo;
    @Autowired RegZbirkeRepository regZbirkeRepo;
    @Autowired RegUDKSubgroupRepository udkPodgrupaRepo;
    @Autowired RegAutOdrRepository autorRepo;
    //---Predmedtna odrednica-----------------------
    @RequestMapping( path = "po" )
    public List<RegPrOd> getRegProD() {
        List<RegPrOd> retVal = odrednicaRepo.findAll();
        retVal = retVal.stream().sorted(Comparator.comparing(RegPrOd::getPojam))
                .collect(Collectors.toList());
        return retVal;
    }

    @RequestMapping( path = "po/count" )
    public Integer countRegProD() {
        return odrednicaRepo.findAll().size();
    }

    @RequestMapping( path = "po/delete", method = RequestMethod.POST)
    public Boolean deleteRegProD(@RequestBody RegPrOd obj) {
        odrednicaRepo.delete(obj);
        return odrednicaRepo.findOne(obj.get_id()) == null;
    }

    @RequestMapping( path = "po", method = RequestMethod.POST)
    public RegPrOd addRegPrOd(@RequestHeader("Library") String lib, @RequestBody RegPrOd obj) {
        obj.setLibrary(lib);
        return odrednicaRepo.save(obj);
    }

    //---Predmetna pododrednica---------------------
    @RequestMapping( path = "pod" )
    public List<RegPrPod> getRegPrPod() {
        List<RegPrPod> retVal = pododrednicaRepo.findAll();
        retVal = retVal.stream().sorted(Comparator.comparing(RegPrPod::getPojam))
                .collect(Collectors.toList());
        return retVal;
    }

    @RequestMapping( path = "pod/count" )
    public Integer countRegPrPod() {
        return pododrednicaRepo.findAll().size();
    }

    @RequestMapping( path = "pod/delete", method = RequestMethod.POST)
    public Boolean deleteRegPrPod(@RequestBody RegPrPod obj) {
        pododrednicaRepo.delete(obj);
        return pododrednicaRepo.findOne(obj.get_id()) == null;
    }

    @RequestMapping( path = "pod", method = RequestMethod.POST)
    public RegPrPod addRegPrPod(@RequestHeader("Library") String lib, @RequestBody RegPrPod obj) {
        obj.setLibrary(lib);
        return pododrednicaRepo.save(obj);
    }

    //---Kolektivna odrednica---------------
    @RequestMapping( path = "ko" )
    public List<RegKolOdr> getRegKolOdr() {
        List<RegKolOdr> retVal = kolektivnaOdrRepo.findAll();
        retVal = retVal.stream().sorted(Comparator.comparing(RegKolOdr::getKolektivac))
                                .collect(Collectors.toList());
        return retVal;
    }

    @RequestMapping( path = "ko/count" )
    public Integer countRegKolOdr() {
        return kolektivnaOdrRepo.findAll().size();
    }

    @RequestMapping( path = "ko/delete", method = RequestMethod.POST)
    public Boolean deleteRegKolOdr(@RequestBody RegKolOdr obj) {
        kolektivnaOdrRepo.delete(obj);
        return kolektivnaOdrRepo.findOne(obj.get_id()) == null;
    }

    @RequestMapping( path = "ko", method = RequestMethod.POST)
    public RegKolOdr addRegKolOdr(@RequestHeader("Library") String lib, @RequestBody RegKolOdr obj) {
        obj.setLibrary(lib);
        return kolektivnaOdrRepo.save(obj);
    }

    //---Zbirke odrednica---------------
    @RequestMapping( path = "zb" )
    public List<RegZbirke> getRegZbirke() {
        List<RegZbirke> retVal = regZbirkeRepo.findAll();
        retVal = retVal.stream().sorted(Comparator.comparing(RegZbirke::getNaziv))
                        .collect(Collectors.toList());
        return retVal;
    }

    @RequestMapping( path = "zb/count" )
    public Integer countRegZbirke() {
        return regZbirkeRepo.findAll().size();
    }

    @RequestMapping( path = "zb/delete", method = RequestMethod.POST)
    public Boolean deleteRegZbirke(@RequestBody RegZbirke obj) {
        regZbirkeRepo.delete(obj);
        return regZbirkeRepo.findOne(obj.get_id()) == null;
    }

    @RequestMapping( path = "zb", method = RequestMethod.POST)
    public RegZbirke addRegZbirke(@RequestHeader("Library") String lib, @RequestBody RegZbirke obj) {
        obj.setLibrary(lib);
        return regZbirkeRepo.save(obj);
    }

    //---UDK podgrupa---------------
    @RequestMapping( path = "udksub" )
    public List<RegUDKSubgroup> getRegUDKS() {
        List<RegUDKSubgroup> retVal = udkPodgrupaRepo.findAll();
        retVal = retVal.stream().sorted(Comparator.comparing(RegUDKSubgroup::getGrupa))
                        .collect(Collectors.toList());
        return retVal;
    }

    @RequestMapping( path = "udksub/count" )
    public Integer countRegUDKS() {
        return udkPodgrupaRepo.findAll().size();
    }

    @RequestMapping( path = "udksub/delete", method = RequestMethod.POST)
    public Boolean deleteRegUDKS(@RequestBody RegUDKSubgroup obj) {
        udkPodgrupaRepo.delete(obj);
        return udkPodgrupaRepo.findOne(obj.get_id()) == null;
    }

    @RequestMapping( path = "udksub", method = RequestMethod.POST)
    public RegAutOdr addRegUDKS(@RequestHeader("Library") String lib, @RequestBody RegAutOdr obj) {
        obj.setLibrary(lib);
        return autorRepo.save(obj);
    }

    //---UDK autor---------------
    @RequestMapping( path = "author" )
    public List<RegAutOdr> getRegAuthor() {
        List<RegAutOdr> retVal = autorRepo.findAll();
        retVal = retVal.stream().sorted(Comparator.comparing(RegAutOdr::getAutor))
                                .collect(Collectors.toList());
        return retVal;
    }

    @RequestMapping( path = "author/count" )
    public Integer countRegAuthor() {
        return autorRepo.findAll().size();
    }

    @RequestMapping( path = "author/delete", method = RequestMethod.POST)
    public Boolean deleteRegAuthor(@RequestBody RegAutOdr obj) {
        autorRepo.delete(obj);
        return autorRepo.findOne(obj.get_id()) == null;
    }

    @RequestMapping( path = "author", method = RequestMethod.POST)
    public RegAutOdr addRegAuthor(@RequestHeader("Library") String lib, @RequestBody RegAutOdr obj) {
        obj.setLibrary(lib);
        return autorRepo.save(obj);
    }

}
