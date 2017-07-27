package com.ftninformatika.bisis.coders;

import com.ftninformatika.bisis.BisisApp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.elasticsearch.common.recycler.Recycler;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CodersHelper {

    public ArrayList<ArrayList<Object>> getCoderTableModelList(String coderName){
        ArrayList<ArrayList<Object>> retVal = new ArrayList<>();

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
}
