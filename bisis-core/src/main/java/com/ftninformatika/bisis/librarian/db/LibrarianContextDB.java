package com.ftninformatika.bisis.librarian.db;

import com.ftninformatika.bisis.librarian.LibrarianContext;
import com.ftninformatika.bisis.librarian.ProcessType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LibrarianContextDB {
    private String pref1;
    private String pref2;
    private String pref3;
    private String pref4;
    private String pref5;
    @DBRef
    private ProcessTypeDB defaultProcessType;
    @DBRef
    private List<ProcessTypeDB> processTypes = new ArrayList<>();

    public LibrarianContextDB(LibrarianContext librarianContext) {
        this.pref1 = librarianContext.getPref1();
        this.pref2 = librarianContext.getPref2();
        this.pref3 = librarianContext.getPref3();
        this.pref4 = librarianContext.getPref4();
        this.pref5 = librarianContext.getPref5();
        if (librarianContext.getDefaultProcessType() != null) {
            this.defaultProcessType = new ProcessTypeDB(librarianContext.getDefaultProcessType());
        }
        for (ProcessType processType : librarianContext.getProcessTypes()) {
            this.processTypes.add(new ProcessTypeDB(processType));
        }
    }
}
