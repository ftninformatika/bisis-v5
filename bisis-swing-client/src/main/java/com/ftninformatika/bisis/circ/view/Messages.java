package com.ftninformatika.bisis.circ.view;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Messages {
	//private static final String BUNDLE_NAME = "messages_properties.circ.messages_sr"; //$NON-NLS-1$
	private static final String BUNDLE_NAME = "messages_properties/circ/messages_sr.properties"; //$NON-NLS-1$

	//private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	private static ResourceBundle bundle;

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
			//return RESOURCE_BUNDLE.getString(key);
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
