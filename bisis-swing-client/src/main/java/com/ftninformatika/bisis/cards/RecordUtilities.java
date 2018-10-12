package com.ftninformatika.bisis.cards;

import com.ftninformatika.bisis.records.*;
import com.ftninformatika.utils.Messages;

import java.util.ArrayList;
import java.util.List;

public class RecordUtilities {
	
	private Record record;
	
	public RecordUtilities(Record record){
		this.record = record;
	}

	//vraca broj primeraka odnsotno svezaka za tekuci record
	public String getBrojPrimerakaSvezakaNS(){
		StringBuffer retVal = new StringBuffer();
		retVal.append(" = ");
		if(record.getPrimerci() != null && record.getPrimerci().size() > 0) {
			int cnt = 0;
			for(Primerak p: record.getPrimerci()) {
				if(p.getStatus() != null && (p.getStatus().equals("A") || p.getStatus().equals("5")))
					cnt++;
			}
			retVal.append(cnt);
		}

		else if(record.getGodine() != null && record.getGodine().size() > 0){
			int cnt = 0;
			for(Godina g: record.getGodine()) {
				if(g.getSveske() != null && g.getSveske().size() > 0)
					for(Sveska s: g.getSveske()) {
						if(s.getStatus() != null && (s.getStatus().equals("A") || s.getStatus().equals("5")))
							cnt++;
					}
			}
			retVal.append(cnt);
		}
		retVal.append(Messages.getString("CARDS_PIECES"));
		
		return retVal.toString();
	}
	
	public String getRaspodelaNSCirc(){
		int brojOgranaka = 50;
		try{
		// niz koji na i-tom mestu ima broj primeraka u i-tom ogranku
		int [] brojPrimerakaUOgranku = new int[brojOgranaka];
		for(int i=0;i<brojPrimerakaUOgranku.length;i++)
			brojPrimerakaUOgranku[i]=0;
		// matrica koja na mestu (i,j) sadrzi broj primeraka koji
		// su iz ogranka i prebaceni u ogranak j
		int[][]preusmereni = new int[brojOgranaka][brojOgranaka];		
		
		boolean firstOdeljenje = true;
		for(int i=0;i<brojOgranaka;i++)
			for(int j=0;j<brojOgranaka;j++)
				preusmereni[i][j]=0; 
		
				
		int odeljenje = 0;
		int odeljenjeUInvBroju = 0;
		for(Primerak p:record.getPrimerci()){
			if(p.getStatus()!=null && !p.getStatus().equals("9")){
			if(p.getOdeljenje()!=null && !p.getOdeljenje().equals("")){
				try{
				odeljenje = Integer.parseInt(p.getOdeljenje());
				brojPrimerakaUOgranku[odeljenje]++;
				}catch(NumberFormatException e){
					
				}
				if(!p.getInvBroj().substring(0,2).equals(p.getOdeljenje())){
					try{
						odeljenjeUInvBroju = Integer.parseInt(p.getInvBroj().substring(0, 2));
						preusmereni[odeljenjeUInvBroju][odeljenje]++;
					}catch(NumberFormatException e){						
					}					
				}		
			}
		 }
		}
		
		// ispis u string
		StringBuffer retVal = new StringBuffer();
		for(int ogranak=0;ogranak<brojPrimerakaUOgranku.length;ogranak++){			
			if(brojPrimerakaUOgranku[ogranak]!=0 || imaPreusmerenih(getOgranakStrFromInt(ogranak))){
				if(!firstOdeljenje)
					retVal.append(",&nbsp;");
				retVal.append("<B>"+getOgranakStrFromInt(ogranak)+"</B>"+"-"+brojPrimerakaUOgranku[ogranak]);
				firstOdeljenje = false;
				if(imaPreusmerenih(getOgranakStrFromInt(ogranak))){
					retVal.append("&nbsp;(");
					boolean first = true;
					for(int j=0;j<brojOgranaka;j++){
						if(preusmereni[ogranak][j]!=0){
							if(!first)
								retVal.append(",&nbsp;");						
							retVal.append("<B>"+getOgranakStrFromInt(j)+"</B>"+"-"+preusmereni[ogranak][j]);
							first = false;
							
						}							
					}
					retVal.append(")");					
				}				
			}
		}
		return retVal.toString();
		}catch(Exception e){			
			e.printStackTrace();
			return "";
		}
	}
	
	/*
	 * da li u zapisu record ima primeraka koji su preusmereni
	 * negde iz ogranka ogranak
	 * odnosno da li postoji inventarni broj koji pocinje sa ogranak
	 * a da mu je ogranak u polju odeljenje razlicit
	 */
	private boolean imaPreusmerenih(String ogranak){
		for(Primerak p:record.getPrimerci())
			if(p.getInvBroj().startsWith(ogranak) 
					&& !p.getInvBroj().substring(0,2).equals(p.getOdeljenje()))
				return true;
		return false;
	}
	 
	private String getOgranakStrFromInt(int ogranak){
		if(ogranak<10)
			return "0"+String.valueOf(ogranak);
		else
			return String.valueOf(ogranak);
	}
	/*
	 * ako ima vise polja sa datim potpoljem vraca njihove sadrzaje u listi
	 * na primer ako ima vise polja 446 i u njima potpolje a, vraca sve sadrzaje 446a u listi
	 */
	
	public List<String> getSubfieldsContent(String sfName){		
		System.out.println("getSubfieldsContent"+sfName);
		String fieldName = sfName.substring(0,3);	
		char sfnameChar = sfName.charAt(3);
		List<String> retVal = new ArrayList<String>();
		if(record.getField(fieldName)!=null){
			for(Field f:record.getFields(fieldName)){
				if(f.getSubfield(sfnameChar)!=null && f.getSubfieldContent(sfnameChar)!=null && !f.getSubfieldContent(sfnameChar).equals(""))
					retVal.add(f.getSubfieldContent(sfnameChar));				
			}			
		}		
		return retVal;
	}
	
	/*
	 * univerzalno za sve
	 * sfName je broj polja + potpolje, na primer 200a
	 */
	public String getSubfieldContent(String sfName){		
		if(record.getSubfieldContent(sfName)==null) return "";
 		return record.getSubfieldContent(sfName);		
	}
	
	/*
	 * za polja 4XX potpolje x, samo do zagrade
	 */
	
	public String getSubfieldContentx(String sfName){		
		if(record.getSubfieldContent(sfName)==null) return "";
		String content = record.getSubfieldContent(sfName);
		if(!content.contains("(")) return content;
		String retVal = content.substring(0,content.indexOf('(')).trim();
 		return retVal;		
	}
	
	
	public String getSubfieldContent(Record rec,String sfName){
		if(rec.getSubfieldContent(sfName)==null) return "";
 		return rec.getSubfieldContent(sfName);		
	}
	
	
	/*
	 * Vraca listu predmetnih odrednica za listic
	 *    
	 */
	
	public String getPredmetneOdrednice(){
		try{
		StringBuffer buff = new StringBuffer();
		buff.append("<BR><BR>Predmetne odrednice:&nbsp;");
		String val = "";
		boolean prva = true;	
		if(record.getFields("600").size()>0){
			for(Field f: record.getFields("600")){
				val = "";
				if(f.getSubfield('a')!=null && !f.getSubfieldContent('a').equals(""))
					val = val + f.getSubfieldContent('a');
				if(f.getSubfield('b')!=null && !f.getSubfieldContent('b').equals(""))
					if(val.equals(""))
						val = val + f.getSubfieldContent('b');
					else 
						val = val + ",&nbsp;"+f.getSubfieldContent('b');
				if(f.getSubfield('f')!=null && !f.getSubfieldContent('f').equals("")){
					val = val + "&nbsp;("+f.getSubfieldContent('f')+")";
				}

					if(!val.equals("")){
					if(!prva){
						buff.append(",&nbsp;");
						buff.append("<BR>");
						for (int i=0;i<8;i++){
							buff.append("&nbsp;");
						}
					}
					prva = false;
					val = val + getPododrednice(f);
					buff.append(val);		
				}

			}
		}
		String str = "";
		if(record.getFields("601").size()>0){
			for(Field f : record.getFields("601")){
				val = "";
				if(f.getSubfield('a')!=null && !f.getSubfieldContent('a').equals(""))
					val = val + f.getSubfieldContent('a');
				if(f.getSubfield('b')!=null && !f.getSubfieldContent('b').equals(""))
					if(val.equals(""))
						val = val + f.getSubfieldContent('b');
					else 
						val = val + "."+f.getSubfieldContent('b');
				if(f.getSubfield('c')!=null && !f.getSubfieldContent('c').equals(""))
					if(val.equals(""))
						val = val + f.getSubfieldContent('c');
					else 
						val = val + "&nbsp;("+f.getSubfieldContent('c')+")";
				
				String dfe = "";
				if(f.getSubfield('d')!=null && !f.getSubfieldContent('d').equals(""))					
						dfe = "&nbsp;("+f.getSubfieldContent('d');
				if(f.getSubfield('f')!=null && !f.getSubfieldContent('f').equals(""))	
					if(dfe.equals(""))
						dfe = "&nbsp;("+f.getSubfieldContent('f');
					else
						dfe = dfe+";&nbsp;"+f.getSubfieldContent('f');
						
				if(f.getSubfield('e')!=null && !f.getSubfieldContent('e').equals(""))					 
					if(dfe.equals(""))
						dfe = "&nbsp;("+f.getSubfieldContent('e');
					else
						dfe = dfe+";&nbsp;"+f.getSubfieldContent('e');
				if(!dfe.equals(""))
					dfe = dfe+")";
				val = val+dfe;
				
				if(!val.equals("")){
					if(!prva){				
						buff.append(",&nbsp;");
						buff.append("<BR>");
						for (int i=0;i<8;i++){
							buff.append("&nbsp;");
						}
					}
					prva = false;
					val = val + getPododrednice(f);
					buff.append(val);
				}
			}

		}
		
		if(record.getFields("602").size()>0){
			for(Field field : record.getFields("602")){
				str = izvuciPredmetnuOdrednicu(field);
				if(!str.equals("")){
					if(!prva){				
						buff.append(",&nbsp;");
						buff.append("<BR>");
						for (int i=0;i<8;i++){
							buff.append("&nbsp;");
						}
					}
					prva = false;
					str = str + getPododrednice(field);
					buff.append(str);
				}

			}

		}
		
		if(record.getFields("605").size()>0){
			for(Field field : record.getFields("605")){
				str = izvuciPredmetnuOdrednicu(field);
				if(!str.equals("")){
					if(!prva){				
						buff.append(",&nbsp;");
						buff.append("<BR>");
						for (int i=0;i<8;i++){
							buff.append("&nbsp;");
						}
					}
					prva = false;
					str = str + getPododrednice(field);
					buff.append(str);

				}

			}
		}
		
		if(record.getFields("606").size()>0){
			for(Field field : record.getFields("606")){
				str = izvuciPredmetnuOdrednicu(field);
				if(!str.equals("")){
					if(!prva){
						buff.append(",&nbsp;");
						buff.append("<BR>");
						for (int i=0;i<8;i++){
							buff.append("&nbsp;");
						}
					}
					prva = false;
					str = str + getPododrednice(field);
					buff.append(str);
				}

			}
		}
	 
		if(record.getFields("607").size()>0){
			for(Field field : record.getFields("607")){
				str = izvuciPredmetnuOdrednicu(field);
				if(!str.equals("")){
					if(!prva){
						buff.append(",&nbsp;");
						buff.append("<BR>");
						for (int i=0;i<8;i++){
							buff.append("&nbsp;");
						}
					}
					prva = false;
					str = str + getPododrednice(field);
					buff.append(str);
				}

			}
		}
		
		if(record.getFields("608").size()>0){
			for(Field field : record.getFields("608")){
				str = izvuciPredmetnuOdrednicu(field);
				if(!str.equals("")){
					if(!prva){
						buff.append(",&nbsp;");
						buff.append("<BR>");
						for (int i=0;i<8;i++){
							buff.append("&nbsp;");
						}
					}
					prva = false;
					str = str + getPododrednice(field);
					buff.append(str);
				}

			}
		}
		
		if(record.getFields("609").size()>0){
			for(Field field : record.getFields("609")){
				str = izvuciPredmetnuOdrednicu(field);
				if(!str.equals("")){
					if(!prva){
						buff.append(",&nbsp;");
						buff.append("<BR>");
						for (int i=0;i<8;i++){
							buff.append("&nbsp;");
						}
					}
					prva = false;
					str = str + getPododrednice(field);
					buff.append(str);
				}

			}
		}
		
		if(record.getFields("610").size()>0){
			for(Field field : record.getFields("610")){
				str = izvuciPredmetnuOdrednicu(field);
				if(!str.equals("")){
					if(!prva){
						buff.append(",&nbsp;");
						buff.append("<BR>");
						for (int i=0;i<8;i++){
							buff.append("&nbsp;");
						}
					}
					prva = false;
					str = str + getPododrednice(field);
					buff.append(str);
				}

			}
		}	
		
		return buff.toString();
		}catch(Exception e){
			return "";
		}
		
}
	
	/*
	 * iz polja u obliku 
	 * 607##[a]Evropa[x]istorija[z]Srednji vek
	 * vraca string:
	 * Evropa - istorija - Srednji vek
	 *
	 */
	
	
	private String izvuciPredmetnuOdrednicu(Field f){
		StringBuffer val = new StringBuffer();
		if(f.getSubfield('a')!=null && !f.getSubfieldContent('a').equals("")){
			val.append(f.getSubfieldContent('a'));
			//val.append(getPododrednice(f));
		}
		if(f.getSubfield('b')!=null && !f.getSubfieldContent('b').equals("")){
			val.append(", ");
			val.append(f.getSubfieldContent('b'));
			//val.append(getPododrednice(f));
		}
		
		return val.toString();
	}
	
	public String getPododrednice(Field f){
		StringBuffer val = new StringBuffer();
		if(f.getSubfields('x').size()>0){
			for(Subfield sf:f.getSubfields('x')){
				if(!sf.getContent().equals("")){
					val.append("&nbsp;-&nbsp;");
					val.append(sf.getContent());
				}
		}
	 }
			
		if(f.getSubfields('y').size()>0){
			for(Subfield sf:f.getSubfields('y')){				
					if(!sf.getContent().equals("")){
						val.append("&nbsp;-&nbsp;");
						val.append(sf.getContent());
					}
			}
		}
		
		/*if(f.getSubfields('f').size()>0){
			for(Subfield sf:f.getSubfields('f')){				
					if(!sf.getContent().equals("")){
						val.append("&nbsp;-&nbsp;");
						val.append(sf.getContent());
					}
			}
		}*/
		
		if(f.getSubfields('z').size()>0){
			for(Subfield sf:f.getSubfields('z')){				
					if(!sf.getContent().equals("")){
						val.append("&nbsp;-&nbsp;");
						val.append(sf.getContent());
					}
			}
		}
		
		if(f.getSubfields('w').size()>0){
			for(Subfield sf:f.getSubfields('w')){				
					if(!sf.getContent().equals("")){
						val.append("&nbsp;-&nbsp;");
						val.append(sf.getContent());
					}
			}
		}		
		return val.toString();
		
	}
	
	public String getISSN(){
		if(!getSubfieldContent("011a").equals(""))
			return "ISSN&nbsp;"+getSubfieldContent("011a");
		if(!getSubfieldContent("011e").equals(""))
			return "ISSN&nbsp;"+getSubfieldContent("011e");
		return "";
	}
	
	/*
	 * vraca string kojim se odredjuje podatak 
	 * maticne publikacije u analitickom listicu
	 * string koji ide posle U: 
	 * 
	 */
	public String getMaticnaPublikacijaNS(){
		StringBuffer buff = new StringBuffer();
		Subfield sf4641 = record.getSubfield("4641");
		String naslovMaticne = "";
		if(sf4641!=null){
			try {
				naslovMaticne = sf4641.getSecField().getSubfieldContent('a');
			}catch(NullPointerException e){
				
			}			
		}
		if(!naslovMaticne.equals(""))		
			buff.append(naslovMaticne);
		if(!getSubfieldContent("011a").equals("")){
			buff.append("&nbsp;.&nbsp;");
			buff.append("ISSN&nbsp;");
			buff.append(getSubfieldContent("011a"));			
		}
		if(record.getField("215")!=null){
			if(getSubfieldContent("215g")!=null 
					|| getSubfieldContent("215i")!=null
					|| getSubfieldContent("215h") !=null
					|| getSubfieldContent("215k") !=null
					|| getSubfieldContent("215a")!=null){				
				buff.append("&nbsp;.&nbsp;");
				String dodatak = "";
				if(getSubfieldContent("215g")!=null && !getSubfieldContent("215g").equals("")){					
					dodatak+=getSubfieldContent("215g");
				}
				if(getSubfieldContent("215i")!=null && !getSubfieldContent("215i").equals("")){
						if(!dodatak.equals(""))
							dodatak+=",&nbsp;";
						dodatak+=getSubfieldContent("215i");
				}					
				if(getSubfieldContent("215h")!=null && !getSubfieldContent("215h").equals("")){
						if(!dodatak.equals(""))
							dodatak+=",&nbsp;";
						dodatak+=getSubfieldContent("215h");
				}
				if(getSubfieldContent("215k")!=null && !getSubfieldContent("215k").equals("")){
					if(!dodatak.equals(""))
						dodatak+=",&nbsp;";
					dodatak+=getSubfieldContent("215k");
					dodatak+=getSubfieldContent("()");
			}
				if(getSubfieldContent("215a")!=null && !getSubfieldContent("215a").equals("")){
						if(!dodatak.equals(""))
							dodatak+=",&nbsp;";
						dodatak+=getSubfieldContent("215a");
				}
				buff.append(dodatak);	
				}				
		}	
		
		return buff.toString();		
	}
	
	/*
	 * vraca glavni deo analitickog listica
	 * za serijske publikacije 
	 */
	
	public String getOpisanalitikaSerijske(){
		StringBuffer retVal = new StringBuffer();
		retVal.append("&nbsp;&nbsp;&nbsp;");
		retVal.append(getSubfieldContent("200a"));
		if(!getSubfieldContent("200e").equals("")){
			retVal.append("&nbsp;:&nbsp;");
			retVal.append(getSubfieldContent("200e"));
		}
		if(!getSubfieldContent("200f").equals("")){
			retVal.append("&nbsp;/&nbsp;");
			retVal.append(getSubfieldContent("200f"));
			retVal.append(".");
		}
		if(!getSubfieldContent("215c").equals("")){
			retVal.append("&nbsp;-&nbsp;");
			retVal.append(getSubfieldContent("215c"));
			retVal.append(".");			
		}
		if(!getSubfieldContent("300a").equals("")){			
			for(Field f:record.getFields("300")){
				if(f.getSubfield('a')!=null && !f.getSubfield('a').getContent().equals("")){
					retVal.append("&nbsp;-&nbsp;");
					retVal.append(f.getSubfield('a').getContent());
				}				
			}
			retVal.append(".");
		}
		
		if(!getSubfieldContent("320a").equals("")){			
			for(Field f:record.getFields("320")){
				if(f.getSubfield('a')!=null && !f.getSubfield('a').getContent().equals("")){
					retVal.append("&nbsp;-&nbsp;");
					retVal.append(f.getSubfield('a').getContent());
				}				
			}
			retVal.append(".");
		}
		
		if(!getSubfieldContent("330a").equals("")){			
			for(Field f:record.getFields("330")){
				if(f.getSubfield('a')!=null && !f.getSubfield('a').getContent().equals("")){
					retVal.append("&nbsp;-&nbsp;");
					retVal.append(f.getSubfield('a').getContent());
				}				
			}
			retVal.append(".");
		}		
		return retVal.toString();		
	}
	
	/*
	 * vraca odrednicu za analiticki listic
	 * moze biti da je to i univerzalna odrednica 
	 * za sve listice
	 */
	public String getOdrednicaAnalitika(){
		StringBuffer retVal = new StringBuffer();
		retVal.append("<B>");
		if(!getSubfieldContent("700a").equals("")){
			retVal.append(getSubfieldContent("700a").toUpperCase());
			if(!getSubfieldContent("700b").equals(""))
				retVal.append(",&nbsp;"+getSubfieldContent("700b"));			
		}
		else if(!getSubfieldContent("710a").equals("")){
			String[] reciOdrednice = getSubfieldContent("710a").split(" ");
			for(int i=0;i<reciOdrednice.length;i++){
				if(i==0)
					retVal.append(reciOdrednice[i].toUpperCase());
				else{
				 retVal.append("&nbsp;");
				 retVal.append(reciOdrednice[i]);
				}
			}			
		}else{
			String naslov = getSubfieldContent("200a");
			if(!naslov.equals("")){
				String[] reciNaslova = naslov.split(" ");
				for(int i=0;i<reciNaslova.length;i++){
					if(i<3)
						if(i<1)
							retVal.append(reciNaslova[i].toUpperCase()+"&nbsp;");
						else
							retVal.append(reciNaslova[i]+"&nbsp;");
					
				}
				if(reciNaslova.length>3)
					retVal.append("...");
			}
		}
		retVal.append("</B>");
		return retVal.toString();
	}
	
	public String getMaticnaPublikacijaMR(){		
	/*	StringBuffer retVal = new StringBuffer();
		int mr = record.getMR();
		if(mr!=0){			
			int[] hits;	
			Term t = new Term("RN",String.valueOf(mr));
			TermQuery tq = new TermQuery(t);
			if(BisisApp.isStandalone())
				hits = BisisApp.getRecordManager().select2(tq, null);
			else
				hits = BisisApp.getRecordManager().select2x(SerializationUtils.serialize(tq), null);
			if(hits.length>0){
				Record masterRecord = BisisApp.getRecordManager().getRecord(hits[0]);			
				if(masterRecord!=null){
					// opis maticne publikacije, preuzeta ideja iz iz bisis30 klasa com.gint.app.bisis.editor.report.Concept
					if(masterRecord.getField("200")!=null){
						if(masterRecord.getField("532")!=null){
							if(masterRecord.getField("532").getSubfield('a')!=null)
								retVal.append(masterRecord.getSubfieldContent("532a"));							
						}else{
							Field f200 = masterRecord.getField("200");
							if(f200.getSubfield('a')!=null) retVal.append(StringUtils.adjustForHTML(f200.getSubfieldContent('a')));							
							if(f200.getSubfield('e')!=null && !f200.getSubfieldContent('e').equals("")) 
								retVal.append(StringUtils.adjustForHTML(" : "+f200.getSubfieldContent('e')));
							if(f200.getSubfield('f')!=null && !f200.getSubfieldContent('f').equals("")) 
								retVal.append(StringUtils.adjustForHTML(" / "+f200.getSubfieldContent('f')));							
							if(f200.getSubfield('h')!=null && !f200.getSubfieldContent('h').equals("")){
								retVal.append(StringUtils.adjustForHTML(". "+f200.getSubfieldContent('h')));
								if(f200.getSubfield('i')!=null && !f200.getSubfieldContent('i').equals("")) 
									retVal.append(StringUtils.adjustForHTML(". "+f200.getSubfieldContent('i')));							
							}else{
								if(f200.getSubfield('i')!=null && !f200.getSubfieldContent('i').equals("")) 
									retVal.append(StringUtils.adjustForHTML(", "+f200.getSubfieldContent('i')));		
							}
						}						
					}
					if(masterRecord.getSubfield("011e")!=null && !masterRecord.getSubfieldContent("011e").equals(""))
						retVal.append(StringUtils.adjustForHTML(". - ISSN "+masterRecord.getSubfieldContent("011e")));
						
						
				}
			
			if(masterRecord.getField("215")!=null){
				if(getSubfieldContent(masterRecord,"215a")!=null 
						|| getSubfieldContent(masterRecord,"215c")!=null
						|| getSubfieldContent(masterRecord,"215d") !=null){				
					retVal.append("&nbsp;-&nbsp;");
					String dodatak = "";
					if(getSubfieldContent(masterRecord,"215a")!=null && !getSubfieldContent(masterRecord,"215a").equals("")){					
						dodatak+=getSubfieldContent(masterRecord,"215a");
					}
					if(getSubfieldContent(masterRecord,"215c")!=null && !getSubfieldContent(masterRecord,"215c").equals("")){
							if(!dodatak.equals(""))
								dodatak+="&nbsp;:&nbsp;";
							dodatak+=getSubfieldContent(masterRecord,"215c");
					}					
					if(getSubfieldContent(masterRecord,"215d")!=null && !getSubfieldContent(masterRecord,"215d").equals("")){
							if(!dodatak.equals(""))
								dodatak+="&nbsp;,&nbsp;";
							dodatak+=getSubfieldContent(masterRecord,"215d");
					}					
					retVal.append(dodatak);	
					}				
			}
			}
			return retVal.toString();
		}*/
			
		return "nepoznato";		
	}
	
	
	public String getMaticnaPublikacijaMRSabac(){		
		/*StringBuffer retVal = new StringBuffer();
		int mr = record.getMR();
		if(mr!=0){			
			int[] hits;	
			Term t = new Term("RN",String.valueOf(mr));
			TermQuery tq = new TermQuery(t);
			if(BisisApp.isStandalone())
				hits = BisisApp.getRecordManager().select2(tq, null);
			else
				hits = BisisApp.getRecordManager().select2x(SerializationUtils.serialize(tq), null);
			if(hits.length>0){
				Record masterRecord = BisisApp.getRecordManager().getRecord(hits[0]);			
				if(masterRecord!=null){
					// opis maticne publikacije, preuzeta ideja iz iz bisis30 klasa com.gint.app.bisis.editor.report.Concept
					if(masterRecord.getField("200")!=null){
						if(masterRecord.getField("532")!=null){
							if(masterRecord.getField("532").getSubfield('a')!=null)
								retVal.append(masterRecord.getSubfieldContent("532a"));							
						}else{
							Field f200 = masterRecord.getField("200");
							if(f200.getSubfield('a')!=null) retVal.append(StringUtils.adjustForHTML(f200.getSubfieldContent('a')));							
							if(f200.getSubfield('e')!=null && !f200.getSubfieldContent('e').equals("")) 
								retVal.append(StringUtils.adjustForHTML(" : "+f200.getSubfieldContent('e')));
					}
					if(masterRecord.getSubfield("011e")!=null && !masterRecord.getSubfieldContent("011e").equals(""))
						retVal.append(StringUtils.adjustForHTML(". - ISSN "+masterRecord.getSubfieldContent("011e")));						
				}
			
			if(record.getField("215")!=null){
				if(getSubfieldContent(record,"215i")!=null 
						|| getSubfieldContent(record,"215h")!=null
						|| getSubfieldContent(record,"215k") !=null){				
					retVal.append(".&nbsp;-&nbsp;");
					String dodatak = "";
					if(getSubfieldContent(record,"215i")!=null && !getSubfieldContent(record,"215i").equals("")){					
						dodatak+=getSubfieldContent(record,"215i");
					}
					if(getSubfieldContent(record,"215h")!=null && !getSubfieldContent(record,"215h").equals("")){
							if(!dodatak.equals(""))
								dodatak+=",&nbsp;";
							dodatak+=getSubfieldContent(record,"215h");
					}					
					if(getSubfieldContent(record,"215k")!=null && !getSubfieldContent(record,"215k").equals("")){
							if(!dodatak.equals(""))
								dodatak+="&nbsp;(";
							dodatak+=getSubfieldContent(record,"215k");
							dodatak+=")";
					}	
					if(getSubfieldContent(record,"215a")!=null && !getSubfieldContent(record,"215a").equals("")){
						if(!dodatak.equals(""))
							dodatak+=",&nbsp;";
						dodatak+=getSubfieldContent(record,"215a");
						
				}		
					retVal.append(dodatak);	
					}				
			}
			}
			return retVal.toString();
		}
		}	*/
		return "nepoznato";		
		
	}
	
	
	public String getMaticnaPublikacijaMRSignatura(){		
		/*String retVal = "";
		int mr = record.getMR();
		if(mr!=0){			
			int[] hits;	
			Term t = new Term("RN",String.valueOf(mr));
			TermQuery tq = new TermQuery(t);
			if(BisisApp.isStandalone())
				hits = BisisApp.getRecordManager().select2(tq, null);
			else
				hits = BisisApp.getRecordManager().select2x(SerializationUtils.serialize(tq), null);
			if(hits.length>0){
				Record masterRecord = BisisApp.getRecordManager().getRecord(hits[0]);			
				if(masterRecord!=null){
					if(masterRecord.getGodine()!=null && masterRecord.getGodine().size()>0){
						for(Godina g:masterRecord.getGodine()){
							if(g.getSigPodlokacija().equals("ZP")){
								 if (g.getSigPodlokacija()!=null && !g.getSigPodlokacija().equals(""))
								      retVal += g.getSigPodlokacija() + "-";
								   if (g.getSigIntOznaka()!=null && !g.getSigIntOznaka().equals(""))
								      retVal += g.getSigIntOznaka() + "-";
								   if (g.getSigFormat()!=null && !g.getSigFormat().equals(""))
								      retVal += g.getSigFormat() + "-";
								   if (g.getSigNumerusCurens()!=null && !g.getSigNumerusCurens().equals(""))
								      retVal += g.getSigNumerusCurens();
							}					
							
							return retVal.toString();
						}
						// ako nema podlokacija ZP
						Godina g = masterRecord.getGodine().get(0);
						if (g.getSigPodlokacija()!=null && !g.getSigPodlokacija().equals(""))
						      retVal += g.getSigPodlokacija() + "-";
						   if (g.getSigIntOznaka()!=null && !g.getSigIntOznaka().equals(""))
						      retVal += g.getSigIntOznaka() + "-";
						   if (g.getSigFormat()!=null && !g.getSigFormat().equals(""))
						      retVal += g.getSigFormat() + "-";
						   if (g.getSigNumerusCurens()!=null && !g.getSigNumerusCurens().equals(""))
						      retVal += g.getSigNumerusCurens();
						   return retVal;
						
					}
				}
			}
		
		}	*/
		return "nepoznata signatura";		
		
	}
	
	public String getRecordRN(){
		return String.valueOf(record.getRN());
	}
	
	
	public boolean isCertainFormatSabac(String sfName){
		if(record.getSubfieldContent(sfName)==null) return false;
		if(record.getSubfieldContent(sfName).contains("-") && record.getSubfieldContent(sfName).contains("(")
				&& record.getSubfieldContent(sfName).contains(")"))
			return true;
		return false;
	}
	
	public static String getGodineSabac(Record rec){
		if(rec.getGodine()==null || rec.getGodine().size()==0)
			return "";
		StringBuffer buff = new StringBuffer();
		buff.append("<html>");
		buff.append("<table>");
		buff.append(Messages.getString("CARDS_YEARING_YEAR_NUMBER_HTML"));
		for(Godina g:rec.getGodine()){
			buff.append("<tr>");
			String godiste = g.getGodiste()!=null?g.getGodiste():"";			
			buff.append("<td>"+godiste+"</td>");
			String godina = g.getGodina()!=null?g.getGodina():"";	
			buff.append("<td>"+godina+"</td>");
			String broj = g.getBroj()!=null?g.getBroj():"";	
			buff.append("<td>"+broj+"</td>");		
			buff.append("</tr>");
		}
		buff.append("</table>");
		buff.append("</html>");	
		return buff.toString();
	}
	
	
	
	

}
