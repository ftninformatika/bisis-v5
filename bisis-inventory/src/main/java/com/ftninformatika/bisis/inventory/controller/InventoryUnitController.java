package com.ftninformatika.bisis.inventory.controller;

import com.ftninformatika.bisis.inventory.InventoryUnit;
import com.ftninformatika.bisis.inventory.RequestInvUnitMin;
import com.ftninformatika.bisis.inventory.config.PathConstants;
import com.ftninformatika.bisis.inventory.service.interfaces.InventoryUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(PathConstants.INVENTORY_UNIT)
public class InventoryUnitController {

    private final InventoryUnitService inventoryUnitService;

    @Autowired
    public InventoryUnitController(InventoryUnitService inventoryUnitService) {
        this.inventoryUnitService = inventoryUnitService;
    }

    @GetMapping
    public ResponseEntity<?> search(@RequestParam("inventory_id") String inventory_id,
                                    @RequestParam(value = "pageNumber", required = false) final Integer pageNumber,
                                    @RequestParam(value = "pageSize", required = false) final Integer pageSize) {
        Page<InventoryUnit> searchResults = inventoryUnitService.search(inventory_id, pageSize, pageNumber);
        if (searchResults == null || searchResults.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(searchResults);
    }

    @PutMapping
    public ResponseEntity<InventoryUnit> update(@RequestBody InventoryUnit updatedInvUnit) {
        InventoryUnit retVal =  this.inventoryUnitService.update(updatedInvUnit);
        if (retVal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(retVal);
    }

    @GetMapping("/findOne")
    public ResponseEntity<InventoryUnit> findScannedInventoryUnit(@RequestParam("inventoryId") String inventoryId, @RequestParam("invNo") String invNo){
        InventoryUnit retVal =  this.inventoryUnitService.findByInventoryIdAndInvNo(inventoryId,invNo);
        if (retVal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(retVal);
    }

    @PutMapping("/changeRevStatusOnPlace")
    public ResponseEntity<InventoryUnit> changeRevStatusOnPlace(@RequestHeader("Library") String library, @RequestBody RequestInvUnitMin requestInvUnitMin) {
        InventoryUnit retVal = inventoryUnitService.setOnPlace(requestInvUnitMin, library);
        if (retVal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(retVal);
    }

}
