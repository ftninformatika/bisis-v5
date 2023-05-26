package com.ftninformatika.bisis.inventory.controller;

import com.ftninformatika.bisis.core.repositories.InventoryStatusRepository;
import com.ftninformatika.bisis.inventory.InventoryStatus;
import com.ftninformatika.bisis.inventory.service.interfaces.InvCodersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ftninformatika.bisis.inventory.config.PathConstants.INVENTORY_CODERS;

@RestController
@RequestMapping(INVENTORY_CODERS)
public class InvCodersController {

    private InvCodersService invCodersService;
    @Autowired
    InventoryStatusRepository inventoryStatusRepository;

    @Autowired
    public InvCodersController(InvCodersService invCodersService) {
        this.invCodersService = invCodersService;
    }


    @GetMapping("/location")
    public ResponseEntity<List<?>> getInvLocations(@RequestHeader("Library") String library) {
        List<?> result = this.invCodersService.getInvLocationsByLib(library);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/status")
    public List<InventoryStatus> getInvetoryStatuses(@RequestHeader("Library") String libName){
        return inventoryStatusRepository.getCoders(libName);
    }
}
