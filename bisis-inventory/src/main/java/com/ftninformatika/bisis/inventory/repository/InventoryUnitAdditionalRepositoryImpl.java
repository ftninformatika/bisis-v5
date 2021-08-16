package com.ftninformatika.bisis.inventory.repository;

import com.ftninformatika.bisis.inventory.InventoryStatus;
import com.ftninformatika.bisis.inventory.InventoryUnit;
import com.ftninformatika.bisis.inventory.dto.InvUnitSearchDTO;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.*;

public class InventoryUnitAdditionalRepositoryImpl implements InventoryUnitAdditionalRepository {

    private static final String[] STANDARD_INDEX_FIELDS_ARR = {"rn", "invNo", "inventoryId"};
    private MongoTemplate mongoTemplate;

    @Autowired
    public InventoryUnitAdditionalRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public void indexFields() { //todo proveriti koja polja indeksirati zbog performansi
//        for (String index: TEXT_INDEX_FIELDS_ARR) {
//        TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
//                .onAllFields()
//                .build();
//        mongoTemplate.indexOps(InventoryUnit.class).ensureIndex(textIndex);
//        }
        for (String index: STANDARD_INDEX_FIELDS_ARR) {
            mongoTemplate.indexOps(InventoryUnit.class).ensureIndex(new Index(index, Sort.Direction.DESC).background());
        }
    }

    @Override
    public Page<InventoryUnit> search(InvUnitSearchDTO invUnitSearchDTO, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Query query = new Query();
        if (invUnitSearchDTO != null && invUnitSearchDTO.generateSearchCriteria() != null) {
            query.addCriteria(invUnitSearchDTO.generateSearchCriteria());
        }
        if (invUnitSearchDTO != null && invUnitSearchDTO.getSortBy() != null && invUnitSearchDTO.getSort() != null) {
            query.with(invUnitSearchDTO.getSort());
        }
        query.with(pageable);
        List<InventoryUnit> results = mongoTemplate.find(query, InventoryUnit.class);

        if (results == null || results.size() == 0) {
            return Page.empty();
        }

        return PageableExecutionUtils.getPage(
                results, pageable, () -> mongoTemplate.count(query, InventoryUnit.class));
    }

    @Override
    public List<InventoryUnit> search(InvUnitSearchDTO invUnitSearchDTO) {
        Query query = new Query();
        if (invUnitSearchDTO != null && invUnitSearchDTO.generateSearchCriteria() != null) {
            query.addCriteria(invUnitSearchDTO.generateSearchCriteria());
        }
        if (invUnitSearchDTO != null && invUnitSearchDTO.getSortBy() != null && invUnitSearchDTO.getSort() != null) {
            query.with(invUnitSearchDTO.getSort());
        }
        return mongoTemplate.find(query, InventoryUnit.class);
    }

    @Override
    public Boolean changeRevisionStatuses(InventoryStatus fromStatus, InventoryStatus toStatus, String library) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(library + "_inventory_units");
        if (fromStatus == null || fromStatus.getCoder_id() == null || toStatus == null || toStatus.getCoder_id() == null) {
            System.out.println("changeRevisionStatuses ne valjaju statusi");
            return null;
        }
        Document select = new Document("inventoryStatusCoderId", fromStatus.getCoder_id());
        Document setDoc = new Document("inventoryStatusCoderId", toStatus.getCoder_id());
        setDoc.append("inventoryStatusDescription", toStatus.getDescription());
        if (!toStatus.getCoder_id().equals(InventoryStatus.IN_REVISION)) {
            setDoc.append("checked", true);
        }
        Document update = new Document("$set", setDoc);
        UpdateResult updateResult = collection.updateMany(select, update);
        return updateResult.isModifiedCountAvailable();
    }

    @Override
    public void removeInventoryIdFromItemAvailabilities(String inventoryId) {
        Query q = new Query();
        q.addCriteria(Criteria.where("inventoryId").is(inventoryId)); //todo proveriti sta se desava
        Update u = new Update();
        u.set("inventoryId", null);
        mongoTemplate.updateMulti(q, u, ItemAvailability.class);
    }

    @Override
    public Iterator<InventoryUnit> findAllByInventoryStatusesAndInventoryId(List<String> invStatusesCoderIdList, String inventoryId) {
        if (inventoryId == null || invStatusesCoderIdList == null || invStatusesCoderIdList.size() == 0) {
            return null;
        }
        Criteria criteria = Criteria.where("inventoryId").is(inventoryId);
        Criteria[] invStatuses = new Criteria[invStatusesCoderIdList.size()];
        int i = 0;
        for (String invStatusCoder: invStatusesCoderIdList) {
            invStatuses[i] = Criteria.where("inventoryStatusCoderId").is(invStatusCoder);
            i++;
        }
        Criteria or = new Criteria().orOperator(invStatuses);
        criteria.andOperator(or);
        Query q = new Query();
        q.addCriteria(criteria);
        q.with(new Sort(Sort.Direction.DESC,"rn"));
        q.fields().include("rn").include("inventoryId").include("invNo").include("inventoryStatusCoderId").include("inventoryStatusDescription");
        return mongoTemplate.stream(q, InventoryUnit.class);
    }
}
