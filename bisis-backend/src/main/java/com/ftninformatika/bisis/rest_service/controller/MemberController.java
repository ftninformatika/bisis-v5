package com.ftninformatika.bisis.rest_service.controller;


import com.ftninformatika.bisis.models.circ.Member;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dboberic on 28/07/2017.
 */
@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    MemberRepository memberRep;

    @RequestMapping(path = "/getById", method = RequestMethod.GET)
    public Member getMember(@RequestParam (value = "userId") String userId){
        return memberRep.getMemberByUserId(userId);
    }

}
