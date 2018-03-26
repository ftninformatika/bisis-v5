package com.ftninformatika.bisis.editor.inventar;

import com.ftninformatika.bisis.BisisApp;

import java.text.SimpleDateFormat;


public class InventarConstraints {
  
  public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  
  public static int duzinaInventarnogBroja = 11;
  public static boolean imaOdeljenja = true;
  
  public static int startPos = 0;
  public static int endPos = 0;
  
  public static String defaultPrimerakInvKnj = "";
  public static String defaultGodinaInvKnj = "";
  public static String defaultSveskaInvKnj = ""; 
  // default odeljenje - ako u biblioteci postoji samo jedno odeljenje
  public static String defaultOdeljenje = "";
  
  
  static{ 
	  String invbrSubStr = BisisApp.appConfig.getClientConfig().getCataloguingInvbrSubStr();
		String [] pos = invbrSubStr.split(" ");
		startPos = Integer.valueOf(pos[0]);
		endPos = Integer.valueOf(pos[1]);
		defaultPrimerakInvKnj = BisisApp.appConfig.getClientConfig().getCataloguingDefaultPrimerakInvKnj();
		defaultGodinaInvKnj = BisisApp.appConfig.getClientConfig().getCataloguingDefaultGodinaInvKnj();
		defaultSveskaInvKnj = BisisApp.appConfig.getClientConfig().getCataloguingDefaultSveskaInvKnj();
		if(BisisApp.appConfig.getCodersHelper().getCoder(BisisApp.appConfig.getCodersHelper().ODELJENJE_CODER).size()==1){
			defaultOdeljenje = BisisApp.appConfig.getCodersHelper().getCoder(BisisApp.appConfig.getCodersHelper().ODELJENJE_CODER).get(0).getCode();
		}
		
		
		
  }
  
  
  
	
}
