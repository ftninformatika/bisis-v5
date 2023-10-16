package com.ftninformatika.bisis.circ;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.manager.RecordsManager;
import com.ftninformatika.bisis.circ.manager.ReservationsManager;
import com.ftninformatika.bisis.circ.manager.SearchUsersManager;
import com.ftninformatika.bisis.circ.manager.UserManager;
import com.ftninformatika.bisis.circ.view.MainFrame;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.login.SplashScreen;
import com.ftninformatika.utils.Messages;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticTheme;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.beans.PropertyVetoException;

public class Cirkulacija {

    private static Cirkulacija app;
    private SplashScreen splash;
    private MainFrame mf;
    private Librarian lib;
    private static Logger log = Logger.getLogger(Cirkulacija.class);
    private Environment env;
    private RecordsManager recmng;
    private UserManager usermng;
    private SearchUsersManager susermng;
    private ReservationsManager reservemng;

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
        Logger.getLogger(Cirkulacija.class).info("Pokretanje cirkulacije");

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
        //splash = BisisApp.getSplash();
        splash.setImage("/circ-images/splash.png");
        splash.setVisible(true);
        splash.getMessage().setText("initializing environment");

        usermng = new UserManager();
        recmng = new RecordsManager();
        susermng = new SearchUsersManager();
        reservemng = new ReservationsManager();

        if (usermng.getEnvFile() == null){
            JOptionPane.showMessageDialog(null, Messages.getString("circulation.error_message"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        env = new Environment(usermng.getEnvFile());
        System.out.println("MAC address: " + env.getMac());
        log.info("MAC address: " + env.getMac());
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

            splash.getMessage().setText(Messages.getString("circulation.initializing_gui"));
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

            splash.getMessage().setText(Messages.getString("circulation.initializing_data"));
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
            getUserManager().loadCombos(mf.getGroupPanel());
            getUserManager().loadCombos(mf.getSearchUsers());
            getUserManager().loadCombos(mf.getSearchBooks());
            getUserManager().loadCombos(mf.getReport());
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, Messages.getString("circulation.error_message"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE);
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

    public SearchUsersManager getSearchUsersManager() {
        return susermng;
    }

    public ReservationsManager getReservationsManager(){
        return reservemng;
    }

    public void close(){
        if (mf != null && mf.isVisible())
            mf.handleClose();
    }

    static {
        app = new Cirkulacija();
    }

}
