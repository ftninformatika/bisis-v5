package com.ftninformatika.bisis.admin.coders;

import java.sql.Types;
import java.util.HashMap;

public class TableCatalog {

  public static Table getTable(String name) {
    return tables.get(name);
  }
  
  private static HashMap<String, Table> tables = new HashMap<String, Table>();
  static {
    tables.put("Interna_oznaka", 
        new Table("Interna_oznaka", "Interna oznaka",  new Column[] {
            new Column("IntOzn_ID", "\u0160ifra", Types.VARCHAR, 2, 0, true), 
            new Column("IntOzn_Opis", "Opis", Types.VARCHAR, 255, 0, false)}));
    tables.put("Nacin_nabavke", 
        new Table("Nacin_nabavke", "Na\u010din nabavke", new Column[] {
            new Column("Nacin_ID", "\u0160ifra", Types.CHAR, 1, 0, true),
            new Column("Nacin_Opis", "Opis", Types.VARCHAR, 255, 0, false)}));
    tables.put("Odeljenje", 
        new Table("Odeljenje", "Odeljenje", new Column[] {
            new Column("Odeljenje_ID", "\u0160ifra", Types.CHAR, 2, 0, true), 
            new Column("Odeljenje_Naziv", "Naziv", Types.VARCHAR, 255, 0, false)}));
    tables.put("Podlokacija", 
        new Table("Podlokacija", "Podlokacija", new Column[] {
            new Column("Podlokacija_ID", "\u0160ifra", Types.VARCHAR, 2, 0, true), 
            new Column("Podlokacija_Opis", "Opis", Types.VARCHAR, 255, 0, false)}));
    tables.put("Povez", 
        new Table("Povez", "Povez", new Column[] {
            new Column("Povez_ID", "\u0160ifra", Types.CHAR, 1, 0, true), 
            new Column("Povez_Opis", "Opis", Types.VARCHAR, 255, 0, false)}));
    tables.put("SigFormat", 
        new Table("SigFormat", "Format", new Column[] {
            new Column("SigFormat_ID", "\u0160ifra", Types.CHAR, 2, 0, true), 
            new Column("Format_Opis", "Opis", Types.VARCHAR, 255, 0, false)}));
    tables.put("Status_Primerka", 
        new Table("Status_Primerka", "Status primerka", new Column[] {
            new Column("Status_ID", "\u0160ifra", Types.CHAR, 1, 0, true), 
            new Column("Status_Opis", "Opis", Types.VARCHAR, 255, 0, false),
            new Column("Zaduziv", "Zadu\u017eiv", Types.INTEGER, 0, 0, false)}));
    tables.put("Invknj", 
        new Table("Invknj", "Inventarna knjiga", new Column[] {
            new Column("Invknj_ID", "\u0160ifra", Types.CHAR, 2, 0, true), 
            new Column("Invknj_Opis", "Opis", Types.VARCHAR, 255, 0, false)}));
    tables.put("Counters", 
        new Table("Counters", "Broja\u010di", new Column[] {
            new Column("counter_name", "Naziv broja\u010da", Types.VARCHAR, 50, 0, true), 
            new Column("counter_value", "Poslednja vrednost", Types.INTEGER, 0, 0, false)}));
    tables.put("user_categs", 
        new Table("user_categs", "Kategorije korisnika", new Column[] {
            new Column("id", "\u0160ifra", Types.INTEGER, 0, 0, true), 
            new Column("name", "Naziv", Types.VARCHAR, 255, 0, false),
            new Column("titles_no", "Broj naslova", Types.INTEGER, 0, 0, false),
            new Column("period", "Period zadu\u017eenja", Types.INTEGER, 0, 0, false),
            new Column("max_period", "Maksimalan period", Types.INTEGER, 0, 0, false)}));
    tables.put("mmbr_types", 
        new Table("mmbr_types", "Vrste \u010dlanstva", new Column[] {
            new Column("id", "\u0160ifra", Types.INTEGER, 0, 0, true), 
            new Column("name", "Naziv", Types.VARCHAR, 255, 0, false),
            new Column("period", "Period \u010dlanarine", Types.INTEGER, 0, 0, false)}));
    tables.put("edu_lvl", 
        new Table("edu_lvl", "Stepen obrazovanja", new Column[] {
            new Column("id", "\u0160ifra", Types.INTEGER, 0, 0, true), 
            new Column("name", "Naziv", Types.VARCHAR, 255, 0, false)}));
    tables.put("languages", 
        new Table("languages", "Maternji jezik", new Column[] {
            new Column("id", "\u0160ifra", Types.INTEGER, 0, 0, true), 
            new Column("name", "Naziv", Types.VARCHAR, 255, 0, false)}));
    tables.put("organization", 
        new Table("organization", "Organizacija", new Column[] {
            new Column("id", "\u0160ifra", Types.INTEGER, 0, 0, true), 
            new Column("name", "Naziv", Types.VARCHAR, 255, 0, false),
            new Column("address", "Adresa", Types.VARCHAR, 255, 0, false),
            new Column("city", "Mesto", Types.VARCHAR, 255, 0, false),
            new Column("zip", "Broj po\u0161te", Types.INTEGER, 0, 0, false)}));
    tables.put("location", 
        new Table("location", "Odeljenja", new Column[] {
            new Column("id", "\u0160ifra", Types.INTEGER, 0, 0, true), 
            new Column("name", "Naziv", Types.VARCHAR, 255, 0, false),
            new Column("last_user_id", "Max broj korisnika", Types.INTEGER, 0, 0, false)}));
    tables.put("places", 
        new Table("places", "Mesta", new Column[] {
            new Column("id", "\u0160ifra", Types.INTEGER, 0, 0, true), 
            new Column("zip", "Broj", Types.VARCHAR, 255, 0, false),
            new Column("city", "Mesto", Types.VARCHAR, 255, 0, false)}));
    tables.put("warn_counters", 
            new Table("warn_counters", "Broja\u010di za opomene", new Column[] {
                new Column("id", "\u0160ifra", Types.INTEGER, 0, 0, true), 
                new Column("warn_year", "Godina", Types.INTEGER, 0, 0, false),
                new Column("wtype", "Tip opomene", Types.INTEGER, 0, 0, false),
                new Column("last_no", "Poslednji broj", Types.INTEGER, 0, 0, false)}));
    tables.put("Sifarnik_992b", 
            new Table("Sifarnik_992b", "Akcije nad zapisom", new Column[] {
                new Column("id", "\u0160ifra", Types.VARCHAR, 20, 0, true), 
                new Column("naziv", "Naziv", Types.VARCHAR, 120, 0, false)}));
    
  }
}
