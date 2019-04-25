package com.ftninformatika.bisis.editor.groupinv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class GroupInvFileUtils {	
	
	public static File err = null;
	public static PrintWriter errwriter = null;	
	
	public static List<String> readFile(File f){		
	 err = new File(f.getParent()+"/neispravni_inventarni_brojevi.txt");			
		List<String> retVal = new ArrayList<String>();		
		try {
			errwriter = new PrintWriter(err);			
			RandomAccessFile in = new RandomAccessFile(f,"r");
			String line;			
			while ((line = in.readLine()) != null) {
				String invBr = parseLine(line);
				if(invBr!=null)
					retVal.add(invBr);
				else 
					errwriter.append(line+"\n");
			}	
			errwriter.flush();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;		
	}
	
	/*
	 * parsira liniju i vraca inventarni broj
	 * u liniji se moze nalaziti broj od 11 cifara
	 * ili podatak u formatu invbr/ogranak
	 */
	public static String parseLine(String line){		
		if(line.contains("/")){
			String odeljenje = line.substring(line.indexOf("/")+1);
			if(odeljenje.length()!=2) return null;
			String broj = line.substring(0,line.indexOf("/"));
			if(broj.length()>9 || !isNumber(broj)) return null;
			return odeljenje+"00000000".substring(0,9-broj.length())+broj;			
		}else{		
			return line;
		}		
	}
	
	public static boolean isNumber(String str){
		for(int i=0;i<str.length();i++)
			if(!Character.isDigit(str.charAt(i)))
				return false;
		return true;
			
	}
	
	public static void main(String[] args){

//		System.out.println(parseLine("19933/00"));
		System.out.println(null + "asdsada");
	}

}
