package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.opac.repository.FAQRepository;
import com.ftninformatika.bisis.opac2.FAQ;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/faq")
public class FAQController {

    @Autowired
    FAQRepository faqRepository;

    @GetMapping("/get")
    public List<FAQ> getFAQ(){
        return faqRepository.findAll();
    }
}
