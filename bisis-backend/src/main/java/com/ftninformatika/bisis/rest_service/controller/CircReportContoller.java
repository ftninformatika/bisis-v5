package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.pojo.Report;
import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    /**
     *  ukupan broj korisnika koji su se uclanili od pocetka godine
     *  ukupan broj korisnika koji su se uclanili u tom periodu
     */

    @RequestMapping(value="/get_number_of_users_by_period",method = RequestMethod.GET)
    public int getNumberOfUsersByPeriod(@RequestParam("start") Date start,@RequestParam("end") Date end, @RequestParam("location")String location){
       int num=0;
       if ((location==null)||(location.equals(""))) {
           num = mr.getNumberOfMembersByPeriod(start, end, location);
       }else{
           num = mr.getNumberOfMembersByPeriod(start, end);
       }
       return num;
    }

    /**
     * podaci o korisniku koji su se uclanili datog dana po kategorijama
     */

    @RequestMapping(value="/get_users_by_categories",method = RequestMethod.GET)
    public List<Report> getUsersByCategory(@RequestParam("start") Date start,@RequestParam("end") Date end, @RequestParam("location")String location){
       List<Report> reports = new ArrayList<>();
        List<Member> members=mr.getMembersByCategories(start,end,location);
        for(Member m:members) {
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
            if(m.getSignings().get(0).getCost()==null || m.getSignings().get(0).getCost().equals(""))
              r.setProperty20("0");
            else{
                r.setProperty20(String.valueOf(m.getSignings().get(0).getCost()));
            }
            reports.add(r);
        }
       return reports;
    }
}
