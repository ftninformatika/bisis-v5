package com.ftninformatika.bisis.acquisition.services;

import com.ftninformatika.bisis.acquisition.model.*;
import com.ftninformatika.bisis.acquisition.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/desideratum")
public class DesideratumController {

    @Autowired
    DesideratumRepository dr;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception ex) {
    }

    @PostMapping("/addOrUpdate")
    public Desideratum addOrUpdate(@RequestBody Desideratum d){
        dr.save(d);
        return d;
    }
    @GetMapping("/getAll")
    public List<Desideratum> getAll(){

        return dr.findAll();
    }

    @GetMapping("/{id}")
    public Desideratum getOne(@PathVariable("id") String id){
        return dr.findById(id).get();
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") String id){
        dr.deleteById(id);
        return true;
    }
}
