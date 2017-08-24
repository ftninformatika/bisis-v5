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

    @RequestMapping( path = "/memberExist", method = RequestMethod.GET)
    public Boolean userExist(@RequestParam (value = "userId") String userId){
        return memberRep.exists(userId);
    }

    @RequestMapping( path = "/addUpdate", method = RequestMethod.POST)
    public Member addUpdateMember(@RequestBody Member member){
        Member retVal  = memberRep.save(member);
        return retVal;
    }

    @RequestMapping(path = "/getById", method = RequestMethod.GET)
    public Member getMember(@RequestParam (value = "userId") String userId){
        return memberRep.getMemberByUserId(userId);
    }

    @RequestMapping( path = "/existUser", method = RequestMethod.GET)
    public boolean existUser(@RequestParam (value = "userId") String userId){
        Member m = memberRep.getMemberByUserId(userId);
        return m != null;
    }

}
