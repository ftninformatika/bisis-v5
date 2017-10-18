package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.models.circ.CorporateMember;
import com.ftninformatika.bisis.rest_service.repository.mongo.CorporateMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Petar on 10/18/2017.
 */
@RestController
@RequestMapping("/corporate_members")
public class CorporateMemberController {

    @Autowired CorporateMemberRepository corporateMemberRepository;

    @RequestMapping( value = "/getById", method = RequestMethod.GET)
    public CorporateMember getCorporateMemberById(@RequestParam("userId") String userId){
        return corporateMemberRepository.findByUserId(userId);
    }

    @RequestMapping( value = "/getById", method = RequestMethod.GET)
    public boolean getCorporateMemberById(@RequestParam("userId") String userId){
        return corporateMemberRepository.findByUserId(userId);
    }
}
