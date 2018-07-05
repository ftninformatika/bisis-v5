package com.ftninformatika.bisis.librarian;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.ftninformatika.bisis.format.PubTypes;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.UIndicator;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.librarian.dto.ProcessTypeDTO;
import com.ftninformatika.bisis.librarian.dto.USubfieldDTO;
import com.ftninformatika.utils.xml.XMLUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.stream.Collectors;


public class ProcessTypeBuilder extends DefaultHandler {

  public static ProcessType getProcessType(String xml) {
    ProcessTypeBuilder builder = new ProcessTypeBuilder();
    try {
      SAXParser parser = factory.newSAXParser();
      parser.parse(XMLUtils.getInputSourceFromString(xml), builder);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }    
    return builder.pt;
  }
  
  public void startElement(String uri, String localName, String qName, Attributes attrs) {
    if (qName.equals("process-type")) {
      pt = new ProcessType();
      pt.setName(attrs.getValue("name"));
      pt.setPubType(PubTypes.getPubType(Integer.parseInt(attrs.getValue("pubType"))));
    } else if (qName.equals("initial-subfield")) {
    		USubfield usf = pt.getPubType().getSubfield(attrs.getValue("name"));
    		if(attrs.getValue("defaultValue")!=null)
    			usf.setDefaultValue(attrs.getValue("defaultValue"));    		
      pt.getInitialSubfields().add(usf);      
    } else if (qName.equals("mandatory-subfield")) {
      pt.getMandatorySubfields().add(pt.getPubType().getSubfield(attrs.getValue("name")));
    } else if (qName.equals("indicator")) {    		
    		String fieldName = attrs.getValue("field");
    		int index = Integer.parseInt(attrs.getValue("index"));
    		String defaultValue = attrs.getValue("defaultValue");    		
    		UIndicator ui = null;
    		try{    			
    			if(index==1){
    				ui = pt.getPubType().getField(fieldName).getInd1();    				
    			}
    			else
    				ui = pt.getPubType().getField(fieldName).getInd2();
    			
    		}catch(Exception ex){   
    			ex.printStackTrace();
    		}
    		if(ui!=null){    			
    			ui.setDefaultValue(defaultValue);
    			pt.getIndicators().add(ui);    			
    		}    			
    }
  }

  public static ProcessType buildProcessTypeFromDTO(ProcessTypeDTO ptDTO){
      if (ptDTO == null)
          return null;

      ProcessType retVal = new ProcessType();
      retVal.set_id(ptDTO.get_id());
      retVal.setPubType(PubTypes.getPubType(ptDTO.getPubType()));
      retVal.setName(ptDTO.getName());
      retVal.setLibName(ptDTO.getLibName());

      if (ptDTO.getInitialFields() != null)
         retVal.setInitialSubfields( ptDTO.getInitialFields().stream().
                  map(i -> retVal.getPubType().getSubfield(i.getFieldName()+i.getSubfieldName())).
                  collect(Collectors.toList()) );


      if (ptDTO.getMandatoryFields() != null)
          retVal.setMandatorySubfields( ptDTO.getMandatoryFields().stream().
                  map(i -> retVal.getPubType().getSubfield(i.getFieldName()+i.getSubfieldName())).
                  collect(Collectors.toList()) );


      return retVal;
  }

  public static ProcessTypeDTO buildDTOFromProcessType(ProcessType pt){
      if (pt == null)
          return null;

      ProcessTypeDTO retVal = new ProcessTypeDTO();
      retVal.set_id(pt.get_id());
      retVal.setPubType(pt.getPubType().getPubType());
      retVal.setName(pt.getName());
      retVal.setLibName(pt.getLibName());

      if (pt.getInitialSubfields() != null && pt.getInitialSubfields().size() > 0)
        retVal.setInitialFields(pt.getInitialSubfields().stream()
                                   .map(i -> new USubfieldDTO(i.getOwner().getName(), i.getName()))
                                   .collect(Collectors.toList()));


      if (pt.getMandatorySubfields() != null && pt.getMandatorySubfields().size() > 0)
          retVal.setMandatoryFields(pt.getMandatorySubfields().stream()
                  .map(i -> new USubfieldDTO(i.getOwner().getName(), i.getName()))
                  .collect(Collectors.toList()));

    return retVal;
  }
  
  ProcessType pt;
  static SAXParserFactory factory = SAXParserFactory.newInstance();
}
