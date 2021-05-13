package com.ftninformatika.bisis.admin.coders;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.*;
import com.ftninformatika.bisis.coders.*;
import com.ftninformatika.bisis.format.UItem;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CodersHelper {

    public boolean isValidOdeljenje(String code){
        if(this.locations.size()>0)
            return locations.containsKey(code);
        else return true;
    }



    public boolean isValidFormat(String code){
        return this.formats.containsKey(code);
    }

    public boolean isValidStatus(String code){
        return itemStatuses.containsKey(code);
    }

    public boolean isValidPovez(String code){
        return bindings.containsKey(code);
    }

    public boolean isValidPodlokacija(String code){
        return sublocations.containsKey(code);
    }

    public boolean isValidNacinNabavke(String code){
        return acquisitionTypes.containsKey(code);
    }

    public boolean isValidInternaOznaka(String code){
        return internalMarks.containsKey(code);
    }

    public boolean isValidDostupnost(String code){
        return availabilities.containsKey(code);
    }

    public boolean isValid992b(String code){
       return tasks.containsKey(code);

    }

    public boolean isValidLibrarian(String code){
        return librarians.containsKey(code);
    }


    public List<String> getLocationsList() {
        List<String> retVal = new ArrayList<>();
        for (Map.Entry<String, Location> l: locations.entrySet()){
            String loc = l.getKey() + " - " + l.getValue().getDescription();
            retVal.add(loc);
        }
        retVal.sort((p1, p2) -> p1.compareTo(p2));
        return retVal;
    }

    public List<Location> getLocationsList2() {
        List<Location> retVal = new ArrayList<>();
        for (Map.Entry<String, Location> l : locations.entrySet()) {
            retVal.add(l.getValue());
        }
        retVal.sort(Comparator.comparing(Coder::getCoder_id));
        return retVal;
    }

    public String getLocationCodeByName(String locName){
        for (Map.Entry<String, Location> l: locations.entrySet()){
            if (l.getValue().getDescription().equals(locName))
                return l.getKey();
        }
        return null;
    }

    public String getLocationCodeByNameExtended(String locName){
        if (locName.trim().equals(""))
            return null;

        locName = locName.split("-")[1].trim();
        for (Map.Entry<String, Location> l: locations.entrySet()){
            if (l.getValue().getDescription().equals(locName))
                return l.getKey();
        }
        return null;
    }

    //za koder frejmove
    public ArrayList<ArrayList<Object>> getCoderTableModelList(String coderName){
        ArrayList<ArrayList<Object>> retVal = new ArrayList<>();


        if (coderName.equals("warn_counters")){
            for(WarningCounter i: warningCounters){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.get_id());
                l.add(i.getWarnYear());
                l.add(i.getWarningType());
                l.add(i.getLastNo());
                retVal.add(l);
            }
        }

        if (coderName.equals("places")){
            for(Place i: places){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.get_id());
                l.add(i.getZip());
                l.add(i.getCity());
                retVal.add(l);
            }
        }

        if (coderName.equals("location")){
            for(CircLocation i: circLocations){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.getLocationCode());
                l.add(i.getDescription());
                l.add(i.getLastUserId());
                retVal.add(l);
            }
        }

        if (coderName.equals("organization")){
            for(Organization i: organizations){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.get_id());
                l.add(i.getName());
                l.add(i.getAddress());
                l.add(i.getCity());
                l.add(i.getZip());
                retVal.add(l);
            }
        }

        if (coderName.equals("languages")){
            for(Language i: languages){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.get_id());
                l.add(i.getDescription());
                retVal.add(l);
            }
        }

        if (coderName.equals("edu_lvl")){
            for(EducationLvl i: educationLevels){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.get_id());
                l.add(i.getDescription());
                retVal.add(l);
            }
        }

        if (coderName.equals("mmbr_types")){
            for(MembershipType i: membershipTypes){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.get_id());
                l.add(i.getDescription());
                l.add(i.getPeriod());
                retVal.add(l);
            }
        }


        if (coderName.equals("user_categs"))
                for(UserCategory uc: userCategories){
                    ArrayList<Object> l = new ArrayList<>();
                    l.add(uc.get_id());
                    l.add(uc.getDescription());
                    l.add(uc.getTitlesNo());
                    l.add(uc.getPeriod());
                    l.add(uc.getMaxPeriod());
                    retVal.add(l);
                }

        if (coderName.equals("Status_Primerka")) {
                for (ItemStatus itemStatus : itemStatuses.values()) {
                    ArrayList<Object> l = new ArrayList<>();
                    l.add(itemStatus.getCoder_id());
                    l.add(itemStatus.getDescription());
                    l.add(itemStatus.isLendable());
                    retVal.add(l);
                }
        }

        if (coderName.equals("Odeljenje")){
                for(Location i: locations.values()){
                    ArrayList<Object> l = new ArrayList<>();
                    l.add(i.getCoder_id());
                    l.add(i.getDescription());
                    retVal.add(l);
                }
        }

        if (coderName.equals("Invknj")){
            for(AccessionRegister i: accessionRegs.values()){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.getCoder_id());
                l.add(i.getDescription());
                retVal.add(l);
            }
        }

        if (coderName.equals("SigFormat")){
            for(Format i: formats.values()){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.getCoder_id());
                l.add(i.getDescription());
                retVal.add(l);
            }
        }

        if (coderName.equals("Povez")){
            for(Binding i: bindings.values()){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.getCoder_id());
                l.add(i.getDescription());
                retVal.add(l);
            }
        }

        if (coderName.equals("Podlokacija")){
            for(Sublocation i: sublocations.values()){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.getCoder_id());
                l.add(i.getDescription());
                retVal.add(l);
            }
        }

        if (coderName.equals("Nacin_nabavke")){
            for(Acquisition i: acquisitionTypes.values()){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.getCoder_id());
                l.add(i.getDescription());
                retVal.add(l);
            }
        }
        if (coderName.equals("Interna_oznaka")){
            for(InternalMark i: internalMarks.values()){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.getCoder_id());
                l.add(i.getDescription());
                retVal.add(l);
            }
        }

        if (coderName.equals("Counters")){
            for(Counter i: counters.values()){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.getCounterName());
                l.add(i.getCounterValue());
                retVal.add(l);
            }
        }
        if (coderName.equals("Task")){
            for(Task i: tasks.values()){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.getCoder_id());
                l.add(i.getDescription());
                retVal.add(l);
            }
        }

        return retVal;
    }

    public void filterCodersByDepartment(String department) {

        if (department != null) {
            try {
                List<Sublocation> sublocCoders = BisisApp.bisisService.getSubLocations(BisisApp.appConfig.getLibrary()).execute().body();
                List<Location> locCoders = BisisApp.bisisService.getLocations(BisisApp.appConfig.getLibrary()).execute().body();

                sublocations = sublocCoders.stream()
                        .filter(i -> i.getDepartment() == null || i.getDepartment().equals(department))
                        .collect(Collectors.toMap(Sublocation::getCoder_id, i -> i));

                locations = locCoders.stream()
                        .filter(i -> i.getDepartment() == null || i.getDepartment().equals(department))
                        .collect(Collectors.toMap(Location::getCoder_id, i -> i));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void init() {

        try {
            List<AccessionRegister> accRegCoders=BisisApp.bisisService.getAccessionRegs(BisisApp.appConfig.getLibrary()).execute().body();
            List<Acquisition> acqCoders=BisisApp.bisisService.getAcquisitonTypes(BisisApp.appConfig.getLibrary()).execute().body();
            List<Availability> availCoders=BisisApp.bisisService.getAvailabilities(BisisApp.appConfig.getLibrary()).execute().body();
            List<Binding> binCoders=BisisApp.bisisService.getBindings(BisisApp.appConfig.getLibrary()).execute().body();
            List<Format> formCoders=BisisApp.bisisService.getFormats(BisisApp.appConfig.getLibrary()).execute().body();
            List<InternalMark> intmCoders=BisisApp.bisisService.getInterMarks(BisisApp.appConfig.getLibrary()).execute().body();
            List<ItemStatus> stCoders=BisisApp.bisisService.getStatusCoders(BisisApp.appConfig.getLibrary()).execute().body();
            List<Sublocation> sublocCoders=BisisApp.bisisService.getSubLocations(BisisApp.appConfig.getLibrary()).execute().body();
            List<Location> locCoders=BisisApp.bisisService.getLocations(BisisApp.appConfig.getLibrary()).execute().body();
            List<Counter> countersCoders = BisisApp.bisisService.getCounters(BisisApp.appConfig.getLibrary()).execute().body();
            List<Task> tasksCoders = BisisApp.bisisService.getTasks(BisisApp.appConfig.getLibrary()).execute().body();
            List<LibrarianDB> librariansCoders = BisisApp.bisisService.getAllLibrarinasInThisLibrary(BisisApp.appConfig.getLibrary()).execute().body();

            accessionRegs = accRegCoders.stream().collect(Collectors.toMap(AccessionRegister::getCoder_id, i -> i));
            acquisitionTypes = acqCoders.stream().collect(Collectors.toMap(Acquisition::getCoder_id, i -> i));
            availabilities = availCoders.stream().collect(Collectors.toMap(Availability::getCoder_id, i -> i));
            bindings = binCoders.stream().collect(Collectors.toMap(Binding::getCoder_id, i -> i));
            formats = formCoders.stream().collect(Collectors.toMap(Format::getCoder_id, i -> i));
            internalMarks = intmCoders.stream().collect(Collectors.toMap(InternalMark::getCoder_id, i -> i));
            itemStatuses = stCoders.stream().collect(Collectors.toMap(ItemStatus::getCoder_id, i -> i));
            locations = locCoders.stream().collect(Collectors.toMap(Location::getCoder_id, i -> i));
            sublocations = sublocCoders.stream().collect(Collectors.toMap(Sublocation::getCoder_id, i -> i));
            counters = countersCoders.stream().collect(Collectors.toMap(Counter::getCounterName, i -> i));
            tasks = tasksCoders.stream().collect(Collectors.toMap(Task::getCoder_id, i -> i));
            librarians = librariansCoders.stream().collect(Collectors.toMap(LibrarianDB::getUsername, i -> i));

            //circkulacija
            circLocations = BisisApp.bisisService.getCircLocations(BisisApp.appConfig.getLibrary()).execute().body();
            corporateMembers = BisisApp.bisisService.getCorporateMembers(BisisApp.appConfig.getLibrary()).execute().body();
            educationLevels = BisisApp.bisisService.getEducationLvls(BisisApp.appConfig.getLibrary()).execute().body();
            languages = BisisApp.bisisService.getLanguages(BisisApp.appConfig.getLibrary()).execute().body();
            places = BisisApp.bisisService.getPlaces(BisisApp.appConfig.getLibrary()).execute().body();
            memberships = BisisApp.bisisService.getMemberships(BisisApp.appConfig.getLibrary()).execute().body();
            membershipTypes = BisisApp.bisisService.getMembershipTypes(BisisApp.appConfig.getLibrary()).execute().body();
            userCategories = BisisApp.bisisService.getUserCategories(BisisApp.appConfig.getLibrary()).execute().body();
            warningTypes = BisisApp.bisisService.getWarningTypes(BisisApp.appConfig.getLibrary()).execute().body();
            warningCounters = BisisApp.bisisService.getWarningCounters(BisisApp.appConfig.getLibrary()).execute().body();
            organizations = BisisApp.bisisService.getOrganizations(BisisApp.appConfig.getLibrary()).execute().body();

            //circLocations = circLocationList.stream().collect(Collectors.toMap(CircLocation::get_id, i-> i));
            //corporateMembers = corporateMemberList.stream().collect(Collectors.toMap(CorporateMember::get_id, i-> i));
            //educationLevels = educationLvlList.stream().collect(Collectors.toMap(EducationLvl::get_id, i-> i));
            //languages = languageList.stream().collect(Collectors.toMap(Language::get_id, i-> i));
            //places = placeList.stream().collect(Collectors.toMap(Place::get_id, i-> i));
            //memberships = membershipList.stream().collect(Collectors.toMap(Membership::get_id, i-> i));
            //membershipTypes = membershipTypeList.stream().collect(Collectors.toMap(MembershipType::get_id, i-> i));
            //userCategories = userCategoryList.stream().collect(Collectors.toMap(UserCategory::get_id, i-> i));
            //warningTypes = warningTypesList.stream().collect(Collectors.toMap(WarningType::get_id, i-> i));
            //warningCounters = warningCounterList.stream().collect(Collectors.toMap(WarningCounter::get_id, i-> i));
            //organizations = organizationList.stream().collect(Collectors.toMap(Organization::get_id, i-> i));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getValueFromList(List<UItem> sif_list,String code){
        for(UItem ui:sif_list){
            if(ui.getCode().equals(code)) return ui.getValue();
        }
        return null;
    }

    public  String getValue(int coder_ref, String code){
        switch(coder_ref){
            case ODELJENJE_CODER: return getValueFromList(this.getCoderUItemList(ODELJENJE_CODER), code);
            case FORMAT_CODER: return getValueFromList(this.getCoderUItemList(FORMAT_CODER), code);
            case STATUS_CODER: return getValueFromList(this.getCoderUItemList(STATUS_CODER), code);
            case POVEZ_CODER: return getValueFromList(this.getCoderUItemList(POVEZ_CODER), code);
            case PODLOKACIJA_CODER: return getValueFromList(this.getCoderUItemList(PODLOKACIJA_CODER), code);
            case NACINNABAVKE_CODER: return getValueFromList(this.getCoderUItemList(NACINNABAVKE_CODER), code);
            case INTERNAOZNAKA_CODER: return getValueFromList(this.getCoderUItemList(INTERNAOZNAKA_CODER), code);
            case INVENTARNAKNJIGA_CODER: return getValueFromList(this.getCoderUItemList(INVENTARNAKNJIGA_CODER), code);
            case DOSTUPNOST_CODER: return getValueFromList(this.getCoderUItemList(DOSTUPNOST_CODER), code);
            case TASK_CODER: return getValueFromList(this.getCoderUItemList(TASK_CODER), code);
            case LIBRARIAN_CODER: return getValueFromList(this.getCoderUItemList(LIBRARIAN_CODER), code);
        }
        return null;

    }

    public ArrayList<UItem> getCoderUItemList(int coderCode){
        List<UItem> retVal = null;
        switch (coderCode){
            case ODELJENJE_CODER: retVal = new ArrayList<Location>(locations.values()).stream().map(i -> new UItem(i.getCoder_id(), i.getDescription())).collect(Collectors.toList()); break;
            case FORMAT_CODER: retVal = new ArrayList<Format>(formats.values()).stream().map(i -> new UItem(i.getCoder_id(), i.getDescription())).collect(Collectors.toList());break;
            case STATUS_CODER: retVal = new ArrayList<ItemStatus>(itemStatuses.values()).stream().map(i -> new UItem(i.getCoder_id(), i.getDescription())).collect(Collectors.toList());break;
            case POVEZ_CODER: retVal = new ArrayList<Binding>(bindings.values()).stream().map(i -> new UItem(i.getCoder_id(), i.getDescription())).collect(Collectors.toList());break;
            case PODLOKACIJA_CODER: retVal = new ArrayList<Sublocation>(sublocations.values()).stream().map(i -> new UItem(i.getCoder_id(), i.getDescription())).collect(Collectors.toList());break;
            case NACINNABAVKE_CODER: retVal = new ArrayList<Acquisition>(acquisitionTypes.values()).stream().map(i -> new UItem(i.getCoder_id(), i.getDescription())).collect(Collectors.toList());break;
            case INTERNAOZNAKA_CODER: retVal = new ArrayList<InternalMark>(internalMarks.values()).stream().map(i -> new UItem(i.getCoder_id(), i.getDescription())).collect(Collectors.toList());break;
            case INVENTARNAKNJIGA_CODER: retVal = new ArrayList<AccessionRegister>(accessionRegs.values()).stream().map(i -> new UItem(i.getCoder_id(), i.getDescription())).collect(Collectors.toList());break;
            case DOSTUPNOST_CODER: retVal = new ArrayList<Availability>(availabilities.values()).stream().map(i -> new UItem(i.getCoder_id(), i.getDescription())).collect(Collectors.toList());break;
            case TASK_CODER: retVal = new ArrayList<Task>(tasks.values()).stream().map(i -> new UItem(i.getCoder_id(), i.getDescription())).collect(Collectors.toList());break;
            case LIBRARIAN_CODER: retVal = new ArrayList<LibrarianDB>(librarians.values()).stream().map(i -> new UItem(i.getUsername(), i.getIme()+" "+i.getPrezime())).collect(Collectors.toList());

        }
        return (ArrayList<UItem>) retVal;
    }

    public void refreshWarningCounters() {
        try {
            warningCounters = BisisApp.bisisService.getWarningCounters(BisisApp.appConfig.getLibrary()).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, AccessionRegister> accessionRegs = new HashMap<>();
    private Map<String, Acquisition> acquisitionTypes = new HashMap<>();
    private Map<String, Availability> availabilities = new HashMap<>();
    private Map<String, Binding> bindings = new HashMap<>();
    private Map<String, Format> formats = new HashMap<>();
    private Map<String, InternalMark> internalMarks = new HashMap<>();
    private Map<String, ItemStatus> itemStatuses = new HashMap<>();
    private Map<String, Sublocation> sublocations = new HashMap<>();
    private Map<String, Location> locations = new HashMap<>();
    private Map<String, Counter> counters = new HashMap<>();
    private Map<String, Task> tasks = new HashMap<>();
    private Map<String, LibrarianDB> librarians = new HashMap<>();

    //circkulacija-----------------
    private List<CircLocation> circLocations = new ArrayList<>();
    private List<CorporateMember> corporateMembers = new ArrayList<>();
    private List<EducationLvl> educationLevels = new ArrayList<>();
    private List<Language> languages = new ArrayList<>();
    private List<Membership> memberships = new ArrayList<>();
    private List<MembershipType> membershipTypes = new ArrayList<>();
    private List<Organization> organizations = new ArrayList<>();
    private List<Place> places = new ArrayList<>();
    private List<UserCategory> userCategories = new ArrayList<>();
    private List<WarningType> warningTypes = new ArrayList<>();
    private List<WarningCounter> warningCounters = new ArrayList<>();

    public static final int ODELJENJE_CODER = 				0;
    public static final int FORMAT_CODER =    				1;
    public static final int STATUS_CODER =    				2;
    public static final int POVEZ_CODER =     				3;
    public static final int PODLOKACIJA_CODER =   		    4;
    public static final int NACINNABAVKE_CODER =  		    5;
    public static final int INTERNAOZNAKA_CODER =   	    6;
    public static final int INVENTARNAKNJIGA_CODER =	    7;
    public static final int DOSTUPNOST_CODER =     		    8;
    public static final int TASK_CODER =     		        9;
    public static final int LIBRARIAN_CODER =     		    10;
}
