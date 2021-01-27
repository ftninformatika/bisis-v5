package com.ftninformatika.bisis.nabavka.services;
import com.ftninformatika.bisis.nabavka.model.*;
import com.ftninformatika.bisis.nabavka.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/distributors")
public class DistributorController {

    @Autowired
    DistributorRepository distRep;

    @Autowired
    OfferRepository offerRep;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception ex) {
    }

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public List<Distributor> getAllDistributors(){
      return distRep.findAll();
    }

    @RequestMapping(value = "get/{pib}", method = RequestMethod.GET)
    public Distributor getDistributor(@PathVariable(value = "pib") String pib){
        return distRep.getDistributorByPib(pib);
    }
    @RequestMapping(value = "addUpdate", method = RequestMethod.POST)
    public List<Distributor> addDistributor(@RequestBody Distributor d) {
        distRep.save(d);
        Offer offer = offerRep.findOfferByDistributor(d.get_id());
        if (offer == null){
            Offer o = new Offer();
            o.setDistributor(d.get_id());
            offerRep.save(o);
        }
        return distRep.findAll();
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public List<Distributor> removeDistributor(@RequestBody String id) {
        Optional<Distributor> d = distRep.findById(id);
        distRep.deleteById(id);
        Offer offer = offerRep.findOfferByDistributor(id);
        offerRep.delete(offer);
        return distRep.findAll();
    }

    @RequestMapping(value = "addOffer", method = RequestMethod.POST)
    public Offer addOffer(@RequestBody Offer o) {
        offerRep.save(o);
        return offerRep.findOfferByDistributor(o.getDistributor());

    }
    @RequestMapping(value = "getOffersByPib/{pib}", method = RequestMethod.GET)
    public Offer getOffersByPib(@PathVariable(value = "pib") String pib){
        return offerRep.findOfferByDistributor(pib);
    }
    @RequestMapping(value = "deleteOffersByPib/{pib}", method = RequestMethod.DELETE)
    public boolean deleteOffersByPib(@PathVariable(value = "pib") String pib){
        try {
            Offer o = offerRep.findOfferByDistributor(pib);
            if (o!=null && o.getItems()!=null) {
                o.getItems().clear();
                offerRep.save(o);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

}
