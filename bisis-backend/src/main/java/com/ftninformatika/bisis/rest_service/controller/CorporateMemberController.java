package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.models.circ.CorporateMember;
import com.ftninformatika.bisis.rest_service.repository.mongo.CorporateMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public boolean saveCorporateMemberById(@RequestBody CorporateMember corporateMember){
        return corporateMemberRepository.save(corporateMember) != null;
    }


}
