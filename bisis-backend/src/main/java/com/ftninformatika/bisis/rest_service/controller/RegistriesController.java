package com.ftninformatika.bisis.rest_service.controller;


import com.ftninformatika.bisis.registry.*;
import com.ftninformatika.bisis.rest_service.repository.mongo.GenericRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/registries")
public class RegistriesController {


    @Autowired
    GenericRegistryRepository genericRegistryRepository;

       //---Genericki registri--
    @RequestMapping( path = "{regCode}" )
    public List<GenericRegistry> getRegistriesForType(@PathVariable("regCode") Integer regCode) {

        return genericRegistryRepository.findByCode(regCode);
    }

    @RequestMapping( path = "{regCode}/count" )
    public Integer countRegistriesForType(@PathVariable("regCode") Integer regCode) {
        return genericRegistryRepository.findByCode(regCode).size();
    }

    @RequestMapping( path = "delete", method = RequestMethod.POST)
    public Boolean deleteRegistryForType(@RequestBody GenericRegistry obj) {
        genericRegistryRepository.delete(obj);
        return genericRegistryRepository.findOne(obj.get_id()) == null;
    }

    @RequestMapping( path = "", method = RequestMethod.POST)
    public GenericRegistry addRegistryForType(@RequestHeader("Library") String lib, @RequestBody GenericRegistry obj) {
        return genericRegistryRepository.save(obj);
    }

}
