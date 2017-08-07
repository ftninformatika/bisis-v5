package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petar on 8/7/2017.
 */
@RestController
@RequestMapping("/primerci")
public class PrimerciController {

    @Autowired private RecordsRepository recordsRepository;

    @RequestMapping( method = RequestMethod.GET )
    public List<Primerak> getPrimerci(){
        List<Primerak> retVal = new ArrayList<>();

        List<Record> recs = recordsRepository.findAll();

        for(Record r: recs)
            retVal.addAll(r.getPrimerci());

        return retVal;
    }

    @RequestMapping( value = "/{invNum}", method = RequestMethod.GET)
    public Primerak getPrimerak(@PathVariable String invNum){
        Record r = recordsRepository.getRecordByPrimerakInvNum(invNum);
        return r.getPrimerak(invNum);
    }
}
