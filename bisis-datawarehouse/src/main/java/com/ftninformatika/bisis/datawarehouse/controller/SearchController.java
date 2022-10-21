package com.ftninformatika.bisis.datawarehouse.controller;

import com.ftninformatika.bisis.datawarehouse.model.SearchRequest;
import com.ftninformatika.bisis.datawarehouse.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @PostMapping
    public List<Object[]> search(@RequestBody SearchRequest searchRequest){
        return searchService.search(searchRequest);
    }
}
