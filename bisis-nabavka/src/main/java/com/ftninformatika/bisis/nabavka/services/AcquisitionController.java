package com.ftninformatika.bisis.nabavka.services;

import com.ftninformatika.bisis.nabavka.dto.DesideratumDTO;
import com.ftninformatika.bisis.nabavka.model.*;
import com.ftninformatika.bisis.nabavka.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/acquisition")
public class AcquisitionController {

    @Autowired
    AcquisitionRepository ar;

    @Autowired
    AllocationRepository alr;

    @Autowired
    OfferRepository offerRep;

    @Autowired
    DesideratumRepository dr;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception ex) {
    }

    @PostMapping("/addOrUpdate")
    public Acquisition addOrUpdate(@RequestBody Acquisition a) {
        return ar.save(a);

    }

    @GetMapping("/getAll")
    public List<Acquisition> getAll() {
        Sort sort = new Sort(Sort.Direction.DESC, "startDate");
        List<Acquisition> l = ar.findAll(sort);
        return l;
    }
    @GetMapping("/getLast")
    public Acquisition getLast() {
        Sort sort = new Sort(Sort.Direction.DESC, "startDate");
        List<Acquisition> l = ar.findLastAcquisitionForDistribution(sort);
        return l.get(0);

    }

    @GetMapping("/{id}")
    public Acquisition getOne(@PathVariable("id") String id) {
        return ar.findById(id).get();
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") String id) {
        ar.deleteById(id);
        return true;
    }

    @GetMapping("/allocations")
    public List<Allocation> getAllocations() {
        return alr.findAll();
    }

    @PostMapping("/allocation/addOrUpdate")
    public Allocation addOrUpdate(@RequestBody Allocation a) {
        try {
            alr.save(a);
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping("/allocation/{id}")
    public boolean deleteAllocation(@PathVariable("id") String id) {
        alr.deleteById(id);
        return true;
    }

    @PostMapping("/getOfferedBookByDistributors")
    public List<Item>getOfferedBookByDistributors(@RequestBody List<Distributor> distributors){
        List<Item> offeredItems = new ArrayList<Item>();
        for(Distributor d:distributors){
            Offer o = offerRep.findOfferByDistributor(d.get_id());
            if(o!=null &&  o.getItems()!=null) {
                for (Book b : o.getItems()) {
                    Item i = new Item();
                    DesideratumDTO des = new DesideratumDTO();
                    Price p = new Price();
                    p.setPrice(b.getPrice());
                    p.setRebate(b.getRebate());
                    p.setVat(b.getVat());
                    i.setPrice(p);
                    des.setAuthor(b.getAuthor());
                    des.setTitle(b.getTitle());
                    des.setIsbn(b.getIsbn());
                    des.setPublisher(b.getPublisher());
                    i.setDesideratum(des);
                    offeredItems.add(i);
                }
            }
        }
        return offeredItems;
    }
    private int findAmountForLocation(DesideratumDTO d,String sublocation){
        if(d.getLocations()!=null) {
            for (Location l : d.getLocations()) {
                if (l.getSublocation().equalsIgnoreCase(sublocation)) {
                    return l.getAmount();
                }
            }
        }
        return 0;
    }
    @GetMapping("/updateDesiderata/{id}")
    public boolean updateDesiderata(@PathVariable("id") String id) {
        try {
            Acquisition a = ar.findById(id).get();
            for (AcquisitionGroup ag : a.getAcquisitionGroupsReal()) {
                if (ag.getItems()!=null) {
                    for (Item i : ag.getItems()) {
                        //knjiga iz nabavke
                        DesideratumDTO des1 = i.getDesideratum();
                        //ista knjiga ali u dezideratima
                        Desideratum des2 = dr.findByIsbn(des1.getIsbn());
                        if (des2 != null && des2.getLocations() != null) {
                            for (Location l : des2.getLocations()) {
                                int amount2 = l.getAmount();
                                int amount1 = findAmountForLocation(des1, l.getSublocation());
                                if (amount1 >= amount2) {
                                    l.setAmount(0);
                                } else {
                                    l.setAmount(amount2 - amount1);
                                }
                            }
                            dr.save(des2);
                        }
                    }
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
