package com.ftninformatika.bisis.barcode.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.ftninformatika.bisis.barcode.epl2.Printer;

public class BarcodeServer {

  public static void main(String[] args) {
    BarcodeServer server = new BarcodeServer();
    try {
      FileHandler fileHandler = new FileHandler("%h/.barcodeserver.log",
          256*1024, 1, true);
      fileHandler.setFormatter(new SimpleFormatter());
      log.addHandler(fileHandler);
    } catch (Exception ex) {
      ex.printStackTrace();
      return;
    }
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        log.info("Shutting down");
      }
    });
    if (args.length < 3) {
      log.info("Program in debug mode, usage: BarcodeServer printer codePage port");
      server.printer = Printer.getDefaultPrinter("DEBUG");
      server.codePage = "1252";
      server.port = 7000;
    } else {
      server.printer = Printer.getDefaultPrinter(args[0]);
      server.codePage = args[1];
      server.port = Integer.parseInt(args[2]);
    }
    log.info("Initialized, printer: " + server.printer + ", codePage: " + 
        server.codePage + ", port: " + server.port);
    server.run();
  }
  
  private void run() {
    try {
      ServerSocket ss = new ServerSocket(port);
      while (true) {
        final Socket sock = ss.accept();
        log.info("Request from: " + sock.getInetAddress().getHostAddress());
        new Thread() {
          public void run() {
            handle(sock);
          }
        }.start();
      }
    } catch (Exception ex) {
      log.severe(ex.getMessage());
    }
  }
  
  private void handle(Socket sock) {
    try {
      BufferedReader in = new BufferedReader(
          new InputStreamReader(sock.getInputStream(), codePage));
      //TODO encoding "cp" + codePage
      StringBuilder label = new StringBuilder();
      char[] charBuf = new char[1024];
      int bytesRead = 0;
      while (bytesRead != -1) {
        bytesRead = in.read(charBuf);
        if (bytesRead != -1)
          label.append(charBuf, 0, bytesRead);
      }
      String commands = label.toString();
      printer.print(commands, codePage);
      if (printer == Printer.DEBUG) {
        log.info("Label: " + String.format("%x", 
            new BigInteger(commands.getBytes(codePage))));
            //TODO encoding "cp" + codePage
      }
      in.close();
      sock.close();
    } catch (Exception ex) {
      log.severe(ex.getMessage());
    }
  }
  
  private Printer printer;
  private String codePage;
  private int port;
  
  private static Logger log = Logger.getLogger(BarcodeServer.class.getName());
}
