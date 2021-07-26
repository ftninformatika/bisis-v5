package com.ftninformatika.bisis.inventory.controller;

import com.ftninformatika.bisis.core.repositories.InventoryStatusRepository;
import com.ftninformatika.bisis.inventory.InventoryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/coders")
public class CodersController {

    @Autowired
    InventoryStatusRepository inventoryStatusRepository;


    @RequestMapping(path = "inventory_status")
    public List<InventoryStatus> getInvetoryStatuses(@RequestHeader("Library") String libName){
        return inventoryStatusRepository.getCoders(libName);
    }


}
