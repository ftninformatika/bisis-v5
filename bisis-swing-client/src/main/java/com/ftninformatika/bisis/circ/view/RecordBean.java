package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.models.coders.ItemStatus;
import com.ftninformatika.bisis.records.*;
import com.ftninformatika.utils.string.Signature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class RecordBean {
  
  private Record rec;
  private List fields1;
  private List fields2;
  private List fields3;
  private String text = "";
  private String empty;
  
  public RecordBean(Record rec){
    this.rec = rec;
  }
  
  private String fieldsToString(List fields){
    String tmp = "";
    Iterator it = fields.iterator();
    while (it.hasNext()){
    	String tmp1 = (String)it.next();
    	if (!tmp1.equals("")){
	      if (!tmp.equals(""))
	        tmp = tmp + "; ";
	      tmp = tmp + tmp1;
    	}
    }
    return tmp;
  }
  
  private String fieldsToString(List fields1, List fields2){
    String tmp = "";
    String item1 = "";
    String item2 = "";
    Iterator it1 = fields1.iterator();
    Iterator it2 = fields2.iterator();
    while (it1.hasNext() && it2.hasNext()){
      item1 = it1.next().toString();
      item2 = it2.next().toString();
      if (!item1.equals("") && !item2.equals(""))
      	item1 = item1 + ", ";
      item1 = item1 + item2;
      if (!item1.equals("")){
	      if (!tmp.equals(""))
	        tmp = tmp + "; ";
	      tmp = tmp + item1;
      }
    }
    return tmp;
  }
  
  private String fieldsToString(List fields1, List fields2, List fields3, String fieldname){
    String tmp = "";
    String item1 = "";
    String item2 = "";
    String item3 = "";
    Iterator it1 = fields1.iterator();
    Iterator it2 = fields2.iterator();
    Iterator it3 = fields3.iterator();
    while (it1.hasNext() && it2.hasNext() && it3.hasNext()){
      item1 = it1.next().toString();
      item2 = it2.next().toString();
      item3 = it3.next().toString();
      if (!item1.equals("") && !item2.equals(""))
      	item1 = item1 + ", ";
      item1 = item1 + item2;
      if (!item1.equals("") && !item3.equals(""))
      	item1 = item1 + ", ";
      item1 = item1 + getNameFromCode(fieldname,item3);
      if (!item1.equals("")){
	      if (!tmp.equals(""))
	        tmp = tmp + "; ";
	      tmp = tmp + item1;
      }
    }
    return tmp;
  }
  
  private String getNameFromCode(String field, String content){
   // if (BisisApp.getFormat().getSubfield(field).getCoder().getValue(content) != null)
     // return BisisApp.getFormat().getSubfield(field).getCoder().getValue(content);
    return "";
  }
  
  public String getNaslov(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("200a");
      text = fieldsToString(fields1);
      empty = text.substring(0, 1);
    } catch (Exception e1) {
      try {
        fields1 = rec.getSubfieldsContent("200i");
        text = fieldsToString(fields1);
        empty = text.substring(0, 1);
      } catch (Exception e2) {
        try {
          fields1 = rec.getSubfieldsContent("540a");
          text = fieldsToString(fields1);
        } catch (Exception e3) {
        }
      }
    }
    fields1 = null;
    return text;
  }
  
  public String getAutor(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("700a");
      fields2 = rec.getSubfieldsContent("700b");
      text = fieldsToString(fields1, fields2);
      empty = text.substring(0, 2);
    } catch (Exception e1) {
      try {
        fields1 = rec.getSubfieldsContent("710a"); 
        text = fieldsToString(fields1);
      } catch (Exception e2) {
      }
    }
    fields1 = null;
    fields2 = null;
    return text;
  }
  
  public String getIzdavac(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("210c");
      text = fieldsToString(fields1);
    } catch (Exception e1) {
    }
    fields1 = null;
    return text;
  }
  
  public String getGodinaizdanja(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("100c");
      text = fieldsToString(fields1);
    } catch (Exception e1) {
    }
    fields1 = null;
    return text;
  }
  
  public String getMestoizdanja(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("210a");
      text = fieldsToString(fields1);
    } catch (Exception e1) {
    }
    fields1 = null;
    return text;
  }
  
  public String getSecodgovornost(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("702a");
      fields2 = rec.getSubfieldsContent("702b");
      fields3 = rec.getSubfieldsContent("7024");
      text = fieldsToString(fields1, fields2, fields3, "7024");
    } catch (Exception e1) {
    }
    fields1 = null;
    fields2 = null;
    fields3 = null;
    return text;
  }
  
  public String getPodnaslov(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("200e");
      text = fieldsToString(fields1);
    } catch (Exception e1) {
    }
    fields1 = null;
    return text;
  }
  
  public String getTom(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("200h");
      text = fieldsToString(fields1);
      if (!text.equals(""))
        text = text + ", ";
      fields1 = rec.getSubfieldsContent("200v");
      text = text + fieldsToString(fields1);
    } catch (Exception e1) {
    }
    fields1 = null;
    return text;
  }
  
  public String getBrstrana(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("215a");
      text = fieldsToString(fields1);
    } catch (Exception e1) {
    }
    fields1 = null;
    return text;
  }
  
  public String getIlustracije(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("215c");
      text = fieldsToString(fields1);
    } catch (Exception e1) {
    }
    fields1 = null;
    return text;
  }
  
  public String getDimenzije(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("215d");
      text = fieldsToString(fields1);
    } catch (Exception e1) {
    }
    fields1 = null;
    return text;
  }
  
  public String getPropratnagr(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("215e");
      text = fieldsToString(fields1);
    } catch (Exception e1) {
    }
    fields1 = null;
    return text;
  }
  
  public String getZbirka(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("225a");
      text = fieldsToString(fields1);
      if (!text.equals(""))
        text = text + ", ";
      fields1 = rec.getSubfieldsContent("225i");
      text = text + fieldsToString(fields1);
    } catch (Exception e1) {
    }
    fields1 = null;
    return text;
  }
  
  public String getNapomene(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("300a");
      text = fieldsToString(fields1);
    } catch (Exception e1) {
    }
    fields1 = null;
    return text;
  }
  
  public String getNapomenasadr(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("327a");
      text = fieldsToString(fields1);
    } catch (Exception e1) {
    }
    fields1 = null;
    return text;
  }
  
  public String getPredmetna(){
    if (rec == null)
      return "";
    try {
      Iterator it = rec.getFields().iterator();
      Field fl;
      fields1 = new ArrayList<String>();
      while (it.hasNext()){
        fl = (Field)it.next();
        if ((fl.getName().substring(0,1).equals("6") && !fl.getName().equals("675")) || fl.getName().substring(0,2).equals("96")){
          Iterator it2 = fl.getSubfields().iterator();
          while (it2.hasNext()){
            fields1.add(((Subfield)it2.next()).getContent());
          }
        }
      }
      text = fieldsToString(fields1);
    } catch (Exception e1) {
    }
    fields1 = null;
    return text;
  }
  
  public String getSignatura(){
    if (rec == null)
      return "";
    text = "";
    try {
      Iterator it = rec.getPrimerci().iterator();
      while (it.hasNext()){
        if (!text.equals(""))
          text = text + ", ";
        text = text + Signature.format((Primerak)it.next());
      }
      empty = text.substring(0, 1);
    } catch (Exception e1) {
      Iterator it = rec.getGodine().iterator();
      while (it.hasNext()){
        if (!text.equals(""))
          text = text + ", ";
        text = text + Signature.format((Godina)it.next());
      }
    }
    return text;
  }
  
  public String getSignatura(String ctlgno){
    if (rec == null)
      return "";
    text = "";
    try {
      Iterator it = rec.getPrimerci().iterator();
      while (it.hasNext() && text.equals("")){
        Primerak tmp = (Primerak)it.next();
        if (tmp.getInvBroj().equals(ctlgno))
          text = Signature.format(tmp);
      }
      empty = text.substring(0, 1);
    } catch (Exception e1) {
      Iterator it = rec.getGodine().iterator();
      Iterator it2 = null;
      List ltmp = null;
      Sveska tmp = null;
      while (it.hasNext() && "".equals(text)){
    	ltmp = ((Godina)it.next()).getSveske();
    	if (ltmp != null){
    		it2 = ltmp.iterator();
            while (it2.hasNext() && "".equals(text)){
              tmp = (Sveska)it2.next();
              if (tmp.getInvBroj().equals(ctlgno))
                text = tmp.getSignatura();
            }
    	}          
      }
    }
    return text;
  }
  
  public String getFormat(){
    if (rec == null)
      return "";
    text = "";
    String tmp;
    try {
      Iterator it = rec.getPrimerci().iterator();
      while (it.hasNext()){
        tmp = ((Primerak)it.next()).getSigFormat();
        if (tmp != null){
          if (!text.equals(""))
            text = text + ", ";
          text = text + tmp;
        }
      }
      empty = text.substring(0, 1);
    } catch (Exception e1) {
      Iterator it = rec.getGodine().iterator();
      while (it.hasNext()){
        tmp = ((Godina)it.next()).getSigFormat();
        if (tmp != null){
          if (!text.equals(""))
            text = text + ", ";
          text = text + tmp;
        }
      }
    }
    return text;
  }
  
  public String getNumerus(){
    if (rec == null)
      return "";
    text = "";
    String tmp;
    try {
      Iterator it = rec.getPrimerci().iterator();
      while (it.hasNext()){
        tmp = ((Primerak)it.next()).getSigNumerusCurens();
        if (tmp != null){
          if (!text.equals(""))
            text = text + ", ";
          text = text + tmp;
        }
      }
      empty = text.substring(0, 1);
    } catch (Exception e1) {
      Iterator it = rec.getGodine().iterator();
      while (it.hasNext()){
        tmp = ((Godina)it.next()).getSigNumerusCurens();
        if (tmp != null){
          if (!text.equals(""))
            text = text + ", ";
          text = text + tmp;
        }
      }
    }
    return text;
  }
  
  public String getUdk(){
    if (rec == null)
      return "";
    try {
      fields1 = rec.getSubfieldsContent("675a");
      text = fieldsToString(fields1);
    } catch (Exception e1) {
    }
    return text;
  }
  
  public String getRaspodela(){
    if (rec == null)
      return "";
    HashMap<String,Integer> map = new HashMap<String,Integer>();
    String key = null;
    int num;
    text = "";
    Primerak pr = null;
    ItemStatus sp = null;
    try {
      fields1 = rec.getPrimerci();
      Iterator it = fields1.iterator();
      while (it.hasNext()){
      	pr = (Primerak)it.next();
      	int zaduzivost = 1;
      	if (pr.getStatus() != null && !pr.getStatus().equals("")){
      		//zaduzivost = HoldingsDataCoders.getZaduzivostStatusa(pr.getStatus());
      	}
      	if (pr.getStatus() == null || zaduzivost != 2){
      		key = pr.getOdeljenje();
      		if (key != null){
	      		if (map.containsKey(key)){
	      			map.put(key, new Integer(map.get(key).intValue()+1));
	      		}else
	      			map.put(key, new Integer(1));
      		}
      	}
      }
      List<String> list = new ArrayList<String>();
      list.addAll(map.keySet());
      Collections.sort(list);
      it = list.iterator();
      while (it.hasNext()){
        key = (String)it.next();
      	num = map.get(key).intValue();
      	if (!text.equals(""))
      		text = text + ", ";
      	text = text + "<b>" + key + "</b>-" + num;
      }
    } catch (Exception e1) {
    	e1.printStackTrace();
    }
    return text;
  }
}
