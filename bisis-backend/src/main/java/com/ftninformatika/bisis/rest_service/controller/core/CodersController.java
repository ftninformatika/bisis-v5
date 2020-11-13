package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.librarian.db.ProcessTypeDB;
import com.ftninformatika.bisis.librarian.dto.ProcessTypeDTO;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.circ.*;
import com.ftninformatika.bisis.coders.*;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.*;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dboberic on 26/07/2017.
 */
@RestController
@RequestMapping("/coders")
public class CodersController {

    @Autowired AcquisitionRepository acqrep;
    @Autowired AvailabilityRepository availrep;
    @Autowired AccessionRegisterRepository accregrep;
    @Autowired BindingRepository bindrep;
    @Autowired FormatRepository formrep;
    @Autowired InternalMarkRepository intmrep;
    @Autowired ItemStatusRepository statrep;
    @Autowired LocationRepository locrep;
    @Autowired SublocationRepository sublocrep;
    @Autowired TaskRepository taskrep;
    @Autowired EducationLvlRepository edurep;
    @Autowired LanguageRepository langrep;
    @Autowired MembershipRepository mbrshiprep;
    @Autowired MembershipTypeRepository mbrtyperep;
    @Autowired PlaceRepository placerep;
    @Autowired UserCategRepository usrcategrep;
    @Autowired WarningTypeRepository warnrep;
    @Autowired WarningCounterRepository warncountrep;
    @Autowired OrganizationRepository orgrep;
    @Autowired ProcessType2Repository processTypeRepository;
    @Autowired CircLocationRepository circLocationRepository;
    @Autowired CorporateMemberRepository corporateMemberRepository;
    @Autowired LibraryConfigurationRepository libraryConfigurationRepository;
    @Autowired CounterRepository counterRepository;

    @RequestMapping( path = "process_types")
    public ProcessTypeDB addProcessType(@RequestBody ProcessTypeDB pt){
      //  ProcessTypeDTO processTypeDTO = processTypeRepository.findByNameAndLibName(pt.getName(),pt.getLibName());
        return processTypeRepository.save(pt);
    }

    @RequestMapping(path = "process_types/getByLibrary")
    public List<ProcessTypeDB> getProcessTypesForLibrary(@RequestParam (value = "libName") String libName){
        return processTypeRepository.getProcessTypesByLibNameIsNullOrLibName(libName);
    }

    @RequestMapping(path = "lib_configurations")
    public List<LibraryConfiguration> getConfigs(String libName){
        return libraryConfigurationRepository.findAll();
    }

    @RequestMapping(path = "accession_register")
    public List<AccessionRegister> getAccessionRegs(@RequestHeader("Library") String libName){
        return accregrep.getCoders(libName);
    }

    @RequestMapping(path = "acquisiton_type")
    public List<Acquisition> getAcquisitonTypes(String libName){
        return acqrep.getCoders(libName);
    }

    @RequestMapping(path = "availability")
    public List<Availability> getAvailabilities(String libName){
        return availrep.getCoders(libName);
    }

    @RequestMapping(path = "binding")
    public List<Binding> getBindings(String libName){
        return bindrep.getCoders(libName);
    }

    @RequestMapping(path = "format")
    public List<Format> getFormats(String libName){
        return formrep.getCoders(libName);
    }

    @RequestMapping(path = "internal_mark")
    public List<InternalMark> getInterMarks(String libName){
        return intmrep.getCoders(libName);
    }

    @RequestMapping(path = "item_status")
    public List<ItemStatus> getStatuses(@RequestHeader("Library") String libName){
        return statrep.getCoders(libName);
    }

    @RequestMapping(path = "location")
    public List<Location> getLocations(@RequestHeader("Library") String libName){
        return locrep.getCoders(libName);
    }

    @RequestMapping(path = "sublocation")
    public List<Sublocation> getSublocations(@RequestHeader("Library") String libName){
        return sublocrep.getCoders(libName);
    }

    @RequestMapping(path = "sublocation/get_by_coder_id")
    public Sublocation getSublocationByCoderId(@RequestParam("coderId") String coderId, @RequestParam("lib") String lib) {
        Sublocation sl = sublocrep.getByCoder_Id(coderId, lib);
        if (sl != null && sl.getDescription() != null) {
            sl.setDescription(LatCyrUtils.toCyrillic(sl.getDescription()));
        }
        return sl;
    }

    @RequestMapping(path = "sublocation/get_by_location")
    public List<Sublocation> getSublocationsByLocation(@RequestParam("loc") String loc, @RequestParam("lib") String lib) {
        List<Sublocation> retVal = sublocrep.getCoders(lib).stream().filter(s -> s.getCoder_id().startsWith(loc)).collect(Collectors.toList());
        System.out.println(retVal);
        return retVal;
    }

    @RequestMapping(path = "tasks")
    public List<Sublocation> getTasks(String libName){
        return taskrep.getCoders(libName);
    }

    //coders from circ------------------------------------

    @RequestMapping(path = "education")
    public List<EducationLvl> getEducations(String libName){
        return edurep.getCoders(libName);
    }

    @RequestMapping(path = "education", method = RequestMethod.POST)
    public ArrayList<Object> insertUpdateEduLvl(@RequestBody EducationLvl educationLvl){
        ArrayList<Object> retVal = new ArrayList<>();
        EducationLvl el = edurep.save(educationLvl);
        retVal.add(0, el.get_id());
        retVal.add(1, el.getDescription());
        return retVal;
    }

    @RequestMapping(path = "education/delete")
    public Boolean deleteEduLvl(@RequestParam ("_id") String place_id){
        edurep.deleteById(place_id);
        return edurep.findById(place_id).get() == null;
    }

    @RequestMapping(path = "language")
    public List<Language> getLanguages(String libName){
        return langrep.getCoders(libName);
    }

    @RequestMapping(path = "place")
    public List<Place> getPlaces(String libName){
        return placerep.getCoders(libName);
    }

    @RequestMapping(path = "place/delete")
    public Boolean deletePlace(@RequestParam("_id") String place_id){
        placerep.deleteById(place_id);
        return placerep.findById(place_id).get() == null;
    }

    @RequestMapping(path = "place", method = RequestMethod.POST)
    public ArrayList<Object> insertEditPlace(@RequestBody Place newPlace) {
        ArrayList<Object> retVal = new ArrayList<>();
        Place place = placerep.save(newPlace);
        retVal.add(0, place.get_id());
        retVal.add(1, place.getZip());
        retVal.add(2, place.getCity());
        return retVal;
    }

    @RequestMapping(path = "membership")
    public List<Membership> getMemberships(String libName){
        return mbrshiprep.getCoders(libName);
    }

    @RequestMapping(path = "membership_type")
    public List<MembershipType> getMembershipTyspes(String libName){
        return mbrtyperep.getCoders(libName);
    }

    @RequestMapping(path = "user_category")
    public List<UserCategory> getUserCategs(String libName){
        return usrcategrep.getCoders(libName);
    }

    @RequestMapping(path = "warning_type")
    public List<WarningType> getWarningTypes(String libName){return warnrep.getCoders(libName);}

    @RequestMapping(path = "warning_counter")
    public List<WarningCounter> getWarningCounters(String libName){return warncountrep.getCoders(libName);}

    @RequestMapping(path = "warning_counter", method = RequestMethod.POST)
    public ArrayList<Object> insertEditWarningCounters(@RequestBody WarningCounter newWC){
        warncountrep.save(newWC);
        ArrayList<Object> retVal = new ArrayList<>();
        retVal.add(0, newWC.get_id());
        retVal.add(1, newWC.getWarnYear());
        retVal.add(2, newWC.getWarningType());
        retVal.add(3, newWC.getLastNo());
        return retVal;
    }

    @RequestMapping(path = "warning_counter/delete")
    public Boolean deleteWarningCounters(@RequestParam("_id") String wc_id){
        warncountrep.deleteById(wc_id);
        return  warncountrep.findById(wc_id).get() == null;
    }

    @RequestMapping(path = "organization")
    public List<Organization> getOrganizations(String libName){return orgrep.getCoders(libName);}

    @RequestMapping(path = "organization", method = RequestMethod.POST)
    public ArrayList<Object> insertEditOrganization(@RequestBody Organization organization){
        ArrayList<Object> retVal = new ArrayList<>();
        Organization org = orgrep.save(organization);
        retVal.add(0, org.get_id());
        retVal.add(1, org.getName());
        retVal.add(2, org.getAddress());
        retVal.add(3, org.getCity());
        retVal.add(4, org.getZip());
        return retVal;
    }

    @RequestMapping(path = "organization/delete")
    public Boolean deleteOrganization(@RequestParam("_id") String org_id){
        orgrep.deleteById(org_id);
        return orgrep.findById(org_id).get() == null;
    }

    @RequestMapping(path = "circlocation")
    public List<CircLocation> getCircLocations(String libName){
        return circLocationRepository.getCoders(libName);
    }

    @RequestMapping(path = "corporatemember")
    public List<CorporateMember> getCorporateMembers(String libName){
        return corporateMemberRepository.getCoders(libName);
    }

    @RequestMapping(path = "counters")
    public List<Counter> getCounters(String libName){
        return counterRepository.getCoders(libName);
    }

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

    @RequestMapping(path = "/addWarningType", method = RequestMethod.POST)
    public Boolean addWarningType(@RequestBody WarningType warningType){
        try {
            warnrep.save(warningType);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
