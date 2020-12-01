package com.ftninformatika.bisis.inventory.controller;

import com.ftninformatika.bisis.inventory.Inventory;
import com.ftninformatika.bisis.inventory.config.PathConstants;
import com.ftninformatika.bisis.inventory.service.interfaces.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(PathConstants.INVENTORY)
public class InventoryController {

    private InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getAllByLib(@RequestHeader("Library") String library) {
        if (library == null) {
            return ResponseEntity.badRequest().build();
        }
        List<Inventory> inventories = inventoryService.getAllForLib(library);
        if (inventories == null || inventories.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inventories);
    }

    @GetMapping()
    public ResponseEntity<Inventory> getOne(@RequestParam("_id") String _id) {
        Inventory inventory =  inventoryService.getOne(_id);
        if (inventory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(inventory);
    }

    @PostMapping()
    public ResponseEntity<Inventory> create(@RequestHeader("Library") String library, @RequestBody Inventory inventory) {
        Inventory inventory1 = inventoryService.create(inventory, library);
        if (inventory1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        return ResponseEntity.ok(inventory1);
    }

    @PutMapping()
    public ResponseEntity<Inventory> update(@RequestBody Inventory inventory) {
        Inventory inventory1 = inventoryService.update(inventory);
        if (inventory1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        return ResponseEntity.ok(inventory1);
    }


    @DeleteMapping()
    public ResponseEntity<Inventory> delete(@RequestParam("_id") String _id) {
        Inventory inventory =  inventoryService.getOne(_id);
        if (inventory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            inventoryService.delete(inventory);
            return ResponseEntity.ok().build();
        }
    }
}
