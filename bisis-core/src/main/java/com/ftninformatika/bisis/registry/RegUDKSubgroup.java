package com.ftninformatika.bisis.registry;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "registries.udksub")
public class RegUDKSubgroup extends Registry{
    @Id String _id;
    private String grupa;
    private String opis;
}
