package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.inventory.InventoryStatus;
import org.springframework.data.mongodb.repository.Query;

public interface InventoryStatusRepository extends CoderRepository<InventoryStatus> {

    @Query("{'coder_id': ?0}")
    InventoryStatus getByCoder_Id(String coder_id);

}
