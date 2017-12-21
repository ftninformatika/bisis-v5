package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.pojo.Report;
import com.ftninformatika.bisis.circ.pojo.Signing;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import com.ftninformatika.bisis.rest_service.repository.mongo.LendingRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.MembershipTypeRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import org.elasticsearch.monitor.os.OsStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dboberic on 03/11/2017.
 */
@RestController
@RequestMapping("/circ_report")
public class CircReportContoller {
    @Autowired
    MemberRepository mr;

    @Autowired
    LendingRepository lr;

    @Autowired
    RecordsRepository rr;

    @Autowired
    MembershipTypeRepository membershipTypeRepository;

    /** podaci o korisnicima koji su tog dana zaduzili knjigu, produzili ili vratili knjigu
     *
     */
    /*VisitorsReportCommand*/
    @RequestMapping( value = "get_visitors_report")
    public List<Report> getVisitorsReport(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date,  @RequestParam(name = "location", required = false)String location){
        List<Report> retVal = new ArrayList<>();
        List<Lending> lendings = null;
        Iterable<Member> members = null;

        lendings = lr.findByLendDateOrReturnDateOrReturnDate(date, date, date);
        Set<String> userIds = lendings.stream().map(i -> i.getUserId()).collect(Collectors.toSet());
        members = mr.findByUserIdIn(userIds);

        members.forEach(
                m -> {
                    Report r = new Report();
                    r.setProperty1(m.getUserId());
                    r.setProperty2(m.getFirstName());
                    r.setProperty3(m.getLastName());
                    r.setProperty4(m.getAddress());
                    r.setProperty5(m.getCity());
                    r.setProperty6(m.getZip());
                    r.setProperty7(m.getDocNo());
                    r.setProperty8(m.getDocCity());
                    r.setProperty9(m.getJmbg());
                    if(location != null && !location.equals(""))
                        r.setProperty10(location);

                }
        );

        return retVal;
    }


    /**
     * podaci o korisniku koji su se uclanili datog dana po vrsti uclanjenja
     */
    /*MmbrTypeReportCommand*/
    @RequestMapping( value = "get_mmbr_type_report")
    public List<Report> getMmbrTypeReport( @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date,  @RequestParam(name = "location", required = false)String location) {
        List<Report> retVal = new ArrayList<>();
        List<Member> members = null;

        members = mr.getSignedMembers(date, date, location, "membershipType.description");
        for (Member m: members){
            Report r = new Report();
            r.setProperty1(m.getUserId());
            r.setProperty2(m.getFirstName());
            r.setProperty3(m.getLastName());
            r.setProperty4(m.getAddress());
            r.setProperty5(m.getZip());
            r.setProperty6(m.getCity());
            r.setProperty7(m.getDocNo());
            r.setProperty8(m.getDocCity());
            r.setProperty9(m.getJmbg());
            r.setProperty10(m.getLibrarianForSigningDate(date));
            r.setProperty11(m.getRecieptForSigingDate(date));
            r.setProperty12(m.getCostForSigningDate(date));
            r.setProperty13(m.getMembershipType().getDescription());
            retVal.add(r);
        }
        return retVal;
    }

        /**
         * podatke o clanu za prosledjen datum uclanjenja i ogranak(opciono) sortirano po bibliotekarima
         */
    /*LibrarianReportCommand*/
    @RequestMapping( value = "get_librarian_report")
    public List<Report> getLibrarianReport( @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date date,  @RequestParam(name = "location", required = false)String location){
        List<Report> retVal = new ArrayList<>();
        List<Member> members = null;

        members = mr.getSignedMembers(date, date, location, "signings.librarian");
        Collections.sort(members, (Member m1, Member m2) -> m1.getSignings().get(0).getLibrarian().compareTo(m2.getSignings().get(0).getLibrarian())); //sortira po bibliotekarima
        for (Member m: members){
            Report r = new Report();
            r.setProperty1(m.getUserId());
            r.setProperty2(m.getFirstName());
            r.setProperty3(m.getLastName());
            r.setProperty4(m.getAddress());
            r.setProperty5(m.getZip());
            r.setProperty6(m.getCity());
            r.setProperty7(m.getDocNo());
            r.setProperty8(m.getDocCity());
            r.setProperty9(m.getJmbg());
            r.setProperty10(m.getLibrarianForSigningDate(date));
            r.setProperty11(m.getRecieptForSigingDate(date));
            r.setProperty12(m.getCostForSigningDate(date));
            r.setProperty13(m.getMembershipType().getDescription());
            retVal.add(r);
        }

        return retVal;
    }

    /**
     * id- jeve korisnika koji su zaduzivali knjigu sa prosledjenim ctlgno u prosledjenom periodu
     * datum zaduzenja, datum razduzenja
     */
    /*BookHistoryReportCommand*//*BookHistoryReportCommand*/
    @RequestMapping( value = "get_book_history_report")
    public List<Report> getBookHistoryReport( @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end,@RequestParam(value = "ctlgno") String ctlgNo, @RequestParam(name = "location", required = false)String location/*desc*/){
        List<Report> retVal = new ArrayList<>();
        List<Lending> lendings = null;
        if (location != null && location != "")
            lendings = lr.findByLendDateBetweenAndCtlgNoAndLocation(start, end, ctlgNo, location);
        else
            lendings = lr.findByLendDateBetweenAndCtlgNo(start, end, ctlgNo);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Lending l: lendings){
            Report r = new Report();
            r.setProperty1(l.getUserId());
            if(l.getLendDate() != null)
                r.setProperty2(formatter.format(l.getLendDate()));
            if(l.getReturnDate() != null)
                r.setProperty3(formatter.format(l.getReturnDate()));
            retVal.add(r);
        }

        return retVal;
    }

    /**
     * ukupan broj korisnika koji su se uclanili od pocetka godine
     * ukupan broj korisnika koji su se uclanili u tom periodu
     */
     /*UsersNumberReportCommand*//*Statistic1ReportCommand*/

    @RequestMapping(value = "/get_number_of_members_by_period", method = RequestMethod.GET)
    public int getNumberOfMembersByPeriod(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location) {
        int num = 0;
        if ((location == null) || (location.equals(""))) {
            num = mr.getNumberOfMembersByPeriod(start, end, location);
        } else {
            num = mr.getNumberOfMembersByPeriod(start, end);
        }
        return num;
    }

    /**
     * podaci o korisniku koji su se uclanili datog dana po kategorijama
     */
/*UserCategReportCommand*/

    @RequestMapping(value = "/get_members_with_categories", method = RequestMethod.GET)
    public List<Report> getMembersWithCategory(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location) {
        List<Report> reports = new ArrayList<>();
        List<Member> members = mr.getSignedMembers(start, end, location, "userCategory.description");
        for (Member m : members) {
            Report r = new Report();
            r.setProperty1(m.getUserId());
            r.setProperty2(m.getFirstName());
            r.setProperty3(m.getLastName());
            r.setProperty4(m.getUserCategory().getDescription());
            r.setProperty5(m.getAddress());
            r.setProperty6(m.getCity());
            r.setProperty7(m.getZip());
            r.setProperty8(m.getDocNo());
            r.setProperty9(m.getDocCity());
            r.setProperty10(m.getJmbg());
            r.setProperty11(m.getSignings().get(0).getLibrarian());
            r.setProperty13(m.getSignings().get(0).getReceipt());
            if (m.getSignings().get(0).getCost() == null || m.getSignings().get(0).getCost().equals(""))
                r.setProperty20("0");
            else {
                r.setProperty20(String.valueOf(m.getSignings().get(0).getCost()));
            }
            reports.add(r);
        }
        return reports;
    }

    /*
    uclanjeni korisnici sa tipom uclanjenja*/ /*MmbrTypeReportCommand*/

    @RequestMapping(value = "/get_members_with_member_type", method = RequestMethod.GET)
    public List<Report> getMembersWithMemberType(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location) {
        List<Report> reports = new ArrayList<>();
        List<Member> members = mr.getSignedMembers(start, end, location, "membershipType.description");
        for (Member m : members) {
            Report r = new Report();
            r.setProperty1(m.getMembershipType().getDescription());
            r.setProperty2(m.getUserId());
            if (m.getSignings().get(0).getCost() == null || m.getSignings().get(0).getCost().equals(""))
                r.setProperty20("0");
            else {
                r.setProperty20(String.valueOf(m.getSignings().get(0).getCost()));
            }
            reports.add(r);
        }
        return reports;
    }

    /*
    uclanjeni korisnici sortitani po prezimenu
     */
    /*MemberBookReportCommand*/

    @RequestMapping(value = "/get_signed_members", method = RequestMethod.GET)
    public List<Report> getSignedMembers(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location) {
        List<Report> reports = new ArrayList<>();
        List<Member> members = mr.getSignedMembers(start, end, location, "lastName");
        for (Member m : members) {
            Report r = new Report();
            r.setProperty1(m.getUserId());
            r.setProperty2(m.getFirstName());
            r.setProperty3(m.getLastName());
            r.setProperty4(m.getAddress());
            r.setProperty5(m.getCity());
            r.setProperty6(m.getZip());
            r.setProperty7(m.getDocNo());
            r.setProperty8(m.getDocCity());
            r.setProperty9(m.getJmbg());
            if (m.getSignings().get(0).getCost() == null || m.getSignings().get(0).getCost().equals(""))
                r.setProperty20("0");
            else {
                r.setProperty20(String.valueOf(m.getSignings().get(0).getCost()));
            }
            reports.add(r);
        }
        return reports;
    }

    /*uclanjeni korisnici preko nekog korporativnog clana*//*MemberByGroupReportCommand*/

    @RequestMapping(value = "/get_signed_corporateMembers", method = RequestMethod.GET)
    public List<Report> getSignedCorporateMembers(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location, @RequestParam("company") String company) {
        List<Report> reports = new ArrayList<>();
        List<Member> members = mr.getSignedCorporateMembers(start, end, location, company);
        for (Member m : members) {
            Report r = new Report();
            r.setProperty1(m.getUserId());
            r.setProperty2(m.getFirstName());
            r.setProperty3(m.getLastName());
            reports.add(r);
        }
        return reports;
    }
/*broj uclanjenih korisnika grupisanih po tipu*/ /*MemberTypeReportCommand*/

    @RequestMapping(value = "/group_by_membership_type", method = RequestMethod.GET)
    public List<Report> groupByMembershipType(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location){
        List<Report> reports = mr.groupMemberByMembershipType(start,end,location);
        return reports;
    }

    /*istorija zaduzenja clana *//*LendingHistoryReportCommand*/

    @RequestMapping(value = "/get_lending_history", method = RequestMethod.GET)
    public List<Report> getLendingHistory(@RequestParam("memberNo") String memberNo, @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date end, @RequestParam("location") String location){
        List<Report> reports=new ArrayList<Report>();
        List<Lending> lendings;
        if (location==null ||location.equals("")){
            lendings =lr.findLendingsByUserIdAndLendDateBetween(memberNo,start,end);
        }else{
            lendings = lr.findLendingsByUserIdAndLendDateBetweenAndLocation(memberNo,start,end,location);
        }
        Record r;
        RecordPreview rp = new RecordPreview();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");

       for(Lending l:lendings){
            r = rr.getRecordByPrimerakInvNum(l.getCtlgNo());
            rp.init(r);
            Report report = new Report();
            String returnDate="";
            if (l.getReturnDate()!=null){
                returnDate = sdf.format(l.getReturnDate());
            }
            report.setProperty1(sdf.format(l.getLendDate()));
            report.setProperty2(returnDate);
            report.setProperty3(rp.getAuthor());
            report.setProperty4(rp.getTitle());
            report.setProperty5(l.getCtlgNo());
            reports.add(report);
       }

        return reports;
    }

       /*istorija zaduzenja clana */

    @RequestMapping(value = "/get_lending_history_full", method = RequestMethod.GET)
    public List<Report> getLendingHistoryFull(@RequestParam("memberNo") String memberNo){
        List<Report> reports=new ArrayList<Report>();
        List<Lending> lendings;
        lendings = lr.findByUserId(memberNo);

        Record r;
        RecordPreview rp = new RecordPreview();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");

        for(Lending l:lendings){
            r = rr.getRecordByPrimerakInvNum(l.getCtlgNo());
            rp.init(r);
            Report report = new Report();
            String returnDate="";
            if (l.getReturnDate()!=null){
                returnDate = sdf.format(l.getReturnDate());
            }
            report.setProperty1(sdf.format(l.getLendDate()));
            report.setProperty2(returnDate);
            report.setProperty3(rp.getAuthor());
            report.setProperty4(rp.getTitle());
            report.setProperty5(l.getCtlgNo());
            reports.add(report);
        }

        return reports;
    }
/*SubMemberBookReportCommand*/
    @RequestMapping(value = "/get_cost_for_user", method = RequestMethod.GET)
    public List<Report> getCostForUser(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end, @RequestParam("location") String location) {
        List<Report> reports=new ArrayList<Report>();
        List<Member> members = mr.getSignedMembers(start, end, location, "membershipType.description");
        for (Member m : members) {
            Report r = new Report();
            r.setProperty1(m.getUserId());
            r.setProperty2(m.getMembershipType().getDescription());
            if (m.getSignings().get(0).getCost() == null || m.getSignings().get(0).getCost().equals(""))
                r.setProperty20("0");
            else {
                r.setProperty20(String.valueOf(m.getSignings().get(0).getCost()));
            }
            reports.add(r);
        }
        return reports;
    }
    }
