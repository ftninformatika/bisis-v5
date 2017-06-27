package com.ftninformatika.bisis.editor.inventar;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.format.HoldingsDataCoders;

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
	  String invbrSubStr = //BisisApp.getINIFile().getString("cataloguing","invbrSubStr");
                            "1 4";
		String [] pos = invbrSubStr.split(" ");
		startPos = Integer.valueOf(pos[0]);
		endPos = Integer.valueOf(pos[1]);
		defaultPrimerakInvKnj = //BisisApp.getINIFile().getString("cataloguing", "defaultPrimerakInvKnj");
                                "00";
		defaultGodinaInvKnj = //BisisApp.getINIFile().getString("cataloguing", "defaultGodinaInvKnj");
                                "00";
		defaultSveskaInvKnj = //BisisApp.getINIFile().getString("cataloguing", "defaultSveskaInvKnj");
                                "00";
		if(HoldingsDataCoders.getCoder(HoldingsDataCoders.ODELJENJE_CODER).size()==1){
			defaultOdeljenje = HoldingsDataCoders.getCoder(HoldingsDataCoders.ODELJENJE_CODER).get(0).getCode();
		}
		
		
		
  }
  
  
  
	
}
