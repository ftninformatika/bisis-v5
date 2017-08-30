package com.ftninformatika.bisis.barcode.epl2;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

/**
 * Stampanje bar koda na stampac koji u svom nazivu ima rec "barcode", bez obzira
 * na model stampaca, putem drajvera za stampac.
 *
 */
public class Printer2 implements IPrinter {
  
  private static Printer2 instance = new Printer2();
  private PrintService psBarCode;
  
  private Printer2() {
    try {
      psBarCode = null;
      PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
      for (int i = 0; i < services.length; i++) {
        if (services[i].getName().toLowerCase().contains("barcode")) {
          psBarCode = services[i];
          break;
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public static Printer2 getInstance() {
    return instance;
  }

  @Override
  public boolean print(Label label, String codePage) {
    if (psBarCode == null) {
      System.err.println("Barcode printer not found");
      return false;
    }
    try {
      byte[] bytes = label.getCommands().getBytes(codePage);
      //TODO encoding "cp" + codePage
      DocPrintJob job = psBarCode.createPrintJob();
      DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
      Doc doc = new SimpleDoc(bytes, flavor, null);
      job.print(doc, null);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    if (psBarCode == null)
      return "[none]";
    return "[" + psBarCode.getName() + "]";
  }

}
