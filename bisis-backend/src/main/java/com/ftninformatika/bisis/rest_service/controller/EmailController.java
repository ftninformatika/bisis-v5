package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.rest_service.repository.mongo.interfaces.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.interfaces.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Petar on 10/27/2017.
 */
@RestController
@RequestMapping("/email")
public class EmailController {


    @Autowired MemberRepository memberRepository;

    @Autowired LibraryMemberRepository libraryMemberRepository;



}
