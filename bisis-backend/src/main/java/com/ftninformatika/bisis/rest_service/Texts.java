package com.ftninformatika.bisis.rest_service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by Petar on 12/7/2017.
 */
public class Texts {
    private static final String BUNDLE_NAME = "messages/messages_sr.properties";

    private static ResourceBundle bundle;

    private Texts() {
    }

    static  {
        try {
            Reader reader = new InputStreamReader(Texts.class.getClassLoader().getResourceAsStream(BUNDLE_NAME), "UTF-8");
            bundle = new PropertyResourceBundle(reader);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
