package com.ftninformatika.bisis.coders;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.format.UItem;
import com.ftninformatika.bisis.models.circ.*;
import com.ftninformatika.bisis.models.coders.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.IOException;
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

  /*  public boolean isValid992b(String code){
        itemStatuses.containsKey(code);
    }
*/
    /*public boolean isValidLibrarian(String code){
        itemStatuses.containsKey(code);
    }*/




    //za koder frejmove
    public ArrayList<ArrayList<Object>> getCoderTableModelList(String coderName){
        ArrayList<ArrayList<Object>> retVal = new ArrayList<>();


        if (coderName.equals("location")){
            for(Location i: locations.values()){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.getCoder_id());
                l.add(i.getDescription());
                retVal.add(l);
            }
        }

        if (coderName.equals("organization")){
            for(Organization i: organizations.values()){
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
            for(Language i: languages.values()){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.get_id());
                l.add(i.getDescription());
                retVal.add(l);
            }
        }

        if (coderName.equals("edu_lvl")){
            for(EducationLvl i: educationLevels.values()){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.get_id());
                l.add(i.getDescription());
                retVal.add(l);
            }
        }

        if (coderName.equals("mmbr_types")){
            for(MembershipType i: membershipTypes.values()){
                ArrayList<Object> l = new ArrayList<>();
                l.add(i.get_id());
                l.add(i.getDescription());
                l.add(i.getPeriod());
                retVal.add(l);
            }
        }


        if (coderName.equals("user_categs"))
                for(UserCategory uc: userCategories.values()){
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

        return retVal;
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

            accessionRegs = accRegCoders.stream().collect(Collectors.toMap(AccessionRegister::getCoder_id, i -> i));
            acquisitionTypes = acqCoders.stream().collect(Collectors.toMap(Acquisition::getCoder_id, i -> i));
            availabilities = availCoders.stream().collect(Collectors.toMap(Availability::getCoder_id, i -> i));
            bindings = binCoders.stream().collect(Collectors.toMap(Binding::getCoder_id, i -> i));
            formats = formCoders.stream().collect(Collectors.toMap(Format::getCoder_id, i -> i));
            internalMarks = intmCoders.stream().collect(Collectors.toMap(InternalMark::getCoder_id, i -> i));
            itemStatuses = stCoders.stream().collect(Collectors.toMap(ItemStatus::getCoder_id, i -> i));
            locations = locCoders.stream().collect(Collectors.toMap(Location::getCoder_id, i -> i));
            sublocations = sublocCoders.stream().collect(Collectors.toMap(Sublocation::getCoder_id, i -> i));

            //circkulacija
            List<CircLocation> circLocationList = BisisApp.bisisService.getCircLocations(BisisApp.appConfig.getLibrary()).execute().body();
            List<CorporateMember> corporateMemberList = BisisApp.bisisService.getCorporateMembers(BisisApp.appConfig.getLibrary()).execute().body();
            List<EducationLvl> educationLvlList = BisisApp.bisisService.getEducationLvls(BisisApp.appConfig.getLibrary()).execute().body();
            List<Language> languageList = BisisApp.bisisService.getLanguages(BisisApp.appConfig.getLibrary()).execute().body();
            List<Place> placeList = BisisApp.bisisService.getPlaces(BisisApp.appConfig.getLibrary()).execute().body();
            List<Membership> membershipList = BisisApp.bisisService.getMemberships(BisisApp.appConfig.getLibrary()).execute().body();
            List<MembershipType> membershipTypeList = BisisApp.bisisService.getMembershipTypes(BisisApp.appConfig.getLibrary()).execute().body();
            List<UserCategory> userCategoryList = BisisApp.bisisService.getUserCategories(BisisApp.appConfig.getLibrary()).execute().body();
            List<WarningType> warningTypesList = BisisApp.bisisService.getWarningTypes(BisisApp.appConfig.getLibrary()).execute().body();
            List<WarningCounter> warningCounterList = BisisApp.bisisService.getWarningCounters(BisisApp.appConfig.getLibrary()).execute().body();
            List<Organization> organizationList = BisisApp.bisisService.getOrganizations(BisisApp.appConfig.getLibrary()).execute().body();

            circLocations = circLocationList.stream().collect(Collectors.toMap(CircLocation::get_id, i-> i));
            corporateMembers = corporateMemberList.stream().collect(Collectors.toMap(CorporateMember::get_id, i-> i));
            educationLevels = educationLvlList.stream().collect(Collectors.toMap(EducationLvl::get_id, i-> i));
            languages = languageList.stream().collect(Collectors.toMap(Language::get_id, i-> i));
            places = placeList.stream().collect(Collectors.toMap(Place::get_id, i-> i));
            memberships = membershipList.stream().collect(Collectors.toMap(Membership::get_id, i-> i));
            membershipTypes = membershipTypeList.stream().collect(Collectors.toMap(MembershipType::get_id, i-> i));
            userCategories = userCategoryList.stream().collect(Collectors.toMap(UserCategory::get_id, i-> i));
            warningTypes = warningTypesList.stream().collect(Collectors.toMap(WarningType::get_id, i-> i));
            warningCounters = warningCounterList.stream().collect(Collectors.toMap(WarningCounter::get_id, i-> i));
            organizations = organizationList.stream().collect(Collectors.toMap(Organization::get_id, i-> i));


        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public ArrayList<UItem> getCoder(int coderCode){
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
            case _992b_CODER: retVal = //new ArrayList<InternalMark>(internalMarks.values()).stream().map(i -> new UItem(i.getCoder_id(), i.getDescription())).collect(Collectors.toList());
                                        new ArrayList<UItem>(Arrays.asList(new UItem("0", "hardcoded value"))); break; //TODO-hardcoded
            case LIBRARIAN_CODER: retVal = //new ArrayList<InternalMark>(internalMarks.values()).stream().map(i -> new UItem(i.getCoder_id(), i.getDescription())).collect(Collectors.toList());
                                            new ArrayList<UItem>(Arrays.asList(new UItem("0", "hardcoded value"))); break;
        }

        return (ArrayList<UItem>) retVal;
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

    //circkulacija-----------------
    private Map<String, CircLocation> circLocations = new HashMap<>();
    private Map<String, CorporateMember> corporateMembers = new HashMap<>();
    private Map<String, EducationLvl> educationLevels = new HashMap<>();
    private Map<String, Language> languages = new HashMap<>();
    private Map<String, Membership> memberships = new HashMap<>();
    private Map<String, MembershipType> membershipTypes = new HashMap<>();
    private Map<String, Organization> organizations = new HashMap<>();
    private Map<String, Place> places = new HashMap<>();
    private Map<String, UserCategory> userCategories = new HashMap<>();
    private Map<String, WarningType> warningTypes = new HashMap<>();
    private Map<String, WarningCounter> warningCounters = new HashMap<>();

    public static final int ODELJENJE_CODER = 				0;
    public static final int FORMAT_CODER =    				1;
    public static final int STATUS_CODER =    				2;
    public static final int POVEZ_CODER =     				3;
    public static final int PODLOKACIJA_CODER =   		    4;
    public static final int NACINNABAVKE_CODER =  		    5;
    public static final int INTERNAOZNAKA_CODER =   	    6;
    public static final int INVENTARNAKNJIGA_CODER =	    7;
    public static final int DOSTUPNOST_CODER =     		    8;
    public static final int _992b_CODER =     		        9; //ovo je patch i treba drugacije da se resi
    public static final int LIBRARIAN_CODER =     		    10;
}
