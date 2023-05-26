package com.ftninformatika.bisis.format.validators;

import com.ftninformatika.bisis.format.UValidator;
import com.ftninformatika.bisis.format.UValidatorException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class DateValidator implements UValidator {

	public DateValidator() {
		targets = new ArrayList();
		targets.add("992c");
    log.info("Loading date validator");
		// ???
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
		if (content.length() != 8)
		      throw new UValidatorException("Du\u017eina datuma mora biti 8 (GGGGMMDD)!");
		    else {
		      int i = 0;
		      while (i < content.length()) {
		        if (!(Character.isDigit(content.charAt(i))))
		          throw new UValidatorException("Datum se sastoji isklju\u010divo od cifara (GGGGMMDD)!");
		        i++;
		      }
		      int godina = Integer.parseInt(content.substring(0,4));
		      int mesec = Integer.parseInt(content.substring(4,6));
		      int dan = Integer.parseInt(content.substring(6,8));
		      if (mesec < 1 || mesec > 12)
		        throw new UValidatorException("Pogre\u0161an broj meseca [1-12] (GGGGMMDD)!");
		      if (dan < 1 || dan > 31)
		        throw new UValidatorException("Pogre\u0161an broj dana [1-31] (GGGGMMDD)!");
		      if (mesec == 4 || mesec == 6 || mesec == 9 || mesec == 11) {
		        if (dan > 30)
		          throw new UValidatorException("Pogre\u0161an broj dana [1-30] (GGGGMMDD)!");
		      } else {
		        if (((godina%4 == 0 && !(godina%100 == 0)) || godina%400 == 0) && mesec == 2) {
		          if (dan > 29)
		            throw new UValidatorException("Pogre\u0161an broj dana [1-29] (GGGGMMDD)!");
		        } else
		          if (mesec == 2 && dan > 28)
		            throw new UValidatorException("Pogre\u0161an broj dana [1-28] (GGGGMMDD)!");
		      }
		    }
		   
	}
	
	private List targets;
  private static Logger log = Logger.getLogger(DateValidator.class);

}
