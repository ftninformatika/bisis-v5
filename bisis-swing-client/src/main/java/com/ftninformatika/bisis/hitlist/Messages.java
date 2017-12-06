package com.ftninformatika.bisis.hitlist;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "messages_properties/hitlist/messages_sr.properties";

	private static ResourceBundle bundle;

	private Messages() {
	}

	static  {
				try {
						Reader reader = new InputStreamReader(Messages.class.getClassLoader().getResourceAsStream(BUNDLE_NAME), "UTF-8");
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
