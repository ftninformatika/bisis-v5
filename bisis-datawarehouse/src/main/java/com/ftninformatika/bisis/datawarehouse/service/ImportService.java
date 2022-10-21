package com.ftninformatika.bisis.datawarehouse.service;

import com.ftninformatika.bisis.circ.CircLocation;
import com.ftninformatika.bisis.circ.MembershipType;
import com.ftninformatika.bisis.circ.UserCategory;
import com.ftninformatika.bisis.coders.Coder;
import com.ftninformatika.bisis.core.repositories.*;
import com.ftninformatika.bisis.datawarehouse.entity.*;
import com.ftninformatika.bisis.datawarehouse.repository.AccessionRegisterRepository;
import com.ftninformatika.bisis.datawarehouse.repository.CircLocationRepository;
import com.ftninformatika.bisis.datawarehouse.repository.InternalMarkRepository;
import com.ftninformatika.bisis.datawarehouse.repository.LanguageRepository;
import com.ftninformatika.bisis.datawarehouse.repository.LendingRepository;
import com.ftninformatika.bisis.datawarehouse.repository.LibrarianRepository;
import com.ftninformatika.bisis.datawarehouse.repository.LocationRepository;
import com.ftninformatika.bisis.datawarehouse.repository.MembershipRepository;
import com.ftninformatika.bisis.datawarehouse.repository.MembershipTypeRepository;
import com.ftninformatika.bisis.datawarehouse.repository.*;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.LibraryPrefixProvider;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImportService {

    @Autowired
    RecordTypeRepository recordTypeRepository;

    @Autowired
    TargetRepository targetRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    SerialTypeRepository serialTypeRepository;

    @Autowired
    BibliographicLevelRepository bibliographicLevelRepository;

    @Autowired
    UDKRepository udkRepository;

    @Autowired
    @Qualifier("accessionRegisterJPARepository")
    AccessionRegisterRepository accessionRegisterRepository;

    @Autowired
    com.ftninformatika.bisis.core.repositories.AccessionRegisterRepository accessionRegisterRepositoryMongo;

    @Autowired
    @Qualifier("acquisitionJPARepository")
    AcquisitionRepository acquisitionRepository;

    @Autowired
    AcquisitionCoderRepository acquisitionRepositoryMongo;

    @Autowired
    @Qualifier("internalMarkJPARepository")
    InternalMarkRepository internalMarkRepository;

    @Autowired
    com.ftninformatika.bisis.core.repositories.InternalMarkRepository internalMarkRepositoryMongo;

    @Autowired
    @Qualifier("locationJPARepository")
    LocationRepository locationRepository;

    @Autowired
    com.ftninformatika.bisis.core.repositories.LocationRepository locationRepositoryMongo;

    @Autowired
    @Qualifier("sublocationJPARepository")
    SublocationRepository sublocationRepository;

    @Autowired
    SubLocationRepository sublocationRepositoryMongo;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    ItemStatusRepository statusRepositoryMongo;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    @Qualifier("recordJPARepository")
    RecordRepository recordRepository;

    @Autowired
    RecordsRepository recordsRepositoryMongo;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserCategRepository userCategRepository;

    @Autowired
    @Qualifier("circLocationJPARepository")
    CircLocationRepository circLocationRepository;

    @Autowired
    com.ftninformatika.bisis.core.repositories.CircLocationRepository circLocationRepositoryMongo;

    @Autowired
    @Qualifier("lendingJPARepository")
    LendingRepository lendingRepository;

    @Autowired
    @Qualifier("memberJPARepository")
    MemberRepository memberRepository;

    @Autowired
    @Qualifier("membershipJPARepository")
    MembershipRepository membershipRepository;

    @Autowired
    com.ftninformatika.bisis.core.repositories.MembershipRepository membershipRepositoryMongo;

    @Autowired
    @Qualifier("membershipTypeJPARepository")
    MembershipTypeRepository membershipTypeRepository;

    @Autowired
    com.ftninformatika.bisis.core.repositories.MembershipTypeRepository membershipTypeRepositoryMongo;

    @Autowired
    LibraryConfigurationRepository libraryConfigurationRepository;

    @Autowired
    @Qualifier("librarianJPARepository")
    LibrarianRepository librarianRepository;

    @Autowired
    com.ftninformatika.bisis.core.repositories.LibrarianRepository librarianRepositoryMongo;

    @Autowired
    LibraryPrefixProvider libraryPrefixProvider;

    Map <String, Acquisition> acquisitionMap;
    Map <String, AccessionRegister> accessionRegisterMap;
    Map <String, RecordType> recordTypeMap;
    Map <String, Target> targetMap;
    Map <String, BibliographicLevel> bibliographicLevelMap;
    Map <String, SerialType> serialTypeMap;
    Map <String, InternalMark> internalMarkMap;
    Map <String, Status> statusMap;
    Map <String, Location> locationMap;
    Map <String, Sublocation> sublocationMap;
    Map <String, Country> countryMap;
    Map <String, Udk> udkMap;
    Map <String, Language> languageMap;


    private Map initCoders(List coders){
        Map<String, com.ftninformatika.bisis.datawarehouse.entity.Coder> map = (Map<String, com.ftninformatika.bisis.datawarehouse.entity.Coder>) coders.stream().collect(Collectors.toMap(com.ftninformatika.bisis.datawarehouse.entity.Coder::getId, coder -> coder));
        return map;
    }


    public void deleteDataWarehouse(){

        itemRepository.deleteAllInBatch();
        lendingRepository.deleteAllInBatch();
        membershipRepository.deleteAllInBatch();

        itemRepository.resetSequence();

        //brisanje sifarnika
        accessionRegisterRepository.deleteAllInBatch();
        acquisitionRepository.deleteAllInBatch();
        internalMarkRepository.deleteAllInBatch();
        locationRepository.deleteAllInBatch();
        sublocationRepository.deleteAllInBatch();
        statusRepository.deleteAllInBatch();
        recordRepository.deleteAllInBatch();

        categoryRepository.deleteAllInBatch();
        circLocationRepository.deleteAllInBatch();
        librarianRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        membershipTypeRepository.deleteAllInBatch();
    }
    private void importCoder(Class coderJPAClass, JpaRepository coderRepository, MongoRepository mongoRepository) throws Exception{
        List<Coder> coderMongoList = mongoRepository.findAll();
        List<com.ftninformatika.bisis.datawarehouse.entity.Coder> coderJPAList = new ArrayList<>();
        com.ftninformatika.bisis.datawarehouse.entity.Coder coder = null;

        for (Coder c: coderMongoList){
            coder = (com.ftninformatika.bisis.datawarehouse.entity.Coder) coderJPAClass.newInstance();
            if (c.getLibrary() !=null){
                    coder.setId(c.getCoder_id() + "_" + c.getLibrary());
            }else{
                coder.setId(c.getCoder_id());
            }
            coder.setDescription(c.getCoder_id() + " - " +c.getDescription());
            coder.setLibrary(c.getLibrary());
            coderJPAList.add(coder);
        }
        coder = (com.ftninformatika.bisis.datawarehouse.entity.Coder) coderJPAClass.newInstance();
        coder.setId("nemavrednost");
        coder.setDescription("Нема вредност");
        coder.setLibrary(null);
        coderJPAList.add(coder);

        coderRepository.saveAll(coderJPAList);
    }

    private void importCircLocation(){
        List<CircLocation> circLocationList = circLocationRepositoryMongo.findAll();
        for (CircLocation c:circLocationList){
            com.ftninformatika.bisis.datawarehouse.entity.CircLocation circLocation = new com.ftninformatika.bisis.datawarehouse.entity.CircLocation();
            if (c.getLibrary() !=null){
                circLocation.setId(c.getLocationCode() + "_" + c.getLibrary());
            }else{
                circLocation.setId(c.getLocationCode());
            }
            circLocation.setDescription(c.getDescription());
            circLocation.setLibrary(c.getLibrary());
            circLocationRepository.save(circLocation);
        }
        com.ftninformatika.bisis.datawarehouse.entity.CircLocation circLocation = new com.ftninformatika.bisis.datawarehouse.entity.CircLocation();
        circLocation.setId("nemavrednost");
        circLocation.setDescription("Нема вредност");
        circLocation.setLibrary(null);
        circLocationRepository.save(circLocation);

    }

    private  void  importLibrarian(){
        List<LibrarianDB> librarianList = librarianRepositoryMongo.findAll();
        String librarianName = null;
        for(LibrarianDB l: librarianList){
            Librarian librarian = new Librarian();
            librarian.setId(l.getUsername());
            if(l.getIme() !=null && l.getPrezime() != null){
                librarianName = l.getIme()+" " + l.getPrezime();
            }else{
                librarianName = l.getUsername();
            }
            librarian.setDescription(librarianName);
            librarian.setLibrary(l.getBiblioteka());
            librarianRepository.save(librarian);
        }
        Librarian librarian = new Librarian();
        librarian.setId("nemavrednost");
        librarian.setDescription("Нема вредност");
        librarian.setLibrary(null);
        librarianRepository.save(librarian);

    }

    private void importMembershipType(){
        List<MembershipType> membershipTypeList = membershipTypeRepositoryMongo.findAll();
        Integer counter = 1;
        for(MembershipType mt: membershipTypeList){
            com.ftninformatika.bisis.datawarehouse.entity.MembershipType membershipType = new com.ftninformatika.bisis.datawarehouse.entity.MembershipType();
            membershipType.setId(String.valueOf(counter));
            if (mt.getLibrary() !=null){
                membershipType.setId(counter+"_"+mt.getLibrary());
            }else{
                membershipType.setId(String.valueOf(counter));
            }
            membershipType.setDescription(mt.getDescription());
            membershipType.setLibrary(mt.getLibrary());
            membershipTypeRepository.save(membershipType);
            counter++;
        }
        com.ftninformatika.bisis.datawarehouse.entity.MembershipType membershipType = new com.ftninformatika.bisis.datawarehouse.entity.MembershipType();
        membershipType.setId(String.valueOf(counter));
        membershipType.setDescription("Нема вредност");
        membershipType.setLibrary(null);
        membershipTypeRepository.save(membershipType);

    }

    private void importCategory(){
        List<UserCategory> categoryList = userCategRepository.findAll();
        Integer counter = 1;
        for(UserCategory uc: categoryList){
            Category category = new Category();
            if (uc.getLibrary() !=null){
                category.setId(counter+"_"+uc.getLibrary());
            }else{
                category.setId(String.valueOf(counter));
            }
            category.setDescription(uc.getDescription());
            category.setLibrary(uc.getLibrary());
            categoryRepository.save(category);
            counter++;
        }
        Category category = new Category();
        category.setId(String.valueOf(counter));
        category.setDescription("Нема вредност");
        category.setLibrary(null);
        categoryRepository.save(category);

    }

    public void importCoders(){
        try {
            importCoder(com.ftninformatika.bisis.datawarehouse.entity.AccessionRegister.class, accessionRegisterRepository, accessionRegisterRepositoryMongo);

            importCoder(com.ftninformatika.bisis.datawarehouse.entity.Acquisition.class, acquisitionRepository, acquisitionRepositoryMongo);

            importCoder(com.ftninformatika.bisis.datawarehouse.entity.InternalMark.class, internalMarkRepository, internalMarkRepositoryMongo);

            importCoder(com.ftninformatika.bisis.datawarehouse.entity.Location.class, locationRepository, locationRepositoryMongo);

            importCoder(com.ftninformatika.bisis.datawarehouse.entity.Sublocation.class, sublocationRepository, sublocationRepositoryMongo);

            importCoder(com.ftninformatika.bisis.datawarehouse.entity.Status.class, statusRepository, statusRepositoryMongo);

            importLibrarian();
            importCircLocation();
            importMembershipType();
            importCategory();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private void handleRecord(Record record, String library, List<Item> itemList, List<com.ftninformatika.bisis.datawarehouse.entity.Record> recordList){
        List<Primerak> primerci = record.getPrimerci();

        String recordTypeRec = RecordUtility.getRecordType(record);
        RecordType recordType = recordTypeMap.get(recordTypeRec);
        RecordType recordTypeNone = recordTypeMap.get("nemavrednost");

        String targetRec = RecordUtility.getTarget(record);
        Target target = targetMap.get(targetRec);
        Target targetNone = targetMap.get("nemavrednost");

        String bibLevelRec = RecordUtility.getBibliographicLevel(record);
        BibliographicLevel bibliographicLevel = bibliographicLevelMap.get(bibLevelRec);
        BibliographicLevel bibliographicLevelNone = bibliographicLevelMap.get("nemavrednost");

        String serialTypeRec = RecordUtility.getSerialType(record);
        SerialType serialType = serialTypeMap.get(serialTypeRec);
        SerialType serialTypeNone = serialTypeMap.get("nemavrednost");

        List<String> countryList = RecordUtility.getCountry(record);
        List<Country> countries = new ArrayList<Country>();
        Country countryNone = countryMap.get("nemavrednost");
        for(String country: countryList) {
            if (countryMap.get(country) != null){
                countries.add(countryMap.get(country));
            }else{
                countries.add(countryNone);
            }
        }
        if (countries.isEmpty()){
            countries.add(countryNone);
        }

        List<String> languageList = RecordUtility.getLanguage(record);
        Language languageNone = languageMap.get("nemavrednost");
        List<Language> languages = new ArrayList<Language>();
        for(String lang: languageList){
            if (languageMap.get(lang) != null) {
                languages.add(languageMap.get(lang));
            }else{
                languages.add(languageNone);
            }
        }
        if (languages.isEmpty()){
            languages.add(languageNone);
        }

        List<String> udkList = RecordUtility.getUDKs(record);
        Udk udksNone = udkMap.get("nemavrednost");
        List<Udk> udks = new ArrayList<Udk>();
        for(String lang: udkList){
            if(udkMap.get(lang) != null){
                udks.add(udkMap.get(lang));
            }else{
                udks.add(udksNone);
            }
        }
        if (udks.isEmpty()){
            udks.add(udksNone);
        }

        String title = RecordUtility.getTitle(record);
        String author = RecordUtility.getAutor(record);
        String publisher = RecordUtility.getPublisher(record);
        String publicationYear = RecordUtility.getPublicationYear(record);

        com.ftninformatika.bisis.datawarehouse.entity.Record  recordDW = new com.ftninformatika.bisis.datawarehouse.entity.Record();
        recordDW.setId(String.valueOf(record.getRecordID())+"_"+library);
        recordDW.setAuthor(author);
        recordDW.setTitle(title);
        recordDW.setPublisher(publisher);
        recordDW.setPublicationYear(publicationYear);
        recordDW.setLibrary(library);
        recordList.add(recordDW);

        for (Primerak p: primerci){
            Item i = new Item();

            i.setLibrary(library);
            i.setRecord(recordDW);

            if(recordType != null){
                i.setRecordType(recordType);
            }else{
                i.setRecordType(recordTypeNone);
            }

            if(target != null){
                i.setTarget(target);
            }else{
                i.setTarget(targetNone);
            }

            if(bibliographicLevel != null){
                i.setBibliographicLevel(bibliographicLevel);
            }else{
                i.setBibliographicLevel(bibliographicLevelNone);
            }

            i.setCtlgNo(p.getInvBroj());
            Date datumInventarisanja = p.getDatumInventarisanja();

            if (datumInventarisanja == null){
                i.setCtlgDate(convertToLocalDateTimeViaInstant(record.getCreationDate()));
            }else{
                i.setCtlgDate(convertToLocalDateTimeViaInstant(datumInventarisanja));
            }
            String acquisitionRec = p.getNacinNabavke();
            if (acquisitionRec !=null) {
                Acquisition acquisition1 = acquisitionMap.get(acquisitionRec);
                Acquisition acquisition2 = acquisitionMap.get(acquisitionRec + "_" + library);
                if (acquisition1 != null) {
                    i.setAcquisition(acquisition1);
                }else if (acquisition2 != null){
                    i.setAcquisition(acquisition2);
                }else{
                    ///TODO
                    Logger.getLogger(ImportService.class).warn("Nema sifre nabavke "+ acquisitionRec+"->"+p.getInvBroj());
                }
            }else{
                Acquisition acquisitionNone = acquisitionMap.get("nemavrednost");
                i.setAcquisition(acquisitionNone);
            }
            String internalMarkRec = p.getSigIntOznaka();
            if (internalMarkRec !=null) {
                InternalMark internalMark1 = internalMarkMap.get(internalMarkRec);
                InternalMark internalMark2 = internalMarkMap.get(internalMarkRec + "_" + library);
                if (internalMark1 != null) {
                    i.setInternalMark(internalMark1);
                }else if (internalMark2 != null){
                    i.setInternalMark(internalMark2);
                }else{
                    ///TODO
                    Logger.getLogger(ImportService.class).warn("Nema sifre interne oznake "+ internalMarkRec+"->"+p.getInvBroj());
                }
            }else{
                InternalMark internalMarkNone = internalMarkMap.get("nemavrednost");
                i.setInternalMark(internalMarkNone);
            }
            String statusRec = p.getStatus();
            if (statusRec !=null) {
                Status status1 = statusMap.get(statusRec);
                Status status2 = statusMap.get(statusRec + "_" + library);
                if (status1 != null) {
                    i.setStatus(status1);
                }else if (status2 != null){
                    i.setStatus(status2);
                }else{
                    ///TODO
                    Logger.getLogger(ImportService.class).warn("Nema sifre statusa "+ statusRec+"->"+p.getInvBroj());
                }
            }else{
                Status statusNone = statusMap.get("nemavrednost");
                i.setStatus(statusNone);
            }
            String locationRec = p.getOdeljenje();
            if (locationRec !=null) {
                Location location1 = locationMap.get(locationRec);
                Location location2 = locationMap.get(locationRec + "_" + library);
                if (location1 != null) {
                    i.setLocation(location1);
                }else if (location2 != null){
                    i.setLocation(location2);
                }else{
                    ///TODO
                    Logger.getLogger(ImportService.class).warn("Nema sifre lokacije "+ locationRec+"->"+p.getInvBroj());
                }
            }else{
                Location locationNone = locationMap.get("nemavrednost");
                i.setLocation(locationNone);
            }
            String sublocationRec = p.getSigPodlokacija();
            if (sublocationRec !=null) {
                Sublocation sublocation1 = sublocationMap.get(sublocationRec);
                Sublocation sublocation2 = sublocationMap.get(sublocationRec + "_" + library);
                if (sublocation1 != null) {
                    i.setSublocation(sublocation1);
                }else if (sublocation2 != null){
                    i.setSublocation(sublocation2);
                }else{
                    ///TODO
                    Logger.getLogger(ImportService.class).warn("Nema sifre podlokacije "+ sublocationRec+"->"+p.getInvBroj());
                }
            }else{
                Sublocation sublocationNone = sublocationMap.get("nemavrednost");
                i.setSublocation(sublocationNone);
            }
            BigDecimal price = p.getCena();
            if (price != null){
                i.setPrice(price);
            }else{
                i.setPrice(new BigDecimal(0));
            }
            String accessionRegisterRec = p.getInvBroj().substring(2,4);
            if (accessionRegisterRec !=null) {
                AccessionRegister accessionRegister = accessionRegisterMap.get(accessionRegisterRec + "_" + library);
                if (accessionRegister != null) {
                    i.setAccessionRegister(accessionRegister);
                }else{
                    ///TODO
                    Logger.getLogger(ImportService.class).warn("Nema sifre inv knjige "+ accessionRegisterRec+"->"+p.getInvBroj());
                }
            }else{
                AccessionRegister accessionRegisterNone = accessionRegisterMap.get("nemavrednost");
                i.setAccessionRegister(accessionRegisterNone);
            }

            for(Udk udk:udks){
                for(Language language:languages){
                    for(Country country:countries){
                        Item item = new Item(i);
                        item.setCountry(country);
                        item.setLanguage(language);
                        item.setUdk(udk);
                        itemList.add(item);
                    }
                }
            }
        }
    }

    public void handleImport(){
        deleteDataWarehouse();
        importCoders();
        List<LibraryConfiguration> libraryConfigurationList = libraryConfigurationRepository.findAll();
        for(LibraryConfiguration lc:libraryConfigurationList){
            importData(lc.getLibraryName());
        }

    }
    public void handleImportOneLibrary(String library){
        itemRepository.deleteByLibrary(library);
        lendingRepository.deleteByLibrary(library);
        membershipRepository.deleteByLibrary(library);
        importData(library);
    }
    private void importData(String library){
        initMaps(library);
        libraryPrefixProvider.setPrefix(library);
        Pageable p = PageRequest.of(0, 1000);
        int count = 0;
        Page<Record> recordsPage = recordsRepositoryMongo.findAll(p);
        int pageCount = recordsPage.getTotalPages();
        for (int i = 0; i < pageCount; i++) {
            List<Item> itemList = new ArrayList<Item>();
            List<com.ftninformatika.bisis.datawarehouse.entity.Record> recordList = new ArrayList<com.ftninformatika.bisis.datawarehouse.entity.Record>();
            for (Record r : recordsPage) {
                count++;
                handleRecord(r, library,itemList,recordList);
                if (count % 1000 == 0){
                    System.out.println("Records processed at "+ LocalDateTime.now()+": "+ count);
                }
            }
            recordRepository.saveAll(recordList);
            recordRepository.flush();
            itemRepository.saveAll(itemList);
            if (!recordsPage.isLast()) {
                p = recordsPage.nextPageable();
                recordsPage = recordsRepositoryMongo.findAll(p);
            }
        }

    }
    private void initMaps(String library){
        List<Acquisition> acquisitionList = acquisitionRepository.findByLibraryIsNullOrLibrary(library);
        acquisitionMap= initCoders(acquisitionList);

        List<AccessionRegister> accessionRegisterList = accessionRegisterRepository.findByLibraryIsNullOrLibrary(library);
        accessionRegisterMap= initCoders(accessionRegisterList);

        List<RecordType> recordTypeList = recordTypeRepository.findByLibraryIsNullOrLibrary(library);
        recordTypeMap= initCoders(recordTypeList);

        List<Target> targetList = targetRepository.findByLibraryIsNullOrLibrary(library);
        targetMap= initCoders(targetList);

        List<BibliographicLevel> bibliographicLevelList = bibliographicLevelRepository.findByLibraryIsNullOrLibrary(library);
        bibliographicLevelMap= initCoders(bibliographicLevelList);

        List<SerialType> serialTypeList = serialTypeRepository.findByLibraryIsNullOrLibrary(library);
        serialTypeMap= initCoders(serialTypeList);

        List<InternalMark> internalMarkList = internalMarkRepository.findByLibraryIsNullOrLibrary(library);
        internalMarkMap= initCoders(internalMarkList);

        List<Status> statusList = statusRepository.findByLibraryIsNullOrLibrary(library);
        statusMap= initCoders(statusList);

        List<Location> locationList = locationRepository.findByLibraryIsNullOrLibrary(library);
        locationMap= initCoders(locationList);

        List<Sublocation> sublocationList = sublocationRepository.findByLibraryIsNullOrLibrary(library);
        sublocationMap= initCoders(sublocationList);

        List<Country> countryList = countryRepository.findByLibraryIsNullOrLibrary(library);
        countryMap= initCoders(countryList);

        List<Udk> udkList = udkRepository.findByLibraryIsNullOrLibrary(library);
        udkMap= initCoders(udkList);

        List<Language> languageList = languageRepository.findByLibraryIsNullOrLibrary(library);
        languageMap= initCoders(languageList);

    }
}
