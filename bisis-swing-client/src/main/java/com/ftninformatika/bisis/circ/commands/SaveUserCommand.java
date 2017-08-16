package com.ftninformatika.bisis.circ.commands;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.models.circ.Member;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;



public class SaveUserCommand {
	
	Member user = null;
	List books = null;
	List children = null;
	List removedchildren = null;
	Serializable staleID = null;
	String staleName = null;
	boolean success = false;
	String message = null;
	String userID;
	
	public void setUser(Member user){
		this.user = user;
	}
	
	public Member getUser(){
		return user;
	}
	
	public void setChildren(List children){
		this.children = children;
	}
	
	public void setRemovedChildren(List removedchildren){
		this.removedchildren = removedchildren;
	}
	
	public void setBooks(List books){
		this.books = books;
	}
	
	public void setUserID(String userID){
		this.userID = userID;
	}
	
	public boolean isSaved(){
		return success;
	}
	
	public Serializable getStaleID(){
		return staleID;
	}
	
	public String getStaleName(){
		return staleName;
	}
	
	public String getMessage(){
		return message;
	}


	public void execute() throws IOException {
		BisisApp.bisisService.addUpdateMember(user).execute();
	}

}
