package com.ftninformatika.bisis.core.controllers;

import com.ftninformatika.bisis.coders.Coder;
import com.ftninformatika.bisis.core.repositories.CoderRepository;
import com.ftninformatika.utils.LibraryPrefixProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coders")
public class CoderController {

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    LibraryPrefixProvider libraryPrefixProvider;

    @GetMapping("/{type}")
    public List<Coder> getCoder() throws Exception {
       // String repoName = type +"JPARepository";
         return (List< Coder>)beanFactory.getBean("circLocationRepository", CoderRepository.class).findAll();
    }
    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex){
        ex.printStackTrace();
    }

   /* @GetMapping("/definition/{type}")
    public List<CoderDefinition> readCodersDefinition(@PathVariable("type") String type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = ResourceUtils.getFile("classpath:"+type+"Coders.json");
        List<CoderDefinition> map = mapper.readValue(file, List.class);
        return map;

    }*/
}
