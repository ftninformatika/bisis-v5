package com.ftninformatika.bisis.inventory.service.implementations;

import com.ftninformatika.bisis.inventory.InventoryUnit;
import com.ftninformatika.bisis.inventory.repository.InventoryUnitRepository;
import com.ftninformatika.bisis.inventory.service.interfaces.InventoryUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryUnitServiceImpl implements InventoryUnitService {

    private final InventoryUnitRepository inventoryUnitRepository;

    @Autowired
    public InventoryUnitServiceImpl(InventoryUnitRepository inventoryUnitRepository) {
        this.inventoryUnitRepository = inventoryUnitRepository;
    }

    @Override
    public Page<InventoryUnit> search(String inventory_id, Integer pageSize, Integer pageNumber) {
        int pSize = 10;
        int pNum = 0;
        if (pageSize != null) {
            pSize = pageSize;
        }
        if (pageNumber != null) {
            pNum = pageNumber;
        }
        Pageable pageRequest = PageRequest.of(pNum, pSize); // todo ovde ide search/sort
        return inventoryUnitRepository.findByInventoryId(inventory_id, pageRequest);
    }

    @Override
    public InventoryUnit create(InventoryUnit inventory) {
        return null;
    }

    @Override
    public InventoryUnit update(InventoryUnit inventory) {
        return inventoryUnitRepository.save(inventory);
    }

    @Override
    public void delete(InventoryUnit inventory) {

    }

    @Override
    public InventoryUnit getOne(String _id) {
        return null;
    }

    @Override
    public List<InventoryUnit> getAllForLib(String lib) {
        return null;
    }

}
