package com.ftninformatika.bisis.datawarehouse.controller;

import com.ftninformatika.bisis.datawarehouse.entity.SavedQuery;
import com.ftninformatika.bisis.datawarehouse.service.SavedQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("query")
public class SavedQueryController {

    @Autowired
    SavedQueryService savedQueryService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody SavedQuery savedQuery){
        return ResponseEntity.ok(savedQueryService.saveQuery(savedQuery));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getSavedQueries() {
        return ResponseEntity.ok(savedQueryService.getSavedQueries());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSavedQuery(@PathVariable("id") Integer id) {
        if (savedQueryService.deleteSavedQuery(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unknown query");
        }

    }
}
