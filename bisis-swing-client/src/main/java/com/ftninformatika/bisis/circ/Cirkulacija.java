package com.ftninformatika.bisis.circ;

import java.beans.PropertyVetoException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Environment;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.manager.RecordsManager;
import com.ftninformatika.bisis.circ.manager.UserManager;
import com.ftninformatika.bisis.circ.view.MainFrame;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.login.SplashScreen;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticTheme;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class Cirkulacija {

    private static Cirkulacija app;
    private SplashScreen splash;
    private MainFrame mf;
    private Librarian lib;
    private static Log log = LogFactory.getLog(Cirkulacija.class.getName());
    private Environment env;
    private RecordsManager recmng;
    private UserManager usermng;
    /*private SearchUsersManager susermng;
    private Service service;
    private Service serviceArchive;*/

    private Cirkulacija() {
    }

    public static void startApp(Librarian lib){
        app.setLibrarian(lib);
        app.init();
    }

    public static Cirkulacija getApp() {
        return app;
    }


    public MainFrame getMainFrame() {
        if (mf == null){
            app.setLibrarian(BisisApp.appConfig.getLibrarian());
            app.init();
        }
        if (!mf.isVisible())
            mf.setVisible(true);
        return mf;
    }

    private void init() {
    PropertyConfigurator.configure(Cirkulacija.class
        .getResource("/log4j.properties"));
        log.info("Application startup");

        UIManager.put("swing.boldMetal", Boolean.FALSE);
        UIManager.installLookAndFeel("PlasticLookAndFeel",
                "com.jgoodies.looks.plastic.PlasticLookAndFeel");
        UIManager.installLookAndFeel("Plastic3DLookAndFeel",
                "com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
        UIManager.installLookAndFeel("PlasticXPLookAndFeel",
                "com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
        UIManager.installLookAndFeel("WindowsLookAndFeel",
                "com.jgoodies.looks.windows.WindowsLookAndFeel");

        splash = new SplashScreen();
        splash = BisisApp.getSplash();
        splash.setImage("/circ-images/splash.png");
        splash.setVisible(true);
        splash.getMessage().setText("initializing environment");

        /*if (service == null){
            createServices();
        }
        if (service == null){
            JOptionPane.showMessageDialog(null, "Gre\u0161ka pri konekciji sa bazom podataka!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }*/
        usermng = new UserManager();
        //recmng = new RecordsManager();
        //susermng = new SearchUsersManager();

        if (usermng.getEnvFile() == null){
            JOptionPane.showMessageDialog(null, "Gre\u0161ka pri ucitavanju parametara!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        env = new Environment(usermng.getEnvFile());
        int i = env.loadOptions();
        if (i == 1) {
            if (!env.getLookAndFeel().equals("default")) {
                try {
                    Class themeName = Class.forName(env.getTheme());
                    PlasticLookAndFeel.setPlasticTheme((PlasticTheme) (themeName
                            .newInstance()));
                    UIManager.setLookAndFeel(env.getLookAndFeel());
                } catch (Exception e) {
                    log.error(e);
                }
            }
            Utils.setUIFontSize(env.getFontSize());

            splash.getMessage().setText("initializing GUI");
            mf = new MainFrame();
            BisisApp.getMainFrame().insertFrame(mf);
            try {
                if (env.getMaximize()){
                    mf.setMaximum(true);
                } else {
                    mf.setMaximum(false);
                }
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }

            splash.getMessage().setText("initializing data");
            loadDefaults();

            splash.setVisible(false);
            splash.dispose();
            mf.setVisible(true);
        } else {
            System.exit(0);
        }
    }

    private void loadDefaults() {
        try{
            getUserManager().loadCombos(mf.getUserPanel());
            //getUserManager().loadCombos(mf.getGroupPanel());
            //getUserManager().loadCombos(mf.getSearchUsers());
            //getUserManager().loadCombos(mf.getSearchBooks());
            //getUserManager().loadCombos(mf.getReport());
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void setLibrarian(Librarian lib) {
        this.lib = lib;
    }

    public Librarian getLibrarian() {
        if (lib == null){
            lib = new Librarian("admin", "admin");
        }
        return lib;
    }

    public Environment getEnvironment() {
        return env;
    }

    public RecordsManager getRecordsManager() {
        return recmng;
    }

    public UserManager getUserManager() {
        return usermng;
    }
/*
    public SearchUsersManager getSearchUsersManager() {
        return susermng;
    }

    public Service getService(){
        if (service == null){
            createServices();
        }
        return service;
    }

    public Service getServiceArchive(){
        if (serviceArchive == null && BisisApp.getINIFile().getBoolean("database-archive", "enabled")){
            createServices();
        }
        return serviceArchive;
    }

    private void createServices(){
        ServiceFactory factory = BisisApp.getFactory(CommandType.HIBERNATE);
        ServiceFactory factoryArchive = null;
        if (BisisApp.getINIFile().getBoolean("database-archive", "enabled")){
            factoryArchive = BisisApp.getFactory(CommandType.HIBERNATEARCHIVE);
        }
        String mac = NetUtils.getMACAddress();
        log.info("Procitana MAC adresa: " + mac);
        String category = "commandsrv";
        if (BisisApp.getINIFile().getCategories().contains(mac)){
            category = mac;
            log.info("Pronadjena konfiguracija za MAC adresu: " + mac);
        }else{
            log.info("Nije pronadjena konfiguracija za MAC adresu: " + mac +". Koristi se default konfiguracija [commandsrv]");
        }
        if (BisisApp.getINIFile().getBoolean(category, "remote")){
            service = factory.createService(ServiceType.REMOTE, BisisApp.getINIFile().getString(category, "service"));
            if (BisisApp.getINIFile().getBoolean("database-archive", "enabled")){
                serviceArchive = service;
            }
            log.info("Kreirana instanca za udaljen pristup serveru.");
        } else {
            service = factory.createService(ServiceType.LOCAL, null);
            if (BisisApp.getINIFile().getBoolean("database-archive", "enabled")){
                serviceArchive = factoryArchive.createService(ServiceType.LOCAL, null);
            }
            log.info("Kreirana instanca za lokalni pristup serveru.");
        }
    }*/

    public void close(){
        if (mf != null && mf.isVisible())
            mf.handleClose();
    }

    static {
        app = new Cirkulacija();
    }

}
