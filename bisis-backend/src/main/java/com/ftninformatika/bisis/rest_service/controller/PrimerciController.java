package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Petar on 8/7/2017.
 */
@RestController
@RequestMapping("/primerci")
public class PrimerciController {

    @Autowired private RecordsRepository recordsRepository;
    @Autowired private MemberController memberController;

    @RequestMapping( value = "/lendPrimerak", method = RequestMethod.GET)
    public Record lendPrimerak(@RequestParam (value = "ctlgNo") String ctlgNo, @RequestParam (value = "memberId") String memberId){
        Record retVal = null;

        Member m = memberController.getMember(memberId);
        retVal = recordsRepository.getRecordByPrimerakInvNum(ctlgNo);
        //TODO- sve sto treba kod zaduzivanja
        retVal.getPrimerak(ctlgNo).setStatus("9"); //hardcoded
        Lending l = new Lending(null, memberId, ctlgNo, null, null, null, null, new Date(), null, null, null, null);
        //m.getLendings().add(l);

        memberController.memberRep.save(m);
        recordsRepository.save(retVal);

        return retVal;
    }

    @RequestMapping( method = RequestMethod.GET )
    public List<Primerak> getPrimerci(){
        List<Primerak> retVal = new ArrayList<>();

        List<Record> recs = recordsRepository.findAll();

        for(Record r: recs)
            retVal.addAll(r.getPrimerci());

        return retVal;
    }

    @RequestMapping( value = "/{invNum}", method = RequestMethod.GET)
    public Primerak getPrimerak(@PathVariable String invNum){
        Record r = recordsRepository.getRecordByPrimerakInvNum(invNum);
        if (r != null) {
            return r.getPrimerak(invNum);
        } else {
            return null;
        }
    }

    @RequestMapping( method = RequestMethod.DELETE )
    public boolean deletePrimerak(@RequestBody Primerak primerak){
        Record r = recordsRepository.getRecordByPrimerakInvNum(primerak.getInvBroj());
        if ( r.getPrimerci().remove(r.getPrimerak(primerak.getInvBroj()))){
            recordsRepository.save(r);
            return true;
        }
        return false;
    }

   /* @RequestMapping( value = "/update", method = RequestMethod.PUT )
    public void updatePrimerak(@RequestBody Primerak primerak){
        Record r = recordsRepository.getRecordByPrimerakInvNum(primerak.getInvBroj());
        r.updatePrimerak(primerak);
        recordsRepository.save(r);
    }*/
}
