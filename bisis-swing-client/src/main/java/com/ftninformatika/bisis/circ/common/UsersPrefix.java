package com.ftninformatika.bisis.circ.common;

public class UsersPrefix implements java.io.Serializable {

  
  private String name;
  private String dbname;
  private String dbtable;
  
  public UsersPrefix(){
    this.name = "";
    this.dbname = "";
    this.dbtable = "";
  }
  
  public UsersPrefix(String name, String dbname, String dbtable){
    this.name = name;
    this.dbname = dbname;
    this.dbtable = dbtable;
  }

  public String getDbname() {
    return dbname;
  }

  public void setDbname(String dbname) {
    this.dbname = dbname;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public String getDbtable() {
    return dbtable;
  }

  public void setDbtable(String dbtable) {
    this.dbtable = dbtable;
  }
  
  
}
