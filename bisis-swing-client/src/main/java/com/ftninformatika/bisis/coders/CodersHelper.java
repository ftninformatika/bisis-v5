package com.ftninformatika.bisis.coders;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.GsonUtils;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class CodersHelper {

    public void init() {

        JsonObject sp = null;

        try {
            sp = BisisApp.bisisService.getStatusCoders().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<StatusPrimerka> sp2 = (List<StatusPrimerka>) GsonUtils.getCollectionFromJsonObject(StatusPrimerka.class,sp);
        for (StatusPrimerka s: sp2){
            statusiPrimerka.put(s.getStatus(), s);
        }

    }

    private Map<String, StatusPrimerka> statusiPrimerka = new HashMap<>();
}
