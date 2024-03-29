package com.ftninformatika.bisis.coders;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by dboberic on 26/07/2017.
 */

@Getter
@Setter
@Document(collection="coders.sublocation")
public class Sublocation extends Coder{
    @Id
    private String _id;
    private String googleMapLocationURL;
    public Sublocation(String id, String library, String coder_id,String description){
        this._id = id;
        this.setLibrary(library);
        this.setCoder_id(coder_id);
        this.setDescription(description);
    }

    public Sublocation() {

    }
}
