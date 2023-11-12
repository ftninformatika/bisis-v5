package com.ftninformatika.bisis.format.validators;

import java.util.ArrayList;
import java.util.List;

import com.ftninformatika.bisis.format.UValidator;
import com.ftninformatika.bisis.format.UValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ISBNValidator implements UValidator {

	public ISBNValidator() {
		targets = new ArrayList();
		targets.add("010a");
    log.info("Loading ISBN validator");
	}

	public List getTargets() {		 
		return targets;
	}

	public String isValid(String content) {
		String retVal = "";
	    try {
	      validate(content);
	    } catch (UValidatorException ex) {
	      retVal = ex.getMessage();
	    }
	    return retVal;		
	}

	public void validate(String content) throws UValidatorException {
		int l = content.length();
    if (l != 10 && l != 13 && l != 17)
      throw new UValidatorException("Du\u017eina ISBN broja mora biti 13 ili 17!");
    else {
      int i = 0;
      int numPov = 0;
      boolean isValid = true;
      while (i < l && isValid) {
        if (!(Character.isDigit(content.charAt(i))
             || content.charAt(i) == '-'
             || content.charAt(i) == 'X'
             || content.charAt(i) == 'x'))
         isValid = false;
        if (content.charAt(i) == '-')
          numPov++;
        i++;
      }
      if ((numPov != 3 && numPov != 0 && l == 13) || 
          (numPov != 4 && l == 17) || 
          !isValid)
        throw new UValidatorException("Sintaksna gre\u0161ka ISBN broja!");
    }    
	}
	
	private List targets;
  private static Logger log = LoggerFactory.getLogger(ISBNValidator.class);

}
