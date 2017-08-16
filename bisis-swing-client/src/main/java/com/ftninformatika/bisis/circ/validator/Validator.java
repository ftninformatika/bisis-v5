package com.ftninformatika.bisis.circ.validator;

import javax.swing.JLabel;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.utils.string.StringUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.util.ValidatorUtils;



public class Validator {
	
	   /**
	    * Checks if the field is required.
	    *
	    * @return boolean If the field isn't <code>null</code> and
	    * has a length greater than zero, <code>true</code> is returned.  
	    * Otherwise <code>false</code>.
	    */
	   public static boolean validateRequired(Object bean, Field field) {
	      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        String labelName = field.getProperty() + "Label";
        try {
          JLabel label = (JLabel)PropertyUtils.getProperty(bean, labelName);
          String text = label.getText();
          int ind = text.indexOf("*");
          if (ind == -1){
            label.setText(text + "*");
          }
        } catch (Exception e) {
        }
        
	      return !GenericValidator.isBlankOrNull(value);
	   }
	   
	   /**
	    * Checks if the field can be successfully converted to a <code>int</code>.
	    *
	    * @param 	value 		The value validation is being performed on.
	    * @return	boolean		If the field can be successfully converted 
	    *                           to a <code>int</code> <code>true</code> is returned.  
	    *                           Otherwise <code>false</code>.
	    */
	   public static boolean validateInt(Object bean, Field field) {
	      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	      return GenericValidator.isInt(value);
	   }
	   
	   public static boolean validateIntOrBlank(Object bean, Field field) {
	      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	      return GenericValidator.isBlankOrNull(value) || GenericValidator.isInt(value);
	   }
	   
	   /**
	    * Checks if field is positive assuming it is an integer
	    * 
	    * @param    value       The value validation is being performed on.
	    * @param    field       Description of the field to be evaluated
	    * @return   boolean     If the integer field is greater than zero, returns
	    *                        true, otherwise returns false.
	    */
	   public static boolean validatePositive(Object bean , Field field) {
	      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	      return GenericTypeValidator.formatInt(value).intValue() > 0;                                                      
	   }
	   
	   public static boolean validatePositiveOrBlank(Object bean , Field field) {
	      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	      return GenericValidator.isBlankOrNull(value) || GenericTypeValidator.formatInt(value).intValue() > 0;                                                      
	   }
	   
	   /**
	    * Checks if the field can be successfully converted to a <code>double</code>.
	    *
	    * @param 	value 		The value validation is being performed on.
	    * @return	boolean		If the field can be successfully converted 
	    *                           to a <code>double</code> <code>true</code> is returned.  
	    *                           Otherwise <code>false</code>.
	    */
	   public static boolean validateDouble(Object bean, Field field) {
	      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	      return GenericValidator.isDouble(value);
	   }
	   
	   public static boolean validateDoubleOrBlank(Object bean, Field field) {
	      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	      return GenericValidator.isBlankOrNull(value) || GenericValidator.isDouble(value);
	   }
	   
	   /**
	    * Checks if the field is an e-mail address.
	    *
	    * @param 	value 		The value validation is being performed on.
	    * @return	boolean		If the field is an e-mail address
	    *                           <code>true</code> is returned.  
	    *                           Otherwise <code>false</code>.
	    */
	   public static boolean validateEmail(Object bean, Field field) {
	      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	      return GenericValidator.isEmail(value);
	   }
	   
	   public static boolean validateEmailOrBlank(Object bean, Field field) {
	      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	      return GenericValidator.isBlankOrNull(value) || GenericValidator.isEmail(value);
	   }
	   
	   /**
	    * Checks if the field can be successfully converted to a <code>date</code>.
	    *
	    * @param value The value validation is being performed on.
	    * @return boolean If the field can be successfully converted 
	    * to a <code>date</code> <code>true</code> is returned.  
	    * Otherwise <code>false</code>.
	    */
	   public static boolean validateDate(Object bean, Field field) {
	      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	      String datePattern = field.getVarValue("datePattern");
	      boolean result = false;
	      if (datePattern != null && datePattern.length() > 0) {
	            result = GenericValidator.isDate(value, datePattern, false);
	      }
	      return result;
	   }
	   
	   public static boolean validateDateOrBlank(Object bean, Field field) {
	      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	      String datePattern = field.getVarValue("datePattern");
	      boolean result = false;
	      if (datePattern != null && datePattern.length() > 0) {
	            result = GenericValidator.isDate(value, datePattern, false);
	      }
	      return GenericValidator.isBlankOrNull(value) || result;
	   }
	   
	   public static boolean validateEnabled(Object bean, Field field){
		   String value = ValidatorUtils.getValueAsString(bean, field.getProperty()+"Enabled");
		   if (value.equals("true")){
			   return true;
		   }else{
			   return false;
		   }
	   }
	   
	   public static boolean validateIntOrNotEnabled(Object bean, Field field) {
		      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		      return !validateEnabled(bean, field) || GenericValidator.isInt(value);
	   }
	   
	   public static boolean validateStringOrNotEnabled(Object bean, Field field) {
		      String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		      return !validateEnabled(bean, field) || !GenericValidator.isBlankOrNull(value);
	   }
     
     public static boolean validateUserId(Object bean, Field field) {
        String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        return GenericValidator.isLong(value) && value.length() == Cirkulacija.getApp().getEnvironment().getUseridLength();
     }
     
     public static boolean validateUserIdOrBlank(Object bean, Field field) {
       String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
       return GenericValidator.isBlankOrNull(value) || validateUserId(bean, field);
     }
     
     public static String convertUserId2DB(String userid) {
    	 
    	 //barcode
    	 if (userid.startsWith("K")){
    		 return userid.substring(1);
    	 }
    	 
    	 if ((userid.length() == Cirkulacija.getApp().getEnvironment().getUseridLength()) && (userid.indexOf(Cirkulacija.getApp().getEnvironment().getUseridSeparatorSign()) == -1)){
    		 return userid;
    	 }
    	 
       if (!Cirkulacija.getApp().getEnvironment().getUseridPrefix() || !Cirkulacija.getApp().getEnvironment().getUseridSeparator()){
         if (userid.length() <= Cirkulacija.getApp().getEnvironment().getUseridLength()){
           return StringUtils.padChars(userid, '0', Cirkulacija.getApp().getEnvironment().getUseridLength());
         }
       } else {
         int ind = userid.indexOf(Cirkulacija.getApp().getEnvironment().getUseridSeparatorSign());
         if (ind != -1){
           String part1 = userid.substring(0,ind).trim();
           String part2 = userid.substring(ind+1).trim();
           if (Cirkulacija.getApp().getEnvironment().getUseridInput() == 1){
             if (part2.length() <= Cirkulacija.getApp().getEnvironment().getUseridPrefixLength() &&
                 part1.length() <= Cirkulacija.getApp().getEnvironment().getUseridLength()-Cirkulacija.getApp().getEnvironment().getUseridPrefixLength()){
                 return StringUtils.padChars(part2, '0', Cirkulacija.getApp().getEnvironment().getUseridPrefixLength()) +
                   StringUtils.padChars(part1, '0', Cirkulacija.getApp().getEnvironment().getUseridLength()-Cirkulacija.getApp().getEnvironment().getUseridPrefixLength());
             }
           } else {
             if (part1.length() <= Cirkulacija.getApp().getEnvironment().getUseridPrefixLength() &&
                 part2.length() <= Cirkulacija.getApp().getEnvironment().getUseridLength()-Cirkulacija.getApp().getEnvironment().getUseridPrefixLength()){
                 return StringUtils.padChars(part1, '0', Cirkulacija.getApp().getEnvironment().getUseridPrefixLength()) +
                   StringUtils.padChars(part2, '0', Cirkulacija.getApp().getEnvironment().getUseridLength()-Cirkulacija.getApp().getEnvironment().getUseridPrefixLength());
             }
           }
        } else {
          if (userid.length() <= Cirkulacija.getApp().getEnvironment().getUseridLength()-Cirkulacija.getApp().getEnvironment().getUseridPrefixLength()){
            return StringUtils.padChars(Integer.toString(Cirkulacija.getApp().getEnvironment().getUseridDefaultPrefix()), '0', Cirkulacija.getApp().getEnvironment().getUseridPrefixLength()) +
              StringUtils.padChars(userid, '0', Cirkulacija.getApp().getEnvironment().getUseridLength()-Cirkulacija.getApp().getEnvironment().getUseridPrefixLength());
          }
        }
       }
       return "";
     }
     
     public static String convertCtlgNo2DB(String ctlgno) {
       if (!ctlgno.equals("")){
      	 
      	 //barcode
      	 if (ctlgno.startsWith("P")){
      		 return ctlgno.substring(1);
      	 }
      	 
         int ctlgnoLength = Cirkulacija.getApp().getEnvironment().getCtlgnoLength();
         int locLength = Cirkulacija.getApp().getEnvironment().getCtlgnoLocationLength();
         int bookLength = Cirkulacija.getApp().getEnvironment().getCtlgnoBookLength();
         int locDef = Cirkulacija.getApp().getEnvironment().getCtlgnoDefaultLocation();
         int bookDef = Cirkulacija.getApp().getEnvironment().getCtlgnoDefaultBook();
         int input = Cirkulacija.getApp().getEnvironment().getCtlgnoInput();
         String tail = "";
         
         int dot = ctlgno.indexOf('.');
         if (dot == -1){
        	 dot = ctlgno.indexOf(':');
         }
         if (dot == -1){
        	 dot = ctlgno.indexOf('!');
         }
         
         if (dot != -1){
        	 if (input == 1){
        		 int sep1 = ctlgno.indexOf(Cirkulacija.getApp().getEnvironment().getCtlgnoSeparator1());
             int sep2 = ctlgno.indexOf(Cirkulacija.getApp().getEnvironment().getCtlgnoSeparator2());
             if (sep1 != -1){
            	 tail = ctlgno.substring(dot,sep1);
            	 ctlgno = ctlgno.substring(0,dot) + ctlgno.substring(sep1);
             } else if (sep2 != -1){
             	 tail = ctlgno.substring(dot,sep2);
            	 ctlgno = ctlgno.substring(0,dot) + ctlgno.substring(sep2);
             }else{
            	 tail = ctlgno.substring(dot).trim();
          		 ctlgno = ctlgno.substring(0,dot);
             }
        	 }else{
        		 tail = ctlgno.substring(dot).trim();
        		 ctlgno = ctlgno.substring(0,dot);
        	 }
         }
         
         if (!Cirkulacija.getApp().getEnvironment().getCtlgnoSeparators()){
           if (ctlgno.length() <= ctlgnoLength){
             return StringUtils.padChars(ctlgno, '0', ctlgnoLength) + tail;
           }
         } else {
           if (Cirkulacija.getApp().getEnvironment().getCtlgnoLocation() && Cirkulacija.getApp().getEnvironment().getCtlgnoBook()){
             int sep1 = ctlgno.indexOf(Cirkulacija.getApp().getEnvironment().getCtlgnoSeparator1());
             int sep2 = ctlgno.indexOf(Cirkulacija.getApp().getEnvironment().getCtlgnoSeparator2());
             if (sep1 != -1 && sep2 != -1){
               String part1 = ctlgno.substring(0,sep1).trim();
               String part2 = ctlgno.substring(sep1+1,sep2).trim();
               String part3 = ctlgno.substring(sep2+1).trim();
               if (input == 1){
                 if (part1.length() <= ctlgnoLength-bookLength-locLength &&
                     part2.length() <= locLength &&
                     part3.length() <= bookLength){
                   return StringUtils.padChars(part2, '0', locLength) +
                     StringUtils.padChars(part3, '0', bookLength) +
                     StringUtils.padChars(part1, '0', ctlgnoLength-bookLength-locLength)
                     + tail;
                 }
               } else {
                 if (part3.length() <= ctlgnoLength-bookLength-locLength &&
                     part1.length() <= locLength &&
                     part2.length() <= bookLength){
                   return StringUtils.padChars(part1, '0', locLength) +
                     StringUtils.padChars(part2, '0', bookLength) +
                     StringUtils.padChars(part3, '0', ctlgnoLength-bookLength-locLength)
                     + tail;
                 }
               }
             } else if (sep1 != -1){
               String part1 = ctlgno.substring(0,sep1).trim();
               String part2 = ctlgno.substring(sep1+1).trim();
               if (input == 1){
                 if (part1.length() <= ctlgnoLength-bookLength-locLength &&
                     part2.length() <= locLength){
                   return StringUtils.padChars(part2, '0', locLength) +
                     StringUtils.padChars(Integer.toString(bookDef), '0', bookLength) +
                     StringUtils.padChars(part1, '0', ctlgnoLength-bookLength-locLength)
                     + tail;
                 }
               } else {
                 if (part2.length() <= ctlgnoLength-bookLength-locLength &&
                     part1.length() <= locLength){
                   return StringUtils.padChars(part1, '0', locLength) +
                     StringUtils.padChars(Integer.toString(bookDef), '0', bookLength) +
                     StringUtils.padChars(part2, '0', ctlgnoLength-bookLength-locLength)
                     + tail;
                 }
               }
             } else if (sep2 != -1){
               String part1 = ctlgno.substring(0,sep2).trim();
               String part2 = ctlgno.substring(sep2+1).trim();
               if (input == 1){
                 if (part1.length() <= ctlgnoLength-bookLength-locLength &&
                     part2.length() <= bookLength){
                   return StringUtils.padChars(Integer.toString(locDef), '0', locLength) +
                     StringUtils.padChars(part2, '0', bookLength) +
                     StringUtils.padChars(part1, '0', ctlgnoLength-bookLength-locLength)
                     + tail;
                 }
               } else {
                 if (part2.length() <= ctlgnoLength-bookLength-locLength &&
                     part1.length() <= bookLength){
                   return StringUtils.padChars(Integer.toString(locDef), '0', locLength) +
                     StringUtils.padChars(part1, '0', bookLength) +
                     StringUtils.padChars(part2, '0', ctlgnoLength-bookLength-locLength)
                     + tail;
                 }
               }
             } else {
               if (ctlgno.length() <= ctlgnoLength-bookLength-locLength){
                 return StringUtils.padChars(Integer.toString(locDef), '0', locLength) +
                 StringUtils.padChars(Integer.toString(bookDef), '0', bookLength) +
                 StringUtils.padChars(ctlgno, '0', ctlgnoLength-bookLength-locLength)
                 + tail;
               }
             }
           } else if (Cirkulacija.getApp().getEnvironment().getCtlgnoLocation()){
             int sep1 = ctlgno.indexOf(Cirkulacija.getApp().getEnvironment().getCtlgnoSeparator1());
             if (sep1 != -1){
               String part1 = ctlgno.substring(0,sep1).trim();
               String part2 = ctlgno.substring(sep1+1).trim();
               if (input == 1){
                 if (part1.length() <= ctlgnoLength-locLength &&
                     part2.length() <= locLength){
                   return StringUtils.padChars(part2, '0', locLength) +
                     StringUtils.padChars(part1, '0', ctlgnoLength-locLength)
                     + tail;
                 }
               } else {
                 if (part2.length() <= ctlgnoLength-locLength &&
                     part1.length() <= locLength){
                   return StringUtils.padChars(part1, '0', locLength) +
                     StringUtils.padChars(part2, '0', ctlgnoLength-locLength)
                     + tail;
                 }
               }
             } else {
               if (ctlgno.length() <= ctlgnoLength-locLength){
                 return StringUtils.padChars(Integer.toString(locDef), '0', locLength) +
                   StringUtils.padChars(ctlgno, '0', ctlgnoLength-locLength)
                   + tail;
               }
             }
           } else if (Cirkulacija.getApp().getEnvironment().getCtlgnoBook()){
             int sep1 = ctlgno.indexOf(Cirkulacija.getApp().getEnvironment().getCtlgnoSeparator1());
             if (sep1 != -1){
               String part1 = ctlgno.substring(0,sep1).trim();
               String part2 = ctlgno.substring(sep1+1).trim();
               if (input == 1){
                 if (part1.length() <= ctlgnoLength-bookLength &&
                     part2.length() <= bookLength){
                   return StringUtils.padChars(part2, '0', bookLength) +
                     StringUtils.padChars(part1, '0', ctlgnoLength-bookLength)
                     + tail;
                 }
               } else {
                 if (part2.length() <= ctlgnoLength-bookLength &&
                     part1.length() <= bookLength){
                   return StringUtils.padChars(part1, '0', bookLength) +
                     StringUtils.padChars(part2, '0', ctlgnoLength-bookLength)
                     + tail;
                 }
               }
             } else {
               if (ctlgno.length() <= ctlgnoLength-bookLength){
                 return StringUtils.padChars(Integer.toString(bookDef), '0', bookLength) +
                   StringUtils.padChars(ctlgno, '0', ctlgnoLength-bookLength)
                   + tail;
               }
             }
           } else {
             if (ctlgno.length() <= ctlgnoLength){
               return StringUtils.padChars(ctlgno, '0', ctlgnoLength)
               + tail;
             }
           }
         }
         return "";
       }else{
         return "";
       }
     }

}
