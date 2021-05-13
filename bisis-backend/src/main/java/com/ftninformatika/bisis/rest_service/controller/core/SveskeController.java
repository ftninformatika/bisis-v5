package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.records.Godina;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Sveska;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petar on 8/17/2017.
 */
@RestController
@RequestMapping("/sveske")
public class SveskeController {

    @Autowired private RecordsRepository recordsRepository;

    @RequestMapping( method = RequestMethod.GET )
    public List<Sveska> getSveske(){
        List<Sveska> retVal = new ArrayList<>();

        for(Record r: recordsRepository.findAll())
            retVal.addAll(r.getAllSveske());

        return retVal;
    }

    @RequestMapping( value = "/{invNum}", method = RequestMethod.GET )
    public Sveska getSveskaForInvBr(@PathVariable("invNum") String invNum){
        Record r = recordsRepository.getRecordBySveskaInvNum(invNum);
        if (r != null) {
            for (Godina g : r.getGodine())
                if (g.getSveska(invNum) != null)
                    return g.getSveska(invNum);
        }
        return null;
    }


}
