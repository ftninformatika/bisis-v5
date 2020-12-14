package com.ftninformatika.bisis.inventory.controller;

import com.ftninformatika.bisis.inventory.InventoryUnit;
import com.ftninformatika.bisis.inventory.dto.ChangeRevStatusesDTO;
import com.ftninformatika.bisis.inventory.dto.MapStatusesToItemsDTO;
import com.ftninformatika.bisis.inventory.dto.RevStatusOnPlaceDTO;
import com.ftninformatika.bisis.inventory.config.PathConstants;
import com.ftninformatika.bisis.inventory.dto.InvUnitSearchDTO;
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

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) final Integer pageNumber,
            @RequestParam(required = false) final Integer pageSize,
            @RequestBody InvUnitSearchDTO invUnitSearchDTO) {
        Page<InventoryUnit> searchResults = inventoryUnitService.search(invUnitSearchDTO, pageNumber, pageSize);

        if (searchResults == null) {
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
    public ResponseEntity<InventoryUnit> changeRevStatusOnPlace(@RequestHeader("Library") String library, @RequestBody RevStatusOnPlaceDTO revStatusOnPlaceDTO) {
        InventoryUnit retVal = inventoryUnitService.setOnPlace(revStatusOnPlaceDTO, library);
        if (retVal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(retVal);
    }

    @PutMapping("/changeRevStatuses")
    public ResponseEntity<Boolean> changeRevStatuses(@RequestHeader("Library") String library, @RequestBody ChangeRevStatusesDTO changeRevStatusesDTO) {
        Boolean retVal = inventoryUnitService.changeRevStatuses(library, changeRevStatusesDTO);
        if (retVal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(retVal);
    }

    @PutMapping("/updateItemStatuses")
    public ResponseEntity<Boolean> updateItemStatuses(@RequestHeader("Library") String library, @RequestBody MapStatusesToItemsDTO mapStatusesToItemsDTO) {
        Boolean retVal = inventoryUnitService.mapStatusesToItems(mapStatusesToItemsDTO);
        if (retVal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(retVal);
    }

}
