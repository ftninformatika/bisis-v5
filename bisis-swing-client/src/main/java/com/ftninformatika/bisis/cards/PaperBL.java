package com.ftninformatika.bisis.cards;

import java.awt.print.*;

public class PaperBL extends PageFormat {

  static double x0         = 10;
  static double y0         = 10;
  static int     A4Height  = 220; 
  static int     A4Width   = 595;
  Paper paper=new Paper();
  public void set(){
  	paper.setImageableArea(0,0,212,595);
  }

  public double getHeight(){
    return (double)A4Height;
  }
  public double getImageableHeight() {
    return (double)A4Height;
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
