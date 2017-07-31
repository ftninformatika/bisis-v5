package com.ftninformatika.bisis.rest_service.controller;


import com.ftninformatika.bisis.models.circ.Member;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dboberic on 28/07/2017.
 */
@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    MemberRepository memberRep;

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public Member getMember(@PathVariable String userId){
        return memberRep.getMemberByUserId(userId);
    }

}
