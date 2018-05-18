package com.ftninformatika.bisis.editor.inventar;

import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.format.validators.DateValidator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;


public class InventarValidation {
	
  private static String codeMessagePref = Messages.getString("WRONG_CODE_FOR_FIELD:");
  private static Logger log = Logger.getLogger(InventarValidation.class);
	
  public static String validateInventarPanelData(InventarPanel panel, boolean all){
    StringBuffer messageBuff = new StringBuffer();
    messageBuff.append(
        validateCodes(
            panel.getNacinNabavkePanel().getCode(), 
            panel.getPovezPanel().getCode(), 
            panel.getPodlokacijaPanel().getCode(), 
            panel.getFormatPanel().getCode(),
            panel.getIntOznakaPanel().getCode(), 
            panel.getOdeljenjePanel().getCode(), 
            panel.getStatusPanel().getCode(),
            panel.getDostupnostPanel().getCode())
            );
    messageBuff.append(
        validateDataFormat(
            panel.getDatumRacunaTxtFld().getText(), 
            panel.getDatumInvTxtFld().getText(),
            panel.getDatumStatusaTxtFld().getText(),
            panel.getCenaTxtFld().getText()));
    if(all)
      messageBuff.append(
          validateInventarniBroj(
              panel.getInventarniBrojPanel().getInventarniBroj()));    
    return messageBuff.toString();
  } 
  
  public static String validateSveskeFormData(SveskePanel panel, boolean all){
    StringBuffer messageBuff = new StringBuffer();
    if(all)
//    proverava se i inventarni broj
      messageBuff.append(validateInventarniBroj
        (panel.getInvBrojPanel().getInventarniBroj()));    
    if(!panel.getStatusPanel().isValidCode())
      messageBuff.append(MessageFormat.format(Messages.getString("0.STATUS"), codeMessagePref));
    if(!panel.getCenaTxtFld().getText().equals("") && !isValidCena(panel.getCenaTxtFld().getText()))
      messageBuff.append(Messages.getString("ERROR_IN_PRICE_FORMAT"));
    return messageBuff.toString();
  }
	
	// proverava da li inventarni broj vec postoji
  // pretrazujemo indeks
  public static boolean isDuplicatedInvBroj(String broj){  
  	Boolean retVal = null;

	  try {
		  retVal = BisisApp.bisisService.checkInv(broj).execute().body();
	  } catch (IOException e) {
		  e.printStackTrace();
	  }
	  if (retVal == null)
	  	return true;

	  return retVal;
  }
  
/*  public static boolean isDuplicatedInvBrojDB(String broj){
  	String selectPrimerci = "SELECT * FROM Primerci WHERE inv_broj="+broj;
  	String selectGodine = "SELECT * FROM Godine WHERE inv_broj="+broj;
  	String selectSveske = "SELECT * FROM Sveske WHERE inv_br="+broj;
  	PreparedStatement stmt;
  	ResultSet rs;
  	try {
				stmt = 	BisisApp.getConnection().prepareStatement(selectPrimerci);
				rs = stmt.executeQuery();
				if(rs.next()) return true;
				
				stmt = 	BisisApp.getConnection().prepareStatement(selectGodine);
				rs = stmt.executeQuery();
				if(rs.next()) return true;
				
				stmt = 	BisisApp.getConnection().prepareStatement(selectSveske);
				rs = stmt.executeQuery();
				if(rs.next()) return true;			
			
			} catch (SQLException e) {				
				e.printStackTrace();
				return true;
			}
			return false;
  }
  */

  public static String validateRaspodela(String odeljenje, String invKnjiga){
  	// raspodela je korektna ako je uneto odeljenje i inventarna knjiga 
  	// jer se bez tih podataka ne moze kreirati inventarni broj 
  	StringBuffer message = new StringBuffer();
  	String messagePref = Messages.getString("MISSING_DATA:");
  	if(odeljenje.equals("")){
  		message.append(MessageFormat.format(Messages.getString("0.LOCATION"), messagePref));
  	}
  	if(invKnjiga.equals("")){
  		message.append(MessageFormat.format(Messages.getString("0.INV_BOOK"), messagePref));
  	}  		
  	return message.toString();
  }
  
  public static String validateInvBrojUnique(String invbroj){
    if(isDuplicatedInvBroj(invbroj))
     return MessageFormat.format(Messages.getString("INV_NUMј.0.TAKEN"), invbroj);
    else
      return "";
  }



  private static String validateCodes(String nacinNabavke, 
									   String povez,
									   String podlokacija,
									   String format,									   
									   String intOznaka,
									   String odeljenje,
									   String status,
            String dostupnost){
		StringBuffer message = new StringBuffer();		
		if(nacinNabavke!=null && !nacinNabavke.equals("")){
			if(!BisisApp.appConfig.getCodersHelper().isValidNacinNabavke(nacinNabavke))
				message.append(MessageFormat.format(Messages.getString("0.ACQ_TYPE"), codeMessagePref));
		}
		if(povez!=null && !povez.equals("")){
			if(!BisisApp.appConfig.getCodersHelper().isValidPovez(povez))
				message.append(MessageFormat.format(Messages.getString("0.BIN"), codeMessagePref));
		}
		if(podlokacija!=null && !podlokacija.equals("")){
			if(!BisisApp.appConfig.getCodersHelper().isValidPodlokacija(podlokacija))
				message.append(MessageFormat.format(Messages.getString("0.SUBLOC_SIGN"), codeMessagePref));
		}		
		if(format!=null && !format.equals("")){
			if(!BisisApp.appConfig.getCodersHelper().isValidFormat(format))
				message.append(MessageFormat.format(Messages.getString("0.SIGN_FORMAT"), codeMessagePref));
		}
		if(intOznaka!=null && !intOznaka.equals("")){
			if(!BisisApp.appConfig.getCodersHelper().isValidInternaOznaka(intOznaka))
				message.append(MessageFormat.format(Messages.getString("0.INTERNAL_MARK_SIG"), codeMessagePref));
		}
		if(odeljenje!=null && !odeljenje.equals("")){
			if(!BisisApp.appConfig.getCodersHelper().isValidOdeljenje(odeljenje))
				message.append(MessageFormat.format(Messages.getString("0.LOCATIONN"), codeMessagePref));
		}
		  if(status!=null && !status.equals("")){
			  if(!BisisApp.appConfig.getCodersHelper().isValidStatus(status))
				  message.append(MessageFormat.format(Messages.getString("0.STATUSS"), codeMessagePref));
		  }
    if(dostupnost!=null && !dostupnost.equals("")){
      if(!BisisApp.appConfig.getCodersHelper().isValidDostupnost(dostupnost))
        message.append(MessageFormat.format(Messages.getString("0.AVAILABILITYY"), codeMessagePref));
    }
		return message.toString();		
	}
  
  private static String validateDataFormat(
  										String datumRacuna,
											String datumInventarisanja,
											String datumStatusa,
											String cena){
		StringBuffer message = new StringBuffer();
		DateValidator dv = new DateValidator();
//		 datumi
		if(datumRacuna!=null && !datumRacuna.equals("")){
			if(!dv.isValid(datumRacuna).equals(""))
				message.append(MessageFormat.format(Messages.getString("0.FIELD_BILL_DATE"), dv.isValid(datumRacuna)));
		}
		
		if(datumInventarisanja!=null && !datumInventarisanja.equals("")){
			if(!dv.isValid(datumInventarisanja).equals(""))
				message.append(MessageFormat.format(Messages.getString("0.IN_FIELD_INV_DATE"), dv.isValid(datumInventarisanja)));
		}	
		if(datumStatusa!=null && !datumStatusa.equals("")){
			if(!dv.isValid(datumStatusa).equals(""))
				message.append(MessageFormat.format(Messages.getString("0.IN_FIELD_STATUS_DATE"), dv.isValid(datumStatusa)));
		}	
		//cena (da li je broj)		
		if(cena!=null && !cena.equals("")){
			if(!isValidCena(cena))	
				message.append(Messages.getString("ERR_IN_PRICE_FORMAT"));
		}		
		return message.toString();
	}
	
	private static String validateInventarniBroj(String content){
		if(content.length()!=11 && isNumberStr(content)) return Messages.getString("INV_NUM_MUST_HAVE");
		String message = "";		
		for(int i=0;i<content.length();i++){				
			 switch (content.charAt(i)) {
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
        case ':':
        case '-':
        case '/':
        case '.':
          break;
        default:
          message = Messages.getString("WRONG_SIGN_IN_INV_NUM");
      }
		}
		if(message.equals("") && content.length()<11){						
			message = Messages.getString("INV_NUM_MINIMAL_SIZE");
		}  
		return message;
	}
	
	private static boolean isNumberStr(String str){
		for(int i=0;i<str.length();i++){				
		 if(!isNumber(String.valueOf(str.charAt(i))))
		 		return false;		 	
		}
		return true;
	}
 
	
	private static boolean isNumber(String str){
    try{
      Integer.parseInt(str);
      return true;
    }catch(NumberFormatException e){
      return false;
    }    
  }
  
  private static boolean isValidCena(String str){
    try {
      BigDecimal bd = new BigDecimal(str.toCharArray());
      return true;
    } catch (NumberFormatException e) {     
      return false;
    }
  }

}
