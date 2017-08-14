package com.ftninformatika.bisis.circ.common;

import java.util.Date;

public class SearchOperandModel implements java.io.Serializable {
	private UsersPrefix label;
	private Date start=null;
	private Date end=null;
	private Object location=null;
	private Object value = null;
	
	public SearchOperandModel(){
	}

	
	 public UsersPrefix getLabel(){
		 return label;
	 }
	 
	 public Object getLocation(){
		 return location;
	 }
	 
	 public Date getStart(){
		 return start;
	 }
	 
	 public Date getEnd(){
		 return end;
	 }
	 
	 public Object getValue(){
		 return value;
	 }
 
	public void setEnd(Date end) {
		this.end = end;
	}
	
	public void setLabel(UsersPrefix label) {
		this.label = label;
	}
	
	public void setLocation(Object location) {
		this.location = location;
	}
	
	public void setStart(Date start) {
		this.start = start;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
}
