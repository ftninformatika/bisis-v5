package com.ftninformatika.bisis.coders;

import com.ftninformatika.bisis.BisisApp;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CodersHelper {

    public void init() {

        try {
            List<ItemStatus> stCoders=BisisApp.bisisService.getStatusCoders(BisisApp.appConfig.getLibrary()).execute().body();
            List<Location> locCoders=BisisApp.bisisService.getLocations(BisisApp.appConfig.getLibrary()).execute().body();

            itemStatuses = stCoders.stream().collect(Collectors.toMap(ItemStatus::getStatus, i -> i));
            locations = locCoders.stream().collect(Collectors.toMap(Location::getLocation_id, i -> i));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Map<String, ItemStatus> itemStatuses = new HashMap<>();
    private Map<String, Location> locations = new HashMap<>();
}
