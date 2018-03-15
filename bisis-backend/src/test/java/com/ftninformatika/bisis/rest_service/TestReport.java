package com.ftninformatika.bisis.rest_service;

import com.ftninformatika.bisis.rest_service.repository.mongo.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dboberic on 07/11/2017.
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestReport {
    @Autowired
    MemberRepository mr;

    @Test
    public void test(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d=sdf.parse("2011-11-27");
        //  mr.getMembersByCategories(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
