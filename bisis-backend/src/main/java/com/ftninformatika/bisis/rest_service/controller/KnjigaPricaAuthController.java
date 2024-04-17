package com.ftninformatika.bisis.rest_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftninformatika.bisis.opac.dto.KnjigaPricaRequestDTO;
import com.ftninformatika.bisis.rest_service.service.implementations.KnjigaPricaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/knjigaprica-auth")
public class KnjigaPricaAuthController {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final KnjigaPricaService knjigaPricaService;

    public KnjigaPricaAuthController(
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            KnjigaPricaService knjigaPricaService
    ) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.knjigaPricaService = knjigaPricaService;
    }

    // TODO
    @GetMapping
    public ResponseEntity<String> getAudioBook(@RequestParam("recordNumber") Integer rn) {

        // Prepare request body
        KnjigaPricaRequestDTO knjigaPricaRequest = knjigaPricaService.createKnjigaPricaRequestBody(rn);

        // Convert request body to JSON string
        String requestBodyJson;
        try {
            requestBodyJson = objectMapper.writeValueAsString(knjigaPricaRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: Unable to serialize request body");
        }

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-Key", knjigaPricaService.getKnjigaPricaApiKey()); // Replace with your API key

        // Prepare HTTP entity
        HttpEntity<String> entity = new HttpEntity<>(requestBodyJson, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(knjigaPricaService.getKnjigaPricaApiUrl(), HttpMethod.POST, entity, String.class);
            // TODO: return i18n errors?
            /*
                user-limitation-exceeded - korisnik želi da pozajmi više od dozvoljenog broja knjiga po nalogu (u ovom slučaju 3)
                library-limitation-exceeded - broj pozajmljenih knjiga u nekoj biblioteci prevazilazi ukupak broj dostupnih knjiga
                book-already-possessed - korisnik je već pozajmio/poseduje knjigu
                book-unavailable - id od knjige u našem sistemu ne postoji
             */
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                // Return custom 401 Unauthorized error
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Custom 401 Unauthorized Error Message");
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                // Return custom 400 Bad Request error
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Custom 400 Bad Request Error Message");
            } else {
                // Handle other client errors
                return ResponseEntity.status(e.getStatusCode()).body("Error: " + e.getStatusCode());
            }
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }

        // Handle successful response
        if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
            // TODO: handle
            return ResponseEntity.ok("nesto");
        } else {
            // Handle unexpected status code from the 3rd party service
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected Error: Received status code " + response.getStatusCode());
        }
    }

}
