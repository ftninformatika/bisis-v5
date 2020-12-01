package com.ftninformatika.bisis.inventory.repository;

import com.ftninformatika.bisis.inventory.InventoryStatus;
import com.ftninformatika.bisis.inventory.InventoryUnit;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class InventoryUnitAdditionalRepositoryImpl implements InventoryUnitAdditionalRepository {

    private static final String[] TEXT_INDEX_FIELDS_ARR = {"invNo", "author", "title", "signature", "publisher",
            "pubYear", "invStatus.coder_id", "revisionStatus.coder_id"};
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
//        mongoTemplate.indexOps(InventoryUnit.class).ensureIndex(uniqueInvNoIndex);
    }

    @Override
    public Boolean changeRevisionStatuses(InventoryStatus fromStatus, InventoryStatus toStatus) {
        if (fromStatus == null || fromStatus.getCoder_id() == null || toStatus == null || toStatus.getCoder_id() == null) {
            System.out.println("changeRevisionStatuses ne valjaju statusi");
            return null; //todo log
        }
        Query select = Query.query(Criteria.where("revisionStatus.coder_id").is(fromStatus.getCoder_id()));
        Update update = new Update();
        update.set("revisionStatus", toStatus);
        UpdateResult updateResult = mongoTemplate.updateMulti(select, update, InventoryUnit.class);
        return updateResult.isModifiedCountAvailable();
    }
}
