package com.ftninformatika.bisis.core.controllers;

import com.ftninformatika.bisis.circ.CorporateMember;
import com.ftninformatika.bisis.circ.WarningType;
import com.ftninformatika.bisis.coders.Coder;
import com.ftninformatika.bisis.coders.Counter;
import com.ftninformatika.bisis.coders.definition.CoderDefinition;
import com.ftninformatika.bisis.coders.definition.Usage;
import com.ftninformatika.bisis.core.config.CoderDefinitionConfig;
import com.ftninformatika.bisis.core.repositories.*;
import com.ftninformatika.bisis.librarian.db.ProcessTypeDB;
import com.ftninformatika.utils.LibraryPrefixProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coders")
public class CoderController {

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    LibraryPrefixProvider libraryPrefixProvider;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CoderDefinitionConfig coderDefinitionConfig;

    @Autowired
    ProcessTypeRepository processTypeRepository;
    @Autowired
    CorporateMemberRepository corporateMemberRepository;
    @Autowired
    CounterRepository counterRepository;
    @Autowired
    WarningTypeRepository warnrep;

    @GetMapping("/{type}")
    public List<Coder> getCoder(@PathVariable("type") String type, @RequestHeader("Library") String libName) throws Exception {
        String repoName = type +"Repository";
        List<Coder> list = (List<Coder>)beanFactory.getBean(repoName, CoderRepository.class).getCoders(libName);
        return list.stream().peek(x -> x.setType(type)).collect(Collectors.toList());
    }

    @GetMapping("/definition/{type}")
    public List<CoderDefinition> readCodersDefinition(@PathVariable("type") String type) throws IOException {
            if (type.equals("circulation")){
                return coderDefinitionConfig.getCirculationCoders();
            }else{
                return coderDefinitionConfig.getRecordCoders();
            }
    }

    @DeleteMapping("/{coderName}/{id}")
    public boolean deleteCoder(@PathVariable("coderName") String coderName, @PathVariable("id") String id, @RequestBody Coder coder) throws IOException {
           List<CoderDefinition> codersDef = coderDefinitionConfig.getAllCoders();
            Optional<CoderDefinition> coderDefinition = codersDef.stream().filter(cd -> cd.getName().equals(coderName)).findFirst();
            if (coderDefinition.isPresent()){
                List<Usage> usages = coderDefinition.get().getUsage();
                boolean exists;
                String query;
                for(Usage u: usages){
                    if (coder.getCoder_id() !=null) {
                        query = String.format(u.getQuery(), coder.getCoder_id());
                    }else{
                        query = String.format(u.getQuery(), coder.getDescription());
                    }
                    BasicQuery mongoQuery = new BasicQuery(query);
                    exists = mongoTemplate.exists(mongoQuery,libraryPrefixProvider.getLibPrefix()+"_"+u.getCollection());
                    if (exists){
                        return false;
                    }
                }
                String repoName = coderDefinition.get().getName() +"Repository";
                if (coder.getLibrary()!= null && coder.getLibrary().equals(libraryPrefixProvider.getLibPrefix())){
                    beanFactory.getBean(repoName, CoderRepository.class).deleteById(id);
                    return true;
                }

            }
            return false;
    }

    @PostMapping("/{type}")
    public ResponseEntity addCoder(@PathVariable("type") String type, @RequestBody Coder coder){
        try{
            String repoName = type +"Repository";
            beanFactory.getBean(repoName, CoderRepository.class).save(coder);
            return ResponseEntity.ok("Успешно додат шифарник.");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @RequestMapping( path = "process_types")
    public ProcessTypeDB addProcessType(@RequestBody ProcessTypeDB pt){
        return processTypeRepository.save(pt);
    }
    @RequestMapping(path = "process_types/getByLibrary")
    public List<ProcessTypeDB> getProcessTypesForLibrary(@RequestParam (value = "libName") String libName){
        return processTypeRepository.getProcessTypesByLibNameIsNullOrLibName(libName);
    }

    @RequestMapping(path = "corporatemember")
    public List<CorporateMember> getCorporateMembers(String libName){
        return corporateMemberRepository.getCoders(libName);
    }

    @RequestMapping(path = "warning_type")
    public List<WarningType> getWarningTypes(String libName){return warnrep.getCoders(libName);}

    /**
     * povecava vrednost brojaca i vraca je nazad
     */
    @RequestMapping(path = "increment_counter")
    public Integer incrementCounter(@RequestHeader("Library") String lib, @RequestParam("counterKey") String counterKey){
        List<Counter> counters = counterRepository.getCoders(lib);
        Counter c = counters.stream().filter(i -> i.getCounterName().equals(counterKey)).findFirst().get();
        c.setCounterValue(c.getCounterValue() + 1);
        counterRepository.save(c);
        return c.getCounterValue();
    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex){
        ex.printStackTrace();
    }

    //ove treba uskladiti sa pozivima sa klijenta i onda ih obrisati odavde

//    @Autowired
//    AcquisitionCoderRepository acqrep;
//    @Autowired
//    AccessionRegisterRepository accregrep;
//    @Autowired InternalMarkRepository intmrep;
//    @Autowired WarningCounterRepository warncountrep;

//    @RequestMapping(path = "accession_register")
//    public List<AccessionRegister> getAccessionRegs(@RequestHeader("Library") String libName){
//        return accregrep.getCoders(libName);
//    }

//    @RequestMapping(path = "acquisiton_type")
//    public List<Acquisition> getAcquisitonTypes(String libName){
//        return acqrep.getCoders(libName);
//    }
//
//
//    @RequestMapping(path = "internal_mark")
//    public List<InternalMark> getInterMarks(String libName){
//        return intmrep.getCoders(libName);
//    }


//    @RequestMapping(path = "warning_counter")
//    public List<WarningCounter> getWarningCounters(String libName){return warncountrep.getCoders(libName);}
//
//    @RequestMapping(path = "counters")
//    public List<Counter> getCounters(String libName){
//        return counterRepository.getCoders(libName);
//    }

}
