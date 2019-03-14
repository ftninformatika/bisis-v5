package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.registry.*;
import com.ftninformatika.bisis.rest_service.repository.mongo.GenericRegistryRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/registries")
public class RegistriesController {


    @Autowired GenericRegistryRepository genericRegistryRepository;
    @Autowired MongoTemplate mongoTemplate;
       //---Genericki registri--
    // TODO - refactor this later(codec or service or repository extension??) and find out why repository queries are extra slow!
    @RequestMapping( path = "{regCode}" )
    public List<GenericRegistry> getRegistriesForType(@RequestHeader("Library") String lib,
                                                      @PathVariable("regCode") Integer regCode) {
        List<GenericRegistry> retVal = new ArrayList<>();
        Iterator<Document> docs = mongoTemplate.getCollection(lib + "_registries").find(new Document("code", regCode)).iterator();
        while (docs.hasNext()) {
            Document d = docs.next();
            GenericRegistry g = new GenericRegistry(d.getObjectId("_id").toHexString(), d.getInteger("code")
                    , d.getString("field1"), d.getString("field2"), d.getString("field3"));
            retVal.add(g);
        }
        return retVal;
    }

    @RequestMapping( path = "{regCode}/count" )
    public Integer countRegistriesForType(@PathVariable("regCode") Integer regCode) {
        return genericRegistryRepository.findByCode(regCode).size();
    }

    @RequestMapping( path = "delete", method = RequestMethod.POST)
    public Boolean deleteRegistryForType(@RequestBody GenericRegistry obj) {
        genericRegistryRepository.delete(obj);
        return genericRegistryRepository.findById(obj.get_id()).get() == null;
    }

    @RequestMapping( path = "", method = RequestMethod.POST)
    public GenericRegistry addRegistryForType(@RequestHeader("Library") String lib, @RequestBody GenericRegistry obj) {
        return genericRegistryRepository.save(obj);
    }

}
