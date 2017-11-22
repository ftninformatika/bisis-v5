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
  	prefixlist.add(new UsersPrefix("Adresa", "address", "members"));
  	prefixlist.add(new UsersPrefix("Broj dokumenta", "docNo", "members"));
  	prefixlist.add(new UsersPrefix("Broj indeksa", "indexNo", "members"));
    prefixlist.add(new UsersPrefix("Broj korisnika", "userId", "members"));
    prefixlist.add(new UsersPrefix("Vrsta u\u010dlanjenja", "membershipType.description", "members"));
    prefixlist.add(new UsersPrefix("Grupa", "corporateMember.instName", "members"));
    prefixlist.add(new UsersPrefix("Ime", "firstName", "members"));
    prefixlist.add(new UsersPrefix("Ime roditelja", "parentName", "members"));
    prefixlist.add(new UsersPrefix("Inventarni broj", "ctlgNo", "lendings"));
    prefixlist.add(new UsersPrefix("Jezik", "language", "members"));
    prefixlist.add(new UsersPrefix("JMBG", "jmbg", "members"));
    prefixlist.add(new UsersPrefix("Kategorija", "userCategory.description", "members"));
    prefixlist.add(new UsersPrefix("Mesto", "city", "members"));
    prefixlist.add(new UsersPrefix("Napomena", "note", "members"));
    prefixlist.add(new UsersPrefix("Organizacija", "organization.name", "members"));
    prefixlist.add(new UsersPrefix("Pol", "gender", "members"));
    prefixlist.add(new UsersPrefix("Prezime", "lastName", "members"));
    prefixlist.add(new UsersPrefix("Priznanica", "signings.receipt", "members"));
    prefixlist.add(new UsersPrefix("Stru\u010dna sprema", "educationLevel", "members"));
    prefixlist.add(new UsersPrefix("Bibliotekar u\u010dlanio", "signings.librarian", "members"));
    prefixlist.add(new UsersPrefix("Bibliotekar zadu\u017eio", "librarianLend", "lendings"));
    prefixlist.add(new UsersPrefix("Bibliotekar razdu\u017eio", "librarianReturn", "lendings"));
        
  }
  
  public void initDatePrefix(){
    prefixlist.add(new UsersPrefix("Datum zadu\u017eenja", "lendDate", "lendings"));
    prefixlist.add(new UsersPrefix("Datum razdu\u017eenja", "returnDate", "lendings"));
    prefixlist.add(new UsersPrefix("Datum upisa", "signings.signDate", "members"));
    prefixlist.add(new UsersPrefix("Istek \u010dlanarine", "signings.untilDate", "members"));
    prefixlist.add(new UsersPrefix("Rok vra\u0107anja", "deadline", "lendings"));
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
