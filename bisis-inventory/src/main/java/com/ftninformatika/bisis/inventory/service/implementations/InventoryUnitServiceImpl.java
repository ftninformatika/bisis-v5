package com.ftninformatika.bisis.inventory.service.implementations;

import com.ftninformatika.bisis.coders.ItemStatus;
import com.ftninformatika.bisis.inventory.InventoryStatus;
import com.ftninformatika.bisis.inventory.InventoryUnit;
import com.ftninformatika.bisis.inventory.dto.ChangeRevStatusesDTO;
import com.ftninformatika.bisis.inventory.dto.MapStatusesToItemsDTO;
import com.ftninformatika.bisis.inventory.dto.RevStatusOnPlaceDTO;
import com.ftninformatika.bisis.inventory.dto.StatusMappingEntry;
import com.ftninformatika.bisis.inventory.repository.InventoryUnitRepository;
import com.ftninformatika.bisis.inventory.service.interfaces.InventoryUnitService;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.mongo.LendingRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.InventoryStatusRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.ItemStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryUnitServiceImpl implements InventoryUnitService {

    private final InventoryUnitRepository inventoryUnitRepository;
    private ItemStatusRepository itemStatusRepository;
    private InventoryStatusRepository inventoryStatusRepository;
    private RecordsRepository recordsRepository;

    @Autowired
    public InventoryUnitServiceImpl(InventoryUnitRepository inventoryUnitRepository, InventoryStatusRepository inventoryStatusRepository,
                                    ItemStatusRepository itemStatusRepository, RecordsRepository recordsRepository) {
        this.inventoryUnitRepository = inventoryUnitRepository;
        this.inventoryStatusRepository = inventoryStatusRepository;
        this.itemStatusRepository = itemStatusRepository;
        this.recordsRepository = recordsRepository;
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
        Pageable pageRequest = PageRequest.of(pNum, pSize,Sort.by("invNo")); // todo ovde ide search/sort
        return inventoryUnitRepository.findByInventoryId(inventory_id, pageRequest);
    }

    @Override
    public InventoryUnit setOnPlace(RevStatusOnPlaceDTO revStatusOnPlaceDTO, String library) {
        if (revStatusOnPlaceDTO == null || revStatusOnPlaceDTO.getInventoryId() == null || revStatusOnPlaceDTO.getInvNo() == null) {
            return null;
        }
        InventoryUnit inventoryUnit = inventoryUnitRepository.findByInventoryIdAndInvNo(revStatusOnPlaceDTO.getInventoryId(), revStatusOnPlaceDTO.getInvNo());
        InventoryStatus onPlaceStatus = inventoryStatusRepository.getByCoder_Id(InventoryStatus.ON_PLACE);
        if (onPlaceStatus == null || inventoryUnit == null) {
            return null; //todo logger
        }
        inventoryUnit.setRevisionStatus(onPlaceStatus);
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
        return inventoryUnitRepository.changeRevisionStatuses(fromInvStaus, toInvStatus, library);
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
        //todo finishing Inventory set enum
        Iterator<InventoryUnit> iterator = inventoryUnitRepository.findAllByInventoryStatusesAndInventoryId(mapStatusesToItems.getStatusMapEntryList()
                .stream().map(StatusMappingEntry::getInventoryStatusCoderId).collect(Collectors.toList()), mapStatusesToItems.getInventoryId());

        if (iterator == null) {
            return false;
        }

        int lastRn = -1;
        List<InventoryUnit> sameRecUnits = new ArrayList<>();
        while (iterator.hasNext()) {
            InventoryUnit unit = iterator.next();
            if (lastRn != unit.getRn() && lastRn != -1) {
                Record r = changeItemStatusesAndGetRec(lastRn, sameRecUnits, mapStatusesToItems);
                recordsRepository.save(r);
                sameRecUnits = new ArrayList<>();
                sameRecUnits.add(unit);
                lastRn = unit.getRn();
            } else {
                sameRecUnits.add(unit);
                lastRn = unit.getRn();
            }
        }
        inventoryUnitRepository.removeInventoryIdFromItemAvailabilities(mapStatusesToItems.getInventoryId());
        milliseconds = System.currentTimeMillis();
        resultdate = new Date(milliseconds);
        System.out.println("Vreme zavrsetka izvrsavanja: " + sdf.format(resultdate));
        return null;
    }

    private Record changeItemStatusesAndGetRec(int rn, List<InventoryUnit> inventoryUnits, MapStatusesToItemsDTO mapStatusesToItemsDTO) {
        if (rn < 0 || inventoryUnits == null || inventoryUnits.size() == 0) {
            return null;
        }
        Record rec = recordsRepository.getByRn(rn);
        for (InventoryUnit unit: inventoryUnits) {
            Primerak p = rec.getPrimerak(unit.getInvNo());
            StatusMappingEntry statusMappingEntry = mapStatusesToItemsDTO.getEntryByInventoryStatus(unit.getRevisionStatus());
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
