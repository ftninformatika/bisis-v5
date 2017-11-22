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
    public List<Report> getUsersByCategory(@RequestParam("start") Date start, @RequestParam("location")String location) {
       List<Report> reports = new ArrayList<>();
      List<Member> h=mr.getMembersByCategories(start);
       Report r = new Report();
     //  r.setProperty1(userId);
     //  r.setProperty2(h.get("firstName"));
  //     r.setProperty3(lastName);
    //   r.setProperty4(address);
    //   r.setProperty5(zip);
   //    r.setProperty6(city);
   //    r.setProperty7(docNo);
   //    r.setProperty8(docCity);
   //    r.getProperty9(jmbg);
  //     r.setProperty10(librarian);
  //     r.setProperty11(receiptId);
 //      r.getProperty12(cost);
    //    r.setProperty13(h.get("userCategory.description"));
       return reports;
    }
}
