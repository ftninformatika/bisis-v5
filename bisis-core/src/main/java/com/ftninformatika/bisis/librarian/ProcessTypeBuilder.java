package com.ftninformatika.bisis.librarian;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.ftninformatika.bisis.format.PubTypes;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.UIndicator;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.librarian.process_type_dto.ProcessTypeDTO;
import com.ftninformatika.bisis.librarian.process_type_dto.USubfieldDTO;
import com.ftninformatika.utils.xml.XMLUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


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
      ProcessType retVal = new ProcessType();
      retVal.setPubType(PubTypes.getPubType(ptDTO.getPubType()));
      retVal.setName(ptDTO.getName());
      retVal.setLibName(ptDTO.getLibName());

      if (ptDTO.getInitialFields() != null)
      for (USubfieldDTO sfDTO: ptDTO.getInitialFields())
          for (UField sf: retVal.getPubType().getFields())
              if (sfDTO.getFieldName().equals(sf.getName())) {
                  retVal.getInitialSubfields().add(sf.getSubfield(sfDTO.getSubfieldName()));
                  continue;
              }

      if (ptDTO.getMandatoryFields() != null)
          for (USubfieldDTO sfDTO: ptDTO.getMandatoryFields())
              for (UField sf: retVal.getPubType().getFields())
                  if (sfDTO.getFieldName().equals(sf.getName())) {
                      retVal.getMandatorySubfields().add(sf.getSubfield(sfDTO.getSubfieldName()));
                      continue;
                  }

      return retVal;
  }
  
  ProcessType pt;
  static SAXParserFactory factory = SAXParserFactory.newInstance();
}
