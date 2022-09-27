package com.ftninformatika.bisis.datawarehouse.service;

import com.ftninformatika.bisis.circ.CircLocation;
import com.ftninformatika.bisis.circ.MembershipType;
import com.ftninformatika.bisis.circ.UserCategory;
import com.ftninformatika.bisis.coders.Coder;
import com.ftninformatika.bisis.core.repositories.*;
import com.ftninformatika.bisis.datawarehouse.entity.Category;
import com.ftninformatika.bisis.datawarehouse.entity.Librarian;
import com.ftninformatika.bisis.datawarehouse.repository.AccessionRegisterRepository;
import com.ftninformatika.bisis.datawarehouse.repository.CircLocationRepository;
import com.ftninformatika.bisis.datawarehouse.repository.InternalMarkRepository;
import com.ftninformatika.bisis.datawarehouse.repository.LendingRepository;
import com.ftninformatika.bisis.datawarehouse.repository.LibrarianRepository;
import com.ftninformatika.bisis.datawarehouse.repository.LocationRepository;
import com.ftninformatika.bisis.datawarehouse.repository.MembershipRepository;
import com.ftninformatika.bisis.datawarehouse.repository.MembershipTypeRepository;
import com.ftninformatika.bisis.datawarehouse.repository.*;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImportService {

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

    private void deleteDataWarehouse(){
        ///TODO obrisati prvo fact table i obrisati sekvence

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


    public void handleImport(){
        deleteDataWarehouse();
        importCoders();
        /*List<LibraryConfiguration> libraryConfigurationList = libraryConfigurationRepository.findAll();
        for(LibraryConfiguration lc:libraryConfigurationList){
        }*/

    }

}
