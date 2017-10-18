package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.models.circ.Membership;
import com.ftninformatika.bisis.rest_service.repository.mongo.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Petar on 10/18/2017.
 */
@RestController
@RequestMapping("/memberships")
public class MembershipController {

    @Autowired MembershipRepository membershipRepository;

    @RequestMapping( method = RequestMethod.POST)
    public Membership addMembership(@RequestBody Membership membership){
       return membershipRepository.insert(membership);
    }

    @RequestMapping( method = RequestMethod.DELETE)
    public boolean deleteMembership(@RequestBody String membershipId){
        Membership m = membershipRepository.findOne(membershipId);
        if ( m == null ) return false;
        membershipRepository.delete(membershipId);
        return membershipRepository.findOne(membershipId) == null;
    }

}