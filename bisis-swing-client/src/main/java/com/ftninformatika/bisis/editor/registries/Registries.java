package com.ftninformatika.bisis.editor.registries;

public class Registries {

  public static final int NEPOZNAT     = 0;
  public static final int AUTORI       = 1;
  public static final int ODREDNICE    = 2;
  public static final int PODODREDNICE = 3;
  public static final int ZBIRKE       = 4;
  public static final int UDK          = 5;
  public static final int KOLEKTIVNI   = 6;
  public static final int IZDAVACI     = 7;

  public static String getShortName(int registryType) {
    switch (registryType) {
      case AUTORI:
        return "Ауторске одр.";
      case ODREDNICE:
        return "Предметне одр.";
      case PODODREDNICE:
        return "Предметне пододр.";
      case ZBIRKE:
        return "Збирке";
      case UDK:
        return "UDK подгрупе";
      case KOLEKTIVNI:
        return "Колективне одр.";
      case IZDAVACI:
        return "Издавачи";
      default:
        return "";
    }
  }
  
  public static String getLongName(int registryType) {
    switch (registryType) {
      case AUTORI:
        return "Ауторске одреднице";
      case ODREDNICE:
        return "Предметне одреднице";
      case PODODREDNICE:
        return "Предметне пододреднице";
      case ZBIRKE:
        return "Збирке";
      case UDK:
        return "UDK подгрупе";
      case KOLEKTIVNI:
        return "Колективне одреднице";
      case IZDAVACI:
        return "Издавачи";
      default:
        return "";
    }
  }
  
  public static String getLabel1(int registryType) {
    switch (registryType) {
      case AUTORI:
        return "Аутор";
      case ODREDNICE:
        return "Појам";
      case PODODREDNICE:
        return "Појам";
      case ZBIRKE:
        return "Назив";
      case UDK:
        return "Група";
      case KOLEKTIVNI:
        return "Колективац";
      case IZDAVACI:
        return "Назив";
      default:
        return "";
    }
  }

  public static String getLabel2(int registryType) {
    switch (registryType) {
      case AUTORI:
        return "Оригинал";
      case ODREDNICE:
        return "";
      case PODODREDNICE:
        return "";
      case ZBIRKE:
        return "";
      case UDK:
        return "Опис";
      case KOLEKTIVNI:
        return "";
      case IZDAVACI:
        return "Објашњење";
      default:
        return "";
    }
  }
}
