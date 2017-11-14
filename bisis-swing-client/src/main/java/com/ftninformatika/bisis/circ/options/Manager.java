package com.ftninformatika.bisis.circ.options;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.CircConfig;


public class Manager {

    private static CircConfig circConfig;
		
	public static void loadDocs(){
        try {
            circConfig = BisisApp.bisisService.getCircConfigs(BisisApp.appConfig.getLibrary()).execute().body();
            EnvironmentOptions.setDoc(circConfig.getCircOptionsXML());
            ValidatorOptions.setDoc(circConfig.getValidatorOptionsXML());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}

    public static boolean save(String envXml, String validatorXml){
        try {
            circConfig.setCircOptionsXML(envXml);
            circConfig.setValidatorOptionsXML(validatorXml);
            CircConfig config = BisisApp.bisisService.saveCircConfigs(circConfig).execute().body();
            if (config != null){
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	


}
