package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.circ.common.UsersPrefix;
import com.ftninformatika.utils.Messages;

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
  	prefixlist.add(new UsersPrefix(Messages.getString("circulation.address"), "address", "members"));
  	prefixlist.add(new UsersPrefix(Messages.getString("circulation.documentnumber"), "docNo", "members"));
  	prefixlist.add(new UsersPrefix(Messages.getString("circulation.studentcard"), "indexNo", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.usernumber"), "userId", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.email"), "email", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.membershiptype"), "membershipType.description", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.corporate"), "corporateMember.instName", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.firstname"), "firstName", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.parentname"), "parentName", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.acquisitionnumber"), "ctlgNo", "lendings"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.language"), "language", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.umcn"), "jmbg", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.usercategory"), "userCategory.description", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.place"), "city", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.note"), "note", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.organization"), "organization.name", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.gender"), "gender", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.lastname"), "lastName", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.receipt"), "signings.receipt", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.education"), "educationLevel", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.librarian_signing"), "signings.librarian", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.librarian_lend"), "librarianLend", "lendings"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.librarian_return"), "librarianReturn", "lendings"));
        
  }
  
  public void initDatePrefix(){
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.chargingdate"), "lendDate", "lendings"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.dischargingdate"), "returnDate", "lendings"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.membershipdate"), "signings.signDate", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.membershipexpire"), "signings.untilDate", "members"));
    prefixlist.add(new UsersPrefix(Messages.getString("circulation.duedate"), "deadline", "lendings"));
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
