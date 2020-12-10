package com.ftninformatika.bisis.inventory.dto;

import com.ftninformatika.bisis.inventory.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvUnitSearchDTO {
    private Integer rn;
    private String inventoryId;
    private String invNo;
    private String author;
    private String title;
    private String publisher;
    private String pubYear;
    private InventoryStatus inventoryStatus;


    public Criteria generateSearchCriteria() {
        Criteria criteria = new Criteria();
        List<Criteria> criteriaList = new ArrayList<>();
        if (this.inventoryId == null) {
            return null;
        }
        criteriaList.add(Criteria.where("inventoryId").is(this.inventoryId));
        if(this.invNo != null  && !this.invNo.equals("")) {
            criteriaList.add(Criteria.where("invNo").regex(Pattern.compile("^" + this.invNo)));
        }
        if (this.author != null  && !this.author.equals("")) {
            criteriaList.add(Criteria.where("author").regex(Pattern.compile("^" + this.author, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
        }
        if (this.title != null && !this.title.equals("")) {
            criteriaList.add(Criteria.where("title").regex(Pattern.compile("^" + this.title, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
        }
        if (this.publisher != null && !this.publisher.equals("")) {
            criteriaList.add(Criteria.where("publisher").regex(Pattern.compile("^" + this.publisher, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
        }
        if (this.inventoryStatus != null && this.inventoryStatus.getCoder_id() != null) {
            criteriaList.add(Criteria.where("revisionStatus.coder_id").is(this.inventoryStatus.getCoder_id())); //todo refactor if change inv statuses
        }
        if (criteriaList.size() == 0) {
            return null;
        }
        criteria.andOperator(criteriaList.stream().toArray(Criteria[]::new));
        return criteria;
    }
}
