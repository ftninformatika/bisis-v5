package com.ftninformatika.bisis.inventory.controller;

import com.ftninformatika.bisis.inventory.service.interfaces.InvCodersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.ftninformatika.bisis.inventory.config.PathConstants.INVENTORY_CODERS;

@Controller
@RequestMapping(INVENTORY_CODERS)
public class InvCodersController {

    private InvCodersService invCodersService;

    @Autowired
    public InvCodersController(InvCodersService invCodersService) {
        this.invCodersService = invCodersService;
    }

    @GetMapping
    public ResponseEntity<List<?>> getInvLocations(@RequestHeader("Library") String library) {
        List<?> result = this.invCodersService.getInvLocationsByLib(library);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(result);
    }
}
