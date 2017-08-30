package com.ftninformatika.bisis.barcode.epl2;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class RemotePrinter implements IPrinter {

  public RemotePrinter(String address, String port) {
    this.address = address;
    this.port = Integer.parseInt(port);
  }

  public boolean print(Label label, String codePage) {
    String commands = label.getCommands();
    try {
      Socket sock = new Socket(address, port);
      PrintWriter out = new PrintWriter(new BufferedWriter(
          new OutputStreamWriter(new BufferedOutputStream(
              sock.getOutputStream()), codePage)));
      //TODO encoding "cp" + codePage
      out.print(commands);
      out.flush();
      out.close();
      sock.close();
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }

  private String address;
  private int port;
}
