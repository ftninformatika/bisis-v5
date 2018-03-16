package com.ftninformatika.utils;

import com.ftninformatika.bisis.BisisApp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by Petar on 12/8/2017.
 */
public class Messages {

    private static final String BUNDLE_PATH = "messages_properties/internationalization/";

    private static ResourceBundle bundle;
    private static String locale = null;

    static  {
        try {
            Reader reader = reader = new InputStreamReader(Messages.class.getClassLoader().getResourceAsStream(BUNDLE_PATH + "messages_sr_CYRL_RS.properties"), "UTF-8");
            bundle = new PropertyResourceBundle(reader);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setLocale(String loc){
        locale = loc;
            try {
                Reader reader = null;
                if(locale == null)
                    reader = new InputStreamReader(Messages.class.getClassLoader().getResourceAsStream(BUNDLE_PATH + "messages_sr_CYRL_RS.properties"), "UTF-8");
                else if(locale.equals("sr_CYRL_RS"))
                    reader = new InputStreamReader(Messages.class.getClassLoader().getResourceAsStream(BUNDLE_PATH + "messages_sr_CYRL_RS.properties"), "UTF-8");
                else if(locale.equals("sr_LATN_RS"))
                    reader = new InputStreamReader(Messages.class.getClassLoader().getResourceAsStream(BUNDLE_PATH + "messages_sr_LATN_RS.properties"), "UTF-8");
                else if(locale.equals("en"))
                    reader = new InputStreamReader(Messages.class.getClassLoader().getResourceAsStream(BUNDLE_PATH + "messages_en.properties"), "UTF-8");
                else
                    reader = new InputStreamReader(Messages.class.getClassLoader().getResourceAsStream(BUNDLE_PATH + "messages_sr_CYRL_RS.properties"), "UTF-8");

                bundle = new PropertyResourceBundle(reader);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private Messages() {
    }

    public static ResourceBundle getBundle(){
        return bundle;
    }


    public static String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
