package com.ftninformatika.bisis.inventory.service.implementations;

import com.ftninformatika.bisis.core.repositories.InventoryStatusRepository;
import com.ftninformatika.bisis.core.repositories.ItemStatusRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.inventory.EnumActionState;
import com.ftninformatika.bisis.inventory.Inventory;
import com.ftninformatika.bisis.inventory.InventoryStatus;
import com.ftninformatika.bisis.inventory.InventoryUnit;
import com.ftninformatika.bisis.inventory.dto.*;
import com.ftninformatika.bisis.inventory.repository.InventoryRepository;
import com.ftninformatika.bisis.inventory.repository.InventoryUnitRepository;
import com.ftninformatika.bisis.inventory.service.interfaces.InventoryUnitService;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InventoryUnitServiceImpl implements InventoryUnitService {

    private final InventoryUnitRepository inventoryUnitRepository;
    private ItemStatusRepository itemStatusRepository;
    private InventoryStatusRepository inventoryStatusRepository;
    private InventoryRepository inventoryRepository;
    private RecordsRepository recordsRepository;

    @Autowired
    public InventoryUnitServiceImpl(InventoryUnitRepository inventoryUnitRepository, InventoryStatusRepository inventoryStatusRepository,
                                    ItemStatusRepository itemStatusRepository, RecordsRepository recordsRepository, InventoryRepository inventoryRepository) {
        this.inventoryUnitRepository = inventoryUnitRepository;
        this.inventoryStatusRepository = inventoryStatusRepository;
        this.itemStatusRepository = itemStatusRepository;
        this.recordsRepository = recordsRepository;
        this.inventoryRepository = inventoryRepository;
    }


    @Override
    public Page<InventoryUnit> search(InvUnitSearchDTO invUnitSearchDTO, Integer pageNumber, Integer pageSize) {
        int pSize = 10;
        int pNum = 0;
        if (pageSize != null) {
            pSize = pageSize;
        }
        if (pageNumber != null) {
            pNum = pageNumber;
        }
        return inventoryUnitRepository.search(invUnitSearchDTO, pNum, pSize);
    }
    @Override
    public List<InventoryUnit> search(InvUnitSearchDTO invUnitSearchDTO) {
         return inventoryUnitRepository.search(invUnitSearchDTO);
    }

    @Override
    public InventoryUnit setOnPlace(RevStatusOnPlaceDTO revStatusOnPlaceDTO, String library) {
        if (revStatusOnPlaceDTO == null || revStatusOnPlaceDTO.getInventoryId() == null || revStatusOnPlaceDTO.getInvNo() == null) {
            return null;
        }
        InventoryUnit inventoryUnit = inventoryUnitRepository.findByInventoryIdAndInvNo(revStatusOnPlaceDTO.getInventoryId(), revStatusOnPlaceDTO.getInvNo());
        InventoryStatus onPlaceStatus = inventoryStatusRepository.getByCoder_Id(InventoryStatus.ON_PLACE);
        inventoryUnit.setChecked(true);
        inventoryUnitRepository.save(inventoryUnit);
        if (onPlaceStatus == null || inventoryUnit == null) {
            return null; //todo logger
        }
        inventoryUnit.setInventoryStatusCoderId(onPlaceStatus.getCoder_id());
        inventoryUnit.setInventoryStatusDescription(onPlaceStatus.getDescription());
        return inventoryUnitRepository.save(inventoryUnit);
    }

    @Override
    public Boolean changeRevStatuses(String library, ChangeRevStatusesDTO revStatusOnPlaceDTO) {
        if (revStatusOnPlaceDTO == null || revStatusOnPlaceDTO.getFromRevCoderId() == null
                ||  revStatusOnPlaceDTO.getInventoryId() == null || revStatusOnPlaceDTO.getToRevCoderId() == null) {
            return null;
        }
        InventoryStatus fromInvStaus = inventoryStatusRepository.getByCoder_Id(revStatusOnPlaceDTO.getFromRevCoderId());
        InventoryStatus toInvStatus = inventoryStatusRepository.getByCoder_Id(revStatusOnPlaceDTO.getToRevCoderId());
        Optional<Inventory> optionalInventory = inventoryRepository.findById(revStatusOnPlaceDTO.getInventoryId());
        if (!optionalInventory.isPresent() || optionalInventory.get().getCurrentAction() != EnumActionState.NONE) {
            return null;
        }
        Inventory inventory = optionalInventory.get();
        inventory.setCurrentAction(EnumActionState.STATUS_CHANGING);
        inventoryRepository.save(inventory);

        Boolean retVal = inventoryUnitRepository.changeRevisionStatuses(fromInvStaus, toInvStatus, library);
        inventory.setCurrentAction(EnumActionState.NONE);
        inventoryRepository.save(inventory);
        return retVal;
    }


    @Override
    public InventoryUnit create(InventoryUnit inventoryUnit) {
        return null;
    }

    @Override
    public InventoryUnit update(InventoryUnit inventoryUnit) {
        inventoryUnit.uncheckInRevision();
        return inventoryUnitRepository.save(inventoryUnit);
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

    @Override
    public Boolean mapStatusesToItems(MapStatusesToItemsDTO mapStatusesToItems) {
        long milliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        Date resultdate = new Date(milliseconds);
        System.out.println("Vreme pocetka izvršavanja upita: " + sdf.format(resultdate));

        if (mapStatusesToItems == null || mapStatusesToItems.getInventoryId() == null || mapStatusesToItems.getStatusMapEntryList() == null
            || mapStatusesToItems.getStatusMapEntryList().size() == 0) {
            return null;
        }
        Optional<Inventory> optionalInventory = inventoryRepository.findById(mapStatusesToItems.getInventoryId());
        if (!optionalInventory.isPresent() || optionalInventory.get().getCurrentAction() != EnumActionState.NONE) {
            return null;
        }
        Inventory inventory = optionalInventory.get();
        inventory.setCurrentAction(EnumActionState.STATUS_MAPPING_TO_BISIS);
        inventoryRepository.save(inventory);

        Iterator<InventoryUnit> iterator = inventoryUnitRepository.findAllByInventoryStatusesAndInventoryId(mapStatusesToItems.getStatusMapEntryList()
                .stream().map(StatusMappingEntry::getInventoryStatusCoderId).collect(Collectors.toList()), mapStatusesToItems.getInventoryId());

        if (iterator == null || !iterator.hasNext()) {
            return false;
        }

        InventoryUnit _0 = iterator.next();
        Set<InventoryUnit> sameRecUnits = new HashSet<>();
        sameRecUnits.add(_0);
        Integer lastRn = _0.getRn();
        while (iterator.hasNext()) {
            InventoryUnit unit = iterator.next();
            if (!lastRn.equals(unit.getRn())) {
                Record r = changeItemStatusesAndGetRec(lastRn, sameRecUnits, mapStatusesToItems);
                recordsRepository.save(r);
                sameRecUnits = new HashSet<>();
            }
            sameRecUnits.add(unit);
            lastRn = unit.getRn();
        }
        inventoryUnitRepository.removeInventoryIdFromItemAvailabilities(mapStatusesToItems.getInventoryId());
        inventory.setCurrentAction(EnumActionState.NONE);
        inventoryRepository.save(inventory);
        milliseconds = System.currentTimeMillis();
        resultdate = new Date(milliseconds);
        System.out.println("Vreme zavrsetka izvrsavanja: " + sdf.format(resultdate));
        return true;
    }

    private Record changeItemStatusesAndGetRec(int rn, Set<InventoryUnit> inventoryUnits, MapStatusesToItemsDTO mapStatusesToItemsDTO) {
        if (rn < 0 || inventoryUnits == null || inventoryUnits.size() == 0) {
            return null;
        }
        Record rec = recordsRepository.getByRn(rn);
        for (InventoryUnit unit: inventoryUnits) {
            Primerak p = rec.getPrimerak(unit.getInvNo());
            StatusMappingEntry statusMappingEntry = mapStatusesToItemsDTO.getEntryByInventoryStatus(unit.getInventoryStatusCoderId());
            p.setStatus(statusMappingEntry.getItemStatusCoderId());
            if (statusMappingEntry.getItemStatusDate() != null) {
                p.setDatumStatusa(statusMappingEntry.getItemStatusDate());
            }
            if (statusMappingEntry.getNote() != null) {
                String note = p.getNapomene() != null ? (p.getNapomene() + "\n") : "";
                note += statusMappingEntry.getNote();
                p.setNapomene(note);
            }
        }
        return rec;
    }
}
