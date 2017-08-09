package com.ftninformatika.bisis.editor.recordtree;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.editor.formattree.CurrFormat;
import com.ftninformatika.bisis.editor.inventar.InventarValidation;
import com.ftninformatika.bisis.format.*;
import com.ftninformatika.bisis.records.*;
import com.ftninformatika.bisis.records.serializers.PrimerakSerializer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CurrRecord {  
  
  public static Record record = new Record();
  public static Field selectedField = null;
  
  // false ako se pravi novi zapis/true ako se modifikuje postojeci
  public static boolean update = false;  
  public static boolean savedOnce = false;  
  private static boolean valid = false;  
  private static Log log = LogFactory.getLog(CurrRecord.class.getName());
  
  public static void recordInitialize(Record rec){  
    int pubType = 0;
    if(rec != null){
      if(update){        
    	  record = rec;    	  
    	  pubType = record.getPubType();    	  
      }else{    	  
      	//pravi se novi zapis na osnovu postojeceg
        pubType = CurrFormat.getPubType();
        if(rec.getField("000")!=null){
        	PrimerakSerializer.polje000UMetapodatke(rec);
        }
        record = rec.copyWithoutHoldings();
        if(record.getSubfield("992b")!=null){
        	System.out.println("992b");
        	record.getSubfield("992b").setContent("");
        }
      }
      RecordUtils.addElementsFromProcessType();
    }else{      
      pubType = CurrFormat.getPubType();
      record = new Record();
      record.setPubType(pubType);
      RecordUtils.addElementsFromProcessType();
      RecordUtils.sortFields();      
    }   
    RecordUtils.sortFields();    
    if(pubType == 0)
      log.warn("Publication type undefined, recordId="+record.getRecordID());    
  }
  
  public static boolean saveCurrentRecord() throws UValidatorException {
  	boolean ok = false;
    valid = false;
    String message = validateRecord();  
   // String holdingsMessage = validateHoldingsData();
    if(!valid){
      throw new UValidatorException(message);            
    }else{
      if(!update){
        if(!savedOnce && record.getRecordID()==0){
          record.setPubType(CurrFormat.getPubType());   
          if(record.getCreator()==null){
          	record.setCreator(new Author(BisisApp.appConfig.getLibrarian().getUsername(),BisisApp.appConfig.getClientConfig().getLibraryName()));
          }
          record.setModifier(new Author(BisisApp.appConfig.getLibrarian().getUsername(),BisisApp.appConfig.getClientConfig().getLibraryName()));
          //ok = BisisApp.getRecordManager().add(record);
          //BisisApp.getRecordManager().lock(id,BisisApp.appConfig.getLibrarian().getUsername());
            Record r = null;
            try {
               r =  BisisApp.bisisService.createRecord(record).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ok = r != null;
            savedOnce = true;
          log.info("add record, recordId="+r.get_id()+ ", creator: "+record.getCreator().getUsername());
        }else{    	
          //record = BisisApp.getRecordManager().update(record);
            Record r = null;
            try {
                r =  BisisApp.bisisService.updateRecord(record).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
          ok = r != null;
        }
      }else{
        record.setModifier(new Author(BisisApp.appConfig.getLibrarian().getUsername(),BisisApp.appConfig.getClientConfig().getLibraryName()));
        //TODO - kada saljemo sa datumima ne prolazi do backenda
        //record.setCreationDate(null);
        //record.setLastModifiedDate(null);
        Record r = null;
          try {
              r =  BisisApp.bisisService.updateRecord(record).execute().body(); //ovo je update
          } catch (IOException e) {
              e.printStackTrace();
          }
        //record = BisisApp.getRecordManager().update(record);
        ok = r != null;
      } 
    }
    return ok;   
  }
  
  /*
   * kreira string sa podacima o zapisu koji se snima
   */
  public static String saveRecordReport(){
  	StringBuffer buff = new StringBuffer();
  	if(record.getRecordID()!=0){
  		buff.append("ID: "+record.getRecordID()+"\n");
  		buff.append("RN: "+record.getRN()+"\n");
  	}
  	buff.append("TI: "+record.getSubfieldContent("200a"));
  	return buff.toString();
  }
  
  public static void unlockRecord(){
    try{
      BisisApp.recMgr.unlock(record.get_id());
    }catch(NullPointerException e){
      log.fatal(e);
    } catch (IOException e) {
        e.printStackTrace();
    }
  } 
  
  public static Object[] addField(UField uf, boolean withSubfields) throws UValidatorException{
    Obrada.editorFrame.recordUpdated();
    Field f = null;
    String fName = uf.getName();
    List l = new ArrayList(); // za treePath    
    if(selectedField ==null ||
      (selectedField!=null && !CurrFormat.block4.contains(selectedField.getName()))){      
      f  =  record.getField(fName);
      if (f==null){       
      	if(!withSubfields)
        f = RecordUtils.getRecordField(uf);
      	else
      		f = RecordUtils.getRecordFieldWithoutSubfield(uf);
       record.add(f);
      }else{
        if (uf.isRepeatable()){
        	if(!withSubfields)
          f = RecordUtils.getRecordField(uf);
        	else
        		f = RecordUtils.getRecordFieldWithoutSubfield(uf);
          record.add(f);
        }else{
          throw new UValidatorException("Polje "+fName+"-"+uf.getDescription()+" nije ponovljivo");
        }
      }
      l.add(record);
      l.add(f);
    }else
    if(selectedField!=null && CurrFormat.block4.contains(selectedField.getName())){
      if(uf.isSecondary()){
        if(RecordUtils.secondaryFieldAlreadyExist(uf) && !uf.isRepeatable())
          throw new UValidatorException("Polje "+fName+"-"+uf.getDescription()+" nije ponovljivo");        
        Subfield sf = new Subfield('1');
        if(!withSubfields)
         f = RecordUtils.getRecordField(uf);
       	else
       		f = RecordUtils.getRecordFieldWithoutSubfield(uf);
        sf.setSecField(f);
        selectedField.add(sf);
        l.add(record);
        l.add(selectedField);
        l.add(sf);
        l.add(f);
      }else
        throw new UValidatorException("Polje "+fName+"-"+uf.getDescription()+" nije sekundarno");
    }
      
    
  //     dodavanje indikatora
    /*  UIndicator ind1 = uf.getInd1();
      UIndicator ind2 = uf.getInd2();
      if(ind1!=null) 
        if(ind1.getDefaultValue()!=null) f.setInd1(ind1.getDefaultValue().charAt(0));
        else f.setInd1(' ');
      if(ind2!=null) 
        if(ind2.getDefaultValue()!=null) f.setInd2(ind2.getDefaultValue().charAt(0));
        else f.setInd2(' ');*/
      RecordUtils.sortFields(); 
    return l.toArray();     
  } 
  
  /*
   * dodaje polje u zapis
   * polje se dodaje na mesto index
   * vrzi se provera ponovljivosti
   */
  public static void addField(Field field, int index)throws UValidatorException{
  	UField uf = CurrFormat.getFullFormat().getField(field.getName());
  	if(record.getField(field.getName())!=null && !uf.isRepeatable()) 
  		throw new UValidatorException("Polje "+uf.getName()+"-"+uf.getDescription()+" nije ponovljivo");
  	else
  		record.getFields().add(index,field);
  	RecordUtils.sortFields();
  }
  
  
  
  public static Subfield addSubfied(USubfield us, String text)throws UValidatorException{
    Obrada.editorFrame.recordUpdated();
    UField uf= us.getOwner();     
    char sfName = us.getName(); 
    Field f; 
    if(selectedField!=null && selectedField.getName().equals(us.getOwner().getName())){      
      f = selectedField;
      if (f!=null){   
        if(f.getSubfield(sfName)!=null){
          // polje vec sadrzi dato potpolje       
          if(!us.isRepeatable()){
            // potpolje nije ponovljivo         
            throw new UValidatorException("Potpolje "+us.getName()+"-"+us.getDescription()
              +" nije ponovljivo u okviru polja"+"\n"+uf.getName()+"-"+uf.getDescription());         
          }         
      }
      }
      }else if(RecordUtils.getFieldCount(uf.getName())==1){
        f = record.getField(uf.getName());
      }else f=null;
      if(f!=null){      	
        Subfield sf = new Subfield(sfName);
        sf.setContent(text);  
        RecordUtils.addOnRightPlace(f, sf);      
        RecordUtils.sortFields();  
       
        return sf;
      }   
    return null;
  }
   
  /*
   * dodaje potpolje sf u polje f
   * na mesto index
   * vrsi se provera ponovljivosti
   */  
  public static void addSubfiled(Field f, Subfield sf, int index) throws UValidatorException{
  	USubfield usf = CurrFormat.getFullFormat()
  			.getSubfield(f.getName()+sf.getName());
  	UField uf = CurrFormat.getFullFormat().getField(f.getName());
  	if(f.getSubfield(sf.getName())!=null && !usf.isRepeatable())
  		 throw new UValidatorException("Potpolje "+usf.getName()+"-"+usf.getDescription()
           +" nije ponovljivo u okviru polja"+"\n"+uf.getName()+"-"+uf.getDescription());
  	else
  		f.getSubfields().add(index, sf);
  }
  
  public static void changeSubfieldConetent(Subfield sf,String fieldName,String newContent) throws UValidatorException{
  	if(newContent.endsWith("\n")){  		
  		newContent = newContent.substring(0,newContent.length()-1);  		
  	}
    Obrada.editorFrame.recordUpdated();
    USubfield us = CurrFormat.getUSubfield(fieldName,sf.getName());    
    if(!newContent.equals("")){
    	//prazan string se ne validira
	    if (us.getCoder()!=null){
	      if (us.getOwner().getName().equalsIgnoreCase("992")&&(us.getName()=='b')){
	    	  //validira nad podacima iz tabele
	    	  if (!HoldingsDataCoders.isValid992b(newContent))
	    		  throw new UValidatorException
		           ("Kod koji ste izabrali nije definisan za potpolje "+"\n"+us.getName()+"-"+us.getDescription());
	      }else if (us.getOwner().getName().equalsIgnoreCase("992")&&(us.getName()=='f')){
	    	  if (!HoldingsDataCoders.isValidLibrarian(newContent))
	    		  throw new UValidatorException
		          ("Kod koji ste izabrali nije definisan za potpolje "+"\n"+us.getName()+"-"+us.getDescription());
	      }else{
	        if (!newContent.equals("") && us.getCoder().getValue(newContent)==null){        
	           throw new UValidatorException
	           ("Kod koji ste izabrali nije definisan za potpolje "+"\n"+us.getName()+"-"+us.getDescription());
	        }
	      }  
	     }
	    String target = us.getOwner().getName()+us.getName();
	    UValidator v = ValidatorFactory.getValidator(target);
	    if(v!=null && v.isValid(newContent)!=""){
	      throw new UValidatorException(v.isValid(newContent));
	    }    
    }
  } 
  
  public static void changeIndicatroValue(UIndicator ui, String newValue) throws UValidatorException{
  	if(newValue.endsWith("\n")){  		
  		newValue = newValue.substring(0,newValue.length()-1);  		
  	}
  	if(newValue.equals("") || ui.getValue(newValue)==null)
  		throw new UValidatorException
    ("Kod koji ste izabrali nije definisan za izabrani indikator!");
  		
  	
  }
  
  public static String validateRecord(){
    StringBuffer izvestaj = new StringBuffer();
    StringBuffer message = new StringBuffer();   
    String potpoljePref = "Nije uneto obavezno potpolje: ";    
    valid = true;    
    List<USubfield> mandatorySubfields = CurrFormat.returnMandatorySubfields();
    for(int i=0;i<mandatorySubfields.size();i++) {
      USubfield usf = mandatorySubfields.get(i);
      String usfFullName = usf.getOwner().getName()+usf.getName();      
      if(record.getSubfield(usfFullName)==null || 
      		(!CurrFormat.block4.contains(usfFullName.substring(0,3)) // nije polje iz bloka 4 i treba da sadrzi tekst
      				&& record.getSubfieldContent(usfFullName).equals("")) 
      				
      		||
      		(CurrFormat.block4.contains(usfFullName.substring(0,3)) // jeste polje iz bloka 4 i proveravamo da li je uneto neko skundarno polje
      				&& record.getSubfield(usfFullName).getSecField()==null)
      		){
        valid = false;
        message.append(potpoljePref+usfFullName+"-"+usf.getDescription()+"\n");
      }
    }    
    if (valid){
      izvestaj.append("Zapis je validan!");
    }else{
      izvestaj.append("Zapis nije validan!");
      izvestaj.append("\n");
      izvestaj.append(message);
    }    
    return izvestaj.toString();
  }
  
  public static String validateHoldingsData(){
  	StringBuffer retVal = new StringBuffer();
  	for(Primerak p:record.getPrimerci())
  			retVal.append(InventarValidation.validateInvBrojUnique(p.getInvBroj()));
  	for(Godina g:record.getGodine())  		
 			retVal.append(InventarValidation.validateInvBrojUnique(g.getInvBroj()));  	
  	valid = retVal.toString().equals("");  		
  	return retVal.toString();
  }
  
  
  
  public static String getTitle(){    
    if(record.getSubfield("200a")!=null)
      return record.getSubfieldContent("200a");
    else return "";
  }
  
  // vraca vrednost iz 675b
  public static String getSigUdk(){
  	for(Field f:record.getFields("675")){
  		if(f.getSubfield('b')!=null && !f.getSubfield('b').getContent().equals(""))
  			return f.getSubfield('b').getContent();
  	}  	
  	return "";  	
  }
  
  public static int brojPrimeraka(){
    return record.getPrimerci().size();
  }
  
  public static Primerak getPrimerak(int i){
    return record.getPrimerci().get(i);
  }
  
  public static void addPrimerak(Primerak p){   
    record.getPrimerci().add(p);
  } 
  
  public static Record getRecord(){
    return record;
  }
  
  public static void removePrimerak(int i){
    if(record.getPrimerci().size()>0)
      record.getPrimerci().remove(i);    
  }
  
  public static int brojGodina(){
    return record.getGodine().size();
  }
  
  public static Godina getGodina(int i){
    return record.getGodine().get(i);
  }
  
  public static void addGodina(Godina g){
    record.getGodine().add(g);
  }
  
  public static void removeGodina(int i){
    if(record.getGodine().size()>0)
      record.getGodine().remove(i);
  }  
}

