package com.ftninformatika.bisis.prefixes.def;


import com.ftninformatika.bisis.prefixes.PrefixHandler;
import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.records.Subsubfield;

/**
 * Default prefix handler.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class DefaultPrefixHandler implements PrefixHandler {

  /**
   * Concatenates subsubfields in a single string so that they can be
   * displayed as a prefix. The resulting string is structured as follows:
   * <ssf-id>content<ssf-id>content...
   * 
   * @param subfield Subfield to be processed.
   * @see com.ftninformatika.bisis.prefixes.PrefixHandler#concatenateSubsubfields(com.ftninformatika.bisis.records.Subfield)
   */
  public String concatenateSubsubfields(Subfield subfield) {
    if (subfield.getSubsubfieldCount() == 0)
      return "";
    StringBuffer retVal = new StringBuffer(100);
    for (int i = 0; i < subfield.getSubsubfieldCount(); i++) {
      Subsubfield ssf = subfield.getSubsubfield(i);
      retVal.append('<');
      retVal.append(ssf.getName());
      retVal.append('>');
      retVal.append(ssf.getContent());
    }
    return retVal.toString();
  }

  /**
   * Creates contents of the author (AU) prefix for the given field.
   * 
   * @param field Field to be processed.
   * @return The concatenated elements representing the name of the author.
   * @see com.ftninformatika.bisis.prefixes.PrefixHandler#createAuthor(com.ftninformatika.bisis.records.Field)
   */
  public String [] createAuthor(Field field) {
	String [] autor =new String [2];
    StringBuffer retVal = new StringBuffer(50);
    StringBuffer retVal2 = new StringBuffer(50);
    String prezime="", ime="";
    if (field.getInd2() != '0') {
      if (field.getSubfield('a') != null){
    	prezime=field.getSubfield('a').getContent();
        retVal.append(prezime);
      }
      if (field.getSubfield('b') != null) {
        if (retVal.length() != 0){
        	ime=field.getSubfield('b').getContent();
        	retVal.append(" ");
            retVal.append(ime);
        }
      }
      if (ime!=null)
    	  retVal2.append(ime);
      if (prezime!=null){
    	  retVal2.append(" ");
    	  retVal2.append(prezime);
      }
      
    } else {
      if (field.getSubfield('a') != null)
        retVal.append(field.getSubfield('a').getContent());
    }
    autor[0]=retVal.toString();
    autor[1]=retVal2.toString();
    return autor;
  }
}
