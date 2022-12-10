package com.ftninformatika.bisis.datawarehouse.service;

import com.ftninformatika.bisis.circ.CircLocation;
import com.ftninformatika.bisis.circ.MembershipType;
import com.ftninformatika.bisis.circ.UserCategory;
import com.ftninformatika.bisis.circ.pojo.Signing;
import com.ftninformatika.bisis.coders.Coder;
import com.ftninformatika.bisis.core.repositories.*;
import com.ftninformatika.bisis.datawarehouse.entity.*;
import com.ftninformatika.bisis.datawarehouse.repository.AccessionRegisterRepository;
import com.ftninformatika.bisis.datawarehouse.repository.CircLocationRepository;
import com.ftninformatika.bisis.datawarehouse.repository.CorporateMemberRepository;
import com.ftninformatika.bisis.datawarehouse.repository.InternalMarkRepository;
import com.ftninformatika.bisis.datawarehouse.repository.LanguageRepository;
import com.ftninformatika.bisis.datawarehouse.repository.LendingRepository;
import com.ftninformatika.bisis.datawarehouse.repository.LibrarianRepository;
import com.ftninformatika.bisis.datawarehouse.repository.LocationRepository;
import com.ftninformatika.bisis.datawarehouse.repository.MemberRepository;
import com.ftninformatika.bisis.datawarehouse.repository.MembershipRepository;
import com.ftninformatika.bisis.datawarehouse.repository.MembershipTypeRepository;
import com.ftninformatika.bisis.datawarehouse.repository.*;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.records.Godina;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Sveska;
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
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImportService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    RecordTypeRepository recordTypeRepository;

    @Autowired
    TargetRepository targetRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    ContentTypeRepository contentTypeRepository;

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
    com.ftninformatika.bisis.core.repositories.MemberRepository membersRepositoryMongo;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserCategRepository userCategRepository;

    @Autowired
    com.ftninformatika.bisis.core.repositories.CorporateMemberRepository corporateMemberRepositoryMongo;

    @Autowired
    @Qualifier("corporateMemberJPARepository")
    CorporateMemberRepository corporateMemberRepository;

    @Autowired
    @Qualifier("circLocationJPARepository")
    CircLocationRepository circLocationRepository;

    @Autowired
    GenderRepository genderRepository;

    @Autowired
    com.ftninformatika.bisis.core.repositories.CircLocationRepository circLocationRepositoryMongo;

    @Autowired
    @Qualifier("lendingJPARepository")
    LendingRepository lendingRepository;

    @Autowired
    com.ftninformatika.bisis.core.repositories.LendingRepository lendingRepositoryMongo;

    @Autowired
    LendingActionRepository lendingActionRepository;

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

    @Autowired
    CustomRepository customRepository;

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
    Map <String, ContentType> contentTypeMap;

    Map <String, Category> categoryMap;
    Map <String, Gender> genderMap;
    Map <String, Librarian> librarianMap;
    Map <String, LendingAction> lendingActionMap;
    Map <String, com.ftninformatika.bisis.datawarehouse.entity.CircLocation> circLocationMap;
    Map <String, com.ftninformatika.bisis.datawarehouse.entity.MembershipType> membershipTypeMap;
    Map <String,CorporateMember> corporateMemberMap;


    private Map initCoders(List coders){
        LinkedCaseInsensitiveMap <com.ftninformatika.bisis.datawarehouse.entity.Coder> map = new LinkedCaseInsensitiveMap<> ();
        for (Object c:coders){
            map.put(((com.ftninformatika.bisis.datawarehouse.entity.Coder) c).getId(),(com.ftninformatika.bisis.datawarehouse.entity.Coder) c);
        }
        return map;
    }

    private Map initCodersWithDescription(List coders, String library){
        LinkedCaseInsensitiveMap <com.ftninformatika.bisis.datawarehouse.entity.Coder> map = new LinkedCaseInsensitiveMap<> ();
        for (Object c:coders){
            if (((com.ftninformatika.bisis.datawarehouse.entity.Coder) c).getId().equals("nemavrednost")){
                map.put(((com.ftninformatika.bisis.datawarehouse.entity.Coder) c).getId(),(com.ftninformatika.bisis.datawarehouse.entity.Coder) c);

            }else{
                map.put(((com.ftninformatika.bisis.datawarehouse.entity.Coder) c).getDescription()+"_"+library,(com.ftninformatika.bisis.datawarehouse.entity.Coder) c);

            }
        }
        return map;
    }


    public void deleteDataWarehouse(){

        customRepository.deleteAllLendingInBatch();
        customRepository.deleteAllMembershipInBatch();
        customRepository.deleteAllItemInBatch();

        customRepository.resetSequence();

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
        corporateMemberRepository.deleteAllInBatch();
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

    private void importCorporateMember(){
        List<com.ftninformatika.bisis.circ.CorporateMember> corporateMemberList = corporateMemberRepositoryMongo.findAll();
        for(com.ftninformatika.bisis.circ.CorporateMember cm: corporateMemberList){
            CorporateMember corporateMember = new CorporateMember();
            if (cm.getLibrary() !=null){
                corporateMember.setId(cm.getUserId()+"_"+cm.getLibrary());
            }else{
                corporateMember.setId(cm.getUserId());
            }
            corporateMember.setDescription(cm.getInstName());
            corporateMember.setLibrary(cm.getLibrary());
            corporateMemberRepository.save(corporateMember);
        }
        CorporateMember corporateMember = new CorporateMember();
        corporateMember.setId("nemavrednost");
        corporateMember.setDescription("Нема вредност");
        corporateMember.setLibrary(null);
        corporateMemberRepository.save(corporateMember);

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
            importCorporateMember();

            com.ftninformatika.bisis.datawarehouse.entity.Record r = new com.ftninformatika.bisis.datawarehouse.entity.Record();
            r.setId("nemavrednost");
            r.setPublisher(" ");
            r.setTitle(" ");
            r.setAuthor(" ");
            r.setPublicationYear(" ");
            recordRepository.save(r);

            Member m = new Member();
            m.setId("nemavrednost");
            m.setCity(" ");
            m.setAddress(" ");
            m.setLastName(" ");
            m.setFirstName(" ");
            memberRepository.save(m);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        if (dateToConvert != null) {
            return dateToConvert.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        }else{
            ///TODO
            return null;
        }
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

        List<String> contentTypeList = RecordUtility.getContentType(record);
        ContentType contentTypeNone = contentTypeMap.get("nemavrednost");
        List<ContentType> contentTypes = new ArrayList<ContentType>();
        for(String content: contentTypeList){
            if (contentTypeMap.get(content) != null) {
                contentTypes.add(contentTypeMap.get(content));
            }else{
                contentTypes.add(contentTypeNone);
            }
        }
        if (contentTypes.isEmpty()){
            contentTypes.add(contentTypeNone);
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
        recordDW.setId(record.getRecordID() +"_"+library);
        if (record.getRN() != 0) {
            recordDW.setRn(String.valueOf(record.getRN()));
        }else{
            recordDW.setRn("");
        }
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
                    Acquisition acquisitionNone = acquisitionMap.get("nemavrednost");
                    i.setAcquisition(acquisitionNone);
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
                    InternalMark internalMarkNone = internalMarkMap.get("nemavrednost");
                    i.setInternalMark(internalMarkNone);
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
                    Status statusNone = statusMap.get("nemavrednost");
                    i.setStatus(statusNone);
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
                    Location locationNone = locationMap.get("nemavrednost");
                    i.setLocation(locationNone);
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
                    Sublocation sublocationNone = sublocationMap.get("nemavrednost");
                    i.setSublocation(sublocationNone);
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
            if(p.getInvBroj() == null || p.getInvBroj().length() != 11){
                AccessionRegister accessionRegisterNone = accessionRegisterMap.get("nemavrednost");
                i.setAccessionRegister(accessionRegisterNone);
            }else {
                String accessionRegisterRec = p.getInvBroj().substring(2, 4);
                if (accessionRegisterRec != null) {
                    AccessionRegister accessionRegister = accessionRegisterMap.get(accessionRegisterRec + "_" + library);
                    if (accessionRegister != null) {
                        i.setAccessionRegister(accessionRegister);
                    } else {
                        AccessionRegister accessionRegisterNone = accessionRegisterMap.get("nemavrednost");
                        i.setAccessionRegister(accessionRegisterNone);
                    }
                } else {
                    AccessionRegister accessionRegisterNone = accessionRegisterMap.get("nemavrednost");
                    i.setAccessionRegister(accessionRegisterNone);
                }
            }
            for(Udk udk:udks){
                i.getUdks().add(udk);
            }
            for(Language language:languages){
                i.getLanguages().add(language);
            }
            for(Country country:countries){
                i.getCountries().add(country);
            }
            for(ContentType content:contentTypes) {
                i.getContentTypes().add(content);
            }
                itemList.add(i);
        }
        for(Godina g: record.getGodine()){
            if (g.getInvBroj() == null){
                continue;
            }
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
            if(serialType != null){
                i.setSerialType(serialType);
            }else{
                i.setSerialType(serialTypeNone);
            }
            i.setCtlgNo(g.getInvBroj());
            Date datumInventarisanja = g.getDatumInventarisanja();

            if (datumInventarisanja == null){
                i.setCtlgDate(convertToLocalDateTimeViaInstant(record.getCreationDate()));
            }else{
                i.setCtlgDate(convertToLocalDateTimeViaInstant(datumInventarisanja));
            }
            String acquisitionRec = g.getNacinNabavke();
            if (acquisitionRec !=null) {
                Acquisition acquisition1 = acquisitionMap.get(acquisitionRec);
                Acquisition acquisition2 = acquisitionMap.get(acquisitionRec + "_" + library);
                if (acquisition1 != null) {
                    i.setAcquisition(acquisition1);
                }else if (acquisition2 != null){
                    i.setAcquisition(acquisition2);
                }else{
                    Acquisition acquisitionNone = acquisitionMap.get("nemavrednost");
                    i.setAcquisition(acquisitionNone);
                  }
            }else{
                Acquisition acquisitionNone = acquisitionMap.get("nemavrednost");
                i.setAcquisition(acquisitionNone);
            }
            String internalMarkRec = g.getSigIntOznaka();
            if (internalMarkRec !=null) {
                InternalMark internalMark1 = internalMarkMap.get(internalMarkRec);
                InternalMark internalMark2 = internalMarkMap.get(internalMarkRec + "_" + library);
                if (internalMark1 != null) {
                    i.setInternalMark(internalMark1);
                }else if (internalMark2 != null){
                    i.setInternalMark(internalMark2);
                }else{
                    InternalMark internalMarkNone = internalMarkMap.get("nemavrednost");
                    i.setInternalMark(internalMarkNone);
                }
            }else{
                InternalMark internalMarkNone = internalMarkMap.get("nemavrednost");
                i.setInternalMark(internalMarkNone);
            }
            String locationRec = g.getOdeljenje();
            if (locationRec !=null) {
                Location location1 = locationMap.get(locationRec);
                Location location2 = locationMap.get(locationRec + "_" + library);
                if (location1 != null) {
                    i.setLocation(location1);
                }else if (location2 != null){
                    i.setLocation(location2);
                }else{
                    Location locationNone = locationMap.get("nemavrednost");
                    i.setLocation(locationNone);
                }
            }else{
                Location locationNone = locationMap.get("nemavrednost");
                i.setLocation(locationNone);
            }
            String sublocationRec = g.getSigPodlokacija();
            if (sublocationRec !=null) {
                Sublocation sublocation1 = sublocationMap.get(sublocationRec);
                Sublocation sublocation2 = sublocationMap.get(sublocationRec + "_" + library);
                if (sublocation1 != null) {
                    i.setSublocation(sublocation1);
                }else if (sublocation2 != null){
                    i.setSublocation(sublocation2);
                }else{
                    Sublocation sublocationNone = sublocationMap.get("nemavrednost");
                    i.setSublocation(sublocationNone);
                }
            }else{
                Sublocation sublocationNone = sublocationMap.get("nemavrednost");
                i.setSublocation(sublocationNone);
            }
            BigDecimal price = g.getCena();
            if (price != null){
                i.setPrice(price);
            }else{
                i.setPrice(new BigDecimal(0));
            }
            String accessionRegisterRec = g.getInvBroj().substring(2,4);
            if (accessionRegisterRec !=null) {
                AccessionRegister accessionRegister = accessionRegisterMap.get(accessionRegisterRec + "_" + library);
                if (accessionRegister != null) {
                    i.setAccessionRegister(accessionRegister);
                }else{
                    AccessionRegister accessionRegisterNone = accessionRegisterMap.get("nemavrednost");
                    i.setAccessionRegister(accessionRegisterNone);
                }
            }else{
                AccessionRegister accessionRegisterNone = accessionRegisterMap.get("nemavrednost");
                i.setAccessionRegister(accessionRegisterNone);
            }

                Status statusNone = statusMap.get("nemavrednost");
            for(Udk udk:udks){
                i.getUdks().add(udk);
            }
            for(Language language:languages){
                i.getLanguages().add(language);
            }
            for(Country country:countries){
                i.getCountries().add(country);
            }
            for(ContentType content:contentTypes) {
                i.getContentTypes().add(content);
            }
            if ((g.getSveske() == null) || (g.getSveske().isEmpty())) {
                i.setStatus(statusNone);
                itemList.add(i);
            }else if ((g.getSveske() != null) && (!g.getSveske().isEmpty())) {
                for(Sveska s: g.getSveske()){
                    Item item = new Item(i);
                    String statusRec = s.getStatus();
                    if (statusRec !=null) {
                        Status status1 = statusMap.get(statusRec);
                        Status status2 = statusMap.get(statusRec + "_" + library);
                        if (status1 != null) {
                            item.setStatus(status1);
                        }else if (status2 != null){
                            item.setStatus(status2);
                        }else{
                            item.setStatus(statusNone);
                        }
                    }else{
                        item.setStatus(statusNone);
                    }
                    item.setIssueNo(s.getInvBroj());
                    itemList.add(item);
                }
            }
            }
        }

    public void handleImport(){
        Logger.getLogger(ImportService.class).info("Import all started...");
        deleteDataWarehouse();
        Logger.getLogger(ImportService.class).info("Datawarehouse is deleted");
        importCoders();
        List<LibraryConfiguration> libraryConfigurationList = libraryConfigurationRepository.findAll();
        for(LibraryConfiguration lc:libraryConfigurationList){
            Logger.getLogger(ImportService.class).info("Import of library "+lc.getLibraryName() +" started...");
            importRecordData(lc.getLibraryName());
            importMembershipData(lc.getLibraryName());
            importLendingData(lc.getLibraryName());
            Logger.getLogger(ImportService.class).info("Import of library "+lc.getLibraryName() +" is finished.");
        }
        Logger.getLogger(ImportService.class).info("Import of all libraries is finished!");

    }
    public void handleImportRecordOneLibrary(String library){
        Logger.getLogger(ImportService.class).info("Import records of library "+library+" started...");
        customRepository.deleteItemByLibrary(library);
        recordRepository.deleteAllByLibrary(library);
        importRecordData(library);
        Logger.getLogger(ImportService.class).info("Import of data finished!");
    }
    public void handleImportMemberOneLibrary(String library){
        Logger.getLogger(ImportService.class).info("Import members of library "+library+" started...");
        customRepository.deleteMembershipByLibrary(library);
        memberRepository.deleteAllByLibrary(library);
        importMembershipData(library);
        Logger.getLogger(ImportService.class).info("Import of data finished!");
    }
    public void handleImportLendingOneLibrary(String library){
        Logger.getLogger(ImportService.class).info("Import lendings of library "+library+" started...");
        customRepository.deleteLendingByLibrary(library);
        importLendingData(library);
        Logger.getLogger(ImportService.class).info("Import of data finished!");
    }

    private void importRecordData(String library){
        initMaps(library);
        libraryPrefixProvider.setPrefix(library);
        Pageable p = PageRequest.of(0, 5000);
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
                    Logger.getLogger(ImportService.class).info("Records processed at "+ LocalDateTime.now()+": "+ count);
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

        List<ContentType> contentTypeList = contentTypeRepository.findByLibraryIsNullOrLibrary(library);
        contentTypeMap= initCoders(contentTypeList);

        List<Gender> genderList = genderRepository.findByLibraryIsNullOrLibrary(library);
        genderMap= initCoders(genderList);

        List<Librarian> librarianList = librarianRepository.findByLibraryIsNullOrLibrary(library);
        librarianMap= initCoders(librarianList);

        List<LendingAction> lendingActionList = lendingActionRepository.findByLibraryIsNullOrLibrary(library);
        lendingActionMap= initCoders(lendingActionList);

        List<com.ftninformatika.bisis.datawarehouse.entity.CorporateMember> corporateMemberList = corporateMemberRepository.findByLibraryIsNullOrLibrary(library);
        corporateMemberMap= initCoders(corporateMemberList);

        List<Category> categoryList = categoryRepository.findByLibraryIsNullOrLibrary(library);
        categoryMap= initCodersWithDescription(categoryList,library);

        List<com.ftninformatika.bisis.datawarehouse.entity.CircLocation> circLocationList = circLocationRepository.findByLibraryIsNullOrLibrary(library);
        circLocationMap= initCodersWithDescription(circLocationList,library);

        List<com.ftninformatika.bisis.datawarehouse.entity.MembershipType> membershipTypeList = membershipTypeRepository.findByLibraryIsNullOrLibrary(library);
        membershipTypeMap= initCodersWithDescription(membershipTypeList,library);



    }

    //import membership
    private void importMembershipData(String library){
        initMaps(library);
        libraryPrefixProvider.setPrefix(library);
        Pageable p = PageRequest.of(0, 5000);
        int count = 0;
        Page<com.ftninformatika.bisis.circ.Member> membersPage = membersRepositoryMongo.findAll(p);
        int pageCount = membersPage.getTotalPages();
        for (int i = 0; i < pageCount; i++) {
            List<Membership> membershipList = new ArrayList<>();
            List<Member> memberList = new ArrayList<>();
            for (com.ftninformatika.bisis.circ.Member m : membersPage) {
                count++;
                handleMember(m, library,membershipList,memberList);
                if (count % 1000 == 0){
                    Logger.getLogger(ImportService.class).info("Members processed at "+ LocalDateTime.now()+": "+ count);
                }
            }
            memberRepository.saveAll(memberList);
            memberRepository.flush();
            membershipRepository.saveAll(membershipList);
            if (!membersPage.isLast()) {
                p = membersPage.nextPageable();
                membersPage = membersRepositoryMongo.findAll(p);
            }
        }
    }

    private void handleMember(com.ftninformatika.bisis.circ.Member m, String library, List<Membership> membershipList, List<Member> memberList) {
        Member member = new Member();
        member.setId(m.getUserId()+"_"+library);
        member.setFirstName(m.getFirstName());
        member.setLastName(m.getLastName());
        member.setAddress(m.getAddress());
        member.setCity(m.getCity());
        member.setLibrary(library);
        memberList.add(member);

        String genderMem = m.getGender();
        Gender gender = genderMap.get(genderMem);
        if (gender == null) {
            gender = genderMap.get("nemavrednost");
        }
        Category category;
        if (m.getUserCategory() != null){
            String categoryMem = m.getUserCategory().getDescription();
            category = categoryMap.get(categoryMem+"_"+library);
            if (category == null){
                category = categoryMap.get("nemavrednost");
            }
        }else{
            category = categoryMap.get("nemavrednost");
        }

        com.ftninformatika.bisis.datawarehouse.entity.MembershipType membershipType;
        if (m.getMembershipType() !=null){
            String membershipTypeMem = m.getMembershipType().getDescription();
            membershipType = membershipTypeMap.get(membershipTypeMem+"_"+library);
            if (membershipType == null){
                membershipType = membershipTypeMap.get("nemavrednost");

            }
        }else{
            membershipType = membershipTypeMap.get("nemavrednost");
        }

        com.ftninformatika.bisis.datawarehouse.entity.CorporateMember corporateMember;
        if (m.getCorporateMember()!=null){
            String corporateMemberId = m.getCorporateMember().getUserId();
            corporateMember = corporateMemberMap.get(corporateMemberId+"_"+library);
            if (corporateMember == null){
                corporateMember = corporateMemberMap.get("nemavrednost");

            }
        }else{
            corporateMember = corporateMemberMap.get("nemavrednost");
        }

        List<Signing> sortSignings = m.getSignings().stream().filter(signing -> signing.getSignDate() != null).sorted(Comparator.comparing(Signing::getSignDate)).collect(Collectors.toList());
        boolean firstSigning = true;
        for(Signing signing: sortSignings){
            Membership membership = new Membership();
            membership.setMember(member);
            membership.setLibrary(library);
            if (signing.getCost() == null){
                membership.setFee(0.0);
            }else{
                membership.setFee(signing.getCost());
            }
            membership.setGender(gender);
            membership.setCategory(category);
            membership.setMembershipType(membershipType);
            membership.setCorporateMember(corporateMember);
            Librarian librarian = librarianMap.get(signing.getLibrarian());
            if (librarian == null){
                librarian = librarianMap.get("nemavrednost");
            }
            membership.setLibrarian(librarian);
            com.ftninformatika.bisis.datawarehouse.entity.CircLocation circLocation = circLocationMap.get(signing.getLocation()+"_"+library);
            if(circLocation == null){
                circLocation = circLocationMap.get("nemavrednost");
            }
            membership.setCircLocation(circLocation);
            membership.setDate(convertToLocalDateTimeViaInstant(signing.getSignDate()));
            if (firstSigning){
                membership.setFirstTime(true);
                firstSigning = false;
            }else{
                membership.setFirstTime(false);
            }
            membershipList.add(membership);

        }
    }

    //import lending
    private void importLendingData(String library){
        initMaps(library);
        libraryPrefixProvider.setPrefix(library);
        Pageable p = PageRequest.of(0, 20000);
        int count = 0;
        LocalDate cutoffdate = LocalDate.now().minusYears(2);
        Page<com.ftninformatika.bisis.circ.Lending> lendingsPage = lendingRepositoryMongo.findByLendDateAfter(cutoffdate, p);
        int pageCount = lendingsPage.getTotalPages();
        for (int i = 0; i < pageCount; i++) {
            List<Lending> lendingList = new ArrayList<>();
            for (com.ftninformatika.bisis.circ.Lending l : lendingsPage) {
                count++;
                handleLending(l, library,lendingList);
                if (count % 10000 == 0){
                    Logger.getLogger(ImportService.class).info("Lendings processed at "+ LocalDateTime.now()+": "+ count);
                }
            }
            lendingRepository.saveAll(lendingList);
            entityManager.clear();
            if (!lendingsPage.isLast()) {
                p = lendingsPage.nextPageable();
                lendingsPage = lendingRepositoryMongo.findByLendDateAfter(cutoffdate,p);
            }
        }
    }

    private void handleLending(com.ftninformatika.bisis.circ.Lending l, String library, List<Lending> lendingList) {
        List<Item> items = itemRepository.findByCtlgNoAndLibrary(l.getCtlgNo(), library);
        List<Membership> membershipList = membershipRepository.findByMember_Id(l.getUserId()+"_"+library);
        Membership membership;

        Lending lending = new Lending();
        lending.setLibrary(library);
        lending.setCtlgNo(l.getCtlgNo());
        if (items != null && !items.isEmpty()){
                Item item = items.get(0);
                lending.setAccessionRegister(item.getAccessionRegister());
                lending.setBibliographicLevel(item.getBibliographicLevel());
                lending.setRecordType(item.getRecordType());
                lending.setInternalMark(item.getInternalMark());
                lending.setSerialType(item.getSerialType());
                lending.setStatus(item.getStatus());
                lending.setTarget(item.getTarget());
                lending.setRecord(item.getRecord());

                Set <Udk> udks = item.getUdks();
                for (Udk u: udks){
                    lending.getUdks().add(udkMap.get(u.getId()));
                }
                Set <Language> languages = item.getLanguages();
                for (Language l1: languages){
                    lending.getLanguages().add(languageMap.get(l1.getId()));
                }
                Set <Country> countries = item.getCountries();
                for (Country c: countries){
                    lending.getCountries().add(countryMap.get(c.getId()));
                }
                Set <ContentType> contentTypes = item.getContentTypes();
                for (ContentType ct: contentTypes){
                    lending.getContentTypes().add(contentTypeMap.get(ct.getId()));
                }
        }
        else{
            lending.setAccessionRegister(accessionRegisterMap.get("nemavrednost"));
            lending.setBibliographicLevel(bibliographicLevelMap.get("nemavrednost"));
            lending.setRecordType(recordTypeMap.get("nemavrednost"));
            lending.setInternalMark(internalMarkMap.get("nemavrednost"));
            lending.setSerialType(serialTypeMap.get("nemavrednost"));
            lending.setStatus(statusMap.get("nemavrednost"));
            lending.setTarget(targetMap.get("nemavrednost"));
            com.ftninformatika.bisis.datawarehouse.entity.Record r = new com.ftninformatika.bisis.datawarehouse.entity.Record();
            r.setId("nemavrednost");
            lending.setRecord(r);
            lending.getUdks().add(udkMap.get("nemavrednost"));
            lending.getCountries().add(countryMap.get("nemavrednost"));
            lending.getLanguages().add(languageMap.get("nemavrednost"));
        }

        if (membershipList == null || membershipList.isEmpty()){
            lending.setCategory(categoryMap.get("nemavrednost"));
            lending.setGender(genderMap.get("nemavrednost"));
            Member m = new Member();
            m.setId("nemavrednost");
            lending.setMember(m);
            lending.setMembershipType(membershipTypeMap.get("nemavrednost"));
        }else{
            membership = membershipList.get(0);
            lending.setCategory(membership.getCategory());
            lending.setGender(membership.getGender());
            lending.setMember(membership.getMember());
            lending.setMembershipType(membership.getMembershipType());
        }

        com.ftninformatika.bisis.datawarehouse.entity.CircLocation circLocation = circLocationMap.get(l.getLocation()+"_"+library);
        if(circLocation == null){
            circLocation = circLocationMap.get("nemavrednost");
        }
        lending.setCircLocation(circLocation);


        Librarian librarian;
        if (l.getReturnDate() != null) {
            Lending lendingReturn = new Lending(lending);
            lendingReturn.setLendingAction(lendingActionMap.get("razduzeno"));
            lendingReturn.setDate(convertToLocalDateTimeViaInstant(l.getReturnDate()));
            librarian = librarianMap.get(l.getLibrarianReturn());
            if (librarian == null) {
                librarian = librarianMap.get("nemavrednost");
            }
            lendingReturn.setLibrarian(librarian);
            lendingList.add(lendingReturn);

        }
       if (l.getLendDate() != null) {
            Lending lendingLend = new Lending(lending);
            lendingLend.setLendingAction(lendingActionMap.get("zaduzeno"));
            lendingLend.setDate(convertToLocalDateTimeViaInstant(l.getLendDate()));
            librarian = librarianMap.get(l.getLibrarianLend());
            if (librarian == null) {
                librarian = librarianMap.get("nemavrednost");
            }
            lendingLend.setLibrarian(librarian);
            lendingList.add(lendingLend);

        }
        if (l.getResumeDate() != null) {
            Lending lendingResume = new Lending(lending);
            lendingResume.setLendingAction(lendingActionMap.get("produzeno"));
            lendingResume.setDate(convertToLocalDateTimeViaInstant(l.getResumeDate()));
            librarian = librarianMap.get(l.getLibrarianResume());
            if (librarian == null) {
                librarian = librarianMap.get("nemavrednost");
            }
            lendingResume.setLibrarian(librarian);
            lendingList.add(lendingResume);

        }

    }
}
