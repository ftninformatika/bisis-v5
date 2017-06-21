package com.ftninformatika.bisis.xmlmessaging;

import java.util.ResourceBundle;

public class ResourceReader{
	static ResourceBundle rb=null;
	
	static{
		try{
			rb=ResourceBundle.getBundle("com.gint.app.bisis4.xmlmessaging.config");
		}catch(Exception e){
			if(MessagingEnvironment.DEBUG==1)
				System.out.println("ResourceReader exception");
			e.printStackTrace();
		}
	}
	
	public static ResourceBundle getRB(){
		return rb;
	}

}