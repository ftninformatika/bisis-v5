package com.ftninformatika.bisis.inventory.service.interfaces;

import com.ftninformatika.bisis.inventory.InventoryUnit;
import com.ftninformatika.bisis.inventory.dto.ChangeRevStatusesDTO;
import com.ftninformatika.bisis.inventory.dto.MapStatusesToItemsDTO;
import com.ftninformatika.bisis.inventory.dto.RevStatusOnPlaceDTO;
import com.ftninformatika.bisis.inventory.dto.InvUnitSearchDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryUnitService {

    InventoryUnit create(InventoryUnit inventory);
    InventoryUnit update(InventoryUnit inventory);
    void delete(InventoryUnit inventory);
    InventoryUnit getOne(String _id);
    List<InventoryUnit> getAllForLib(String lib);
    InventoryUnit findByInventoryIdAndInvNo(String inventoryId, String invNo);
    InventoryUnit setOnPlace(RevStatusOnPlaceDTO revStatusOnPlaceDTO, String library);
    Boolean changeRevStatuses(String library, ChangeRevStatusesDTO revStatusOnPlaceDTO);
    Boolean mapStatusesToItems(MapStatusesToItemsDTO mapStatusesToItems);
    Page<InventoryUnit> search(InvUnitSearchDTO invUnitSearchDTO, Integer pageNumber, Integer pageSize);

}
