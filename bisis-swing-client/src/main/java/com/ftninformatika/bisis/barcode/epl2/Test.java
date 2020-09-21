package com.ftninformatika.bisis.barcode.epl2;

import java.math.BigInteger;

public class Test {
  public static void main(String[] args) throws Exception {
    //IPrinter printer = new RemotePrinter("localhost", "7000");
    IPrinter printer = Printer2.getInstance();
    Label label = new Label(300, 0, 203, 2, 3, 30, "1250");
  /*  label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("821.163.41.09");
    label.appendCode128("K01000140010");
    printer.print(label);

    label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("821.163.41-1");
    label.appendCode128("K27000140011");
    printer.print(label);*/

    //label = new Label(0, 0, 203,3,2,50,"1250");
    label.setCurrentY(1);
    label.appendText("X-II-214231234",4);
    label.appendSpace(6);
    label.appendText("\\\"2145712342118",4);
    label.appendSpace(6);
    label.appendText("182145712344118",4);
//    label.appendSpace();
    label.appendCode128("P01000001540");
    label.appendCode128RsideText2("0105", 3);
    label.appendR90Text("BGB",3);
    printer.print(label,"1250");
    System.out.println(label.getCommands());
    System.out.println(String.format("%x", 
        new BigInteger(label.getCommands().getBytes("cp1251"))));

 /*   label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("94(497.1)");
    label.appendCode128("K01000140015");
    printer.print(label);

    label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("94(497.1)");
    label.appendCode128("K23000140016");
    printer.print(label);

    label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("821.111-31");
    label.appendCode128("K01000139628");
    printer.print(label);

    label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("821.111-31");
    label.appendCode128("K01000139629");
    printer.print(label);

    label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("821.111-31");
    label.appendCode128("K01000139630");
    printer.print(label);

    label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("821.111-31");
    label.appendCode128("K05000139631");
    printer.print(label);

    label = new Label(456, 0, 203);
    label.appendText("Gradska biblioteka");
    label.appendText("Novi Sad");
    label.appendBlankLine();
    label.appendText("821.111-31");
    label.appendCode128("K19000139632");
    printer.print(label);*/
  }
}
