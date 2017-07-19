package com.ftninformatika.bisis.editor.recordtree;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.records.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RecordUtils { //TODO-hardcoded all over the place
  
  public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  
  
  // sortira polja ali ne i potpolja
  public static void sortFields(){
   /* for (int i = 1; i < CurrRecord.record.getFields().size(); i++) {
          for (int j = 0; j < CurrRecord.record.getFields().size() - i; j++) {
            Field f1 = (Field)CurrRecord.record.getFields().get(j);
            Field f2 = (Field)CurrRecord.record.getFields().get(j+1);
            if (f1.getName().compareTo(f2.getName()) > 0) {
              CurrRecord.record.getFields().set(j, f2);
              CurrRecord.record.getFields().set(j+1, f1);
            }
          }
        }   */
  }
  
  public static Record sortFields(Record record){
   for (int i = 1; i < record.getFields().size(); i++) {
         for (int j = 0; j < record.getFields().size() - i; j++) {
           Field f1 = (Field)record.getFields().get(j);
           Field f2 = (Field)record.getFields().get(j+1);
           if (f1.getName().compareTo(f2.getName()) > 0) {
            record.getFields().set(j, f2);
            record.getFields().set(j+1, f1);
           }
         }
    }
   return record;
 }

 public static int getInvNumsCountFromRecordCollection(List<Record> records){
      int retVal = 0;

      for (Record r: records){
          if(r.getPrimerci() != null && r.getPrimerci().size() > 0)
              retVal += r.getPrimerci().size();

          if(r.getGodine() != null && r.getGodine().size() > 0) {
              retVal += r.getGodine().size();
              for(Godina g: r.getGodine())
                  if(g.getSveske() != null && g.getSveskeCount() > 0)
                      retVal += g.getSveskeCount();
          }

      }

      return retVal;
 }
  
  public static void addUFieldWithSubfields(UField uf){
   /* Field f = new Field(uf.getName());
    CurrRecord.record.add(f);
    if(uf.getInd1()!=null && uf.getInd1().getDefaultValue()!=null)
      f.setInd1(uf.getInd1().getDefaultValue().charAt(0));
    if(uf.getInd2()!=null && uf.getInd2().getDefaultValue()!=null)
      f.setInd2(uf.getInd2().getDefaultValue().charAt(0));
    for(int i=0;i<uf.getSubfieldCount();i++){
      USubfield usf = (USubfield)uf.getSubfields().get(i);
      Subfield sf = new Subfield(usf.getName());
      if(usf.getDefaultValue()!=null)
        sf.setContent(usf.getDefaultValue());
      f.add(sf);
    } 
    sortFields();*/
  } 
  

  //ovde ne treba praviti nikakve kontrole, ali je pitanje da li
  //dodati u sve pojave polja u zapisu
  //zahtev nekih biblioteka: da se doda u selektovano polje
  
  
  public static void addUSubfield(USubfield usf){
    /*Subfield sf = new Subfield(usf.getName());
    if(usf.getDefaultValue()!=null)
      sf.setContent(usf.getDefaultValue());
    List<Field> fields = CurrRecord.record.getFields(usf.getOwner().getName());
    if (fields.contains(CurrRecord.selectedField))
    	CurrRecord.selectedField.add(sf);
    else{
    for(int i=0;i<fields.size();i++){
      fields.get(i).add(sf);
    }
    }    */
  }
  
  public static void addUSubfields(List<USubfield> usfs){
    for(int i=0;i<usfs.size();i++){
      addUSubfield(usfs.get(i));
    }
  }
  
//vraca broj polja sa imenom name
  public static int getFieldCount(String name){
   /* if(CurrRecord.record.getFields(name)!=null)
      return CurrRecord.record.getFields(name).size();*/
    return 0;
  }
  
//vraca broj potplja u polju
  public static int getSubfieldCount(String name){
    /*if(CurrRecord.record.getSubfields(name)!=null){
      return CurrRecord.record.getSubfields(name).size();
    }*/
    return 0;
  } 
  
  //vraca ime prvog polja ciji naziv pocinje sa str
  public static String returnFirstFieldName(String str){
    Field f;
   /* for(int i=0;i<CurrRecord.record.getFieldCount();i++){
      f = CurrRecord.record.getField(i);
      if(f.getName().startsWith(str))  return f.getName();
    }*/
    return null;    
  }
  
  public static int getNumberOfIndicators(Field f){
    int i=0;
    if (f.getInd1()!=' ') i++;
    if (f.getInd2()!=' ') i++;
    return i;
  }
  
  public static void addElementsFromProcessType(){
   /* List<UField> ufL = CurrFormat.returnElementsFromProcesstype();
    for(int i=0;i<ufL.size();i++){
      UField uf = ufL.get(i); 
      if(CurrRecord.record.getField(uf.getName())==null){
        Field f = new Field(uf.getName());
        if(uf.getInd1()!=null){
          if(uf.getInd1().getDefaultValue()!=null)
            f.setInd1(uf.getInd1().getDefaultValue().charAt(0));
          else f.setInd1(' ');
        }         
        if(uf.getInd2()!=null){
          if(uf.getInd2().getDefaultValue()!=null)            
              f.setInd2(uf.getInd2().getDefaultValue().charAt(0));          
          else f.setInd2(' ');
        }
        f.sort();
        CurrRecord.record.add(f);        
      }
    }
    for(int i=0;i<ufL.size();i++){
      UField uf = ufL.get(i); 
      List<Field> fList = CurrRecord.record.getFields(uf.getName());      
      for(int j=0;j<uf.getSubfieldCount();j++){
        USubfield usf = (USubfield)uf.getSubfields().get(j);        
        Subfield sf = new Subfield(usf.getName());         
        for(int k=0;k<fList.size();k++){
          if(fList.get(k).getSubfield(usf.getName())==null)
              fList.get(k).add(sf);
        }
        if(usf.getDefaultValue()!=null){
          sf.setContent(usf.getDefaultValue());
        }
      }     
      }
    //default vrednosti za indikatore
    for(UIndicator ui : CurrFormat.getProcessType().getIndicators())
    	if(CurrRecord.record.getField(ui.getOwner().getName())!=null)
    		if(ui.getIndex()==1)
    			CurrRecord.record.getField(ui.getOwner().getName())
    				.setInd1(ui.getDefaultValue().charAt(0));
    		else
    			CurrRecord.record.getField(ui.getOwner().getName())
   				.setInd2(ui.getDefaultValue().charAt(0));*/
    } 
  
  public static void replaceSubfieldWithPrevious(Field f,Subfield sf){
    int index = getIndexOfSubfield(f,sf);
    if(index==0) return;    
    Subfield previous = f.getSubfield(index-1);
    f.remove(sf);
    f.remove(previous);
    f.getSubfields().add(index-1,sf);
    f.getSubfields().add(index,previous);   
  }
  
  public static void replaceSubfieldWithNext(Field f, Subfield sf){
    int index = getIndexOfSubfield(f,sf);
    if(index==f.getSubfieldCount()-1) return;   
    Subfield next = f.getSubfield(index+1);
    f.remove(sf);   
    f.getSubfields().add(index+1,sf);
    f.remove(next);
    f.getSubfields().add(index,next);    
  }
  
  public static void replaceFieldWithPrevious(Field f){
  	/*int index = CurrRecord.record.getFields().indexOf(f);
  	if(index==0) return;
  	Field previous = CurrRecord.record.getField(index-1);
  	CurrRecord.record.remove(f);
  	CurrRecord.record.remove(previous);
  	CurrRecord.record.getFields().add(index-1,f);
  	CurrRecord.record.getFields().add(index,previous); 	*/
  }
  
  public static void replaceFieldWithNext(Field f){
  	/*int index = CurrRecord.record.getFields().indexOf(f);
  	if(index==CurrRecord.record.getFieldCount()-1) return;
  	Field next = CurrRecord.record.getField(index+1);
  	CurrRecord.record.remove(f);  	
  	CurrRecord.record.getFields().add(index+1,f);
  	CurrRecord.record.remove(next);
  	CurrRecord.record.getFields().add(index,next);
  	*/
  }
  public static int getIndexOfField(Field f){
    /*for(int i=0;i<CurrRecord.record.getFieldCount();i++){
      if(CurrRecord.record.getField(i).equals(f)) return i;
    }*/
    return -1;
  }
  
  public static int getIndexOfSubfield(Field f,Subfield sf){
    for(int i=0;i<f.getSubfieldCount();i++){
      if(f.getSubfield(i).equals(sf)) return i;
    }   
    return -1;
  } 
  
  // ovo je budz,treba naci bolje resenje da se iz stabla
  // zapisa dodje do polja kome pripada selektovano potpolje 
  static Field findParent(Subfield sf){
    Field f;
   /* for (int i=0;i<CurrRecord.record.getFieldCount();i++){
      f = (Field)CurrRecord.record.getField(i);
      if(isParent(f,sf)) return f;
    }
    for (int i=0;i<CurrRecord.record.getFieldCount();i++){
      f = (Field)CurrRecord.record.getField(i);
      for(int j=0;j<f.getSubfieldCount();j++){
        if(f.getSubfield(j).getSecField()!=null){
          if(isParent(f.getSubfield(j).getSecField(),sf))
            return f.getSubfield(j).getSecField();        
        if(isParent(f,sf)) return f;
        }
      }
    }   */
    return null;
  }
  
  static boolean isParent(Field f, Subfield sf){    
    for(int j=0;j<f.getSubfieldCount();j++){        
        if(sf.equals(f.getSubfield(j)))
          return true;
    }   
    return false;
  }
  
  static boolean isRN(Subfield sf){
  	Field parent = findParent(sf);
  	return parent.getName().equals("001") && sf.getName()=='e';
  	
  }
  
  // vraca naziv potpolja u obliku 200a
  static String returnSubfieldString(Subfield sf){  	
    return findParent(sf).getName()+sf.getName();
  }
  
  // da li selektovano polje, koje je jedno od polja za povezivanje vec
  // sadrzi sekundarno polje uf
  static boolean secondaryFieldAlreadyExist(UField uf){
   /* for(int i=0;i<CurrRecord.selectedField.getSubfieldCount();i++){
      if(CurrRecord.selectedField.getSubfield(i).getSecField()!=null &&
          CurrRecord.selectedField.getSubfield(i).getSecField().getName().equals(uf.getName()))
        return true;      
    }*/
    return false;    
  }
  
  static String canBeDeleted(String name){    
    /*if(name.length()==3){
      UField uf = CurrFormat.format.getField(name); 
      if(CurrFormat.isMandatoryFiled(name) && RecordUtils.getFieldCount(name)<2){
        return "Polje "+uf.getName()+"-"+uf.getDescription()+"\n"
        +"je obavezno polje i ne mo\u017ee biti obrisano!";       
      }
    }else if(name.length()==4){
      USubfield usf = CurrFormat.format.getSubfield(name);
      if(CurrFormat.isMandatorySubfield(name) && RecordUtils.getSubfieldCount(name)<2){       
        return "Potpolje "+usf.getName()+"-"+usf.getDescription()+"\n"
        +"je obavezno i ne mo\u017ee biti obrisano!";     
      }
    }*/
    return "";
  }
  
  public static Primerak copyPrimerakBezInv(Primerak p){
    return  new Primerak(0, "", p.getDatumRacuna(),
        p.getBrojRacuna(), p.getDobavljac(), p.getCena(), p.getFinansijer(),
        p.getUsmeravanje(), p.getDatumInventarisanja(), p.getSigFormat(),
        p.getSigPodlokacija(), p.getSigIntOznaka(), p.getSigDublet(),
        p.getSigNumerusCurens(),p.getSigUDK(), p.getPovez(),
        p.getNacinNabavke(),p.getOdeljenje(), p.getStatus(), new Date(p.getDatumStatusa()),
        p.getDostupnost(), p.getNapomene(), p.getStanje(), p.getInventator());
  } 
 
  public static Field copyField(Field f){
  	Field copy = new Field(f.getName(),f.getInd1(),f.getInd2());
  	for(int i=0;i<f.getSubfieldCount();i++){
  		copy.add(copySubfield(f.getSubfield(i)));  		
  	}  	
  	return copy;
  }
  
  /*
   * kopira polje sa indikatorima
   */
  public static Field shallowCopyFiled(Field f){
  	Field newFiled = new Field(f.getName());
  	newFiled.setInd1(f.getInd1());
  	newFiled.setInd2(f.getInd2());  	
  	return newFiled;
  }
  
  public static Subfield copySubfield(Subfield sf){
  	Subfield copy = new Subfield(sf.getName());
  	copy.setContent(sf.getContent());
  	//TODO copy secondary fields
  	return copy;
  	
  }
  
  public static int returnHoldingsNumber(){
	  //1 - ako treba otvoriti formu sa primercima
	  //2 - ako treba otvoriti formu sa sveskama
	/*  if(CurrRecord.record.getPrimerci()!=null
			  && CurrRecord.record.getPrimerci().size()>0)
		  return 1;
	  else if(CurrRecord.record.getGodine()!=null
			  && CurrRecord.record.getGodine().size()>0)
		  return 2;
	  else{
	  	// za neknjiznu gradju otvaramo formu sa primercima
	  	// za sada
	  	if(CurrFormat.getPubType()==4 || CurrFormat.getPubType()==5) return 1;
		  return CurrFormat.getPubType();
	  }
		  */
	return -1;
  }
  
  public static Primerak getPrimerakPoInv(String invBroj){
	 /* for(Primerak p:CurrRecord.record.getPrimerci())
		if(p.getInvBroj().equals(invBroj)) return p;*/
	  return null;
	  
  }
  
  public static Godina getGodinaPoInv(String invBroj){
	  /*for(Godina g:CurrRecord.record.getGodine())
		  if(g.getInvBroj().equals(invBroj))
			  return g;*/
	  return null;
  }
  
  public static int getIndexForPrimerak(Primerak p){
	  /*return CurrRecord.record.getPrimerci().indexOf(p);*/
	  return  0;
  }
  
  public static int getIndexForGodina(Godina g){
	  /*return CurrRecord.record.getGodine().indexOf(g);*/
	  return 0;
  }
  
  public static void unlockRecord(Record rec){
    /*try{
      BisisApp.getRecordManager().unlock(rec.getRecordID());
    }catch(NullPointerException e){
      
    }*/
  }
  
  public static void removeSubfieldsFromProcesstype(/*ProcessType pt*/){
  /*	for(int i=0;i<CurrRecord.record.getFieldCount();i++){
  		Field f = CurrRecord.record.getField(i);
  		Iterator<Subfield> it = f.getSubfields().iterator();
  		while(it.hasNext()){
  			Subfield sf = it.next();
  			if(!sf.getContent().equals(""))
  				if(pt.containsSubfield(f.getName()+sf.getName()))
  					it.remove();  
  		}
  			
  	}  	
  	CurrRecord.record.pack();  	*/
  }
  
  public static void addOnRightPlace(Field f, Subfield sf){
  	if(f.getSubfieldCount()==0)
  		f.add(sf);
  	else{  		
  		int i = 0;
  		while(i<f.getSubfieldCount()){  			
  			if(f.getSubfield(i).getName()>=sf.getName()){  				
  				f.getSubfields().add(i, sf);
  				break;				
  			}
  			i++;
  		}
  		f.getSubfields().add(i, sf);
  	}  	
  }
  
  /*
   * brise potpolje i vraca niz objekata na osnovu
   * koga ce se kreirati putanja u stablu koja ce biti 
   * selektovana nakon brisanja
   */
  
 
  
  public static Object[] deleteSubfield(Object[] path){
  	Obrada.editorFrame.recordUpdated();
  	if(path.length == 3){
  		Field f = (Field)path[1];
  		Subfield sf = (Subfield)path[2];
	  	Object[] newPath;
	  	if(f.getSubfieldCount()==1){
	  		newPath = new Object[2];
	  		//newPath[0] = CurrRecord.record;
	  		newPath[1] = f;
	  	}else{
	  		newPath = new Object[3];
	  		//newPath[0] = CurrRecord.record;
	  		newPath[1] = f;
	  		int index = f.getSubfields().indexOf(sf);
	  		if(index==0)
	  			newPath[2] = f.getSubfield(1); 	
	  		else
	  			newPath[2] = f.getSubfield(index-1);
	  	}
	  	f.remove(sf);  	
	  	return newPath;
  	}else if(path.length==5){ // brise se potpolje iz sekundarnog polja
  			Field f = (Field)path[3];
  			Subfield sf = (Subfield)path[4];
  			Object[] newPath;
 	  	if(f.getSubfieldCount()==1){
 	  		newPath = new Object[4]; 	  		
 	  		//newPath[0] = CurrRecord.record;
 	  		newPath[1] = path[1];
 	  		newPath[2] = path[2];
 	  		newPath[3] = f;
 	  	}else{
 	  		newPath = new Object[5];
 	  		//newPath[0] = CurrRecord.record;
 	  		newPath[1] = path[1];
 	  		newPath[2] = path[2];
 	  		newPath[3] = f;
 	  		int index = f.getSubfields().indexOf(sf);
 	  		if(index==0)
 	  			newPath[4] = f.getSubfield(1); 	
 	  		else
 	  			newPath[4] = f.getSubfield(index-1);
 	  	}
 	  	f.remove(sf);  	
 	  	return newPath; 		
  	}
  	return null;
  }
  
  public static Object[] deleteField(Object[] path){
    Obrada.editorFrame.recordUpdated();
    /*if(path.length==2){
      Object[] newPath = new Object[2];
      Field f = (Field) path[1];      
      int index = CurrRecord.record.getFields().indexOf(f);
      CurrRecord.record.remove(f);
      newPath[0] = CurrRecord.record;
      if(CurrRecord.record.getFieldCount()>0){
      	if(index==0)
      		newPath[1] = CurrRecord.record.getField(1);
      	else
      		newPath[1] = CurrRecord.record.getField(index-1);
      }     
      return newPath;
    }else{
      // brise se sekundarno polje, odnosne celo potpolje 1 koje ga sadrzi
      Field f4XX = (Field) path[1];
      Subfield sf1 = (Subfield) path[2];
      f4XX.remove(sf1);
      Object[] newPath = new Object[2];
      newPath[0]= CurrRecord.record;
      newPath[1] = f4XX;
      return newPath;
    }   */
    return null;
  }  
  
  
 
  
  /*
   * od polja formata pravi polje zapisa tako sto doda sva potpolja
   */
  
  public static Field getRecordField(UField uf){
  	Field f = new Field(uf.getName());
  	//dodavanje indikatora
  	
    /*UIndicator ind1 = uf.getInd1();
    UIndicator ind2 = uf.getInd2();
    if(ind1!=null) 
      if(ind1.getDefaultValue()!=null) f.setInd1(ind1.getDefaultValue().charAt(0));
      else f.setInd1(' ');
    if(ind2!=null) 
      if(ind2.getDefaultValue()!=null) f.setInd2(ind2.getDefaultValue().charAt(0));
      else f.setInd2(' ');*/
    
    //dodavanje potpolja
    for(USubfield usf:uf.getSubfields()){
    	Subfield sf = new Subfield(usf.getName());
    	if(usf.getDefaultValue()!=null) sf.setContent(usf.getDefaultValue());
    	f.add(sf);
    }
  	
  	return f;
  }
  
  
  public static Field getRecordFieldWithoutSubfield(UField uf){
  	Field f = new Field(uf.getName());
  	//dodavanje indikatora
  	
    /*UIndicator ind1 = uf.getInd1();
    UIndicator ind2 = uf.getInd2();
    if(ind1!=null) 
      if(ind1.getDefaultValue()!=null) f.setInd1(ind1.getDefaultValue().charAt(0));
      else f.setInd1(' ');
    if(ind2!=null) 
      if(ind2.getDefaultValue()!=null) f.setInd2(ind2.getDefaultValue().charAt(0));
      else f.setInd2(' ');    
   */
   
  	
  	return f;
  }
  
  /*
   * od polja formata pravi polje zapisa sa indikatorima bez potpolja  */
  
  public static Field getRecordFieldWithoutSubfields(UField uf){
  	Field f = new Field(uf.getName());
  	//dodavanje indikatora
  	
    /*UIndicator ind1 = uf.getInd1();
    UIndicator ind2 = uf.getInd2();
    if(ind1!=null) 
      if(ind1.getDefaultValue()!=null) f.setInd1(ind1.getDefaultValue().charAt(0));
      else f.setInd1(' ');
    if(ind2!=null) 
      if(ind2.getDefaultValue()!=null) f.setInd2(ind2.getDefaultValue().charAt(0));
      else f.setInd2(' ');
    */
   
  	
  	return f;
  }
  
  
  /*
   * vraca ispis koji ce se prikazati korisniku
   * uz pitanje da li ste sigurni da zelite da odrisete zapis  
   */
  public static String getDeleteRecordReport(Record rec){
  	StringBuffer buff = new StringBuffer();
  	buff.append("<html><b>ID: </b> "+rec.getRecordID()+"\n");
  	buff.append("<html><b>RN: </b>"+rec.getRN()+"\n");
  	buff.append("<html><b>TI:</b> "+rec.getSubfieldContent("200a")+"\n");
  	buff.append("<html><b>PY</b>: "+rec.getSubfieldContent("100c")+"\n");  	
  	buff.append("Inventarni brojevi:\n");
  	for(Primerak p:rec.getPrimerci())
  		buff.append(p.getInvBroj()+"\n");
  	for(Godina g:rec.getGodine())
  		buff.append(g.getInvBroj());  	
  	return buff.toString();  	
  }
  
  public static boolean invBrojSveskePostojiUZapisu(String invBroj){
  	/*for(Godina g:CurrRecord.record.getGodine())
  		for(Sveska s:g.getSveske())
  			if(s.getInvBroj().equals(invBroj)) return true;*/
  	return false;
  	}
  
  public static Record getAnalitickaObrada(Record masterRecord){
  	Record rec = new Record();  	 
  	rec.setMR(masterRecord.getRN());
  	rec.setPubType(3);
  	return rec;

  	
  }
  
 }
