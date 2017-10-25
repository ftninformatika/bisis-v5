package com.ftninformatika.bisis.reports;

import com.ftninformatika.bisis.records.Record;

import java.util.StringTokenizer;


public class ReportsUtils {
	  public static String getAutor(Record rec) {
		    if (rec.getField("700") != null) {
		      String sfa = rec.getSubfieldContent("700a");
		      String sfb = rec.getSubfieldContent("700b");
		      if ((sfa != null)&&(!sfa.equals(""))) {
		        if ((sfb != null)&&(!sfb.equals("")))
		          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
		        else
		          return toSentenceCase(sfa);
		      } else {
		        if (sfb != null)
		          return toSentenceCase(sfb);
		        else
		          return "";
		      }
		    } else if (rec.getField("701") != null) {
		      String sfa = rec.getSubfieldContent("701a");
		      String sfb = rec.getSubfieldContent("701b");
		      if ((sfa != null)&&(!sfa.equals(""))) {
		        if ((sfb != null)&&(!sfb.equals("")))
		          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
		        else
		          return toSentenceCase(sfa);
		      } else {
		        if (sfb != null)
		          return toSentenceCase(sfb);
		        else
		          return "";
		      }
		    } else if (rec.getField("702") != null) {
		      String sfa = rec.getSubfieldContent("702a");
		      String sfb = rec.getSubfieldContent("702b");
		      if ((sfa != null)&&(!sfa.equals(""))) {
		        if ((sfb != null)&&(!sfb.equals("")))
		          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
		        else
		          return toSentenceCase(sfa);
		      } else {
		        if (sfb != null)
		          return toSentenceCase(sfb);
		        else
		          return "";
		      }
		    }
		    return "";
		  }
	  
	 public static String parseHeading(Record rec){
			String heading="";
			heading=parse700(rec);
			if(!heading.equals("")){
				return heading;
			}else{
				heading=parse710(rec);
				if(!heading.equals("")){
					return heading;
				}else{
					heading=parse720(rec);
					if(!heading.equals("")){
						return heading;
					}else{
						heading=parse532(rec);
						if(!heading.equals("")){
							return heading;
						}else{
							heading=parse500(rec);
							if(!heading.equals("")){
								return heading;
							}else{
								heading=parse503(rec);
								return heading;
							
							}
						}
					}
				}
		
		}
	 }
	 
	 private static String parse700(Record rec) {
		 String content="";
		 if (rec.getField("700") != null) {
		      String sfa = rec.getSubfieldContent("700a");
		      String sfb = rec.getSubfieldContent("700b");
		      String sfc = rec.getSubfieldContent("700c");
		      String sff = rec.getSubfieldContent("700f");
		      if((sfa != null) && (sfa.length())>0){
		    	  content=content+sfa;
		      }
		      if ((sfb != null)&&(sfb.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfb;
		      }
		      if ((sfc != null)&&(sfc.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfc;
		      }
		      if ((sff != null)&&(sff.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sff;
		      }
		  }
		 return content;
	 }
	 private static String parse710(Record rec) {
		 String content="";
		 if (rec.getField("710") != null) {
		      String sfa = rec.getSubfieldContent("710a");
		      String sfb = rec.getSubfieldContent("710b");
		      String sfg = rec.getSubfieldContent("710g");
		      String sfh = rec.getSubfieldContent("710h");
		      String sfc = rec.getSubfieldContent("710c");
		      String sfd = rec.getSubfieldContent("710d");
		      String sff = rec.getSubfieldContent("710f");
		      String sfe = rec.getSubfieldContent("710e");
		      if((sfa != null) && (sfa.length())>0){
		    	  content=content+sfa;
		      }
		      if ((sfb != null)&&(sfb.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfb;
		      }
		      if ((sfg != null)&&(sfg.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfg;
		      }
		      if ((sfh != null)&&(sfh.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfh;
		      }
		      if ((sfc != null)&&(sfc.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfc;
		      }
		      if ((sfd != null)&&(sfd.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfd;
		      }
		      if ((sff != null)&&(sff.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sff;
		      }
		      if ((sfe != null)&&(sfe.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfe;
		      }
		  }
		 return content;
	 }
	 
	 private static String parse720(Record rec) {
		 String content="";
		 if (rec.getField("720") != null) {
		      String sfa = rec.getSubfieldContent("720a");
		      String sff = rec.getSubfieldContent("720f");

		      if((sfa != null) && (sfa.length())>0){
		    	  content=content+sfa;
		      }
		      if ((sff != null)&&(sff.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sff;
		      }	      	  
		  }
		 return content;
	 }
	 
	 private static String parse532(Record rec) {
		 String content="";
		 if (rec.getField("532") != null) {
		      String sfa = rec.getSubfieldContent("532a");

		      if((sfa != null) && (sfa.length())>0){
		    	  content=content+sfa;
		      }
		        	  
		  }
		 return content;
	 }
	 
	 private static String parse500(Record rec) {
		 String content="";
		 if (rec.getField("500") != null) {
		      String sfa = rec.getSubfieldContent("500a");
		      String sfb = rec.getSubfieldContent("500b");
		      String sfh = rec.getSubfieldContent("500h");
		      String sfk = rec.getSubfieldContent("500k");
		      String sfl = rec.getSubfieldContent("500l");
		      String sfm = rec.getSubfieldContent("500m");
		      if((sfa != null) && (sfa.length())>0){
		    	  content=content+sfa;
		      }
		      if ((sfb != null)&&(sfb.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfb;
		      }
		      if ((sfh != null)&&(sfh.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfh;
		      }
		      if ((sfk != null)&&(sfk.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfk;
		      }
		      if ((sfl != null)&&(sfl.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfl;
		      }
		      if ((sfm != null)&&(sfm.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfm;
		      }
		      
		  }
		 return content;
	 }
	 
	 private static String parse503(Record rec) {
		 String content="";
		 if (rec.getField("503") != null) {
		      String sfa = rec.getSubfieldContent("503a");
		      String sfj = rec.getSubfieldContent("503j");
		      String sfb = rec.getSubfieldContent("503b");
		      if((sfa != null) && (sfa.length())>0){
		    	  content=content+sfa;
		      }
		      if ((sfj != null)&&(sfj.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfj;
		      }
		      if ((sfb != null)&&(sfb.length()>0)){
			      if (content.length()>0){
			    	  content= content + ", ";
			      }
		          content= content + sfb;
		      }
		 }      
		 return content;
	 }
		 
	  public static String toSentenceCase(String s) {
		    StringBuffer retVal = new StringBuffer();
		    StringTokenizer st = new StringTokenizer(s, " -.", true);
		    while (st.hasMoreTokens()) {
		      String word = st.nextToken();
		      if (word.length() > 0)
		        retVal.append(Character.toUpperCase(word.charAt(0)) + 
		            word.substring(1).toLowerCase());
		        
		    }
		    return retVal.toString();
		  }
	  public static String  trimZeros(String s) {
		    if (s == null)
		      return null;
		    String retVal = s;
		    while (retVal.length() > 0 && retVal.charAt(0) == '0')
		      retVal = retVal.substring(1);
		    return retVal;
		  }  
	  public static String nvl(String s) {
		    return s == null ? " " : s;
		 }
	  public static char getFirstDigit(String s) {
		    if (s.length() == 0)
		      return ' ';
		    int pos = 0;
		    if (s.charAt(0) == '(') {
		      pos = s.indexOf(')') + 1;
		      if (pos == 0 || pos == s.length())
		        pos = 1;
		    }
		    while (pos < s.length()&&!Character.isDigit(s.charAt(pos)))
		      pos++;
		    if (pos == s.length())
		      return ' ';
		    return s.charAt(pos);
		  }
    public static String addZeroes(String invbroj){
    	String zeroes="00000000000";
    	int zeroCount=11-invbroj.length();
    	return zeroes.substring(0,zeroCount)+ invbroj;
    }
}
