package com.ftninformatika.bisis.editor.inventar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.barcode.epl2.*;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.utils.string.LatCyrUtils;
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
  public static void printBarcodeForPrimerak(Primerak p, String printer) {
    String labelFormat = BisisApp.appConfig.getClientConfig().getBarcodeLabelFormat();
    if (labelFormat == null) {
      printBarcodeForPrimerakStandard(p);
      return;
    }
    switch (labelFormat) {
      case "small": printBarcodeForPrimerakSmallLabel(p, false); break; //todo put enum
      case "standard": printBarcodeForPrimerakStandard(p); break;
      default: printBarcodeForPrimerakStandard(p);
    }
  }

  private static void printBarcodeForPrimerakStandard(Primerak p) {
    IPrinter printer;
    //novi nacin za stampanje bar koda
    printer = Printer2.getInstance();

    Label label = new Label(labelWidth, labelHeight, labelResolution, widebar, narrowbar, barwidth, pageCode);
    String[] library = optionName.split(",");
    String[] wrapparts = wrap.split(",");
    String libName, ogr, libraryName;
    for (int i = 0; i < library.length; i++) {
      libName = BisisApp.appConfig.getClientConfig().getBarcodeLibrary1();
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

  public static void printBarcodeForPrimerakSmallLabel(Primerak p, boolean largeSignature) {
    IPrinter printer;
    printer = Printer2.getInstance();
    int sigFontSize = sigfont;
    int barWidthSize = barwidth;
    int wrapChars = 19;
    String[] wrapparts = wrap.split(",");
    try {
      wrapChars = Integer.parseInt(wrapparts[0]);
    } catch (Exception e) {
      System.out.println("Error parsing barcode wrapparts, using 19");
    }
    if (largeSignature) {
      sigFontSize++;
      wrapChars = 7;
      barWidthSize -= 30;
    }

    String subLoc = p.getSigPodlokacija();
    String intOzn = p.getSigIntOznaka();
    String udk = p.getSigUDK();
    String format = p.getSigFormat();
    String numerusCurrens = p.getSigNumerusCurens();
    String shortLib = BisisApp.appConfig.getClientConfig().getLibraryName().toUpperCase();

    Label label = new Label(labelWidth, labelHeight, labelResolution, widebar, narrowbar, barWidthSize, pageCode);
    label.setCurrentY(1);

    if (!largeSignature) {
      String signature = ((intOzn != null ? intOzn + "-" : "")  + (udk != null ? udk : "")).trim();
      signature = LatCyrUtils.toLatin(signature
              .replace("\"", "\\\"")
              .replace("\'", "\\\'")
              .trim().replace(" ", "").toUpperCase());
      String[] signatureChunks = signature.split("(?<=\\G.{" + wrapChars + "})");
      int sigRowsCount = signatureChunks.length;
      if (sigRowsCount > 2) {
        label.setBarwidth(label.getBarwidth() - 30);
      }
      for (String chunk: signatureChunks) {
        label.appendText(chunk, sigFontSize, 0);
        label.appendSpace(6);
      }
      label.appendCode128("P" + p.getInvBroj(), 0);
      if (subLoc != null && !subLoc.equals("")) {
        if (sigRowsCount < 3) {
          label.appendCode128RsideText(subLoc, 3);
        } else {
          label.appendCode128RsideText2(subLoc, 3);
        }
      }
      label.appendR90Text(shortLib, 3);
    }
    else {
      String _1stRow =  (((intOzn != null && !intOzn.trim().equals("")) ? intOzn + "-" : "") + ((format != null && !format.trim().equals("")) ? format + "-" : "")).trim();
      label.appendText(_1stRow, sigFontSize);
      label.appendSpace(35);
      String _2ndRow =((numerusCurrens != null && !numerusCurrens.trim().equals("")) ? numerusCurrens : "").trim();
      String[] chunks = _2ndRow.split("(?<=\\G.{" + wrapChars + "})");
      for (String chunk: chunks) {
        label.appendText(chunk, sigFontSize);
        label.appendSpace(35);
      }
      label.appendCode128WithoutNum("P" + p.getInvBroj(), 5);
    }
    printer.print(label, pageCode);
  }

}
