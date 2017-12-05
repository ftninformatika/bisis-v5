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
        new Table("Interna_oznaka", "Интерна ознака",  new Column[] {
            new Column("IntOzn_ID", "Шифра", Types.VARCHAR, 2, 0, true),
            new Column("IntOzn_Opis", "Опис", Types.VARCHAR, 255, 0, false)}));
    tables.put("Nacin_nabavke", 
        new Table("Nacin_nabavke", "Начин набавке", new Column[] {
            new Column("Nacin_ID", "Шифра", Types.CHAR, 1, 0, true),
            new Column("Nacin_Opis", "Опис", Types.VARCHAR, 255, 0, false)}));
    tables.put("Odeljenje", 
        new Table("Odeljenje", "Одељење", new Column[] {
            new Column("Odeljenje_ID", "Шифра", Types.CHAR, 2, 0, true),
            new Column("Odeljenje_Naziv", "Назив", Types.VARCHAR, 255, 0, false)}));
    tables.put("Podlokacija", 
        new Table("Podlokacija", "Подлокација", new Column[] {
            new Column("Podlokacija_ID", "Шифра", Types.VARCHAR, 2, 0, true),
            new Column("Podlokacija_Opis", "Опис", Types.VARCHAR, 255, 0, false)}));
    tables.put("Povez", 
        new Table("Povez", "Повез", new Column[] {
            new Column("Povez_ID", "Шифра", Types.CHAR, 1, 0, true),
            new Column("Povez_Opis", "Опис", Types.VARCHAR, 255, 0, false)}));
    tables.put("SigFormat", 
        new Table("SigFormat", "Формат", new Column[] {
            new Column("SigFormat_ID", "\u0160ifra", Types.CHAR, 2, 0, true), 
            new Column("Format_Opis", "Opis", Types.VARCHAR, 255, 0, false)}));
    tables.put("Status_Primerka", 
        new Table("Status_Primerka", "Статус примерка", new Column[] {
            new Column("Status_ID", "Ширфа", Types.CHAR, 1, 0, true),
            new Column("Status_Opis", "Опис", Types.VARCHAR, 255, 0, false),
            new Column("Zaduziv", "Задужив", Types.INTEGER, 0, 0, false)}));
    tables.put("Invknj", 
        new Table("Invknj", "Инвентарна књига", new Column[] {
            new Column("Invknj_ID", "Шифра", Types.CHAR, 2, 0, true),
            new Column("Invknj_Opis", "Опис", Types.VARCHAR, 255, 0, false)}));
    tables.put("Counters", 
        new Table("Counters", "Бројачи", new Column[] {
            new Column("counter_name", "Назив бројача", Types.VARCHAR, 50, 0, true),
            new Column("counter_value", "Последња вредност", Types.INTEGER, 0, 0, false)}));
    tables.put("user_categs", 
        new Table("user_categs", "Категорије корисника", new Column[] {
            new Column("id", "Шифра", Types.INTEGER, 0, 0, true),
            new Column("name", "Назив", Types.VARCHAR, 255, 0, false),
            new Column("titles_no", "Број наслова", Types.INTEGER, 0, 0, false),
            new Column("period", "Период задужења", Types.INTEGER, 0, 0, false),
            new Column("max_period", "Максималан период", Types.INTEGER, 0, 0, false)}));
    tables.put("mmbr_types", 
        new Table("mmbr_types", "Врсте чланства", new Column[] {
            new Column("id", "Шифра", Types.INTEGER, 0, 0, true),
            new Column("name", "Назив", Types.VARCHAR, 255, 0, false),
            new Column("period", "Период чланарине", Types.INTEGER, 0, 0, false)}));
    tables.put("edu_lvl", 
        new Table("edu_lvl", "Степен образовања", new Column[] {
            new Column("id", "Шифра", Types.INTEGER, 0, 0, true),
            new Column("name", "Назив", Types.VARCHAR, 255, 0, false)}));
    tables.put("languages", 
        new Table("languages", "Матерњи језик", new Column[] {
            new Column("id", "Шифра", Types.INTEGER, 0, 0, true),
            new Column("name", "Назив", Types.VARCHAR, 255, 0, false)}));
    tables.put("organization", 
        new Table("organization", "Организација", new Column[] {
            new Column("id", "Шифра", Types.INTEGER, 0, 0, true),
            new Column("name", "Назив", Types.VARCHAR, 255, 0, false),
            new Column("address", "Адреса", Types.VARCHAR, 255, 0, false),
            new Column("city", "Место", Types.VARCHAR, 255, 0, false),
            new Column("zip", "Број поште", Types.INTEGER, 0, 0, false)}));
    tables.put("location", 
        new Table("location", "Одељења", new Column[] {
            new Column("id", "Шифра", Types.INTEGER, 0, 0, true),
            new Column("name", "Назив", Types.VARCHAR, 255, 0, false),
            new Column("last_user_id", "Максималан бр. корисника", Types.INTEGER, 0, 0, false)}));
    tables.put("places", 
        new Table("places", "Места", new Column[] {
            new Column("id", "Шифра", Types.INTEGER, 0, 0, true),
            new Column("zip", "Број", Types.VARCHAR, 255, 0, false),
            new Column("city", "Место", Types.VARCHAR, 255, 0, false)}));
    tables.put("warn_counters", 
            new Table("warn_counters", "Бројачи за опомене", new Column[] {
                new Column("id", "Шифра", Types.INTEGER, 0, 0, true),
                new Column("warn_year", "Година", Types.INTEGER, 0, 0, false),
                new Column("wtype", "Тип опомене", Types.INTEGER, 0, 0, false),
                new Column("last_no", "Последњи број", Types.INTEGER, 0, 0, false)}));
    tables.put("Sifarnik_992b", 
            new Table("Sifarnik_992b", "Акције над записом", new Column[] {
                new Column("id", "Шифра", Types.VARCHAR, 20, 0, true),
                new Column("naziv", "Назив", Types.VARCHAR, 120, 0, false)}));
    
  }
}
