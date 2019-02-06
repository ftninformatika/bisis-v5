package com.ftninformatika.bisis.editor.invholes;


public class InvNumberHolesFinder {
	
	public static String getInvHolesfromIndex(String odeljenje, String invKnjiga, int odInt, int doInt){
		StringBuffer retVal = new StringBuffer();
 		//	
		int[] hits = {};  					

		for (int brojac = odInt;brojac<=doInt;brojac++){
			String brojStr = String.valueOf(brojac);
			brojStr = "00000000000".substring(0, 7-brojStr.length())+brojStr;
			String invBroj = odeljenje+invKnjiga+brojStr;
			if(hits.length==0)
				 retVal.append(brojStr+"\n");
		}

	 return retVal.toString();		
	}


}
