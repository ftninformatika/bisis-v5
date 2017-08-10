package com.ftninformatika.bisis.librarian;

import com.ftninformatika.bisis.librarian.dto.LibrarianContextDTO;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.librarian.dto.ProcessTypeDTO;

/**
 * Created by Petar on 8/10/2017.
 */
public class LibrarianManager {


    public static Librarian initializeLibrarianFromDTO(LibrarianDTO librarianDTO){

        Librarian retVal = new Librarian();
        retVal.set_id(librarianDTO.get_id());
        retVal.setUsername(librarianDTO.getUsername());
        retVal.setPassword(librarianDTO.getPassword());
        retVal.setIme(librarianDTO.getIme());
        retVal.setPrezime(librarianDTO.getPrezime());
        retVal.setEmail(librarianDTO.getEmail());
        retVal.setNapomena(librarianDTO.getNapomena());
        retVal.setObrada(librarianDTO.isObrada());
        retVal.setCirculation(librarianDTO.isCirkulacija());
        retVal.setAdministracija(librarianDTO.isAdministracija());
        retVal.setBiblioteka(librarianDTO.getBiblioteka());
        retVal.setCurentProcessType(ProcessTypeBuilder.buildProcessTypeFromDTO(librarianDTO.getCurentProcessType()));
        retVal.setContext(initializeContextFromDTO(librarianDTO.getContext()));



        return retVal;
    }

    public static LibrarianContext initializeContextFromDTO(LibrarianContextDTO librarianContextDTO){
        LibrarianContext retVal = new LibrarianContext();

        retVal.setPref1(librarianContextDTO.getPref1());
        retVal.setPref2(librarianContextDTO.getPref2());
        retVal.setPref3(librarianContextDTO.getPref3());
        retVal.setPref4(librarianContextDTO.getPref4());
        retVal.setPref5(librarianContextDTO.getPref5());

        retVal.setDefaultProcessType(ProcessTypeBuilder.buildProcessTypeFromDTO(librarianContextDTO.getDefaultProcessType()));

        for (ProcessTypeDTO pd: librarianContextDTO.getProcessTypes())
            retVal.getProcessTypes().add(ProcessTypeBuilder.buildProcessTypeFromDTO(pd));

        return retVal;
    }

}
