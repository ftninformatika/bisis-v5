package com.ftninformatika.bisis.rest_service.controller;


import com.ftninformatika.bisis.circ.Organization;
import com.ftninformatika.bisis.circ.WarningCounter;
import com.ftninformatika.bisis.circ.wrappers.WarningsData;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired WarningCounterRepository warningCounterRepository;

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
    public MemberData addUpdateMemberData(@RequestBody MemberData memberData){
        try {
            if (memberData.getMember() != null){
                memberData.setMember(memberRep.save(memberData.getMember()));
            }
            if (memberData.getLendings() != null && !memberData.getLendings().isEmpty()) {
                memberData.setLendings(lendingRepository.save(memberData.getLendings()));
                memberData.setBooks(itemAvailabilityRepository.save(memberData.getBooks()));
            }
            return memberData;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


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

    @RequestMapping( path = "/getCharged" )
    public Member getChargedMember(@RequestParam("ctlgNo") String ctlgNo){
        Lending lending = lendingRepository.findByCtlgNoAndReturnDateIsNull(ctlgNo);
        if (lending != null){
            return memberRep.getMemberByUserId(lending.getUserId());
        } else {
            return null;
        }
    }

    @RequestMapping( path = "/getLending" )
    public Lending getLending(@RequestParam("ctlgNo") String ctlgNo){
        Lending lending = lendingRepository.findByCtlgNoAndReturnDateIsNull(ctlgNo);
        return lending;
    }

    @RequestMapping( path = "/dischargeBook" )
    public Boolean dischargeBook(@RequestBody Lending lending){
        try {
            Lending l = lendingRepository.save(lending);
            ItemAvailability item = itemAvailabilityRepository.getByCtlgNo(lending.getCtlgNo());
            item.setBorrowed(false);
            itemAvailabilityRepository.save(item);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping( path = "/getWarnMembers" )
    public List<MemberData> getWarnMembers(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "location", required = false)String location){
        List<Lending> overdueLendings = lendingRepository.getOverdueLendings(start, end, location);
        List<String> userIds = overdueLendings.stream().map(l -> l.getUserId()).collect(Collectors.toList());
        Map<String, Member> members = memberRep.findByUserIdIn(userIds).stream().collect(Collectors.toMap(Member::getUserId, member -> member));

        Map<String, MemberData> memberMap = new HashMap<>();

        overdueLendings.forEach(
                l -> {
                    MemberData memberData = memberMap.get(l.getUserId());
                    if (memberData == null) {
                        memberData = new MemberData();
                        memberData.setMember(members.get(l.getUserId()));
                        memberData.setLendings(new ArrayList<>());
                        memberMap.put(l.getUserId(), memberData);
                    }
                    memberData.getLendings().add(l);
                }
        );

//        Map<String, MemberData> result = map.entrySet().stream().sorted(Map.Entry.comparingByKey())
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
//                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        TreeMap<String, MemberData> result = new TreeMap<>(memberMap);

        return result.values().stream().collect(Collectors.toList());
    }


    @RequestMapping(path = "/addWarnings", method = RequestMethod.POST)
    public boolean addWarnings(@RequestBody WarningsData warningsData){
        try {
            if (warningsData.getLendings() != null && !warningsData.getLendings().isEmpty()) {
                lendingRepository.save(warningsData.getLendings());
            }
            if (warningsData.getCounters() != null && !warningsData.getCounters().isEmpty()) {
                warningCounterRepository.save(warningsData.getCounters());
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }

}
