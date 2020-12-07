package com.ftninformatika.bisis.inventory.service.implementations;

import com.ftninformatika.bisis.circ.Lending;
import com.ftninformatika.bisis.coders.ItemStatus;
import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisis.inventory.*;
import com.ftninformatika.bisis.inventory.dto.MapStatusesToItemsDTO;
import com.ftninformatika.bisis.inventory.repository.InventoryRepository;
import com.ftninformatika.bisis.inventory.repository.InventoryUnitRepository;
import com.ftninformatika.bisis.inventory.service.interfaces.InventoryService;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import com.ftninformatika.bisis.rest_service.repository.mongo.ItemAvailabilityRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LendingRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.InventoryStatusRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.ItemStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;
    private InventoryUnitRepository inventoryUnitRepository;
    private RecordsRepository recordsRepository;
    private InventoryStatusRepository inventoryStatusRepository;
    private ItemStatusRepository itemStatusRepository;
    private ItemAvailabilityRepository itemAvailabilityRepository;
    private LendingRepository lendingRepository;
    private MongoTemplate mongoTemplate;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository, ItemAvailabilityRepository itemAvailabilityRepository,
                                RecordsRepository recordsRepository, InventoryUnitRepository inventoryUnitRepository,
                                InventoryStatusRepository inventoryStatusRepository, ItemStatusRepository itemStatusRepository, LendingRepository lendingRepository, MongoTemplate mongoTemplate) {
        this.inventoryRepository = inventoryRepository;
        this.recordsRepository = recordsRepository;
        this.inventoryUnitRepository = inventoryUnitRepository;
        this.itemStatusRepository = itemStatusRepository;
        this.inventoryStatusRepository = inventoryStatusRepository;
        this.itemAvailabilityRepository = itemAvailabilityRepository;
        this.lendingRepository = lendingRepository;
        this.mongoTemplate = mongoTemplate;
    }

    //    @Transactional todo
    @Override
    public Inventory create(Inventory inventory, String lib) {
        if (inventory == null || inventory.get_id() != null) {
            return null;
        }
        if (inventoryRepository.findAllByInventoryStateAndLibrary(EnumInventoryState.IN_PREPARATION, lib).size() > 0) {
            // todo Ne moze da se upisuje nova dok ima neka u pripremi (puni kolekciju inventory_unit)
            return null;
        }
        inventory.setInventoryState(EnumInventoryState.IN_PREPARATION);
        try {
            if (inventory.getYear() == null && inventory.getStartDate() != null) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(inventory.getStartDate());
                inventory.setYear(calendar.get(Calendar.YEAR));
            }
            inventory = inventoryRepository.insert(inventory);
            generateInventoryUnits(inventory, lib);
            inventory.setInventoryState(EnumInventoryState.IN_PROGRESS);
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
            inventoryUnitRepository.deleteAllByInventoryId(inventory.get_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Inventory getOne(String _id) {
        if (_id == null) {
            throw new IllegalArgumentException("Id of Inventory is not passed");
        }
        Optional<Inventory> optionalInventory = inventoryRepository.findById(_id);
        if (optionalInventory.isPresent()) {
            Double progress = getProgress(_id);
            Inventory inventory = optionalInventory.get();
            inventory.setProgress(progress);
            return inventory;
        }
        return null;
    }

    @Override
    public List<Inventory> getAllForLib(String lib) {
        List<Inventory> retVal = inventoryRepository.findAllByLibrary(lib);
        retVal.forEach(i -> i.setProgress(getProgress(i.get_id())));
        return retVal;
    }

    private String createInvNum(String location, String book, String lastNum) {
        String res = location + book;
        int size = 7 - lastNum.length();
        String zeros = "0000000";
        res = res + zeros.substring(0, size) + lastNum;
        return res;
    }

    private Map<Integer, List<InventoryUnit>> createInventoryUnits(Inventory createdInventory, String library) {
        long milliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        Date resultdate = new Date(milliseconds);
        System.out.println("Vreme pocetka izvršavanja upita: " + sdf.format(resultdate));

        UnwindOperation unwindOp = Aggregation.unwind("primerci");

        //upit za podlokaciju i inv knjige, i inv brojeve
        List<Criteria> sublocationCriteriaList = new ArrayList<Criteria>();
        List<Criteria> invBookCriteriaList = new ArrayList<Criteria>();
        for (Sublocation sublocation : createdInventory.getSublocations()) {
            Criteria c1 = Criteria.where("primerci.sigPodlokacija").is(sublocation.getCoder_id());
            sublocationCriteriaList.add(c1);
            for (InventoryBook book : createdInventory.getInvBooks()) {
                if (book.getLastNo() != null) {
                    String firstInvNum = sublocation.getCoder_id().substring(0, 2) + book.getCode() + "0000000";
                    String lastInvNum = createInvNum(sublocation.getCoder_id().substring(0, 2), book.getCode(), String.valueOf(book.getLastNo()));
                    Criteria c2 = Criteria.where("primerci.invBroj").gte(firstInvNum).lte(lastInvNum);
                    invBookCriteriaList.add(c2);
                } else {
                    Criteria c3 = Criteria.where("primerci.invBroj").regex("^" + sublocation.getCoder_id().substring(0, 2) + book.getCode());
                    invBookCriteriaList.add(c3);
                }
            }
        }
        MatchOperation matchSublocationOp = null;
        if (sublocationCriteriaList.size() > 0) {
            matchSublocationOp = Aggregation.match(new Criteria().orOperator(sublocationCriteriaList.toArray(new Criteria[sublocationCriteriaList.size()])));
        }

        MatchOperation matchInvBooksOp = null;
        if (invBookCriteriaList.size() > 0) {
            matchInvBooksOp = Aggregation.match(new Criteria().orOperator(invBookCriteriaList.toArray(new Criteria[invBookCriteriaList.size()])));
        }

        //upit za status
        List<Criteria> statusCriteriaList = new ArrayList<Criteria>();
        for (String status : createdInventory.getItemStatuses()) {
            Criteria c = Criteria.where("primerci.status").is(status);
            statusCriteriaList.add(c);
        }
        MatchOperation matchStatusOp = null;
        if (statusCriteriaList.size() > 0) {
            matchStatusOp = Aggregation.match(new Criteria().orOperator(statusCriteriaList.toArray(new Criteria[statusCriteriaList.size()])));
        }
        //projekcija
        ProjectionOperation changeNameOp = Aggregation.project("rn", "primerci.invBroj", "primerci.status", "primerci.sigUDK").
                andExclude("_id").
                and("primerci.invBroj").as("invNo").
                and("primerci.status").as("status").
                and("primerci.sigUDK").as("signature").
                and(createdInventory.get_id()).asLiteral().as("inventoryId");
        //sortiranje
        SortOperation sortOp = Aggregation.sort(Sort.by(Sort.Direction.ASC, "rn"));
        List<AggregationOperation> pipeline = new ArrayList<AggregationOperation>();
        pipeline.add(unwindOp);
        if (matchSublocationOp != null) {
            pipeline.add(matchSublocationOp);
        }
        if (matchInvBooksOp != null) {
            pipeline.add(matchInvBooksOp);
        }
        if (matchStatusOp != null) {
            pipeline.add(matchStatusOp);
        }
        pipeline.add(changeNameOp);
        pipeline.add(sortOp);
        Aggregation aggregation = Aggregation.newAggregation(pipeline.toArray(new AggregationOperation[pipeline.size()]));
        AggregationOptions aggregationOptions = Aggregation.newAggregationOptions()
                .cursorBatchSize(10000).allowDiskUse(true).build();
        AggregationResults<InventoryUnit> output = mongoTemplate.aggregate(aggregation.withOptions(aggregationOptions), library + "_records", InventoryUnit.class);

        List<InventoryUnit> units = output.getMappedResults();

        Map res = units.stream().collect(Collectors.groupingBy(InventoryUnit::getRn));
        milliseconds = System.currentTimeMillis();
        resultdate = new Date(milliseconds);
        System.out.println("Vreme zavrsetka izvrsavanja pipeline-a: " + sdf.format(resultdate));
        return res;

    }

    private void updateInventoryUnits(Map<Integer,List<InventoryUnit>> inventoryUnitsMap,Inventory createdInventory, String library) {
        Map<String, ItemStatus> itemStatusesMap = itemStatusRepository.getCoders(library).stream().collect(Collectors.toMap(ItemStatus::getCoder_id, is -> is));
        Map<String, InventoryStatus> inventoryStatusesMap = inventoryStatusRepository.getCoders(library).stream().collect(Collectors.toMap(InventoryStatus::getCoder_id, is -> is));
        createdInventory.setItemStatusesMap(itemStatusesMap);
        createdInventory.setInventoryStatusesMap(inventoryStatusesMap);
        RecordPreview rp;
        List<InventoryUnit> invUnitsBulkList = new ArrayList<>();
        List<Integer> rnList = new ArrayList<Integer>(inventoryUnitsMap.keySet());
        int totalPages = rnList.size();
        int count = 1;
        Record record;
        for (Integer rn : rnList) {
            record = recordsRepository.getByRn(rn);
            rp = new RecordPreview();
            rp.init(record);
            List<InventoryUnit> inventoryUnits = inventoryUnitsMap.get(rn);
            for (InventoryUnit unit : inventoryUnits) {
                            unit.setAuthor(rp.getAuthor());
                            unit.setTitle(rp.getTitle());
                            unit.setPublisher(rp.getPublisher());
                            unit.setPubYear(rp.getPublishingYear());
                            unit.setInvStatus(createdInventory.getItemStatus(unit.getStatus()));
                            unit.setRevisionStatus(createdInventory.getRevStatusByInv(unit.getStatus()));
                            unit.setDateModified(new Date());
                            unit.setStatus(null);
                            invUnitsBulkList.add(unit);
            }
            count ++;
            if (count % 1000 == 0 ){
                inventoryUnitRepository.saveAll(invUnitsBulkList);
                itemAvailabilityUpdate(invUnitsBulkList, createdInventory.get_id());
                System.out.println("Processed items: " + count + " of " + totalPages);
                invUnitsBulkList.clear();
            }
        }
        if (!invUnitsBulkList.isEmpty()){
            inventoryUnitRepository.saveAll(invUnitsBulkList);
            itemAvailabilityUpdate(invUnitsBulkList, createdInventory.get_id());
            System.out.println("Processed items: " + count + " of " + totalPages);
            invUnitsBulkList.clear();
        }

        long milliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        Date resultdate = new Date(milliseconds);
        System.out.println("Vreme zavrsetka čuvanja inventory unita: " + sdf.format(resultdate));

    }


    private void generateInventoryUnits(Inventory createdInventory, String library) {
        Map inventoryUnits = createInventoryUnits(createdInventory,library);
        updateInventoryUnits(inventoryUnits,createdInventory,library);
    }

//        // todo move this somewhere, make nicer query
//    private void generateInventoryUnits(Inventory createdInventory, String library) {
//        Pageable pageRequest = PageRequest.of(0, 1000); // todo ovde mozda pozvati elastic za dovlacenje recorda
//
//        Page<Record> onePage = recordsRepository.findAll(pageRequest);
//        int totalPages = onePage.getTotalPages();
//        int count = 1;
//        if (inventoryUnitRepository.count() == 0) {
//            inventoryUnitRepository.indexFields();
//        }
//        Map<String, ItemStatus> itemStatusesMap = itemStatusRepository.getCoders(library).stream().collect(Collectors.toMap(ItemStatus::getCoder_id, is -> is));
//        Map<String, InventoryStatus> inventoryStatusesMap = inventoryStatusRepository.getCoders(library).stream().collect(Collectors.toMap(InventoryStatus::getCoder_id, is -> is));
//        createdInventory.setItemStatusesMap(itemStatusesMap);
//        createdInventory.setInventoryStatusesMap(inventoryStatusesMap);
//        for (int i = 0; i < totalPages; i++) {
//            List<InventoryUnit> invUnitsBulkList = new ArrayList<>();
//            for (Record rec : onePage) {
//                List<InventoryUnit> inventoryUnits = createdInventory.initListOfUnitsFromRecord(rec);
//                if (inventoryUnits != null && inventoryUnits.size() > 0) {
//                    invUnitsBulkList.addAll(inventoryUnits);
//                }
//            }
//            if (invUnitsBulkList.size() > 0) {
//                inventoryUnitRepository.saveAll(invUnitsBulkList);
//                itemAvailabilityUpdate(invUnitsBulkList, createdInventory.get_id());
//            }
//
//            if (!onePage.isLast()) {
//                pageRequest = onePage.nextPageable();
//                onePage = recordsRepository.findAll(pageRequest);
//            }
//
//            if (count % 10 == 0 || count == totalPages) {
//                System.out.println("Processed pages: " + count + " of " + totalPages);
//            }
//            count++;
//        }
//    }

    private void itemAvailabilityUpdate(List<InventoryUnit> inventoryUnits, String inventoryId) {
        List<String> invNums = inventoryUnits.stream().map(InventoryUnit::getInvNo).collect(Collectors.toList());
        List<ItemAvailability> itemAvailabilities = itemAvailabilityRepository.findAllByCtlgNoIsIn(invNums);
        itemAvailabilities.forEach(i -> i.setInventoryId(inventoryId));
        itemAvailabilityRepository.deleteAll(itemAvailabilities); //pokazano je da saveAll radi brze kada je cist save a ne update
        itemAvailabilityRepository.saveAll(itemAvailabilities);
    }

    private Double getProgress(String inventoryId) {
        Double total = inventoryUnitRepository.countAllByInventoryId(inventoryId);
        Double checked = inventoryUnitRepository.countByInventoryIdAndCheckedIsTrue(inventoryId);
        if (checked == null || checked == 0d) {
            return 0d;
        }
        return (double)Math.round((checked / total) * 100);
    }
    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    public Boolean updateLendingStatus(String inventoryId) {
        long milliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        Date resultdate = new Date(milliseconds);
        System.out.println("Vreme pocetka azuriranja zaduženih primeraka: " + sdf.format(resultdate));
        try {
            List<ItemAvailability> borrowedList = itemAvailabilityRepository.findByInventoryIdAndBorrowedIsTrue(inventoryId);
            Lending lending;
            LocalDate lendingDate;
            LocalDate resumeDate = null;
            LocalDate dateL2 = LocalDate.now().minusYears(3);
            InventoryUnit unit;
            InventoryStatus borrowed = inventoryStatusRepository.getByCoder_Id(InventoryStatus.ON_LENDING);
            InventoryStatus borrowedL2 = inventoryStatusRepository.getByCoder_Id(InventoryStatus.ON_LENDING_L2);
            List<InventoryUnit> unitsForUpdate = new ArrayList<InventoryUnit>();
            for (ItemAvailability itemAvailability : borrowedList) {
                String ctlgNo = itemAvailability.getCtlgNo();
                unit = inventoryUnitRepository.findByInventoryIdAndInvNo(inventoryId, ctlgNo);
                if (!unit.isChecked()) {
                    lending = lendingRepository.findByCtlgNoAndReturnDateIsNull(ctlgNo);
                    lendingDate = convertToLocalDateViaInstant(lending.getLendDate());
                    if (lending.getResumeDate() != null) {
                        resumeDate = convertToLocalDateViaInstant(lending.getResumeDate());
                    }
                    if (resumeDate != null && resumeDate.isBefore(dateL2)) {
                        unit.setRevisionStatus(borrowedL2);
                    } else if (lendingDate.isBefore(dateL2)) {
                        unit.setRevisionStatus(borrowedL2);
                    } else {
                        unit.setRevisionStatus(borrowed);
                    }
                    unit.setDateModified(new Date());
                    unitsForUpdate.add(unit);
                }
            }
            if(!unitsForUpdate.isEmpty()){
                inventoryUnitRepository.saveAll(unitsForUpdate);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            milliseconds = System.currentTimeMillis();
            resultdate = new Date(milliseconds);
            System.out.println("Vreme završetka ažuriranje zaduženih primeraka: " + sdf.format(resultdate));
        }

    }
}
