package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.pojo.Report;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import com.ftninformatika.bisis.rest_service.repository.mongo.LendingRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    /**
     * ukupan broj korisnika koji su se uclanili od pocetka godine
     * ukupan broj korisnika koji su se uclanili u tom periodu
     */
     /*UsersNumberReportCommand*//*Statistic1ReportCommand*/

    @RequestMapping(value = "/get_number_of_members_by_period", method = RequestMethod.GET)
    public int getNumberOfMembersByPeriod(@RequestParam("start") Date start, @RequestParam("end") Date end, @RequestParam("location") String location) {
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
    public List<Report> getMembersWithCategory(@RequestParam("start") Date start, @RequestParam("end") Date end, @RequestParam("location") String location) {
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
    public List<Report> getMembersWithMemberType(@RequestParam("start") Date start, @RequestParam("end") Date end, @RequestParam("location") String location) {
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
    public List<Report> getSignedMembers(@RequestParam("start") Date start, @RequestParam("end") Date end, @RequestParam("location") String location) {
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
    public List<Report> getSignedCorporateMembers(@RequestParam("start") Date start, @RequestParam("end") Date end, @RequestParam("location") String location, @RequestParam("company") String company) {
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
    public List<Report> groupByMembershipType(@RequestParam("start") Date start, @RequestParam("end") Date end, @RequestParam("location") String location){
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
/*SubMemberBookReportCommand*/
    @RequestMapping(value = "/get_cost_for_user", method = RequestMethod.GET)
    public List<Report> getCostForUser(@RequestParam("start") Date start, @RequestParam("end") Date end, @RequestParam("location") String location) {
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
