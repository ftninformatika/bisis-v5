package com.ftninformatika.bisis.invetory.service.implementations;

import com.ftninformatika.bisis.inventory.Inventory;
import com.ftninformatika.bisis.inventory.InventoryStatus;
import com.ftninformatika.bisis.inventory.InventoryUnit;
import com.ftninformatika.bisis.invetory.repository.InventoryRepository;
import com.ftninformatika.bisis.invetory.repository.InventoryUnitRepository;
import com.ftninformatika.bisis.invetory.service.interfaces.InventoryService;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;
    private InventoryUnitRepository inventoryUnitRepository;
    private RecordsRepository recordsRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository,
                                RecordsRepository recordsRepository, InventoryUnitRepository inventoryUnitRepository){
        this.inventoryRepository = inventoryRepository;
        this.recordsRepository = recordsRepository;
        this.inventoryUnitRepository = inventoryUnitRepository;
    }

//    @Transactional todo
    @Override
    public Inventory create(Inventory inventory) {
        if (inventory == null || inventory.get_id() != null) {
            return null;
        }
        if (inventoryRepository.findAllByInventoryStatus(InventoryStatus.IN_PREPARATION).size() > 0) {
            // todo Ne moze da se upisuje nova dok ima neka u pripremi (puni kolekciju inventory_unit)
            return null;
        }
        inventory.setInventoryStatus(InventoryStatus.IN_PREPARATION);
        try {
            if (inventory.getYear() == null && inventory.getStartDate() != null) {
                inventory.setYear(inventory.getStartDate().getYear());
            }
            inventory = inventoryRepository.insert(inventory);
            if (inventory != null) {
                generateInventoryUnits(inventory);
            }
            inventory.setInventoryStatus(InventoryStatus.IN_PROGRESS);
            return inventoryRepository.save(inventory);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Inventory update(Inventory inventory) {
        if (inventory == null || inventory.get_id() == null) {
            //todo put logger
            return null;
        }
        try {
            return inventoryRepository.save(inventory);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Inventory inventory) {
        try {
            inventoryRepository.delete(inventory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Inventory getOne(String _id) {
        return null;
    }

    @Override
    public List<Inventory> getAllForLib(String lib) {
        return inventoryRepository.findAllByLibrary(lib);
    }

    // todo move this somewhere, make nicer query
    // todo set inv check
    private void generateInventoryUnits(Inventory createdInventory) {
        Pageable pageRequest = PageRequest.of(0, 1000);
        Page<Record> onePage = recordsRepository.findAll(pageRequest);
        int totalPages = recordsRepository.findAll(pageRequest).getTotalPages();
        int count = 1;
        for (int i = 0; i < totalPages; i++) {
            List<InventoryUnit> invUnitsBulkList = new ArrayList<>();
            for (Record rec : onePage) {
                List<InventoryUnit> inventoryUnits = createdInventory.initListOfUnitsFromRecord(rec);
                invUnitsBulkList.addAll(inventoryUnits);
            }
            inventoryUnitRepository.saveAll(invUnitsBulkList);

            if (!onePage.isLast()) {
                pageRequest = onePage.nextPageable();
                onePage = recordsRepository.findAll(pageRequest);
            }

            if (count % 10 == 0 || count == totalPages) {
                System.out.println("Processed pages: " + count + " of " + totalPages);
            }
            count++;
        }
    }


}
