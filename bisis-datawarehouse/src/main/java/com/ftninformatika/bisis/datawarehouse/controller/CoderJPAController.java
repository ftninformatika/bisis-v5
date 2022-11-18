package com.ftninformatika.bisis.datawarehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftninformatika.bisis.datawarehouse.entity.Coder;
import com.ftninformatika.bisis.datawarehouse.model.CoderDefinition;
import com.ftninformatika.bisis.datawarehouse.repository.CoderRepository;
import com.ftninformatika.utils.LibraryPrefixProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("coders")
public class CoderJPAController {

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    LibraryPrefixProvider libraryPrefixProvider;

    @GetMapping("/get/{type}")
    public List<Coder> getCoder(@PathVariable("type") String type) throws Exception {
        String repoName = type +"JPARepository";
         return (List< Coder>)beanFactory.getBean(repoName, CoderRepository.class).findByLibraryIsNullOrLibrary(libraryPrefixProvider.getLibPrefix());
    }
    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex){
        ex.printStackTrace();
    }

    @GetMapping("/{type}")
    public List<CoderDefinition> readCodersDefinition(@PathVariable("type") String type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = ResourceUtils.getFile("classpath:"+type+"Coders.json");
        List<CoderDefinition> map = mapper.readValue(file, List.class);
        return map;

    }
}