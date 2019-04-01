package com.ftninformatika.bisis.editor.invholes;

import com.ftninformatika.bisis.BisisApp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvNumberHolesFinder {
	
	public static String getInvHolesfromIndex(String odeljenje, String invKnjiga, int odInt, int doInt){
		StringBuilder retVal = new StringBuilder();

		String odString = String.valueOf(odInt);
		odString = "00000000000".substring(0, 7 - odString.length()) + odString;
		odString = odeljenje + invKnjiga + odString;

		String doString = String.valueOf(doInt);
		doString = "00000000000".substring(0, 7 - doString.length()) + doString;
		doString = odeljenje + invKnjiga + doString;

		List<Integer> freeInvs = new ArrayList<>();
		try {
			freeInvs = BisisApp.bisisService.findInvHoles(odString, doString).execute().body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (freeInvs == null)
			return retVal.toString();

		for (Integer i: freeInvs) {
			retVal.append(i);
			retVal.append("\n");
		}
		return retVal.toString();
	}


}
