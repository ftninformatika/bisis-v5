package com.ftninformatika.bisis.acquisition.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_allocation")
@Getter
@Setter
@NoArgsConstructor
public class Allocation {
    @Id
    private String _id;
    private String title;
    private List<LocationAmount> locationAmountList= new ArrayList<LocationAmount>();
}
