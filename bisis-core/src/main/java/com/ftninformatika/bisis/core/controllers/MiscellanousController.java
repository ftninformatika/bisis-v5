package com.ftninformatika.bisis.core.controllers;

import com.ftninformatika.bisis.coders.ItemStatus;
import com.ftninformatika.bisis.core.repositories.ItemStatusRepository;
import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MiscellanousController {

    @Autowired
    LibraryConfigurationRepository libraryConfigurationRepository;

    @Autowired
    ItemStatusRepository itemStatusRepository;

    @RequestMapping(path = "/coders/lib_configurations")
    public List<LibraryConfiguration> getConfigs(String libName){
        return libraryConfigurationRepository.findAll();
    }

    @RequestMapping(path = "/coders/item_status")
    public List<ItemStatus> getStatuses(@RequestHeader("Library") String libName){
        return itemStatusRepository.getCoders(libName);
    }
}
