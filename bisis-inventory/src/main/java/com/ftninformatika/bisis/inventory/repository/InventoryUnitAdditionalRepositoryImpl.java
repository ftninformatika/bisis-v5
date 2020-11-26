package com.ftninformatika.bisis.inventory.repository;

import com.ftninformatika.bisis.inventory.InventoryUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;

public class InventoryUnitAdditionalRepositoryImpl implements InventoryUnitAdditionalRepository {

    private static final String[] TEXT_INDEX_FIELDS_ARR = {"invNo", "author", "title", "signature", "publisher",
            "pubYear", "invStatus", "revisionStatus"};
    private static final String[] STANDARD_INDEX_FIELDS_ARR = {"rn", "dateModified"};
    private MongoTemplate mongoTemplate;

    @Autowired
    public InventoryUnitAdditionalRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public void indexFields() {

        for (String index: TEXT_INDEX_FIELDS_ARR) {
            TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                    .onAllFields()
                    .build();
            mongoTemplate.indexOps(InventoryUnit.class).ensureIndex(textIndex);
        }
        for (String index: STANDARD_INDEX_FIELDS_ARR) {
            mongoTemplate.indexOps(InventoryUnit.class).ensureIndex(new Index(index, Sort.Direction.DESC).background());
        }
        IndexDefinition uniqueInvNoIndex = new Index("invNo", Sort.Direction.DESC).unique().background();
        mongoTemplate.indexOps(InventoryUnit.class).ensureIndex(uniqueInvNoIndex);
    }
}