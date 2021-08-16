package com.ftninformatika.bisis.acquisition.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryReportRequest {

    private List<DeliveryDTO> deliveryList;
    private String title;

}
