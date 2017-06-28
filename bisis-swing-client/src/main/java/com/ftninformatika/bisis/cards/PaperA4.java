package com.ftninformatika.bisis.cards;

import java.awt.print.*;

public class PaperA4 extends PageFormat {

  static double x0         = 10;
  static double y0         = 10;
  static int     A4Height  = 842;      // 1/72 inch-units
  static int     A4Width   = 595;

  public double getHeight() {
    return (double)A4Height;
  }
  public double getImageableHeight(){
    return (double)A4Height; // - 200;
  }
  public double getWidth() {
    return  (double)A4Width;
  }
  public double getImageableWidth() {
    return  (double)A4Width - 40;
  }
  public double getImageableX() {
    return x0;
  }
  public double getImageableY() {
    return y0;
  }
}
