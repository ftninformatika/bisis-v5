/*
 *  Created on 2004.11.7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ftninformatika.bisis.xmlmessaging.communication;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LibraryServerDesc {
	private int libId;
	private String urlAddress;
	private String libName;
	private String libInstitution;

	public LibraryServerDesc(){
		libId=0;
		urlAddress="";
		libName="Unknown library";
		libInstitution="undisclosed";
	}
	
	/**
	 * @param libId
	 * @param urlAddress
	 * @param libName
	 * @param libInstitution
	 */
	public LibraryServerDesc(int libId, String libName,
			String urlAddress, String libInstitution) {
		super();
		this.libId = libId;
		this.urlAddress = urlAddress;
		this.libName = libName;
		this.libInstitution = libInstitution;
	}

	
	/**
	 * @param urlAddress
	 * @param libName
	 * @param libInstitution
	 */
	public LibraryServerDesc(String urlAddress, String libName,
			String libInstitution) {
		super();
		this.urlAddress = urlAddress;
		this.libName = libName;
		this.libInstitution = libInstitution;
	}
	
	//copy constructor
	public LibraryServerDesc(LibraryServerDesc lib) {
		super();
		this.libId = lib.getLibId();
		this.urlAddress = new String(lib.getUrlAddress());
		this.libName = new String(lib.getLibName());
		this.libInstitution = new String(lib.getLibInstitution());
	}

	
	
	/**
	 * @return Returns the libId.
	 */
	public int getLibId() {
		return libId;
	}
	/**
	 * @param libId The libId to set.
	 */
	public void setLibId(int libId) {
		this.libId = libId;
	}
	
	/**
	 * @return Returns the libInstitution.
	 */
	public String getLibInstitution() {
		return libInstitution;
	}
	/**
	 * @param libInstitution The libInstitution to set.
	 */
	public void setLibInstitution(String libInstitution) {
		this.libInstitution = libInstitution;
	}
	/**
	 * @return Returns the libName.
	 */
	public String getLibName() {
		return libName;
	}
	/**
	 * @param libName The libName to set.
	 */
	public void setLibName(String libName) {
		this.libName = libName;
	}
	/**
	 * @return Returns the urlAddress.
	 */
	public String getUrlAddress() {
		return urlAddress;
	}
	/**
	 * @param urlAddress The urlAddress to set.
	 */
	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}
	
	public String toString(){
		return libInstitution;
	}
	
	public boolean equals(LibraryServerDesc obj1){
		if(this.libId!=obj1.libId)
			return false;
		if(!this.libInstitution.equals(obj1.libInstitution))
			return false;
		if(!this.libName.equals(obj1.libName))
			return false;
		if(!this.urlAddress.equals(obj1.urlAddress))
			return false;
		return true;
	}
	
	public String getFullDescriptionString(){
		return ""+libId+" : "+libName+" : "+urlAddress+" : "+libInstitution;
	}
}
