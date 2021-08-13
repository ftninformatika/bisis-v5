package com.ftninformatika.bisis.acquisition.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftninformatika.bisis.acquisition.dto.DesideratumDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Item {
    private DesideratumDTO desideratum;
    private Price price;
    @JsonIgnore
    private int amount;
    public int getAmount(){
        int temp = 0;
        for(Location l: desideratum.getLocations()){
            temp = temp + l.getAmount();
        }
        return temp;
    }
}
