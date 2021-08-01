package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.rest_service.service.implementations.ExternalHitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author badf00d21  6.11.19.
 * Dedicated controller to generate static html with populated meta-tags
 * for books. Should be used only for external hits (fb..), because it is much
 * faster than prerendering. Other SE bots are routed to Rendertron.
 */
@Controller("/external_hit")
public class ExternalHitController {

    @Autowired ExternalHitService externalHitService;

    @GetMapping
    public ResponseEntity<String> generateHtml(@RequestParam("url") String url) {
        String result = externalHitService.getBookHtml(url);
        if (result != null)
            return ResponseEntity.ok(result);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("");
    }
}
