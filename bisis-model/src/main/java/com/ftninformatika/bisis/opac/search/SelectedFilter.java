package com.ftninformatika.bisis.opac.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectedFilter {
    private FilterItem item = new FilterItem();
    private FilterType type;

    public boolean isValid() {
        return (item != null && item.getValue() != null && !item.getValue().equals(""));
    }
}
