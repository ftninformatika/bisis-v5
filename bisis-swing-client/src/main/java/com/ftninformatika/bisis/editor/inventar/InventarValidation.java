package com.ftninformatika.bisis.editor.inventar;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.format.HoldingsDataCoders;
import com.ftninformatika.bisis.format.validators.DateValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class InventarValidation {
	
  private static String codeMessagePref = "Neodgovaraju\u0107a \u0161ifra za polje: ";
  private static Log log = LogFactory.getLog(InventarValidation.class.getName());
	
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
      messageBuff.append(codeMessagePref+"Status!\n");
    if(!panel.getCenaTxtFld().getText().equals("") && !isValidCena(panel.getCenaTxtFld().getText()))
      messageBuff.append("Gre\u0161ka u formatu broja u polju Cena!\n");    
    return messageBuff.toString();
  }
	
	// proverava da li inventarni broj vec postoji
  // pretrazujemo indeks
  public static boolean isDuplicatedInvBroj(String broj){  
  	/*int[] hits;
			Term t = new Term("IN",broj);
			TermQuery tq = new TermQuery(t);
			if(BisisApp.isStandalone())
				hits = BisisApp.getRecordManager().select2(tq, null);
			else
				hits = BisisApp.getRecordManager().select2x(SerializationUtils.serialize(tq), null);
			return hits!=null && hits.length!=0; 	 */
  	return false; //TODO-hardcoded
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
  	String messagePref = "Nedostaje podatak: ";
  	if(odeljenje.equals("")){
  		message.append(messagePref+"Odeljenje!\n");
  	}
  	if(invKnjiga.equals("")){
  		message.append(messagePref+"Inventarna knjiga!\n");
  	}  		
  	return message.toString();
  }
  
  public static String validateInvBrojUnique(String invbroj){
    if(isDuplicatedInvBroj(invbroj))
     return "Inventarni broj "+invbroj+" je ve\u0107 zauzet!";
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
			if(!HoldingsDataCoders.isValidNacinNabavke(nacinNabavke))
				message.append(codeMessagePref+"Na\u010din nabavke!\n");				
		}
		if(povez!=null && !povez.equals("")){
			if(!HoldingsDataCoders.isValidPovez(povez))
				message.append(codeMessagePref+"Povez!\n");
		}
		if(podlokacija!=null && !podlokacija.equals("")){
			if(!HoldingsDataCoders.isValidPodlokacija(podlokacija))
				message.append(codeMessagePref+"Podlokacija (signatura)!\n");
		}		
		if(format!=null && !format.equals("")){
			if(!HoldingsDataCoders.isValidFormat(format))
				message.append(codeMessagePref+"Format (signatura)!\n");
		}
		if(intOznaka!=null && !intOznaka.equals("")){
			if(!HoldingsDataCoders.isValidInternaOznaka(intOznaka))
				message.append(codeMessagePref+"Interna oznaka (signatura)!\n");
		}
		if(odeljenje!=null && !odeljenje.equals("")){
			if(!HoldingsDataCoders.isValidOdeljenje(odeljenje))
				message.append(codeMessagePref+"Odeljenje!\n");
		}
    if(dostupnost!=null && !dostupnost.equals("")){
      if(!HoldingsDataCoders.isValidDostupnost(dostupnost))
        message.append(codeMessagePref+"Dostupnost!\n");
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
				message.append(dv.isValid(datumRacuna)+" (polje Datum ra\u010duna)\n");
		}
		
		if(datumInventarisanja!=null && !datumInventarisanja.equals("")){
			if(!dv.isValid(datumInventarisanja).equals(""))
				message.append(dv.isValid(datumInventarisanja)+" u polju Datum inventarisanja!\n");
		}	
		if(datumStatusa!=null && !datumStatusa.equals("")){
			if(!dv.isValid(datumStatusa).equals(""))
				message.append(dv.isValid(datumStatusa)+" u polju Datum statusa!\n");
		}	
		//cena (da li je broj)		
		if(cena!=null && !cena.equals("")){
			if(!isValidCena(cena))	
				message.append("Gre\u0161ka u formatu broja u polju Cena!\n");
		}		
		return message.toString();
	}
	
	private static String validateInventarniBroj(String content){
		if(content.length()!=11 && isNumberStr(content)) return "Inventarni broj mora imati 11 cifara!";
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
          message = "Pogre\u0161an znak u inv. broju: ";
      }
		}
		if(message.equals("") && content.length()<11){						
			message = "Minimalna du\u017eina inv. broja je 11 znakova!";		
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
