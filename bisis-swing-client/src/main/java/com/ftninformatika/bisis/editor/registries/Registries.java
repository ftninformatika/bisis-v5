package com.ftninformatika.bisis.editor.registries;

public class Registries {

  public static final int NEPOZNAT     = 0;
  public static final int AUTORI       = 1;
  public static final int ODREDNICE    = 2;
  public static final int PODODREDNICE = 3;
  public static final int ZBIRKE       = 4;
  public static final int UDK          = 5;
  public static final int KOLEKTIVNI   = 6;

  public static String getShortName(int registryType) {
    switch (registryType) {
      case AUTORI:
        return "Autorske odr.";
      case ODREDNICE:
        return "Predmetne odr.";
      case PODODREDNICE:
        return "Predmetne pododr.";
      case ZBIRKE:
        return "Zbirke";
      case UDK:
        return "UDK podgrupe";
      case KOLEKTIVNI:
        return "Kolektivne odr.";
      default:
        return "";
    }
  }
  
  public static String getLongName(int registryType) {
    switch (registryType) {
      case AUTORI:
        return "Autorske odrednice";
      case ODREDNICE:
        return "Predmetne odrednice";
      case PODODREDNICE:
        return "Predmetne pododrednice";
      case ZBIRKE:
        return "Zbirke";
      case UDK:
        return "UDK podgrupe";
      case KOLEKTIVNI:
        return "Kolektivne odrednice";
      default:
        return "";
    }
  }
  
  public static String getLabel1(int registryType) {
    switch (registryType) {
      case AUTORI:
        return "Autor";
      case ODREDNICE:
        return "Pojam";
      case PODODREDNICE:
        return "Pojam";
      case ZBIRKE:
        return "Naziv";
      case UDK:
        return "Grupa";
      case KOLEKTIVNI:
        return "Kolektivac";
      default:
        return "";
    }
  }

  public static String getLabel2(int registryType) {
    switch (registryType) {
      case AUTORI:
        return "Original";
      case ODREDNICE:
        return "";
      case PODODREDNICE:
        return "";
      case ZBIRKE:
        return "";
      case UDK:
        return "Opis";
      case KOLEKTIVNI:
        return "";
      default:
        return "";
    }
  }
}
