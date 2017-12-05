package com.ftninformatika.bisis.editor.inventar;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.coders.CodersHelper;
import com.ftninformatika.bisis.format.HoldingsDataCoders;
import com.ftninformatika.bisis.format.validators.DateValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class InventarValidation {
	
  private static String codeMessagePref = "Неодговарајућа шифра за поље: ";
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
      messageBuff.append(codeMessagePref+"Статус!\n");
    if(!panel.getCenaTxtFld().getText().equals("") && !isValidCena(panel.getCenaTxtFld().getText()))
      messageBuff.append("Грешка у формату броја у пољу Цена!\n");
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
  	String messagePref = "Недостаје податак: ";
  	if(odeljenje.equals("")){
  		message.append(messagePref+"Одељење!\n");
  	}
  	if(invKnjiga.equals("")){
  		message.append(messagePref+"Инвентарна књига!\n");
  	}  		
  	return message.toString();
  }
  
  public static String validateInvBrojUnique(String invbroj){
    if(isDuplicatedInvBroj(invbroj))
     return "Инвентарни број "+invbroj+" је већ заузет!";
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
				message.append(codeMessagePref+"Начин набавке!\n");
		}
		if(povez!=null && !povez.equals("")){
			if(!BisisApp.appConfig.getCodersHelper().isValidPovez(povez))
				message.append(codeMessagePref+"Повез!\n");
		}
		if(podlokacija!=null && !podlokacija.equals("")){
			if(!BisisApp.appConfig.getCodersHelper().isValidPodlokacija(podlokacija))
				message.append(codeMessagePref+"Подлокација (сигнатура)!\n");
		}		
		if(format!=null && !format.equals("")){
			if(!BisisApp.appConfig.getCodersHelper().isValidFormat(format))
				message.append(codeMessagePref+"Формат (сигнатура)!\n");
		}
		if(intOznaka!=null && !intOznaka.equals("")){
			if(!BisisApp.appConfig.getCodersHelper().isValidInternaOznaka(intOznaka))
				message.append(codeMessagePref+"Интерна ознака (сигнатура)!\n");
		}
		if(odeljenje!=null && !odeljenje.equals("")){
			if(!BisisApp.appConfig.getCodersHelper().isValidOdeljenje(odeljenje))
				message.append(codeMessagePref+"Одељење!\n");
		}
		  if(status!=null && !status.equals("")){
			  if(!BisisApp.appConfig.getCodersHelper().isValidStatus(status))
				  message.append(codeMessagePref+"Статус!\n");
		  }
    if(dostupnost!=null && !dostupnost.equals("")){
      if(!BisisApp.appConfig.getCodersHelper().isValidDostupnost(dostupnost))
        message.append(codeMessagePref+"Доступност!\n");
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
				message.append(dv.isValid(datumRacuna)+" (поље Датум рачуна)\n");
		}
		
		if(datumInventarisanja!=null && !datumInventarisanja.equals("")){
			if(!dv.isValid(datumInventarisanja).equals(""))
				message.append(dv.isValid(datumInventarisanja)+" у пољу Датум инвентарисања!\n");
		}	
		if(datumStatusa!=null && !datumStatusa.equals("")){
			if(!dv.isValid(datumStatusa).equals(""))
				message.append(dv.isValid(datumStatusa)+" у пољу Датум статуса!\n");
		}	
		//cena (da li je broj)		
		if(cena!=null && !cena.equals("")){
			if(!isValidCena(cena))	
				message.append("Грешка у формату броја у пољу Цена!\n");
		}		
		return message.toString();
	}
	
	private static String validateInventarniBroj(String content){
		if(content.length()!=11 && isNumberStr(content)) return "Инвентарни број мора бити 11 цифара!";
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
          message = "Погрешан знаг у инв. броју: ";
      }
		}
		if(message.equals("") && content.length()<11){						
			message = "Минимална дужина инв. броја је 11 знакова!";
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
