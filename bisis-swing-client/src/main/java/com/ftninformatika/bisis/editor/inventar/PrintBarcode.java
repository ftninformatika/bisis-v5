package com.ftninformatika.bisis.editor.inventar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ftninformatika.bisis.records.Primerak;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PrintBarcode {

  static private int labelWidth;
  static private int labelHeight;
  static private int labelResolution;
  static private String wrap;
  static private int widebar;
  static private int narrowbar;
  static private int barwidth, sigfont, labelfont;
  static private String pageCode;
  static private String optionName;
  static private String port;
  //static private boolean remote;
  static private HashMap<String, List<String>> printers;
  static private List<String> socket;
  static private int printersNo = 0;
  private static Log log = LogFactory.getLog(PrintBarcode.class.getName());

  static {
   /* optionName = BisisApp.getINIFile().getString("barcode", "optionName");
    labelWidth = BisisApp.getINIFile().getInt("barcode", "labelWidth");
    labelHeight = BisisApp.getINIFile().getInt("barcode", "labelHeight");
    labelResolution = BisisApp.getINIFile().getInt("barcode", "labelResolution");
    pageCode = BisisApp.getINIFile().getString("barcode", "pageCode");
    wrap = BisisApp.getINIFile().getString("barcode", "wrap");
    widebar = BisisApp.getINIFile().getInt("barcode", "widebar");
    narrowbar = BisisApp.getINIFile().getInt("barcode", "narrowbar");
    barwidth = BisisApp.getINIFile().getInt("barcode", "barwidth");
    sigfont = BisisApp.getINIFile().getInt("barcode", "sigfont");
    labelfont = BisisApp.getINIFile().getInt("barcode", "labelfont");
    port = BisisApp.getINIFile().getString("barcode", "port");*/
    //remote = BisisApp.getINIFile().getBoolean("barcode", "remote");
//    if (remote) {
//      printers = new HashMap<String, List<String>>();
//      int i = 1;
//      while (BisisApp.getINIFile().getString("barcode", "remoteName" + Integer.toString(i)) != null) {
//        socket = new ArrayList<String>();
//        socket.add(BisisApp.getINIFile().getString("barcode", "remoteAddress" + Integer.toString(i)));
//        socket.add(BisisApp.getINIFile().getString("barcode", "remotePort" + Integer.toString(i)));
//        printers.put(BisisApp.getINIFile().getString("barcode", "remoteName" + Integer.toString(i)), socket);
//        i++;
//      }
//      printersNo = i - 1;
//    }
  }

//  public static boolean multiplePrinters() {
//    return (remote && (printersNo > 1));
//  }

  public static List<String> getPrinters() {
    List<String> list = new ArrayList<String>();
    list.addAll(printers.keySet());
    return list;
  }

  public static void printBarcodeForPrimerak(Primerak p, String printerName) {
   /* IPrinter printer;
    //novi nacin za stampanje bar koda
    printer = Printer2.getInstance();

//    if (remote && (printersNo > 1)) {
//      socket = printers.get(printerName);
//      printer = new RemotePrinter(socket.get(0), socket.get(1));
//      log.info("Printing barcode: " + printerName + ", " + socket.get(0) + ":" + socket.get(1));
//    } else if (remote && (printersNo == 1)) {
//      String name = printers.keySet().iterator().next();
//      socket = printers.get(name);
//      printer = new RemotePrinter(socket.get(0), socket.get(1));
//      log.info("Printing barcode: " + name + ", " + socket.get(0) + ":" + socket.get(1));
//    } else {
//      printer = Printer.getDefaultPrinter(port);
//      log.info("Printing barcode: local printer");
//    }

    Label label = new Label(labelWidth, labelHeight, labelResolution, widebar, narrowbar, barwidth, pageCode);
    String[] library = optionName.split(",");
    String[] wrapparts = wrap.split(",");
    String libName, ogr, libraryName;
    for (int i = 0; i < library.length; i++) {
      libName = BisisApp.getINIFile().getString("barcode", library[i]);
      int crta = libName.indexOf("-");
      if (crta != -1) {
        ogr = libName.substring(0, crta);
        libraryName = libName.substring(crta + 1);
        if (p.getInvBroj().startsWith(ogr)) {
          if ((Integer.parseInt(wrapparts[0]) != 0) && (libraryName.length() > Integer.parseInt(wrapparts[0]))) {
            label.appendText(libraryName.substring(0, Integer.parseInt(wrapparts[0])), labelfont);
            for (int w = 0; w < wrapparts.length - 1; w++) {
              label.appendText(
                  libraryName.substring(Integer.parseInt(wrapparts[w]) + 1, Integer.parseInt(wrapparts[w + 1])),
                  labelfont);
            }
            label.appendText(libraryName.substring(Integer.parseInt(wrapparts[wrapparts.length - 1]) + 1), labelfont);
          } else {
            label.appendText(libraryName, labelfont);
          }
          continue;
        }
      } else {
        if ((Integer.parseInt(wrapparts[0]) != 0) && (libName.length() > Integer.parseInt(wrapparts[0]))) {
          label.appendText(libName.substring(0, Integer.parseInt(wrapparts[0])), labelfont);
          for (int w = 0; w < wrapparts.length - 1; w++) {
            label.appendText(libName.substring(Integer.parseInt(wrapparts[w]) + 1, Integer.parseInt(wrapparts[w + 1])),
                labelfont);
          }
          label.appendText(libName.substring(Integer.parseInt(wrapparts[wrapparts.length - 1]) + 1), labelfont);
        } else {
          label.appendText(libName, labelfont);
        }
      }
    }

    label.appendBlankLine();
    if (sigfont > 4) {
      label.appendText(Signature.format(p).toUpperCase().replace("\"", " "), sigfont);
      label.appendBlankLine();
    } else {
      label.appendText(Signature.format(p).replace("\"", " "), sigfont);
    }
    label.appendBlankLine();
    label.appendCode128("P" + p.getInvBroj());
    printer.print(label, pageCode);
*/
  }
}
