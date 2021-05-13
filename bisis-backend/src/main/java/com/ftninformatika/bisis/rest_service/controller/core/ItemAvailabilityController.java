package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.core.repositories.ItemAvailabilityRepository;
import com.ftninformatika.bisis.records.ItemAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Petar on 10/19/2017.
 */
@RestController
@RequestMapping("/itemAvailabilities")
public class ItemAvailabilityController {

    @Autowired
    ItemAvailabilityRepository itemAvailabilityRepository;

    @RequestMapping( value = "/getByCtlgNo", method = RequestMethod.GET)
    public ItemAvailability getItemAvailabilityByCtlgNo(@RequestParam("ctlgno") String ctlgno){
        return itemAvailabilityRepository.getByCtlgNo(ctlgno);
    }
}
