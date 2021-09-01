package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.opac.repository.FAQRepository;
import com.ftninformatika.bisis.opac.FAQ;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/faq")
public class FAQController {

    @Autowired
    FAQRepository faqRepository;

    @GetMapping("/get")
    public List<FAQ> getFAQ() {
        return faqRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<FAQ> addFAQ(@RequestBody FAQ faq) {
        try {
            FAQ createdFaq = faqRepository.save(faq);
            return new ResponseEntity<>(createdFaq, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
