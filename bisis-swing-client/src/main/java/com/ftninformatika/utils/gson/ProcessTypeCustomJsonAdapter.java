package com.ftninformatika.utils.gson;

import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.librarian.ProcessType;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Petar on 8/7/2017.
 */
public class ProcessTypeCustomJsonAdapter implements JsonSerializer<ProcessType>, JsonDeserializer<ProcessType> {
    @Override
    public JsonElement serialize(ProcessType src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonPt = new JsonObject();

        //flat properties
        jsonPt.addProperty("_id", src.get_id());
        jsonPt.addProperty("id", src.get_id());
        jsonPt.addProperty("name", src.getName());
        jsonPt.addProperty("libName", src.getLibName());

        //pubType
        JsonObject jsonPubType = new JsonObject();
        jsonPubType.addProperty("pubType", src.getPubType().getPubType());
        jsonPubType.addProperty("name", src.getPubType().getName());
        jsonPubType.addProperty("description", src.getPubType().getDescription());

        //subfields
        JsonArray jsonSubfields = new JsonArray();
        for(UField f: src.getPubType().getFields()){
            JsonObject field = new JsonObject();


        }



        jsonPt.add("pubType", jsonPubType);


        return jsonPt;
    }

    @Override
    public ProcessType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
