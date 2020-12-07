package com.ftninformatika.bisis.inventory.repository;

import com.ftninformatika.bisis.inventory.InventoryStatus;
import com.ftninformatika.bisis.inventory.InventoryUnit;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;

public class InventoryUnitAdditionalRepositoryImpl implements InventoryUnitAdditionalRepository {

    private static final String[] TEXT_INDEX_FIELDS_ARR = {"invNo", "author", "title", "signature", "publisher",
            "pubYear", "invStatus.coder_id", "revisionStatus.coder_id"};
    private static final String[] STANDARD_INDEX_FIELDS_ARR = {"rn", "dateModified", "inventoryId"};
    private MongoTemplate mongoTemplate;

    @Autowired
    public InventoryUnitAdditionalRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public void indexFields() {
//        for (String index: TEXT_INDEX_FIELDS_ARR) {
        TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onAllFields()
                .build();
        mongoTemplate.indexOps(InventoryUnit.class).ensureIndex(textIndex);
//        }
        for (String index: STANDARD_INDEX_FIELDS_ARR) {
            mongoTemplate.indexOps(InventoryUnit.class).ensureIndex(new Index(index, Sort.Direction.DESC).background());
        }
    }

    @Override
    public Boolean changeRevisionStatuses(InventoryStatus fromStatus, InventoryStatus toStatus, String library) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(library + "_inventory_units");
        if (fromStatus == null || fromStatus.getCoder_id() == null || toStatus == null || toStatus.getCoder_id() == null) {
            System.out.println("changeRevisionStatuses ne valjaju statusi");
            return null; //todo log
        }
        Document select = new Document("revisionStatus.coder_id", fromStatus.getCoder_id());
        Document update = new Document("$set", new Document("revisionStatus", getDocumentFromStatus(toStatus)));
        UpdateResult updateResult =collection.updateMany(select, update);
        return updateResult.isModifiedCountAvailable();
    }

    private Document getDocumentFromStatus(InventoryStatus status) {
        Document d = new Document("_id", status.get_id());
        d.append("coder_id", status.getCoder_id());
        d.append("description", status.getDescription());
        d.append("library", status.getLibrary());
        return d;
    }
}
