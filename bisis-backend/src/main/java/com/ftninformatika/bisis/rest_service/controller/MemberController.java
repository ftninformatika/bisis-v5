package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.circ.pojo.Warning;
import com.ftninformatika.bisis.circ.wrappers.MergeData;
import com.ftninformatika.bisis.circ.wrappers.WarningsData;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.ItemAvailabilityRepository;
import com.ftninformatika.utils.validators.memberdata.MemberDataDatesValidator;
import com.ftninformatika.utils.validators.memberdata.MemberDateError;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(path = "/memberExist", method = RequestMethod.GET)
    public String userExist(@RequestParam(value = "userId") String userId) {
        Member m = memberRep.getMemberByUserId(userId);
        if (m != null)
            return m.get_id();
        return null;
    }

    @RequestMapping(path = "/getByEmail", method = RequestMethod.GET)
    public Member getMemberByEmail(@RequestParam(value = "email") String email) {
        return memberRep.getMemberByEmail(email);
    }

    @RequestMapping(path = "/addUpdate", method = RequestMethod.POST)
    public Member addUpdateMember(@RequestBody Member member) {
        return memberRep.save(member);
    }

    @RequestMapping(path = "/getById", method = RequestMethod.GET)
    public Member getMember(@RequestParam(value = "userId") String userId) {
        return memberRep.getMemberByUserId(userId);
    }

    @RequestMapping(path = "/existUser", method = RequestMethod.GET)
    public boolean existUser(@RequestParam(value = "userId") String userId) {
        Member m = memberRep.getMemberByUserId(userId);
        return m != null;
    }

    @RequestMapping(path = "/addUpdateMemberData", method = RequestMethod.POST)
    public MemberData addUpdateMemberData(@RequestBody MemberData memberData) {
        try {
            if (MemberDataDatesValidator.validateMemberDataDates(memberData) != MemberDateError.NO_ERROR)
                return null;

            if (memberData.getMember() != null) {
                memberData.setMember(memberRep.save(memberData.getMember()));
            }
            if (memberData.getLendings() != null && !memberData.getLendings().isEmpty()) {
                lendingRepository.saveAll(memberData.getLendings());
                List<Lending> lendings = lendingRepository.findByUserIdAndReturnDateIsNull(memberData.getMember().getUserId());
                memberData.setLendings(lendings);
                memberData.setBooks(itemAvailabilityRepository.saveAll(memberData.getBooks()));
            }
            return memberData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @RequestMapping(path = "/getMemberDataById")
    public MemberData getMemberDataById(@RequestParam("userId") String userId) {
        MemberData retVal = new MemberData();
        Member m = memberRep.getMemberByUserId(userId);
        if (m == null)
            return null;
        retVal.setMember(m);
        retVal.setLendings(lendingRepository.findByUserIdAndReturnDateIsNull(userId));

        return retVal;
    }


    /**
     * @param userId      - ID korisnika (nije mongoId!!!)
     * @param librarianId - mongodId bibliotekara
     * @return null - ako ne pronadje bibliotekara ili korisnika
     * MemberData objekat, bez inUseBy propertija (inUseBy azuriran i sacuvan kod Member- a) - ako je uspoesno zakljucao
     * MemberData objekat, koji sadrzi samo inUseBy (ostalo null) - ako je vec zakljucan
     */
    @RequestMapping(path = "/getAndLock")
    public MemberData getAndLockMemberById(@RequestParam("userId") String userId, @RequestParam("librarianId") String librarianId) {
        MemberData retVal = new MemberData();
        Member m = memberRep.getMemberByUserId(userId);
        LibrarianDTO l = librarianRepository.findById(librarianId).get();

        if (m == null || l == null) { //nema tog clana (ili nekim cudom bibliotekara)
            log.info("(getAndLockMemberById) nije pronadjen korisnik ID: " + userId + " ili bibliotekar ID: " + librarianId);
            return null;
        }

        if (m.getInUseBy() != null) {            // ako je zakljucan
            log.info("(getAndLockMemberById) vec je zakljucan korisnik ID: " + userId);
            retVal.setInUseBy(m.getInUseBy());
            return retVal;
        }
        m.setInUseBy(librarianId);
        memberRep.save(m);
        List<Lending> lendings = lendingRepository.findByUserIdAndReturnDateIsNull(userId);
        retVal.setMember(m);
        retVal.setLendings(lendings);
        log.info("(getAndLockByMemberId) zakljucan i vracen korisnik ID: " + userId);
        return retVal;
    }

    /**
     * @param userId
     * @return false - ako ne postoji korisnik za taj userId
     * true - ako postoji i promeni inUseBy na null
     */
    @RequestMapping(path = "/releaseById")
    public boolean unlockMemberById(@RequestParam("userId") String userId) {
        Member m = memberRep.getMemberByUserId(userId);
        if (m == null) {
            log.info("(unlockMemberById) Nije pronadjen user ID: " + userId);
            return false;
        }
        m.setInUseBy(null);
        memberRep.save(m);
        log.info("(unlockMemberById) Otkljucan user ID: " + userId);
        return true;

    }

    @RequestMapping(path = "/getCharged")
    public Member getChargedMember(@RequestParam("ctlgNo") String ctlgNo) {
        Lending lending = lendingRepository.findByCtlgNoAndReturnDateIsNull(ctlgNo);
        if (lending != null) {
            return memberRep.getMemberByUserId(lending.getUserId());
        } else {
            return null;
        }
    }

    @RequestMapping(path = "/getLending")
    public Lending getLending(@RequestParam("ctlgNo") String ctlgNo) {
        Lending lending = lendingRepository.findByCtlgNoAndReturnDateIsNull(ctlgNo);
        return lending;
    }

    @RequestMapping(path = "/dischargeBook")
    public Boolean dischargeBook(@RequestBody Lending lending) {
        try {
            Lending l = lendingRepository.save(lending);
            ItemAvailability item = itemAvailabilityRepository.getByCtlgNo(lending.getCtlgNo());
            item.setBorrowed(false);
            itemAvailabilityRepository.save(item);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(path = "/getWarnMembers")
    public List<MemberData> getWarnMembers(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "location", required = false) String location) {
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
    public boolean addWarnings(@RequestBody WarningsData warningsData) {
        try {
            if (warningsData.getLendings() != null && !warningsData.getLendings().isEmpty()) {
                lendingRepository.saveAll(warningsData.getLendings());
            }
            if (warningsData.getCounters() != null && !warningsData.getCounters().isEmpty()) {
                warningCounterRepository.saveAll(warningsData.getCounters());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    @RequestMapping(path = "/getWarnHistory")
    public List<MemberData> getWarnHistory(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam(name = "warningType") String warningType, @RequestParam(name = "location", required = false) String location) {
        List<Lending> lendingsByWarningHistory;
        if (location == null || location.isEmpty()) {
            lendingsByWarningHistory = lendingRepository.findLendingsByWarningHistory(start, end, warningType);
        } else {
            lendingsByWarningHistory = lendingRepository.findLendingsByWarningHistory(start, end, warningType, location);
        }
        List<String> userIds = lendingsByWarningHistory.stream().map(l -> l.getUserId()).collect(Collectors.toList());
        Map<String, Member> members = memberRep.findByUserIdIn(userIds).stream().collect(Collectors.toMap(Member::getUserId, member -> member));

        Map<String, MemberData> memberMap = new HashMap<>();

        for (Lending l: lendingsByWarningHistory) {
            MemberData memberData = memberMap.get(l.getUserId());
            if (memberData == null) {
                memberData = new MemberData();
                memberData.setMember(members.get(l.getUserId()));
                memberData.setLendings(new ArrayList<>());
                memberMap.put(l.getUserId(), memberData);
            }
            List<Warning> warningList = new ArrayList<>();
            for (Warning w: l.getWarnings()) {
                if(w.getDeadline().after(start) && w.getDeadline().before(end) && w.getWarningType().equals(warningType)){
                    warningList.add(w);
                }
            }
            l.setWarnings(warningList);
            memberData.getLendings().add(l);
        }

        TreeMap<String, MemberData> result = new TreeMap<>(memberMap);

        return result.values().stream().collect(Collectors.toList());
    }


    @RequestMapping(path = "/merge", method = RequestMethod.POST)
    public boolean merge(@RequestBody MergeData mergeData) {
        Member mainMember = memberRep.getMemberByUserId(mergeData.getUser());
        for (String userId : mergeData.getUserList()) {
            if (!userId.equals(mergeData.getUser())) {
                Member m = memberRep.getMemberByUserId(userId);
                mainMember.getSignings().addAll(m.getSignings());
                mainMember.getDuplicates().addAll(m.getDuplicates());
                mainMember.getPicturebooks().addAll(m.getPicturebooks());
                memberRep.save(mainMember);
                memberRep.delete(m);
            }
            if (!userId.equals(mergeData.getUserId())) {
                List<Lending> lendings = lendingRepository.findByUserId(userId);
                for (Lending l : lendings) {
                    l.setUserId(mergeData.getUserId());
                    lendingRepository.save(l);
                }
            }
        }
        mainMember.setUserId(mergeData.getUserId());
        mainMember.setInUseBy(null);
        memberRep.save(mainMember);
        log.info("Spojen korisnik: " + mergeData.getUser());
        return true;

    }

    private Logger log = Logger.getLogger(MemberController.class);

}
