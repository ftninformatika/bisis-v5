package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.circ.Membership;
import com.ftninformatika.bisis.rest_service.repository.mongo.interfaces.MembershipRepository;
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
       return membershipRepository.save(membership);
    }

    @RequestMapping( method = RequestMethod.DELETE)
    public boolean deleteMembership(@RequestBody Membership membership){
        Membership m = membershipRepository.findById(membership.get_id()).get();
        if ( m == null ) return false;
        membershipRepository.deleteById(membership.get_id());
        return membershipRepository.findById(membership.getLibrary()).get() == null;
    }

}
