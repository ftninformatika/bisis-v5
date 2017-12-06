package com.ftninformatika.bisis.admin.coders;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by Petar on 12/6/2017.
 */
public class Messages {
    private static final String BUNDLE_NAME = "messages_properties/admin.coders/messages_sr.properties";

    private static ResourceBundle bundle;

    private Messages() {
    }

    static  {
        try {
            Reader reader = new InputStreamReader(com.ftninformatika.bisis.editor.Messages.class.getClassLoader().getResourceAsStream(BUNDLE_NAME), "UTF-8");
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