package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.rest_service.repository.mongo.LendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Petar on 8/28/2017.
 */

@RestController
@RequestMapping("/lendings")
public class LendingController {

    @Autowired private LendingRepository lendingRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Lending> allLendings(){
        return lendingRepository.findAll();
    }

    @RequestMapping(value = "/getLendingsByUserId", method = RequestMethod.GET)
    public List<Lending> getLendingsByUserId(@RequestParam(value = "userId")String userId){
        List<Lending> retVal = null;
        retVal = lendingRepository.findByUserId(userId);
        return retVal;
    }

}
