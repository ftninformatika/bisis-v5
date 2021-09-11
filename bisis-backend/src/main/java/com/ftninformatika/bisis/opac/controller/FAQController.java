package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.opac.repository.FAQRepository;
import com.ftninformatika.bisis.opac.FAQ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/faq")
public class FAQController {

    @Autowired
    FAQRepository faqRepository;

    @GetMapping("/get")
    public ResponseEntity<Page<FAQ>> getFAQ(@RequestHeader("Library") String lib,
                                            @RequestParam(value = "pageNumber", required = false) final Integer pageNumber,
                                            @RequestParam(value = "pageSize", required = false) final Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<FAQ> faqs = this.faqRepository.findAll(paging);
        return new ResponseEntity<>(faqs, HttpStatus.OK);
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

    @PutMapping
    public ResponseEntity<FAQ> editFAQ(@RequestBody FAQ faq) {
        if (!faqRepository.findById(faq.get_id()).isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        try {
            FAQ createdFaq = faqRepository.save(faq);
            return new ResponseEntity<>(createdFaq, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{faqId}")
    public ResponseEntity<Boolean> deleteFAQ(@PathVariable String faqId) {
        if (!faqRepository.findById(faqId).isPresent()) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        try {
            faqRepository.deleteById(faqId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
