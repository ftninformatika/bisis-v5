package com.ftninformatika.bisis.acquisition.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_acquisition")
@Getter
@Setter
@NoArgsConstructor
public class Acquisition {
    @Id
    private String _id;
    private String title;
    private Date startDate;
    private Date acquisitionDate;
    private String status;
    private double budget;
    private boolean desiderataUpdated;
    private List<AcquisitionGroup> acquisitionGroupsPlaned;
    private List<AcquisitionGroup> acquisitionGroupsReal;
    private List<Delivery> deliveries;
}
