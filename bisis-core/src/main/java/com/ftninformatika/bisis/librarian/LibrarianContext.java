package com.ftninformatika.bisis.librarian;

import com.ftninformatika.bisis.librarian.db.LibrarianContextDB;
import com.ftninformatika.bisis.librarian.db.ProcessTypeDB;
import com.ftninformatika.bisis.librarian.dto.ProcessTypeDTO;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LibrarianContext {

  private String pref1;
  private String pref2;
  private String pref3;
  private String pref4;
  private String pref5;
  private ProcessType defaultProcessType;
  private ArrayList<ProcessType> processTypes = new ArrayList<ProcessType>();

  public LibrarianContext(LibrarianContextDB librarianContextDB) {
    this.pref1 = librarianContextDB.getPref1();
    this.pref2 = librarianContextDB.getPref2();
    this.pref3 = librarianContextDB.getPref3();
    this.pref4 = librarianContextDB.getPref4();
    this.pref5 = librarianContextDB.getPref5();
    if (librarianContextDB.getDefaultProcessType() != null) {
      this.defaultProcessType = new ProcessType(librarianContextDB.getDefaultProcessType());
    }
    for (ProcessTypeDB processTypeDB : librarianContextDB.getProcessTypes()) {
      this.processTypes.add(new ProcessType(processTypeDB));
    }
  }


}
