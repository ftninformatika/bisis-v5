package com.ftninformatika.bisis.librarian;

import com.ftninformatika.bisis.format.PubTypes;
import com.ftninformatika.bisis.format.UFormat;
import com.ftninformatika.bisis.format.UIndicator;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.librarian.db.ProcessTypeDB;
import com.ftninformatika.bisis.librarian.db.USubfieldDB;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public class ProcessType implements Serializable {

  @Id private String _id;
  private Integer id;
  private String name;
  private UFormat pubType;
  private String libName;
  private List<USubfield> initialSubfields;
  private List<USubfield> mandatorySubfields;
  // koristice se za default vrednosti indikatora
  private List<UIndicator> indicators;

  public void setUFormat(Integer pubType){
      this.pubType = PubTypes.getPubType(pubType);
  }

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
    this.libName = libName;
  }

  public ProcessType(ProcessTypeDB processTypeDB) {
      this._id = processTypeDB.get_id();
      this.name = processTypeDB.getName();
      this.libName = processTypeDB.getLibName();
      this.pubType = PubTypes.getPubType(processTypeDB.getPubType());
      this.initialSubfields = new ArrayList<USubfield>();
      this.mandatorySubfields = new ArrayList<USubfield>();
      this.indicators = new ArrayList<UIndicator>();
      if (processTypeDB.getInitialFields() != null){
          for (USubfieldDB uSubfieldDB : processTypeDB.getInitialFields()){
              USubfield sub = this.pubType.getSubfield(uSubfieldDB.getFieldName()+uSubfieldDB.getSubfieldName()).shallowCopy();
              sub.setDefaultValue(uSubfieldDB.getDefaultValue());
              initialSubfields.add(sub);
          }
      }
      if (processTypeDB.getMandatoryFields() != null) {
          this.mandatorySubfields = processTypeDB.getMandatoryFields().stream().
                  map(i -> this.pubType.getSubfield(i.getFieldName()+i.getSubfieldName()).shallowCopy()).
                  collect(Collectors.toList());

      }
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
      	retVal.append(" defaultValue=\"");
      	retVal.append(s.getDefaultValue()==null ? "":s.getDefaultValue());
      	retVal.append("\" ");
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
    //"return toXML();" //TODO-hardcoded
      return this.libName + " process type";
  }

	
}
