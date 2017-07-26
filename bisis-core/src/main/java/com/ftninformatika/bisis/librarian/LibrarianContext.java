package com.ftninformatika.bisis.librarian;

import com.ftninformatika.utils.string.StringUtils;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LibrarianContext {

  //private List<ProcessType> processTypes;
  //private ProcessType defaultProcessType;
  private String pref1;
  private String pref2;
  private String pref3;
  private String pref4;
  private String pref5;
  private ProcessType defaultProcessType;
  private ArrayList<ProcessType> processTypes = new ArrayList<ProcessType>();


  public String toString(){
    return pref1 + "   itd.....";
  }
}