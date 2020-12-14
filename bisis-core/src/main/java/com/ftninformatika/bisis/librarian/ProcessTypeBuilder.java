package com.ftninformatika.bisis.librarian;

import com.ftninformatika.bisis.format.PubTypes;
import com.ftninformatika.bisis.format.UIndicator;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.librarian.db.ProcessTypeDB;
import com.ftninformatika.bisis.librarian.db.USubfieldDB;
import com.ftninformatika.bisis.librarian.dto.ProcessTypeDTO;
import com.ftninformatika.bisis.librarian.dto.USubfieldDTO;
import com.ftninformatika.utils.xml.XMLUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
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

//  public static ProcessType buildProcessTypeFromDTO(ProcessTypeDTO ptDTO){
//      if (ptDTO == null)
//          return null;
//
//      ProcessType retVal = new ProcessType();
//      retVal.set_id(ptDTO.get_id());
//      retVal.setPubType(PubTypes.getPubType(ptDTO.getPubType()));
//      retVal.setName(ptDTO.getName());
//      retVal.setLibName(ptDTO.getLibName());
//
//      if (ptDTO.getInitialFields() != null){
//          for (USubfieldDTO subDTO : ptDTO.getInitialFields()){
//              USubfield sub = retVal.getPubType().getSubfield(subDTO.getFieldName()+subDTO.getSubfieldName()).shallowCopy();
//              sub.setDefaultValue(subDTO.getDefaultValue());
//              retVal.getInitialSubfields().add(sub);
//          }
//      }
//
//
//
//      if (ptDTO.getMandatoryFields() != null)
//          retVal.setMandatorySubfields( ptDTO.getMandatoryFields().stream().
//                  map(i -> retVal.getPubType().getSubfield(i.getFieldName()+i.getSubfieldName()).shallowCopy()).
//                  collect(Collectors.toList()) );
//
//
//      return retVal;
//  }

    public static ProcessTypeDB buildProcessTypeDBFromDTO(ProcessTypeDTO ptDTO){
        if (ptDTO == null)
            return null;

        ProcessTypeDB retVal = new ProcessTypeDB();
        retVal.set_id(ptDTO.get_id());
        retVal.setPubType(ptDTO.getPubType());
        retVal.setName(ptDTO.getName());
        retVal.setLibName(ptDTO.getLibName());

        if (ptDTO.getInitialFields() != null){
            retVal.setInitialFields(new ArrayList<>());
            for (USubfieldDTO subDTO : ptDTO.getInitialFields()){
                USubfieldDB sub = new USubfieldDB();
                sub.setFieldName(subDTO.getFieldName());
                sub.setSubfieldName(subDTO.getSubfieldName());
                sub.setDefaultValue(subDTO.getDefaultValue());
                retVal.getInitialFields().add(sub);
            }
        }



        if (ptDTO.getMandatoryFields() != null) {
            retVal.setMandatoryFields(new ArrayList<>());
            for (USubfieldDTO subDTO : ptDTO.getMandatoryFields()){
                USubfieldDB sub = new USubfieldDB();
                sub.setFieldName(subDTO.getFieldName());
                sub.setSubfieldName(subDTO.getSubfieldName());
                sub.setDefaultValue(subDTO.getDefaultValue());
                retVal.getMandatoryFields().add(sub);
            }
        }

        return retVal;
    }

//  public static ProcessTypeDTO buildDTOFromProcessType(ProcessType pt){
//      if (pt == null)
//          return null;
//
//      ProcessTypeDTO retVal = new ProcessTypeDTO();
//      retVal.set_id(pt.get_id());
//      retVal.setPubType(pt.getPubType().getPubType());
//      retVal.setName(pt.getName());
//      retVal.setLibName(pt.getLibName());
//
//      if (pt.getInitialSubfields() != null && pt.getInitialSubfields().size() > 0)
//        retVal.setInitialFields(pt.getInitialSubfields().stream()
//                                   .map(i -> new USubfieldDTO(i.getOwner().getName(), i.getName(), i.getDefaultValue()))
//                                   .collect(Collectors.toList()));
//
//
//      if (pt.getMandatorySubfields() != null && pt.getMandatorySubfields().size() > 0)
//          retVal.setMandatoryFields(pt.getMandatorySubfields().stream()
//                  .map(i -> new USubfieldDTO(i.getOwner().getName(), i.getName(), i.getDefaultValue()))
//                  .collect(Collectors.toList()));
//
//    return retVal;
//  }
  
  ProcessType pt;
  static SAXParserFactory factory = SAXParserFactory.newInstance();
}
