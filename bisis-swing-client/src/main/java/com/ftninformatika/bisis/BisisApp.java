package com.ftninformatika.bisis;

import ch.randelshofer.quaqua.QuaquaManager;
import ch.randelshofer.quaqua.leopard.Quaqua15LeopardCrossPlatformLookAndFeel;
import com.ftninformatika.bisis.librarian.LibrarianManager;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.login.*;
import com.ftninformatika.bisis.login.SplashScreen;
import com.ftninformatika.bisis.service.BisisService;
import com.ftninformatika.bisis.config.AppConfig;
import com.ftninformatika.bisis.config.ConfigType;
import com.ftninformatika.bisis.config.ConfigFactory;
import com.ftninformatika.bisis.service.RecordManager;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.RetrofitUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import retrofit2.Call;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class BisisApp {

  public static void main(String[] args) {

    Properties props = new Properties();
    String profile = null;
    try (final InputStream stream = BisisApp.class.getResourceAsStream("/config.properties")) {
      props.load(stream);
      appVersion = props.getProperty("appVersion");
      profile = props.getProperty("profile");
    } catch (Exception ex) {
      System.err.println(ex);
      return;
    }
    if (profile != null && profile.equals("production")){
      appConfig = ConfigFactory.getConfig(ConfigType.PRODUCTION);
    } else {
      appConfig = ConfigFactory.getConfig(ConfigType.DEVELOPMENT);
    }

    //Swing komponente koje zelimo iz default teme (zbog editora)
    Set exc = new HashSet();
//    exc.add("Component");
    exc.add("Tree");
    exc.add("SidebarTreeModel");
    QuaquaManager.setExcludedUIs(exc);
    try {
      UIManager.setLookAndFeel(new Quaqua15LeopardCrossPlatformLookAndFeel());

    } catch (Exception ex) {
      System.err.println(ex.getMessage());
      return;
    }
    Font f = new Font("sans-serif", Font.PLAIN, 15);
    UIManager.put("Menu.font", f);


    LoginFrame login = new LoginFrame();
    boolean correct = false;
    while (!correct) {
      if (login.isConfirmed()) {

        splashScreen = new SplashScreen();
        splashScreen.setImage("/icons/book-big.png");
        splashScreen.setVisible(true);
        splashScreen.getMessage().setText(Messages.getString("MAIN_LOADING_RECORD_MANAGER"));

        String token = null;
        try {
          token = RetrofitUtils.acquireToken(appConfig.getServerUrl(), login.getUsername(), login.getPassword());
        } catch (IOException e) {
          //e.printStackTrace();
          splashScreen.setVisible(false);
          JOptionPane.showMessageDialog(null, Messages.getString("MAIN_SERVER_UNAVAILABLE"),
                  Messages.getString("MAIN_ERROR"), JOptionPane.ERROR_MESSAGE);
          System.exit(0);
        }


        if (token != null && !token.equals("")) {


          correct = true;
          login.disp();
          appConfig.setRetrofit(token, getDomainFromUsername(login.getUsername()));
          bisisService = appConfig.getRetrofit().create(BisisService.class);

          Call<LibrarianDTO> lib = bisisService.getLibrarianByUsername(login.getUsername());
          LibrarianDTO response = null;
          try {
            response = lib.execute().body();
            log.info("Prijavljen bibliotekar: " + response.getUsername());
          } catch (IOException e) {
            System.err.println(e);
          }
          appConfig.setLibrarian(LibrarianManager.initializeLibrarianFromDTO(response));
          appConfig.setLibrary(response.getBiblioteka());
          appConfig.setLibraryConfiguration(appConfig.getLibrary(), appConfig.getRetrofit());
          Messages.setLocale(appConfig.getClientConfig().getLocale());
          appConfig.initCoders();

          recMgr = new RecordManager();
          //testing purposes only!
//          try {
//            bisisService.clearElasticStorage().execute();
//            System.out.println("Elastic storage cleared!");
//            bisisService.fillElasticStorage().execute();
//            System.out.println("Elastic storage filled!");
//          } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Greska pri reinicijalizaciji podataka u elastic-u!");
//          }
          //----------------------
          mf = new MainFrame();
          mf.setResizable(true);
          mf.setSize(Toolkit.getDefaultToolkit().getScreenSize());
          if (Toolkit.getDefaultToolkit().isFrameStateSupported(JFrame.MAXIMIZED_BOTH))
            mf.setExtendedState(mf.getExtendedState() | JFrame.MAXIMIZED_BOTH);
          mf.setVisible(true);
          mf.setJMenuBar(new MenuBuilder(appConfig.getLibrarian()));
          mf.initialize(appConfig.getLibrarian());

          splashScreen.setVisible(false);
          splashScreen.dispose();

        } else {
          splashScreen.setVisible(false);
          JOptionPane.showMessageDialog(null, Messages.getString("MAIN_WRONG_USERNAME_PASS"),
                  Messages.getString("MAIN_ERROR"), JOptionPane.ERROR_MESSAGE);
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
  public static RecordManager recMgr;
  public static SplashScreen splashScreen;



  public static SplashScreen getSplash(){
    return splashScreen;
  }

  public static MainFrame getMainFrame(){ //zbog lakseg refaktorisanja koda
    return mf;
  }

  public static Log log = LogFactory.getLog(BisisApp.class);

  private static String getDomainFromUsername(String username) {
    //mora zbog header interceptora u retrofitu
    return username.split("@")[1].replace('.','_');
  }
}
