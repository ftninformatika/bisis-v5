package com.ftninformatika.bisis.inventory.service.implementations;

import com.ftninformatika.bisis.inventory.InventoryStatus;
import com.ftninformatika.bisis.inventory.InventoryUnit;
import com.ftninformatika.bisis.inventory.RequestInvUnitMin;
import com.ftninformatika.bisis.inventory.repository.InventoryUnitRepository;
import com.ftninformatika.bisis.inventory.service.interfaces.InventoryUnitService;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.InventoryStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryUnitServiceImpl implements InventoryUnitService {

    private final InventoryUnitRepository inventoryUnitRepository;
    private InventoryStatusRepository inventoryStatusRepository;

    @Autowired
    public InventoryUnitServiceImpl(InventoryUnitRepository inventoryUnitRepository, InventoryStatusRepository inventoryStatusRepository) {
        this.inventoryUnitRepository = inventoryUnitRepository;
        this.inventoryStatusRepository = inventoryStatusRepository;
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
    public InventoryUnit setOnPlace(RequestInvUnitMin requestInvUnitMin, String library) {
        if (requestInvUnitMin == null || requestInvUnitMin.getInventoryId() == null || requestInvUnitMin.getInvNo() == null) {
            return null;
        }
        InventoryUnit inventoryUnit = inventoryUnitRepository.findByInventoryIdAndInvNo(requestInvUnitMin.getInventoryId(), requestInvUnitMin.getInvNo());
        InventoryStatus onPlaceStatus = inventoryStatusRepository.getByCoder_Id(InventoryStatus.ON_PLACE, library);
        if (onPlaceStatus == null || inventoryUnit == null) {
            return null; //todo logger
        }
        inventoryUnit.setRevisionStatus(onPlaceStatus);
        return inventoryUnitRepository.save(inventoryUnit);
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

    @Override
    public InventoryUnit findByInventoryIdAndInvNo(String inventoryId, String invNo) {
        return inventoryUnitRepository.findByInventoryIdAndInvNo(inventoryId, invNo);
    }
}
