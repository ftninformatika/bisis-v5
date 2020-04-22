package com.ftninformatika.bisis.editor.inventar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.barcode.epl2.*;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.utils.string.Signature;
import org.apache.log4j.Logger;


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
  static private boolean remote;
  static private HashMap<String, List<String>> printers;
  static private List<String> socket;
  static private int printersNo = 0;
  private static Logger log = Logger.getLogger(PrintBarcode.class);

  static {
    optionName = BisisApp.appConfig.getClientConfig().getBarcodeOptionName();
    labelWidth = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeLabelWidth());
    labelHeight = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeLabelHeight());;
    labelResolution = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeLabelResolution());;
    pageCode = BisisApp.appConfig.getClientConfig().getBarcodePageCode();;
    wrap = BisisApp.appConfig.getClientConfig().getBarcodeWrap();;
    widebar = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeWidebar());;
    narrowbar = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeNarrowbar());;
    barwidth = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeBarwidth());;
    sigfont = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeSigfont());
    labelfont = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeLabelfont());
    port = BisisApp.appConfig.getClientConfig().getBarcodePort();
  }

  public static List<String> getPrinters() {
    List<String> list = new ArrayList<String>();
    list.addAll(printers.keySet());
    return list;
  }

  public static void printBarcodeForPrimerak(Primerak p, String printerName) {
    IPrinter printer;
    //novi nacin za stampanje bar koda
    printer = Printer2.getInstance();

    Label label = new Label(labelWidth, labelHeight, labelResolution, widebar, narrowbar, barwidth, pageCode);
    String[] library = optionName.split(",");
    String[] wrapparts = wrap.split(",");
    String libName, ogr, libraryName;
    for (int i = 0; i < library.length; i++) {
      libName = BisisApp.appConfig.getClientConfig().getLibraryFullName();
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
      label.appendText(Signature.format(p).toUpperCase().replace("\"", " ") + "                       ", sigfont);
      label.appendBlankLine();
    } else {
      label.appendText(Signature.format(p).replace("\"", " ") + "                       ", sigfont);
    }
    label.appendBlankLine();
    label.appendCode128("P" + p.getInvBroj());
    printer.print(label, pageCode);

  }
}
