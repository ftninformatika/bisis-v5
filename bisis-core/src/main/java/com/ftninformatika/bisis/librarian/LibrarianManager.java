package com.ftninformatika.bisis.librarian;

import com.ftninformatika.bisis.librarian.db.LibrarianContextDB;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.librarian.dto.LibrarianContextDTO;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.librarian.dto.ProcessTypeDTO;

/**
 * Created by Petar on 8/10/2017.
 */
public class LibrarianManager {


//    public static Librarian initializeLibrarianFromDTO(LibrarianDTO librarianDTO){
//
//        Librarian retVal = new Librarian();
//        retVal.set_id(librarianDTO.get_id());
//        retVal.setUsername(librarianDTO.getUsername());
//        retVal.setPassword(librarianDTO.getPassword());
//        retVal.setIme(librarianDTO.getIme());
//        retVal.setPrezime(librarianDTO.getPrezime());
//        retVal.setEmail(librarianDTO.getEmail());
//        retVal.setNapomena(librarianDTO.getNapomena());
//        retVal.setObrada(librarianDTO.isObrada());
//        retVal.setCirculation(librarianDTO.isCirkulacija());
//        retVal.setCirkulacijaPlus(librarianDTO.isCirkulacijaPlus());
//        retVal.setAdministracija(librarianDTO.isAdministracija());
//        retVal.setInventator(librarianDTO.isInventator());
//        retVal.setRedaktor(librarianDTO.isRedaktor());
//        retVal.setBiblioteka(librarianDTO.getBiblioteka());
//        retVal.setCurentProcessType(ProcessTypeBuilder.buildProcessTypeFromDTO(librarianDTO.getCurentProcessType()));
//        retVal.setContext(initializeContextFromDTO(librarianDTO.getContext()));
//        retVal.setDefaultDepartment(librarianDTO.getDefaultDepartment());
//        retVal.setCircDepartment(librarianDTO.getCircDepartment());
//        retVal.setOpacAdmin(librarianDTO.isOpacAdmin());
//        retVal.setDeziderati(librarianDTO.isDeziderati());
//        retVal.setNabavka(librarianDTO.isNabavka());
//        return retVal;
//    }

    public static LibrarianDB initializeLibrarianDBFromDTO(LibrarianDTO librarianDTO){

        LibrarianDB retVal = new LibrarianDB();
        retVal.set_id(librarianDTO.get_id());
        retVal.setBiblioteka(librarianDTO.getBiblioteka());
        retVal.setUsername(librarianDTO.getUsername());
        retVal.setPassword(librarianDTO.getPassword());
        retVal.setIme(librarianDTO.getIme());
        retVal.setPrezime(librarianDTO.getPrezime());
        retVal.setEmail(librarianDTO.getEmail());
        retVal.setNapomena(librarianDTO.getNapomena());
        if (librarianDTO.isObrada()) {
            retVal.getLibrarianRoles().add(Librarian.Role.OBRADA);
        }
        if (librarianDTO.isCirkulacija()) {
            retVal.getLibrarianRoles().add(Librarian.Role.CIRKULACIJA);
        }
        if (librarianDTO.isCirkulacijaPlus()) {
            retVal.getLibrarianRoles().add(Librarian.Role.CIRKULACIJAPLUS);
        }
        if (librarianDTO.isAdministracija()) {
            retVal.getLibrarianRoles().add(Librarian.Role.ADMINISTRACIJA);
        }
        if (librarianDTO.isInventator()) {
            retVal.getLibrarianRoles().add(Librarian.Role.INVENTATOR);
        }
        if (librarianDTO.isRedaktor()) {
            retVal.getLibrarianRoles().add(Librarian.Role.REDAKTOR);
        }
        if (librarianDTO.isOpacAdmin()) {
            retVal.getLibrarianRoles().add(Librarian.Role.OPACADMIN);
        }
        if (librarianDTO.isNabavka()) {
            retVal.getLibrarianRoles().add(Librarian.Role.NABAVKA);
        }
        if (librarianDTO.isDeziderati()) {
            retVal.getLibrarianRoles().add(Librarian.Role.DEZIDERATI);
        }
        retVal.setCurentProcessType(ProcessTypeBuilder.buildProcessTypeDBFromDTO(librarianDTO.getCurentProcessType()));
        retVal.setContext(initializeContextDBFromDTO(librarianDTO.getContext()));
        retVal.setDefaultDepartment(librarianDTO.getDefaultDepartment());
        retVal.setCircDepartment(librarianDTO.getCircDepartment());
        retVal.setAuthorities(librarianDTO.getAuthorities());
        return retVal;
    }

//    public static LibrarianDTO initializeDTOFromLibrarian(Librarian lib) {
//
//        LibrarianDTO retVal = new LibrarianDTO();
//        retVal.set_id(lib.get_id());
//        retVal.setUsername(lib.getUsername());
//        retVal.setPassword(lib.getPassword());
//        retVal.setIme(lib.getIme());
//        retVal.setPrezime(lib.getPrezime());
//        retVal.setEmail(lib.getEmail());
//        retVal.setNapomena(lib.getNapomena());
//        retVal.setObrada(lib.isObrada());
//        retVal.setCirkulacija(lib.isCirkulacija());
//        retVal.setCirkulacijaPlus(lib.isCirkulacijaPlus());
//        retVal.setAdministracija(lib.isAdministracija());
//        retVal.setInventator(lib.isInventator());
//        retVal.setRedaktor(lib.isRedaktor());
//        retVal.setBiblioteka(lib.getBiblioteka());
//        retVal.setCurentProcessType(ProcessTypeBuilder.buildDTOFromProcessType(lib.getCurentProcessType()));
//        retVal.setContext(initializeDTOFromContext(lib.getContext()));
//        retVal.setDefaultDepartment(lib.getDefaultDepartment());
//        retVal.setCircDepartment(lib.getCircDepartment());
//        retVal.setOpacAdmin(lib.isOpacAdmin());
//        retVal.setDeziderati(lib.isDeziderati());
//        retVal.setNabavka(lib.isNabavka());
//        return retVal;
//    }

//    public static LibrarianContextDTO initializeDTOFromContext( LibrarianContext librarianContext){
//        LibrarianContextDTO retVal = new LibrarianContextDTO();
//
//        retVal.setPref1(librarianContext.getPref1());
//        retVal.setPref2(librarianContext.getPref2());
//        retVal.setPref3(librarianContext.getPref3());
//        retVal.setPref4(librarianContext.getPref4());
//        retVal.setPref5(librarianContext.getPref5());
//
//        retVal.setDefaultProcessType(ProcessTypeBuilder.buildDTOFromProcessType(librarianContext.getDefaultProcessType()));
//
//        for (ProcessType pd: librarianContext.getProcessTypes())
//            retVal.getProcessTypes().add(ProcessTypeBuilder.buildDTOFromProcessType(pd));
//
//        return retVal;
//    }
//
//    public static LibrarianContext initializeContextFromDTO(LibrarianContextDTO librarianContextDTO){
//        LibrarianContext retVal = new LibrarianContext();
//
//        retVal.setPref1(librarianContextDTO.getPref1());
//        retVal.setPref2(librarianContextDTO.getPref2());
//        retVal.setPref3(librarianContextDTO.getPref3());
//        retVal.setPref4(librarianContextDTO.getPref4());
//        retVal.setPref5(librarianContextDTO.getPref5());
//
//        retVal.setDefaultProcessType(ProcessTypeBuilder.buildProcessTypeFromDTO(librarianContextDTO.getDefaultProcessType()));
//
//        for (ProcessTypeDTO pd: librarianContextDTO.getProcessTypes())
//            retVal.getProcessTypes().add(ProcessTypeBuilder.buildProcessTypeFromDTO(pd));
//
//        return retVal;
//    }

    public static LibrarianContextDB initializeContextDBFromDTO(LibrarianContextDTO librarianContextDTO){
        LibrarianContextDB retVal = new LibrarianContextDB();

        retVal.setPref1(librarianContextDTO.getPref1());
        retVal.setPref2(librarianContextDTO.getPref2());
        retVal.setPref3(librarianContextDTO.getPref3());
        retVal.setPref4(librarianContextDTO.getPref4());
        retVal.setPref5(librarianContextDTO.getPref5());

        retVal.setDefaultProcessType(ProcessTypeBuilder.buildProcessTypeDBFromDTO(librarianContextDTO.getDefaultProcessType()));

        for (ProcessTypeDTO pd: librarianContextDTO.getProcessTypes())
            retVal.getProcessTypes().add(ProcessTypeBuilder.buildProcessTypeDBFromDTO(pd));

        return retVal;
    }


}
