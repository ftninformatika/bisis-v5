package com.ftninformatika.bisis.librarian;

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


}