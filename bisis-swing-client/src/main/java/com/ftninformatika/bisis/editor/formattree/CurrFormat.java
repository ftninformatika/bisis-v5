package com.ftninformatika.bisis.editor.formattree;


import java.util.ArrayList;
import java.util.List;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.recordtree.CurrRecord;
import com.ftninformatika.bisis.format.PubTypes;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.UFormat;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.librarian.ProcessType;
import com.ftninformatika.bisis.records.Record;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CurrFormat {

	/** Current format*/
  public  static UFormat format = null;
  public static ProcessType processType;
  private static int pubType = 1;
  private static List<USubfield> mandatorySubfields = null;
  
  private static Log log = LogFactory.getLog(CurrFormat.class.getName());
  
//lista polja iz bloka 4 koja sadrze sekundarna polja
  public static List<String> block4 = new ArrayList<String>();
  
  static{
    block4.add("421");
    block4.add("423");
    block4.add("469");
    block4.add("464");
  }  
 
	public static void formatInitialize(Record rec){
    format = new UFormat();    
		if(rec!=null){
      log.info("Initializing format tree from record.");
      try{       
        pubType = rec.getPubType();
        //processType = FormatUtils.returnProcessTypeForPubtype(pubType); //TODO- napraviti kolekciju sa tipovima obrade i repozitorijum
		processType = //BisisApp.appConfig.getLibrarian().getCurentProcessType();
						FormatUtils.returnProcessTypeForPubtype(rec.getPubType());
        createFormatFromRecord(rec);
        createFormatFromProcessType();
      }catch(NullPointerException ex){        
        log.fatal(ex);
      }
		}else{
      // otvara se potpuno novi zapis
      log.info("Initializing format tree from process type.");
      try{        
        //processType = BisisApp.getLibrarian().getContext().getDefaultProcessType();
		processType = BisisApp.appConfig.getLibrarian().getCurentProcessType();
        createFormatFromProcessType();
        pubType = processType.getPubType().getPubType();
      }catch(Exception ex){        
        log.fatal(ex);
      }
		}
    mandatorySubfields = processType.getMandatorySubfields();
    sortFields();		
	}
	
	public static void changeProcessType(ProcessType pt){
		processType = pt;
		format = new UFormat();
		createFormatFromProcessType();
		CurrRecord.recordInitialize(null);
		mandatorySubfields = pt.getMandatorySubfields();
		sortFields();
		pubType = pt.getPubType().getPubType();
		
	}
	
	public static void createFormatFromRecord(Record rec){		
		UField uf;
		USubfield usf;		
		for(int i =0;i<rec.getFieldCount();i++){
     try{
							uf = getFullFormat().getField(rec.getField(i).getName()).shallowCopy();
							if(format.getField(uf.getName())==null)
								uf.getSubfields().clear();
							else
								uf = format.getField(uf.getName());
							for(int j=0;j<rec.getField(i).getSubfieldCount();j++){
									usf = (USubfield)getFullFormat()
											.getField(uf.getName()).getSubfield(rec.getField(i).getSubfield(j).getName());
									if(!uf.getSubfields().contains(usf))
										uf.add(usf);
								}
								if(format.getField(uf.getName())==null)
									format.add(uf);								
							
     }catch(NullPointerException ex){
       log.fatal("Cannot find field "+rec.getField(i).getName()+" in full format definition.");
     }
		}
	}
		
  public static int getPubType(){
    return pubType;
  }
	
	public static UFormat getFullFormat(){		
		return PubTypes.getFormat();
	}
	
	public static ProcessType getProcessType(){
		return processType;
	}
	
	public static void createFormatFromProcessType(){
    format.setName(processType.getName());
		UField owner;
		String fieldName;    
		for(int i=0;i<processType.getInitialSubfields().size();i++){
			USubfield usf = processType.getInitialSubfields().get(i);
			fieldName = usf.getOwner().getName();
			if(format.getField(fieldName)==null){
				owner = usf.getOwner().shallowCopy();				
				format.add(owner);
			}else owner = format.getField(fieldName);
			//usf.setMandatory(processType.getMandatorySubfields().contains(usf));
      if(owner.getSubfield(usf.getName())==null)
        owner.add(usf);
		}
	} 
	
	public static void sortFields(){
		List<UField> fields = format.getFields();
		 for (int i = 1; i < fields.size(); i++) {
		      for (int j = 0; j < fields.size() - i; j++) {
		        UField f1 = (UField)fields.get(j);
		        UField f2 = (UField)fields.get(j+1);
		        if (f1.getName().compareTo(f2.getName()) > 0) {
		          fields.set(j, f2);
		          fields.set(j+1, f1);
		        }
		      }
		    }
		    for (int i = 0; i < fields.size(); i++) {
		      UField f = (UField)fields.get(i);
		          sortSubfields(f);
		    }
	}
	
	public static void sortSubfields(UField f){
		List<USubfield> subfields = f.getSubfields();
		 for (int i = 1; i < subfields.size(); i++) {
		      for (int j = 0; j < subfields.size() - i; j++) {
		    	USubfield sf1 = (USubfield)subfields.get(j);
		    	USubfield sf2 = (USubfield)subfields.get(j+1);
		    	if(sf1!=null && sf2!=null)
		        if (sf1.getName()>sf2.getName()) {
		          subfields.set(j, sf2);
		          subfields.set(j+1, sf1);
		        }
		      }
		    }	
	}
  
	public static USubfield getUSubfield(String fieldName,char subfieldName){
		return format.getField(fieldName).getSubfield(subfieldName);		
	}	
	
	/* vraca sva polja koja se ne nalaze u tekucem formatu, 
	 * ali su definisana u standardu
	 * operacije ce se koristiti prilikom dodavanja novih polja u strukturu*/
	
	public static List<UField> returnMissingUFields(){		
		ArrayList<UField> ret = new ArrayList<UField>();		
		for(int i=0;i<PubTypes.getPubType(pubType).getFields().size();i++){
			if (format.getField(PubTypes.getPubType(pubType).getField(i).getName())==null)
				ret.add(PubTypes.getPubType(pubType).getField(i));				
		}				
		return ret;		
	}
	/* vraca listu potpolja polja uf koja su definisana po standardu ali ih 
	 * polje ne sadrzi 
	 * jos nije uradjeno do kraja
	 */
	public static List<USubfield> returnMissingUSubfields(UField uf){
		//System.out.println(uf.toString());
		List<USubfield> usfList = new ArrayList<USubfield>();
		UField ufFull = PubTypes.getPubType(pubType).getField(uf.getName());		
		for(int i=0;i<ufFull.getSubfieldCount();i++){
			boolean exists = false;
			for(int j=0;j<uf.getSubfieldCount();j++){
				if(((USubfield)uf.getSubfields().get(j)).getName()==
					((USubfield)ufFull.getSubfields().get(i)).getName())
					exists = true;
			}
			if(!exists){
				usfList.add((USubfield)ufFull.getSubfields().get(i));
			}
		}		
		return usfList;
	}	
  
  public static List<USubfield> returnMandatorySubfields(){    
    return mandatorySubfields;
  }
  
  public static List<UField> returnElementsFromProcesstype(){  	
    UFormat uf = new UFormat();     
    UField owner;
    String fieldName;    
    for(int i=0;i<processType.getInitialSubfields().size();i++){
      USubfield usf = processType.getInitialSubfields().get(i);      
      try{     
      	fieldName = usf.getOwner().getName();
      if(uf.getField(fieldName)==null){
        owner = usf.getOwner().shallowCopy();       
        uf.add(owner);
      }else owner = uf.getField(fieldName);
      usf.setMandatory(processType.getMandatorySubfields().contains(usf));
      owner.add(usf);
      }catch(Exception e){
      	 
      }
    }
    return uf.getFields();
  }
	
  public static boolean isMandatorySubfield(String name){   
    for(int i=0;i<mandatorySubfields.size();i++){
      USubfield usf = mandatorySubfields.get(i);      
      if(usf.getName()==name.charAt(3) && usf.getOwner().getName().equals(name.substring(0,3)))          
        return true;
    }    
    return false;    
  }
  
  // da li polje sadrzi potpolja koja su u skupu obaveznih po tipu obrade
  public static boolean isMandatoryFiled(String name){
    for(int i=0;i<mandatorySubfields.size();i++){
      USubfield usf = mandatorySubfields.get(i);      
      if(usf.getOwner().getName().equals(name))          
        return true;
    }    
    return false;    
  }  
  
	public static boolean isSubfieldCoded(String ownerName, char sfName){
		return format.getField(ownerName).getSubfield(sfName).getCoder()!=null;
	}
  
  public static Log getLog(){
    return log;
  }
  
  
	
	
	
}
