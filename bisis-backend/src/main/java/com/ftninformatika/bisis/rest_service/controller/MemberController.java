package com.ftninformatika.bisis.rest_service.controller;


import com.ftninformatika.bisis.models.circ.Member;
import com.ftninformatika.bisis.models.circ.wrappers.MemberData;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import org.elasticsearch.monitor.os.OsStats;
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
    public String userExist(@RequestParam (value = "userId") String userId){
        Member m = memberRep.getMemberByUserId(userId);
        if (m != null)
            return m.get_id();
        return null;
    }

    @RequestMapping( path = "/getByEmail", method = RequestMethod.GET)
    public Member getMemberByEmail(@RequestParam (value = "email") String email){
        return memberRep.getMemberByEmail(email);
    }

    @RequestMapping( path = "/addUpdate", method = RequestMethod.POST)
    public Member addUpdateMember(@RequestBody Member member){
        return memberRep.save(member);
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

    @RequestMapping( path = "/getAndLockById")
    public MemberData getAndLockMemberById(@RequestParam("userId") String userId, @RequestParam("librarianId") String librarianId){
        MemberData retVal = new MemberData();
        Member m = memberRep.getMemberByUserId(userId);

        if ( m == null )
            return null;

        if (m.getInUseBy() != null){            // ako je zakljucan
            retVal.setInUseBy(m.getInUseBy());
            return retVal;
        }
        
        m.setInUseBy();


        return retVal;
    }

}
