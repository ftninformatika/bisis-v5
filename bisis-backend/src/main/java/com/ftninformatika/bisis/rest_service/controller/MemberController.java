package com.ftninformatika.bisis.rest_service.controller;


import com.ftninformatika.bisis.circ.Organization;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Autowired OrganizationRepository organizationRepository;

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

    @RequestMapping( path = "/fixMemberOrganizationIds")
    public String fixOrganizationIds(){
        String retVal = "";

        List<Organization> orgs = organizationRepository.findAll();
        int overall = memberRep.findAll().size();
        int count = 0;
        PageRequest p = new PageRequest(0, 1000);
        Page<Member> mems = memberRep.findAll(p);

        while (mems.hasNext()){
            for (Member m : mems){
                for (Organization o: orgs){
                    if(sameOrgs(o,m.getOrganization())) {
                        m.getOrganization().setId(o.get_id().toString());
                        break;
                    }
                }
            }
            memberRep.save(mems.getContent());
            count += 1000;
            System.out.println("Processed " + count + " of " + overall + " members!");
            mems = memberRep.findAll(mems.nextPageable());
        }

        for (Member m : mems){
            for (Organization o: orgs){
                if(sameOrgs(o,m.getOrganization())) {
                    m.getOrganization().setId(o.get_id());
                    break;
                }
            }
        }

        memberRep.save(mems.getContent());
        System.out.println("Processed all members!");
        return retVal;
    }

    public boolean sameOrgs(Organization org, com.ftninformatika.bisis.circ.pojo.Organization memOrg){
        if (memOrg == null) return false;

        if(org.getName() == null && memOrg.getName() != null) return false;
        if(org.getName() != null && memOrg.getName() == null) return false;
        if(org.getName() != null && memOrg.getName() != null && (!org.getName().equals(memOrg.getName()))) return false;

        if(org.getZip() == null && memOrg.getZip() != null) return false;
        if(org.getZip() != null && memOrg.getZip() == null) return false;
        if(org.getZip() != null && memOrg.getZip() != null && (!org.getZip().equals(memOrg.getZip()))) return false;

        if(org.getCity() == null && memOrg.getCity() != null) return false;
        if(org.getCity() != null && memOrg.getCity() == null) return false;
        if(org.getCity() != null && memOrg.getCity() != null && (!org.getCity().equals(memOrg.getCity()))) return false;

        if(org.getAddress() == null && memOrg.getAddress() != null) return false;
        if(org.getAddress() != null && memOrg.getAddress() == null) return false;
        if(org.getAddress() != null && memOrg.getAddress() != null && (!org.getAddress().equals(memOrg.getAddress()))) return false;

        return true;
    }

}
