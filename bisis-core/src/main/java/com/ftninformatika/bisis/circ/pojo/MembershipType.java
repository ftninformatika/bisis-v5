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
public class MembershipType {
    private String description;
    private int period;

    public static class MembershiTypeComparator implements Comparator<MembershipType> {
        @Override
        public int compare(MembershipType o1, MembershipType o2) {
            Collator collator = Collator.getInstance(new Locale("sr_RS"));
            collator.setStrength(Collator.SECONDARY);
            return collator.compare(o1.description, o2.description);
            //return o1.description.compareToIgnoreCase(o2.description);
        }
    }
}
