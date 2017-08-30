package com.ftninformatika.bisis.circ.common;


import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.models.circ.WarningType;
import com.ftninformatika.bisis.models.circ.pojo.CorporateMember;
import com.ftninformatika.bisis.models.circ.pojo.MembershipType;
import com.ftninformatika.bisis.models.circ.pojo.Organization;
import com.ftninformatika.bisis.models.circ.pojo.UserCategory;
import com.ftninformatika.utils.string.StringUtils;
import com.toedter.calendar.JDateChooser;

import java.awt.Component;
import java.awt.Font;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;



public class Utils {

	
	public static void setUIFontSize (float size){
	    java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value instanceof javax.swing.plaf.FontUIResource){
	    	Font font = UIManager.getFont(key);
	  	    if(font != null) {
	  	    	UIManager.put (key,new javax.swing.plaf.FontUIResource(font.deriveFont(size)));
	  	    }
	      } 
	    }
	}
	
	public static float getUIFontSize (){
		java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value instanceof javax.swing.plaf.FontUIResource){
	    	Font font = UIManager.getFont(key);
	  	    if(font != null) {
	  	    	float size = font.getSize2D();
	  	    	return size;
	  	    }
	      } 
	    }
	    return (float)12;
	}
	
	
	public static Object getCmbValue(JComboBox cmb){
		Object item = cmb.getSelectedItem();
		if (item instanceof String){
			return null;
		}else{
			return item;
		}
	}
	
	public static void loadCombo(JComboBox cmb, List data){
		cmb.addItem(" ");
		if (data != null){
			Iterator it = data.iterator();
			while (it.hasNext()){
				cmb.addItem(it.next());
			}
		}
	}
	
	public static void setComboItem(JComboBox cmb, Object obj){
		
		if (obj != null){
			for (int i = 1; i < cmb.getModel().getSize(); i++) {
				if (obj instanceof CorporateMember) {
					if (((CorporateMember)obj).getInstName().equals(((CorporateMember)cmb.getModel().getElementAt(i)).getInstName())){
						 cmb.setSelectedIndex(i);
						 break;
				    }
				} else if (obj instanceof MembershipType){
					if (((MembershipType)obj).getDescription().equals(((MembershipType)cmb.getModel().getElementAt(i)).getDescription())) {
						 cmb.setSelectedIndex(i);
						 break;
				    }
				} else if (obj instanceof Organization){
					if (((Organization)obj).getId().equals(((Organization)cmb.getModel().getElementAt(i)).getId())) {
						 cmb.setSelectedIndex(i);
						 break;
				    }
				} else if (obj instanceof UserCategory){
					if (((UserCategory)obj).getDescription().equals(((UserCategory)cmb.getModel().getElementAt(i)).getDescription())) {
						 cmb.setSelectedIndex(i);
						 break;
				    }
				} else if (obj instanceof WarningType){
					if (((String)obj).equals(((WarningType)cmb.getModel().getElementAt(i)).getDescription())) {
						 cmb.setSelectedIndex(i);
						 break;
				    }
				} else if (obj instanceof Integer){
                    if (((Integer)obj).toString().equals((cmb.getModel().getElementAt(i)).toString())) {
                        cmb.setSelectedIndex(i);
                        break;
                    }
                } else if (obj instanceof String){
                    if (obj.equals(cmb.getModel().getElementAt(i))) {
                        cmb.setSelectedIndex(i);
                        break;
                    }
            } else {
					cmb.setSelectedIndex(0);
				}
			}
		} else {
			cmb.setSelectedIndex(0);
		}
		
	}
	
	public static Integer getInteger(String str){
			try {
				return Integer.valueOf(str);
			} catch (NumberFormatException e) {
				return null;
			}
	}
	
	public static String getString(Integer num){
		if (num == null){
			return "";
		}else{
			return num.toString();
		}
	}
  
  public static void clear(JPanel panel){
    Component[] comps = panel.getComponents();
    Component temp;
    for (int i = 0; i < panel.getComponentCount(); i++){
      temp = comps[i];
      if (temp instanceof JTextField){
        ((JTextField)temp).setText("");
      } else if (temp instanceof JScrollPane){
      	if (((JScrollPane)temp).getViewport().getComponent(0) instanceof JTextArea){
	      	((JTextArea)((JScrollPane)temp).getViewport().getComponent(0)).setText("");
      	}
      } else if (temp instanceof JComboBox){
    	  if(((JComboBox)temp).getSelectedItem()!=null){
    	   ((JComboBox)temp).setSelectedIndex(0);
    	  }
      } else if (temp instanceof JDateChooser){
        ((JDateChooser)temp).setDate(null);
      } 
    }
  }
  
  public static String makeUserId(String loc, String number){
	if (number == null || number.equals("")){
		return null;
	}
    String userId;
    if (Cirkulacija.getApp().getEnvironment().getUseridPrefix() && !loc.equals("")){
      userId = StringUtils.padChars(loc, '0', Cirkulacija.getApp().getEnvironment().getUseridPrefixLength()) +
          StringUtils.padChars(number, '0', Cirkulacija.getApp().getEnvironment().getUseridLength()-Cirkulacija.getApp().getEnvironment().getUseridPrefixLength());
    } else {
      userId = StringUtils.padChars(number, '0', Cirkulacija.getApp().getEnvironment().getUseridLength());
    } 
    return userId;
  }

  public static Date setMaxDate(Date d) {
  
  	Calendar max=Calendar.getInstance(); 
  	max.setTime(d);
  	if(max.get(Calendar.AM_PM)==Calendar.AM){
  		max.set(Calendar.HOUR,23);
  	}else{
  		max.set(Calendar.HOUR,11);
      }
      max.set(Calendar.MINUTE, max.getActualMaximum(Calendar.MINUTE));
      max.set(Calendar.SECOND, max.getActualMaximum(Calendar.SECOND));
      max.set(Calendar.MILLISECOND, max.getActualMaximum(Calendar.MILLISECOND));
      return max.getTime();
  			
  }
  
  public static Date setMinDate(Date d) {
    
  	Calendar min=Calendar.getInstance(); 
  	min.setTime(d);
  	if(min.get(Calendar.AM_PM)==Calendar.AM){
  		min.set(Calendar.HOUR,0);
  	}else{
  		min.set(Calendar.HOUR,-12);
      }
  	min.set(Calendar.MINUTE, min.getActualMinimum(Calendar.MINUTE));
  	min.set(Calendar.SECOND, min.getActualMinimum(Calendar.SECOND));
    min.set(Calendar.MILLISECOND, min.getActualMinimum(Calendar.MILLISECOND));
  	return min.getTime();
       
  }
  
  public static String toLocaleDate(Date d){
  	if(d!=null){
    	Calendar min=Calendar.getInstance(); 
    	min.setTime(d);
    	String date = DateFormat.getDateInstance().format(min.getTime());
    	return date;
  	}
  	else {
  		return "";
  	}
  
  }
  public static Date setFirstDate(Date d){
  	Calendar max=Calendar.getInstance(); 
  	max.setTime(d);
  	if(max.get(Calendar.AM_PM)==Calendar.AM){
  		max.set(Calendar.HOUR,0);
  	}else{
  		max.set(Calendar.HOUR,-12);
    }
  	max.set(Calendar.DATE,1);
  	max.set(Calendar.MONTH,0);
  	max.set(Calendar.MINUTE, max.getActualMinimum(Calendar.MINUTE));
  	max.set(Calendar.SECOND, 0);
  	
  	return max.getTime();
  	
  }
  
  
  
  public static String latinToCyr(String str) {
    StringTokenizer st = new StringTokenizer(str);
    String res = "";
    String pom;
    String arabicNum;
    while(st.hasMoreTokens()) {
      pom = st.nextToken();
      arabicNum = arabicNumber(pom);
      if(arabicNum.equals("")) {
        char c, c1=0;
        for(int i=0; i<pom.length(); i++){
          c = pom.charAt(i);
          if(i<(pom.length()-1))
            c1 = pom.charAt(i+1);
          switch (c) {
            case 'a': res+="\u0430";
                      break;
            case 'b': res+="\u0431";
                      break;
            case 'c': res+="\u0446";
                      break;
            case '\u010D': res+="\u0447";
                            break;
            case '\u0107': res+="\u045B";
                            break;
            case 'd': if(i < (str.length()-1)) {
                        switch(c1) {
                          case '\u017E': res+="\u045F";
                                    i++;
                                    break;
                          case '\u017D': res+="\u045F";
                            			i++;
                            			break;          
                          case 'j': res+="\u0452";
                                    i++;
                                    break;
                          case 'J': res+="\u0452";
                            			i++;
                            			break;         
                          default: res+="\u0434";
                        }
                      }
                      else {
                        res+="\u0434";
                      }
                      break;
            case '\u0111': res+="\u0452";
            		  break;
            case 'e': res+="\u0435";
                      break;
            case 'f': res+="\u0444";
                      break;
            case 'g': res+="\u0433";
                      break;
            case 'h': res+="\u0445";
                      break;
            case 'i': res+="\u0438";
                      break;
            case 'j': res+="\u0458";
                      break;
            case 'k': res+="\u043A";
                      break;
            case 'l': if(i < (str.length()-1)) {
                        switch(c1) {
                          case 'j': res+="\u0459";
                                    i++;
                                    break;
                          case 'J': res+="\u0459";
                            			i++;
                            			break;          
                          default: res+="\u043B";
                        }
                      }
                      else {
                        res+="\u043B";
                      }
                      break;
            case 'm': res+="\u043C";
                      break;
            case 'n': if(i < (str.length()-1)) {
                        switch(c1) {
                          case 'j': res+="\u045A";
                                    i++;
                                    break;
                          case 'J': res+="\u045A";
                            			i++;
                            			break;          
                          default: res+="\u043D";
                        }
                      }
                      else {
                        res+="\u043D";
                      }
                      break;
            case 'o': res+="\u043E";
                      break;
            case 'p': res+="\u043F";
                      break;
            case 'r': res+="\u0440";
                      break;
            case 's': res+="\u0441";
                      break;
            case '\u0161': res+="\u0448";
                      break;
            case 't': res+="\u0442";
                      break;
            case 'u': res+="\u0443";
                      break;
            case 'v': res+="\u0432";
                      break;
            case 'z': res+="\u0437";
                      break;
            case '\u017E': res+="\u0436";
                      break;
            case 'A': res+="\u0410";
                      break;
            case 'B': res+="\u0411";
                      break;
            case 'C': res+="\u0426";
                      break;
            case '\u010C': res+="\u0427";
                           break;
            case '\u0106': res+="\u040B";
                           break;
            case 'D': if(i < (str.length()-1)) {
                        switch(c1) {
                          case '\u017E': res+="\u040F";
                                    i++;
                                    break;
                          case '\u017D': res+="\u040F";
		                            i++;
		                            break;
                          case 'j': res+="\u0402";
                                    i++;
                                    break;
                          case 'J': res+="\u0402";
                            			i++;
                            			break;
                          default: res+="\u0414";
                        }
                      }
                      else {
                        res+="\u0414";
                      }
            		  break;
            case '\u0110': res+="\u0402";
                      break;
            case 'E': res+="\u0415";
                      break;
            case 'F': res+="\u0424";
                      break;
            case 'G': res+="\u0413";
                      break;
            case 'H': res+="\u0425";
                      break;
            case 'I': res+="\u0418";
                      break;
            case 'J': res+="\u0408";
                      break;
            case 'K': res+="\u041A";
                      break;
            case 'L': if(i < (str.length()-1)) {
                        switch(c1) {
                          case 'j': res+="\u0409";
                                    i++;
                                    break;
                          case 'J': res+="\u0409";
                            			i++;
                            			break;        
                          default: res+="\u041B";
                        }
                      }
                      else {
                        res+="\u041B";
                      }
                      break;
            case 'M': res+="\u041C";
                      break;
            case 'N': if(i < (str.length()-1)) {
                        switch(c1) {
                          case 'j': res+="\u040A";
                                    i++;
                                    break;
                          case 'J': res+="\u040A";
                            			i++;
                            			break;         
                          default: res+="\u041D";
                        }
                      }
                      else {
                        res+="\u041D";
                      }
                      break;
            case 'O': res+="O";
                      break;
            case 'P': res+="\u041F";
                      break;
            case 'R': res+="\u0420";
                      break;
            case 'S': res+="\u0421";
                      break;
            case '\u0160': res+="\u0428";
                      break;
            case 'T': res+="\u0422";
                      break;
            case 'U': res+="\u0423";
                      break;
            case 'V': res+="\u0412";
                      break;
            case 'Z': res+="\u0417";
                      break;
            case '\u017D': res+="\u0416";
                      break;
            default: res+=""+c;
          }
        }
      }
      else {
        res+=arabicNum;
      }
      res+=" ";
    }
    return res;
  }


	public static String cyrToLatin(String str) {
	    StringTokenizer st = new StringTokenizer(str);
	    String res = "";
	    String pom;
	    String arabicNum;
	    while(st.hasMoreTokens()) {
	      pom = st.nextToken();
	      arabicNum = arabicNumber(pom);
	      if(arabicNum.equals("")) {
	        char c, c1=0;
	        for(int i=0; i<pom.length(); i++){
	          c = pom.charAt(i);
	          if(i<(pom.length()-1))
	            c1 = pom.charAt(i+1);
	          switch (c) {
	            case '\u0430': res+="a";
	                      break;
	            case '\u0431': res+="b";
	                      break;
	            case '\u0446': res+="c";
	                      break;
	            case '\u0447': res+="\u010D";
	                        break;
	            case '\u045B': res+="\u0107";
	                        break;
	            case '\u0434': res+="d";
	      				  break;
	            case '\u045F': res+="d\u017E";
	      				  break;
	            case '\u0452': res+="\u0111";
	            		  break;
	            case '\u0435': res+="e";
	                      break;
	            case '\u0444': res+="f";
	                      break;
	            case '\u0433': res+="g";
	                      break;
	            case '\u0445': res+="h";
	                      break;
	            case '\u0438': res+="i";
	                      break;
	            case '\u0458': res+="j";
	                      break;
	            case '\u043A': res+="k";
	                      break;
	            case '\u043B': res+="l";
	            		  break;
	            case '\u0459': res+="lj";
	            		  break;     
	            case '\u043C': res+="m";
	                      break;
	            case '\u043D': res+="n";
	            		  break;
	            case '\u045A': res+="nj";
	            		  break;
	            case '\u043E': res+="o";
	                      break;
	            case '\u043F': res+="p";
	                      break;
	            case '\u0440': res+="r";
	                      break;
	            case '\u0441': res+="s";
	                      break;
	            case '\u0448': res+="\u0161";
	                      break;
	            case '\u0442': res+="t";
	                      break;
	            case '\u0443': res+="u";
	                      break;
	            case '\u0432': res+="v";
	                      break;
	            case '\u0437': res+="z";
	                      break;
	            case '\u0436': res+="\u017E";
	                      break;
	            case '\u0410': res+="A";
	                      break;
	            case '\u0411': res+="B";
	                      break;
	            case '\u0426': res+="C";
	                      break;
	            case '\u0427': res+="\u010C";
	                      break;
	            case '\u040B': res+="\u0106";
	                      break;
	            case '\u0414': res+="D";
	            		  break;
	            case '\u040F': res+="D\u017E";
	                        break;			   
	            case '\u0402': res+="\u0110";
	                      break;
	            case '\u0415': res+="E";
	                      break;
	            case '\u0424': res+="F";
	                      break;
	            case '\u0413': res+="G";
	                      break;
	            case '\u0425': res+="H";
	                      break;
	            case '\u0418': res+="I";
	                      break;
	            case '\u0408': res+="J";
	                      break;
	            case '\u041A': res+="K";
	                      break;
	            case '\u041B': res+="L";
	            		  break;
	            case '\u0409': res+="Lj";
	  					  break;        		  
	            case '\u041C': res+="M";
	                      break;
	            case '\u041D': res+="N";
	            		  break;
	            case '\u040A': res+="Nj";
	            		  break;
	            case 'O': res+="O";
	                      break;
	            case '\u041F': res+="P";
	                      break;
	            case '\u0420': res+="R";
	                      break;
	            case '\u0421': res+="S";
	                      break;
	            case '\u0428': res+="\u0160";
	                      break;
	            case '\u0422': res+="T";
	                      break;
	            case '\u0423': res+="U";
	                      break;
	            case '\u0412': res+="V";
	                      break;
	            case '\u0417': res+="Z";
	                      break;
	            case '\u0416': res+="\u017D";
	                      break;
	            default: res+=""+c;
	          }
	        }
	      }
	      else {
	        res+=arabicNum;
	      }
	      res+=" ";
	    }
	    return res;
	  }
	
	  private static String arabicNumber(String str) {
	    if(str.equals("I"))
	      return "I";
	    else if(str.equals("II"))
	      return "II";
	    else if(str.equals("III"))
	      return "III";
	    else if(str.equals("IV"))
	      return "IV";
	    else if(str.equals("V"))
	      return "V";
	    else if(str.equals("VI"))
	      return "VI";
	    else if(str.equals("VII"))
	      return "VII";
	    else if(str.equals("VIII"))
	      return "VIII";
	    else if(str.equals("IX"))
	      return "IX";
	    else if(str.equals("X"))
	      return "X";
	    else if(str.equals("XI"))
	      return "XI";
	    else if(str.equals("XII"))
	      return "XII";
	    else if(str.equals("XIII"))
	      return "XIII";
	    else if(str.equals("XIV"))
	      return "XIV";
	    else if(str.equals("XV"))
	      return "XV";
	    else if(str.equals("XVI"))
	      return "XVI";
	    else if(str.equals("XVII"))
	      return "XVII";
	    else if(str.equals("XVIII"))
	      return "XVIII";
	    else if(str.equals("XIX"))
	      return "XIX";
	    else if(str.equals("XX"))
	      return "XX";
	    else if(str.equals("XXI"))
	      return "XXI";
	    else
	      return "";
	  }
	  
	public static String convert(String str, boolean cyr){
	  if (str != null){
		  if (cyr){
			  return latinToCyr(str);
		  }else{
			  return cyrToLatin(str);
		  }
	  }
	  return null;
	}
	  
	
	public static int dayDistance(Calendar date1, Calendar date2){
	  int noDays=0;
	  
	  if (date1.before(date2)){
		  Calendar temp = Calendar.getInstance();
		  temp.setTimeInMillis(date1.getTimeInMillis());
		  while (temp.before(date2)) {
		      temp.add(Calendar.DATE, 1);
		      noDays++;
		  }
	  }else if (date1.after(date2)){
		  Calendar temp = Calendar.getInstance();
		  temp.setTimeInMillis(date2.getTimeInMillis());
		  while (temp.before(date1)) {
		      temp.add(Calendar.DATE, 1);
		      noDays++;
		  }
	  }
	  return noDays;
	}
	
	public static String generatePin(){
		StringBuilder pin = new StringBuilder();
		Random randomGenerator = new Random();
	    for (int i = 0; i <= 3; i++){
	      pin.append(randomGenerator.nextInt(10));
	    }
		return pin.toString();
	}

	
}
