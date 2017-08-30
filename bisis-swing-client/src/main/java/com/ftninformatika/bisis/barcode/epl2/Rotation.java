package com.ftninformatika.bisis.barcode.epl2;

public enum Rotation {
  R0(0), R90(1), R180(2), R270(3);
  
  private Rotation(int code) {
    this.code = code;
  }
  
  public int getCode() {
    return code;
  }
  
  public String toString() {
    switch(code) {
      case 0: return "R: 0";
      case 1: return "R: 90";
      case 2: return "R: 180";
      case 3: return "R: 270";
      default: return "R: 0";
    }
  }
  
  private int code;
}
