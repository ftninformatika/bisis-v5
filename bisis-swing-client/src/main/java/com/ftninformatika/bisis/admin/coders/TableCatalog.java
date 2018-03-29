package com.ftninformatika.bisis.admin.coders;

import com.ftninformatika.utils.Messages;
import java.sql.Types;
import java.util.HashMap;

public class TableCatalog {

  public static Table getTable(String name) {
    return tables.get(name);
  }
  
  private static HashMap<String, Table> tables = new HashMap<String, Table>();
  static {
    tables.put("Interna_oznaka", 
        new Table("Interna_oznaka", Messages.getString("internalmark"),  new Column[] {
            new Column("IntOzn_ID", Messages.getString("code"), Types.VARCHAR, 2, 0, true),
            new Column("IntOzn_Opis", Messages.getString("description"), Types.VARCHAR, 255, 0, false)}));
    tables.put("Nacin_nabavke", 
        new Table("Nacin_nabavke", Messages.getString("acqtype"), new Column[] {
            new Column("Nacin_ID", Messages.getString("code"), Types.CHAR, 1, 0, true),
            new Column("Nacin_Opis", Messages.getString("description"), Types.VARCHAR, 255, 0, false)}));
    tables.put("Odeljenje", 
        new Table("Odeljenje", Messages.getString("location"), new Column[] {
            new Column("Odeljenje_ID", Messages.getString("code"), Types.CHAR, 2, 0, true),
            new Column("Odeljenje_Naziv", Messages.getString("title"), Types.VARCHAR, 255, 0, false)}));
    tables.put("Podlokacija", 
        new Table("Podlokacija", Messages.getString("sublocation"), new Column[] {
            new Column("Podlokacija_ID", Messages.getString("Code"), Types.VARCHAR, 2, 0, true),
            new Column("Podlokacija_Opis", Messages.getString("description"), Types.VARCHAR, 255, 0, false)}));
    tables.put("Povez", 
        new Table("Povez", Messages.getString("binding"), new Column[] {
            new Column("Povez_ID", Messages.getString("code"), Types.CHAR, 1, 0, true),
            new Column("Povez_Opis", Messages.getString("description"), Types.VARCHAR, 255, 0, false)}));
    tables.put("SigFormat", 
        new Table("SigFormat", Messages.getString("format"), new Column[] {
            new Column("SigFormat_ID", Messages.getString("Code"), Types.CHAR, 2, 0, true),
            new Column("Format_Opis", Messages.getString("description"), Types.VARCHAR, 255, 0, false)}));
    tables.put("Status_Primerka", 
        new Table("Status_Primerka", Messages.getString("itemstatus"), new Column[] {
            new Column("Status_ID", Messages.getString("code"), Types.CHAR, 1, 0, true),
            new Column("Status_Opis", Messages.getString("description"), Types.VARCHAR, 255, 0, false),
            new Column("Zaduziv", Messages.getString("lendable"), Types.INTEGER, 0, 0, false)}));
    tables.put("Invknj", 
        new Table("Invknj", Messages.getString("invbook"), new Column[] {
            new Column("Invknj_ID", Messages.getString("Code"), Types.CHAR, 2, 0, true),
            new Column("Invknj_Opis", Messages.getString("description"), Types.VARCHAR, 255, 0, false)}));
    tables.put("Counters", 
        new Table("Counters", Messages.getString("Counters"), new Column[] {
            new Column("counter_name", Messages.getString("countername"), Types.VARCHAR, 50, 0, true),
            new Column("counter_value", Messages.getString("lastvalue"), Types.INTEGER, 0, 0, false)}));
    tables.put("user_categs", 
        new Table("user_categs", Messages.getString("usercategs"), new Column[] {
            new Column("id", Messages.getString("Code"), Types.INTEGER, 0, 0, true),
            new Column("name", Messages.getString("title"), Types.VARCHAR, 255, 0, false),
            new Column("titles_no", Messages.getString("titleno"), Types.INTEGER, 0, 0, false),
            new Column("period", Messages.getString("lendperiod"), Types.INTEGER, 0, 0, false),
            new Column("max_period", Messages.getString("maxperiod"), Types.INTEGER, 0, 0, false)}));
    tables.put("mmbr_types", 
        new Table("mmbr_types", Messages.getString("mmbrtype"), new Column[] {
            new Column("id", Messages.getString("Code"), Types.INTEGER, 0, 0, true),
            new Column("name", Messages.getString("title"), Types.VARCHAR, 255, 0, false),
            new Column("period", Messages.getString("mmbrperiod"), Types.INTEGER, 0, 0, false)}));
    tables.put("edu_lvl", 
        new Table("edu_lvl", Messages.getString("edulvl"), new Column[] {
            new Column("id", Messages.getString("Code"), Types.INTEGER, 0, 0, true),
            new Column("name", Messages.getString("title"), Types.VARCHAR, 255, 0, false)}));
    tables.put("languages", 
        new Table("languages", Messages.getString("language"), new Column[] {
            new Column("id", Messages.getString("Code"), Types.INTEGER, 0, 0, true),
            new Column("name", Messages.getString("title"), Types.VARCHAR, 255, 0, false)}));
    tables.put("organization", 
        new Table("organization", Messages.getString("organization"), new Column[] {
            new Column("id", Messages.getString("Code"), Types.INTEGER, 0, 0, true),
            new Column("name", Messages.getString("title"), Types.VARCHAR, 255, 0, false),
            new Column("address", Messages.getString("address"), Types.VARCHAR, 255, 0, false),
            new Column("city", Messages.getString("place"), Types.VARCHAR, 255, 0, false),
            new Column("zip", Messages.getString("zipcode"), Types.INTEGER, 0, 0, false)}));
    tables.put("location", 
        new Table("location", Messages.getString("locations"), new Column[] {
            new Column("id", Messages.getString("Code"), Types.INTEGER, 0, 0, true),
            new Column("name", Messages.getString("title"), Types.VARCHAR, 255, 0, false),
            new Column("last_user_id", Messages.getString("maxuserno"), Types.INTEGER, 0, 0, false)}));
    tables.put("places", 
        new Table("places", Messages.getString("places"), new Column[] {
            new Column("id", Messages.getString("Code"), Types.INTEGER, 0, 0, true),
            new Column("zip", Messages.getString("number"), Types.VARCHAR, 255, 0, false),
            new Column("city", Messages.getString("place"), Types.VARCHAR, 255, 0, false)}));
    tables.put("warn_counters", 
            new Table("warn_counters", Messages.getString("warncounters"), new Column[] {
                new Column("id", Messages.getString("Code"), Types.INTEGER, 0, 0, true),
                new Column("warn_year", Messages.getString("year"), Types.INTEGER, 0, 0, false),
                new Column("wtype", Messages.getString("remindertype"), Types.INTEGER, 0, 0, false),
                new Column("last_no", Messages.getString("lastnum"), Types.INTEGER, 0, 0, false)}));
    tables.put("Task",
            new Table("Task", Messages.getString("actions"), new Column[] {
                new Column("id", Messages.getString("Code"), Types.VARCHAR, 20, 0, true),
                new Column("naziv", Messages.getString("title"), Types.VARCHAR, 120, 0, false)}));
    
  }
}
