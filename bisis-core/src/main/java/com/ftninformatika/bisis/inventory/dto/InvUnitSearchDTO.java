package com.ftninformatika.bisis.inventory.dto;

import com.ftninformatika.bisis.inventory.EnumSortByInvUnit;
import com.ftninformatika.bisis.inventory.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;
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
    private String signature;
    private EnumSortByInvUnit sortBy;
    private InventoryStatus inventoryStatus;

    public Sort getSort() {
        return this.getSortBy().getSort();
    }

    public Criteria generateSearchCriteria() {
        Criteria criteria = new Criteria();
        List<Criteria> criteriaList = new ArrayList<>();
        if (this.inventoryId == null) {
            return null;
        }
        criteriaList.add(Criteria.where("inventoryId").is(this.inventoryId));
        if(this.invNo != null  && !this.invNo.equals("")) { //todo bug
            criteriaList.add(Criteria.where("invNo").regex(Pattern.compile("^" + this.invNo + "*")));
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
        if (this.signature != null && !this.signature.equals("")) {
            criteriaList.add(Criteria.where("signature").regex(Pattern.compile("^" + this.signature, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
        }
        if (this.inventoryStatus != null && this.inventoryStatus.getCoder_id() != null) {
            criteriaList.add(Criteria.where("inventoryStatusCoderId").is(this.inventoryStatus.getCoder_id()));
        }
        if (criteriaList.size() == 0) {
            return null;
        }
        criteria.andOperator(criteriaList.stream().toArray(Criteria[]::new));
        return criteria;
    }
    @Override
    public String toString(){
        StringBuffer query = new StringBuffer("");
        if (rn != null) {
            query = query.append("рн: " + rn + " ");
        }
        if (invNo !=null && !invNo.isEmpty()){
            query = query.append("инвентарни број: " + rn + " ");
        }
        if (title !=null && !title.isEmpty()){
            query = query.append("наслов: " + title + " ");
        }
        if (author !=null && !author.isEmpty()){
            query = query.append("аутор: " + author + " ");
        }
        if (signature !=null && !signature.isEmpty()){
            query = query.append("сигнатура: " + signature + " ");
        }
        if (inventoryStatus !=null){
            query = query.append("статус: " + inventoryStatus.getDescription());
        }
        return query.toString();
    }

}
