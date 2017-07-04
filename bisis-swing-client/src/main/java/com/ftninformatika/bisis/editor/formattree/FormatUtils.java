package com.ftninformatika.bisis.editor.formattree;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.recordtree.CurrRecord;
import com.ftninformatika.bisis.format.PubTypes;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.librarian.Librarian;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class FormatUtils {
  
  //vraca ime prvog polja ciji naziv pocinje sa str
  public static String returnFirstFieldName(String str){
    UField uf;
    for(int i=0;i<CurrFormat.format.getFields().size();i++){
      uf = CurrFormat.format.getField(i);
      if(uf.getName().startsWith(str))  return uf.getName();
    }
    return null;    
  }
  
  //za ulogovanog bibliotekera vraca njegov tip obrade 
  //za prosledjeni tip publikacije, ako bibliotekar nema 
  //definisan tip obrade za taj tip publikacije
  //vraca default tip obrade za bibliotekara
  
  /*public static ProcessType returnProcessTypeForPubtype(int pubType){
    for(ProcessType pt: BisisApp.getLibrarian().getContext().getProcessTypes()){
      if(pt.getPubType().getPubType()==pubType)
        return pt;
    }
    return null;
   // return BisisApp.getLibrarian().getContext().getDefaultProcessType();    
  }*/
  
  /*
   * proverava da li prosledjeni tip publikacije 
   * sadrzi prosledjeno potpolje
   */
  public static boolean pubTypeContainsSubfield(int pubType, USubfield usf){
  	return PubTypes.getPubType(pubType)
  		.getSubfield(usf.getOwner().getName()+usf.getName())==null;
  }
  
  /*
   * vraca string u obliku ime polja+ime potpolja+"-"+opis
   */
  public static String returnSubfieldSpec(USubfield usf){
  	return usf.getOwner().getName()
  				+usf.getName()+"-"
  				+usf.getDescription();
  }
  
  public static boolean isPubTypeDefined(Librarian lib, int pubType){     	
  	//for(ProcessType pt : lib.getContext().getProcessTypes())
  	//	if(pt.getPubType().getPubType()==pubType)
  	//		return true;
  	return false;  	
  }
  
  
  /*
   * grupa mogucih tipova obrade
   * zavisi od toga da li se obradjuje
   * postojeci tip publikacije
   * ili je otvoren prazan zapis  
   */
  /*public static List<ProcessType> getProcessTypeGroup(Librarian lib){
  	List<ProcessType> retList = new ArrayList<ProcessType>();
  	if(CurrRecord.update){
  		//vracamo smao tipove obrade koji pripadaju tipu publikacije
  		//koji odgovara zapisu koji se obradjuje
  		int pubType = CurrRecord.record.getPubType();
  		for(ProcessType pt:lib.getContext().getProcessTypes())
  			if(pt.getPubType().getPubType()==pubType)
  				retList.add(pt);
  		return retList;
  	}else
  		return lib.getContext().getProcessTypes();  	
  }
  
  public static void removeElementsFromProcessType(ProcessType pt){
  	for(UField uf:CurrFormat.format.getFields()){
  		Iterator<USubfield> it = uf.getSubfields().iterator();
  		while(it.hasNext()){
  			USubfield usf = it.next();
  			if(pt.containsSubfield(usf.getOwner().getName()+usf.getName()))
  				it.remove();
  		}
  	}
  	CurrFormat.format.pack();
  }*/

}
