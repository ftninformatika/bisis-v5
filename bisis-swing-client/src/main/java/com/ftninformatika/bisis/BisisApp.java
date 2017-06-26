package com.ftninformatika.bisis;

import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.service.BisisService;
import com.ftninformatika.bisis.config.AppConfig;
import com.ftninformatika.bisis.config.ConfigType;
import com.ftninformatika.bisis.config.ConfigFactory;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.login.LoginFrame;
import com.ftninformatika.utils.RetrofitUtils;
import retrofit2.Call;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BisisApp {

  public static void main(String[] args) {
    Properties props = new Properties();
    try (final InputStream stream = BisisApp.class.getResourceAsStream("/config.properties")) {
      props.load(stream);
      appVersion = props.getProperty("appVersion");
    } catch (Exception ex) {
      System.err.println(ex);
      return;
    }
    appConfig = ConfigFactory.getConfig(ConfigType.DEVELOPMENT);

    UIManager.put("swing.boldMetal", Boolean.FALSE);
    try {
      //UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
      return;
    }
    JDialog.setDefaultLookAndFeelDecorated(true);
    LoginFrame login = new LoginFrame();
    boolean correct = false;
    while (!correct) {
      if (login.isConfirmed()) {
        String token = RetrofitUtils.acquireToken(appConfig.getServerUrl(), login.getUsername(), login.getPassword());
        if (token == null) {
          JOptionPane.showMessageDialog(null, "Server je nedostupan!",
              "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
          System.exit(0);
        }

        if (token != null && !token.equals("")) {
          correct = true;
          login.disp();
          appConfig.setRetrofit(token, getDomainFromUsername(login.getUsername()));
          bisisService = appConfig.getRetrofit().create(BisisService.class);
          Call<Librarian> lib = bisisService.getLibrarian(login.getUsername());
          Librarian response = null;
          try {
            response = lib.execute().body();
            System.out.println(response);
          } catch (IOException e) {
            System.err.println(e);
          }
          appConfig.setLibrarian(response);
          appConfig.setLibrary(response.getBiblioteka());

          Record zapis = null;
          try {
            zapis = bisisService.getOneRecord().execute().body();
          } catch (IOException e) {
            e.printStackTrace();
          }
          System.out.println(zapis.toString());

          mf = new MainFrame();
          mf.setResizable(true);
          mf.setSize(Toolkit.getDefaultToolkit().getScreenSize());
          if (Toolkit.getDefaultToolkit().isFrameStateSupported(JFrame.MAXIMIZED_BOTH))
            mf.setExtendedState(mf.getExtendedState() | JFrame.MAXIMIZED_BOTH);
          mf.setVisible(true);
          mf.setJMenuBar(new MenuBuilder(appConfig.getLibrarian()));
          mf.initialize(appConfig.getLibrarian());

        } else {
          JOptionPane.showMessageDialog(null, "Pogre\u0161no ime/lozinka",
              "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
          login.setVis(true);
        }
      } else {
        System.exit(0);
      }

    }

  }

  public static AppConfig appConfig;
  public static BisisService bisisService;
  public static MainFrame mf;
  public static String appVersion;

  public static MainFrame getMainFrame(){ //zbog lakseg refaktorisanja koda
    return mf;
  }


  private static String getDomainFromUsername(String username) {
    //mora zbog header interceptora u retrofitu
    return username.split("@")[1].replace('.','_');
  }
}
