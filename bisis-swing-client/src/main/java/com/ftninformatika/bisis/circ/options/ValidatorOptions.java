package com.ftninformatika.bisis.circ.options;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.xb.xsdschema.FieldDocument;


public class ValidatorOptions {

 /* private static FormValidationDocument doc = null;
  
  
  public static void setDoc(String xml){
  	try{
	  	if (xml != null){
	  		doc = FormValidationDocument.Factory.parse(new StringReader(xml));
			} else {
				InputStream input = ValidatorOptions.class.getResourceAsStream("/user-bean-validator.xml");
				doc = FormValidationDocument.Factory.parse(input);
			}
  	}catch(Exception e){
      e.printStackTrace();
    }
  }
	
	public static void loadOptions(OptionsFrame frame){
		try{
      
			noNamespace.FieldDocument.Field[] fields = doc.getFormValidation().getFormsetArray(0).getFormArray(0).getFieldArray();
			int i = 0;
			while (i < fields.length){
				if (fields[i].getProperty().equals("firstName")){
					frame.setChkdFirstName(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("lastName")){
					frame.setChkdLastName(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("parentName")){
					frame.setChkdParentName(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("address")){
					frame.setChkdAddress(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("zip")){
					frame.setChkdZip(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("city")){
					frame.setChkdCity(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("phone")){
					frame.setChkdPhone(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("email")){
					frame.setChkdEmail(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("tmpAddress")){
					frame.setChkdTmpAddress(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("tmpZip")){
					frame.setChkdTmpZip(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("tmpCity")){
					frame.setChkdTmpCity(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("tmpPhone")){
					frame.setChkdTmpPhone(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("jmbg")){
					frame.setChkdJmbg(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("docNo")){
					frame.setChkdDocNo(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("docCity")){
					frame.setChkdDocCity(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("country")){
					frame.setChkdCountry(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("title")){
					frame.setChkdTitle(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("occupation")){
					frame.setChkdOccupation(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("indexNo")){
					frame.setChkdIndexNo(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("note")){
					frame.setChkdNote(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("interests")){
					frame.setChkdInterests(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("organization")){
          frame.setChkdOrganization(isRequired(fields[i].getDepends()));
        }else if (fields[i].getProperty().equals("eduLvl")){
          frame.setChkdEduLvl(isRequired(fields[i].getDepends()));
        }else if (fields[i].getProperty().equals("classNo")){
          frame.setChkdClass(isRequired(fields[i].getDepends()));
        }else if (fields[i].getProperty().equals("language")){
          frame.setChkdLanguage(isRequired(fields[i].getDepends()));
        }
				i++;
			}
				
			fields = doc.getFormValidation().getFormsetArray(0).getFormArray(1).getFieldArray();
			i = 0;
			while (i < fields.length){
				if (fields[i].getProperty().equals("userID")){
					frame.setChkdUserID(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("mmbrshipDate")){
					frame.setChkdSignDate(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("mmbrshipUntilDate")){
					frame.setChkdUntilDate(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("mmbrshipCost")){
					frame.setChkdCost(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("mmbrshipReceiptId")){
					frame.setChkdReceiptID(isRequired(fields[i].getDepends()));
				}else if (fields[i].getProperty().equals("mmbrType")){
          frame.setChkdMmbrType(isRequired(fields[i].getDepends()));
        }else if (fields[i].getProperty().equals("userCateg")){
          frame.setChkdUserCateg(isRequired(fields[i].getDepends()));
        }
				i++;
			}
			
			
		}catch (Exception e){
			//TODO log
			e.printStackTrace();
		}
	}
	
	private static boolean isRequired(String depends){
		int ind = depends.indexOf("required");
		if (ind == -1){
			return false;
		}else {
			return true;
		}
	}
	
	public static void saveOptions(OptionsFrame frame){
		try{
			noNamespace.FieldDocument.Field[] fields = doc.getFormValidation().getFormsetArray(0).getFormArray(0).getFieldArray();
			int i = 0;
			while (i < fields.length){
				if (fields[i].getProperty().equals("firstName")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdFirstName()));
				}else if (fields[i].getProperty().equals("lastName")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdLastName()));
				}else if (fields[i].getProperty().equals("parentName")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdParentName()));
				}else if (fields[i].getProperty().equals("address")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdAddress()));
				}else if (fields[i].getProperty().equals("zip")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdZip()));
				}else if (fields[i].getProperty().equals("city")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdCity()));
				}else if (fields[i].getProperty().equals("phone")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdPhone()));
				}else if (fields[i].getProperty().equals("email")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdEmail()));
				}else if (fields[i].getProperty().equals("tmpAddress")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdTmpAddress()));
				}else if (fields[i].getProperty().equals("tmpZip")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdTmpZip()));
				}else if (fields[i].getProperty().equals("tmpCity")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdTmpCity()));
				}else if (fields[i].getProperty().equals("tmpPhone")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdTmpPhone()));
				}else if (fields[i].getProperty().equals("jmbg")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdJmbg()));
				}else if (fields[i].getProperty().equals("docNo")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdDocNo()));
				}else if (fields[i].getProperty().equals("docCity")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdDocCity()));
				}else if (fields[i].getProperty().equals("country")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdCountry()));
				}else if (fields[i].getProperty().equals("title")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdTitle()));
				}else if (fields[i].getProperty().equals("occupation")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdOccupation()));
				}else if (fields[i].getProperty().equals("indexNo")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdIndexNo()));
				}else if (fields[i].getProperty().equals("note")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdNote()));
				}else if (fields[i].getProperty().equals("interests")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdInterests()));
				}else if (fields[i].getProperty().equals("organization")){
          fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdOrganization()));
        }else if (fields[i].getProperty().equals("eduLvl")){
          fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdEduLvl()));
        }else if (fields[i].getProperty().equals("classNo")){
          fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdClass()));
        }else if (fields[i].getProperty().equals("language")){
          fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdLanguage()));
        }
				i++;
			}
				
			fields = doc.getFormValidation().getFormsetArray(0).getFormArray(1).getFieldArray();
			i = 0;
			while (i < fields.length){
				if (fields[i].getProperty().equals("userID")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdUserID()));
				}else if (fields[i].getProperty().equals("mmbrshipDate")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdSignDate()));
				}else if (fields[i].getProperty().equals("mmbrshipUntilDate")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdUntilDate()));
				}else if (fields[i].getProperty().equals("mmbrshipCost")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdCost()));
				}else if (fields[i].getProperty().equals("mmbrshipReceiptId")){
					fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdReceiptID()));
				}else if (fields[i].getProperty().equals("mmbrType")){
          fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdMmbrType()));
        }else if (fields[i].getProperty().equals("userCateg")){
          fields[i].setDepends(setRequired(fields[i].getDepends(),frame.isChkdUserCateg()));
        }
				i++;
			}
			
		}catch (Exception e){
			e.printStackTrace();
			//TODO log
		}
	}
	
	public static boolean save(){
    XmlOptions opts = new XmlOptions();
    opts.setSavePrettyPrint();
    try {
    	StringWriter doctext = new StringWriter();
			doc.save(doctext,opts);
			boolean ok = true;// TODO-hardcoded Manager.saveValidator(doctext.toString());
			return ok;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
  }
	
	private static String setRequired(String depends, boolean required){
		int ind = depends.indexOf("required");
		if (required){
			if (ind == -1){
				if (depends == null || depends.equals("")){
					return "required";
				}else{
					return depends + ",required";
				}
			}else {
				return depends;
			}
		}else{
			if (ind == -1){
				return depends;
			}else {
				String[] tmp = depends.split(",");
				depends = "";
				for (int i = 0; i < tmp.length; i++){
					if (!tmp[i].equals("required")){
						if (depends.equals("")){
							depends = tmp[i];
						}else{
							depends = depends +","+ tmp[i];
						}
					}
				}
				return depends;
			}
		}
	}*/
}
