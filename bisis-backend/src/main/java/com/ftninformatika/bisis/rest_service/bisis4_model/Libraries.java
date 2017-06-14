package com.ftninformatika.bisis.rest_service.bisis4_model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by Petar on 6/9/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "libraries")
public class Libraries {

    @Id
    private String _id;
    private String libraryName;
    private List<Bibliotekari> bibliotekari;
}
