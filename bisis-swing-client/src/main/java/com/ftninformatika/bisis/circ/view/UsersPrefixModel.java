package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.circ.common.UsersPrefix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;




public class UsersPrefixModel extends AbstractListModel<String> {

  private List<UsersPrefix> prefixlist = null;
  
  
  public UsersPrefixModel(){
    prefixlist = new ArrayList<UsersPrefix>();
   
  }
  
  public void initUsersPrefix(){
  	prefixlist.add(new UsersPrefix("Adresa", "address", "users"));
  	prefixlist.add(new UsersPrefix("Broj dokumenta", "docNo", "users"));
  	prefixlist.add(new UsersPrefix("Broj indeksa", "indexNo", "users"));
    prefixlist.add(new UsersPrefix("Broj korisnika", "userId", "users"));
    prefixlist.add(new UsersPrefix("Vrsta u\u010dlanjenja", "mmbrTypes", "users"));
    prefixlist.add(new UsersPrefix("Godina", "classNo", "users"));
    prefixlist.add(new UsersPrefix("Grupa", "groups", "users"));
    prefixlist.add(new UsersPrefix("Ime", "firstName", "users"));
    prefixlist.add(new UsersPrefix("Ime roditelja", "parentName", "users"));
    prefixlist.add(new UsersPrefix("Inventarni broj", "ctlgNo", "lending"));
    prefixlist.add(new UsersPrefix("Jezik", "language", "users"));
    prefixlist.add(new UsersPrefix("JMBG", "jmbg", "users"));
    prefixlist.add(new UsersPrefix("Kategorija", "userCategs", "users"));
    prefixlist.add(new UsersPrefix("Mesto", "city", "users"));
    prefixlist.add(new UsersPrefix("Napomena", "note", "users"));
    prefixlist.add(new UsersPrefix("Organizacija", "organization", "users"));
    prefixlist.add(new UsersPrefix("Pol", "gender", "users"));
    prefixlist.add(new UsersPrefix("Prezime", "lastName", "users"));
    prefixlist.add(new UsersPrefix("Priznanica", "receiptId", "signing"));
    prefixlist.add(new UsersPrefix("Stru\u010dna sprema", "eduLvl", "users")); 
    prefixlist.add(new UsersPrefix("Bibliotekar u\u010dlanio", "librarian", "signing"));
    prefixlist.add(new UsersPrefix("Bibliotekar zadu\u017eio", "librarianLend", "lending"));
    prefixlist.add(new UsersPrefix("Bibliotekar produ\u017eio", "librarianResume", "lending"));
    prefixlist.add(new UsersPrefix("Bibliotekar razdu\u017eio", "librarianReturn", "lending"));
        
  }
  
  public void initDatePrefix(){
    prefixlist.add(new UsersPrefix("Datum zadu\u017eenja", "lendDate", "lending"));
    prefixlist.add(new UsersPrefix("Datum produ\u017eenja", "resumeDate", "lending"));
    prefixlist.add(new UsersPrefix("Datum razdu\u017eenja", "returnDate", "lending"));
    prefixlist.add(new UsersPrefix("Datum upisa", "signDate", "signing"));
    prefixlist.add(new UsersPrefix("Istek \u010dlanarine", "untilDate", "signing"));
    prefixlist.add(new UsersPrefix("Rok vra\u0107anja", "deadline", "signing"));
 }
  
  public int getSize(){
    return prefixlist.size();
  }
  
  public String getElementAt(int index){
    UsersPrefix up = prefixlist.get(index);
    if (up == null)
      return null;
    return up.getName();
  }
  
  public int getSelection(char c) {
    int n = prefixlist.size();
    for (int i = 0; i < n; i++) {
      if (Character.toUpperCase(
            (prefixlist.get(i)).getName().charAt(0)) == 
          Character.toUpperCase(c))
        return i;
    }
    return -1;
  }

  public int getSelection(String s) {
    int n = prefixlist.size();
    for (int i = 0; i < n; i++) {
      UsersPrefix disp = prefixlist.get(i);
      if (disp.getName().startsWith(s))
        return i;
    }
    return -1;
  }
  
  public UsersPrefix getUsersPrefix(int index){
    return prefixlist.get(index);
  }
  
  public UsersPrefix getUsersPrefixByName(String name){
    Iterator it = prefixlist.iterator();
    while (it.hasNext()){
      UsersPrefix up = (UsersPrefix)it.next();
      if (up.getName().equals(name)){
      return up;
      }
    }
    return null;
  }
  
  public UsersPrefix getUsersPrefixByDBName(String name){
    Iterator it = prefixlist.iterator();
    while (it.hasNext()){
      UsersPrefix up = (UsersPrefix)it.next();
      if (up.getDbname().equals(name)){
      return up;
      }
    }
    return null;
  }
  

}
