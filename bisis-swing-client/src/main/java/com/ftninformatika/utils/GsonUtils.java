package com.ftninformatika.utils;

import com.ftninformatika.bisis.coders.ItemStatus;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.records.Record;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petar on 6/28/2017.
 */
public class GsonUtils {

    public static List<?> getCollectionFromJsonObject(Class<?> cls, JsonObject response) {
        try {
            if (cls == Record.class) {
                Type listType = new TypeToken<ArrayList<Record>>() {
                }.getType();
                List<Record> yourClassList = new Gson().fromJson(response.getAsJsonObject("_embedded").getAsJsonArray("records"), listType);
                return yourClassList;
            }
            if (cls == ItemStatus.class){
                Type listType = new TypeToken<ArrayList<ItemStatus>>() {
                }.getType();
                List<ItemStatus> yourClassList = new Gson().fromJson(response.getAsJsonObject("_embedded").getAsJsonArray("status"), listType);
                return yourClassList;
            }
            if (cls == Librarian.class){
                Type listType = new TypeToken<ArrayList<Librarian>>() {
                }.getType();
                List<Librarian> yourClassList = new Gson().fromJson(response.getAsJsonObject("_embedded").getAsJsonArray("librarians"), listType);
                return yourClassList;
            }
        }
        catch(Exception e){
                e.printStackTrace();;
        }
        return null;
    }


}
