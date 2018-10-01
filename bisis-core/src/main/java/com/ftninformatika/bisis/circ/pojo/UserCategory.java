package com.ftninformatika.bisis.circ.pojo;

import lombok.*;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by dboberic on 27/07/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCategory {
    private String description;
    private int titlesNo;
    private int period;
    private int maxPeriod;

    public static class UserCategoryComparator implements Comparator<UserCategory> {
        @Override
        public int compare(UserCategory o1, UserCategory o2) {
            Collator collator = Collator.getInstance(new Locale("sr_RS"));
            collator.setStrength(Collator.SECONDARY);
            return collator.compare(o1.description, o2.description);
        }
    }
}
