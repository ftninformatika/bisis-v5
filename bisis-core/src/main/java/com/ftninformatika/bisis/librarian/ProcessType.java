package com.ftninformatika.bisis.librarian;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ftninformatika.bisis.format.PubTypes;
import com.ftninformatika.bisis.format.UFormat;
import com.ftninformatika.bisis.format.UIndicator;
import com.ftninformatika.bisis.format.USubfield;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProcessType implements Serializable {

  private int id;
  private String name;
  private UFormat pubType;
  private List<USubfield> initialSubfields;
  private List<USubfield> mandatorySubfields;
  // koristice se za default vrednosti indikatora
  private List<UIndicator> indicators;

  public static ProcessType getProcessType(String xml) {
    return ProcessTypeBuilder.getProcessType(xml);
  }
  
  public ProcessType() {
    initialSubfields = new ArrayList<USubfield>();
    mandatorySubfields = new ArrayList<USubfield>();
    indicators = new ArrayList<UIndicator>();
  }

  public ProcessType(String name, UFormat pubType,
      List<USubfield> initialSubfields, List<USubfield> mandatorySubfields, List<UIndicator> indicators) {
    this.name = name;
    this.pubType = pubType;
    this.initialSubfields = initialSubfields;
    this.mandatorySubfields = mandatorySubfields;
    this.indicators = indicators;
    
  }
  
  public ProcessType(String name, int pubType,
      List<USubfield> initialSubfields, List<USubfield> mandatorySubfields, List<UIndicator> indicators) {
    this.name = name;
    this.pubType = PubTypes.getPubType(pubType);
    this.initialSubfields = initialSubfields;
    this.mandatorySubfields = mandatorySubfields;
    this.indicators = indicators;
  }

  public List<USubfield> getInitialSubfields() {
    return initialSubfields;
  }

  public void setInitialSubfields(List<USubfield> initialSubfields) {
    this.initialSubfields = initialSubfields;
  }

  public List<USubfield> getMandatorySubfields() {
    return mandatorySubfields;
  }

  public void setMandatorySubfields(List<USubfield> mandatorySubfields) {
    this.mandatorySubfields = mandatorySubfields;
  }
  
  public void setIndicators(List<UIndicator> indicators){
  	this.indicators = indicators;
  }
  
  public List<UIndicator> getIndicators(){
  	return indicators;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UFormat getPubType() {
    return pubType;
  }

  public void setPubType(UFormat pubType) {
    this.pubType = pubType;
  }
  
  public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean containsSubfield(USubfield usf){
		for(USubfield us:initialSubfields)
			if(us.equals(usf)) return true;
		return false;
	}
	
	public boolean containsSubfield(String subfieldName){		
		for(USubfield us:initialSubfields)
			if(subfieldName.equals(us.getOwner().getName()+us.getName())) 
				return true;
		return false;
	}
	
  public String toXML() {
    StringBuffer retVal = new StringBuffer();
    retVal.append("<?xml version=\"1.0\"?>\n");
    retVal.append("<process-type name=\"");
    retVal.append(name);
    retVal.append("\" pubType=\"");
    retVal.append(pubType.getPubType());    
    retVal.append("\">\n");
    for (USubfield s : initialSubfields) {
      retVal.append(" <initial-subfield name=\"");
      retVal.append(s.getOwner().getName()+s.getName());      
      retVal.append("\"");
      if(s.getDefaultValue()!=null){
      	retVal.append(" defaultValue=\"");
      	retVal.append(s.getDefaultValue());
      	retVal.append("\" ");
      }      	
      retVal.append("/>\n");
    }
    for (USubfield s : mandatorySubfields) {
      retVal.append("  <mandatory-subfield name=\"");
      retVal.append(s.getOwner().getName()+s.getName());
      retVal.append("\"/>\n");
    }
    for (UIndicator ui : indicators){
    	retVal.append("  <indicator field=\"");
    	retVal.append(ui.getOwner().getName());
    	retVal.append("\" ");
    	retVal.append("index=\"");
    	retVal.append(ui.getIndex());
    	retVal.append("\" ");
    	retVal.append("defaultValue=\"");
    	retVal.append(ui.getDefaultValue());
     retVal.append("\"/>\n");   	
    	
    }
    retVal.append("</process-type>\n");
    return retVal.toString();
  }
  
  public String toString() {
    return toXML();
  }

	
}
