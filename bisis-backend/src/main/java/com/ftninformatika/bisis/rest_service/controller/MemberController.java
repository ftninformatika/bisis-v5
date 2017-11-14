package com.ftninformatika.bisis.rest_service.controller;


import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.rest_service.repository.mongo.ItemAvailabilityRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LendingRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibrarianRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dboberic on 28/07/2017.
 */
@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired MemberRepository memberRep;
    @Autowired LibrarianRepository librarianRepository;
    @Autowired LendingRepository lendingRepository;
    @Autowired ItemAvailabilityRepository itemAvailabilityRepository;

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

    @RequestMapping( path = "/addUpdateMemberData", method = RequestMethod.POST)
    public boolean addUpdateMemberData(@RequestBody MemberData memberData){
       if ( memberRep.save(memberData.getMember()) == null )
           return false;
       if (memberData.getLendings() != null && !memberData.getLendings().isEmpty()) {
           lendingRepository.save(memberData.getLendings());
           itemAvailabilityRepository.save(memberData.getBooks());
       }

       return true;
    }

    @RequestMapping( path = "/getMemberDataById")
    public MemberData getMemberDataById(@RequestParam("userId") String userId){
        MemberData retVal = new MemberData();
        Member m = memberRep.getMemberByUserId(userId);
        if ( m == null )
            return null;
        retVal.setMember(m);
        retVal.setLendings(lendingRepository.findByUserIdAndReturnDateIsNull(userId));

        return retVal;
    }


    /**
     *
     * @param userId - ID korisnika (nije mongoId!!!)
     * @param librarianId - mongodId bibliotekara
     * @return null - ako ne pronadje bibliotekara ili korisnika
     *         MemberData objekat, bez inUseBy propertija (inUseBy azuriran i sacuvan kod Member- a) - ako je uspoesno zakljucao
     *         MemberData objekat, koji sadrzi samo inUseBy (ostalo null) - ako je vec zakljucan
     */
    @RequestMapping( path = "/getAndLock")
    public MemberData getAndLockMemberById(@RequestParam("userId") String userId, @RequestParam("librarianId") String librarianId){
        MemberData retVal = new MemberData();
        Member m = memberRep.getMemberByUserId(userId);
        LibrarianDTO l = librarianRepository.findOne(librarianId);

        if ( m == null || l == null) //nema tog clana (ili nekim cudom bibliotekara)
            return null;

        if (m.getInUseBy() != null){            // ako je zakljucan
            retVal.setInUseBy(m.getInUseBy());
            return retVal;
        }
        m.setInUseBy(librarianId);
        memberRep.save(m);
        List<Lending> lendings = lendingRepository.findByUserIdAndReturnDateIsNull(userId);
        retVal.setMember(m);
        retVal.setLendings(lendings);

        return retVal;
    }

    /**
     *
     * @param userId
     * @return false - ako ne postoji korisnik za taj userId
     *         true - ako postoji i promeni inUseBy na null
     */
    @RequestMapping( path = "/releaseById" )
    public boolean unlockMemberById(@RequestParam("userId") String userId){
        Member m = memberRep.getMemberByUserId(userId);

        if ( m == null )
            return false;

        m.setInUseBy(null);
        memberRep.save(m);
        return true;

    }

}
