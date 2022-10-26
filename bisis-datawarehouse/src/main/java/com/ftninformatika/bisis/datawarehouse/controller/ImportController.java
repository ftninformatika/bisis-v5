package com.ftninformatika.bisis.datawarehouse.controller;

import com.ftninformatika.bisis.datawarehouse.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/import")
public class ImportController {
    @Autowired
    ImportService importService;

    @GetMapping
    public void importData(){
        importService.handleImport();
    }
    @GetMapping("/{library}")
    public void importData(@PathVariable("library") String library){
        importService.handleImportOneLibrary(library);
    }
}
