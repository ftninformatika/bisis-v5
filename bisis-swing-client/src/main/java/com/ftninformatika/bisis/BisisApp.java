package com.ftninformatika.bisis;

import com.ftninformatika.bisis.backend_api.BisisService;
import com.ftninformatika.bisis.config.AppConfig;
import com.ftninformatika.bisis.config.Config;
import com.ftninformatika.bisis.config.ConfigFactory;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.login.LoginFrame;
import com.ftninformatika.utils.RetrofitUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.swing.*;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Petar on 6/20/2017.
 */
public class BisisApp {

    public static void main(String[] args) {
        appConfig = ConfigFactory.getConfig(Config.DEVELOPMENT);

        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JDialog.setDefaultLookAndFeelDecorated(true);
        LoginFrame login = new LoginFrame();
        boolean correct = false;
        while (!correct){
            if (login.isConfirmed()) {

                String token = "";
                try {
                    token = RetrofitUtils.acquireToken("http://127.0.0.1:8080", login.getUsername(), login.getPassword());
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                if (token != null && !token.equals("")) {
                    correct = true;
                    login.disp();
                    appConfig.setRetrofit(token,getDomainFromUsername(login.getUsername()));
                    BisisService bs = appConfig.getRetrofit().create(BisisService.class);
                    Call<Librarian> lib = bs.getLibrarian(login.getUsername());
                    Librarian response = null;
                    try {
                          response = lib.execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    appConfig.setLibrarian(response);
                    appConfig.setLibrary(response.getBiblioteka());


                    System.out.println("Uspesno ulogovan :)");

                    mf = new MainFrame();
                    mf.setResizable(true);
                    mf.setSize(Toolkit.getDefaultToolkit().getScreenSize());
                    if (Toolkit.getDefaultToolkit().isFrameStateSupported(JFrame.MAXIMIZED_BOTH))
                        mf.setExtendedState(mf.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                    mf.setVisible(true);
                    mf.setJMenuBar(new MenuBuilder(appConfig.getLibrarian()));
                    mf.initialize(appConfig.getLibrarian());
                }

                else {
                    JOptionPane.showMessageDialog(null, "Pogre\u0161no ime/lozinka",
                            "Greska", JOptionPane.ERROR_MESSAGE);
                    login.setVis(true);
                }


            } else {
                System.exit(0);
            }

        }

    }



    public static Retrofit retrofit;
    public static AppConfig appConfig;
    public static MainFrame mf;

    private static String getDomainFromUsername(String username){ //mora zbog header interceptora u retrofitu
               return username.split(".com")[0].split("@")[1];
    }
}
