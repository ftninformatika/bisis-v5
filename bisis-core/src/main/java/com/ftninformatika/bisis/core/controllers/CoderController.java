package com.ftninformatika.bisis.core.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftninformatika.bisis.coders.Coder;
import com.ftninformatika.bisis.coders.definition.CoderDefinition;
import com.ftninformatika.bisis.core.repositories.CoderRepository;
import com.ftninformatika.utils.LibraryPrefixProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/coders")
public class CoderController {

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    LibraryPrefixProvider libraryPrefixProvider;

    @GetMapping("/{type}")
    public List<Coder> getCoder(@PathVariable("type") String type) throws Exception {
        String repoName = type +"Repository";
        return (List< Coder>)beanFactory.getBean(repoName, CoderRepository.class).findAll();
    }
    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex){
        ex.printStackTrace();
    }

    @GetMapping("/definition/{type}")
    public List<CoderDefinition> readCodersDefinition(@PathVariable("type") String type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = ResourceUtils.getFile("classpath:coders/"+type+"Coders.json");
        List<CoderDefinition> map = mapper.readValue(file, new TypeReference<List<CoderDefinition>>(){});
        return map;
    }
}
