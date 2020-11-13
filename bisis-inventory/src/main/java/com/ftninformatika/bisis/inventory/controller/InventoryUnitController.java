package com.ftninformatika.bisis.inventory.controller;

import com.ftninformatika.bisis.inventory.InventoryUnit;
import com.ftninformatika.bisis.inventory.config.PathConstants;
import com.ftninformatika.bisis.inventory.service.interfaces.InventoryUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(PathConstants.INVENTORY_UNIT)
public class InventoryUnitController {

    private InventoryUnitService inventoryUnitService;

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
}
