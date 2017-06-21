/*
 *  Created on 2004.10.3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ftninformatika.bisis.xmlmessaging.util;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RecordBriefs {
	private int docId;
	private String autor;
	private String title;
	private String publisher;
	private String publicYear;
	private String language;
	private String country;
	
	
	/**
	 * @param docId
	 * @param autor
	 * @param title
	 * @param publisher
	 * @param publicYear
	 * @param language
	 * @param country
	 */
	public RecordBriefs(int docId, String autor, String title,
			String publisher, String publicYear, String language, String country) {
		super();
		this.docId = docId;
		this.autor = autor;
		this.title = title;
		this.publisher = publisher;
		this.publicYear = publicYear;
		this.language = language;
		this.country = country;
	}
	/**
	 * @return Returns the language.
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language The language to set.
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return Returns the autor.
	 */
	public String getAutor() {
		return autor;
	}
	/**
	 * @param autor The autor to set.
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}
	/**
	 * @return Returns the country.
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return Returns the docId.
	 */
	public int getDocId() {
		return docId;
	}
	/**
	 * @param docId The docId to set.
	 */
	public void setDocId(int docId) {
		this.docId = docId;
	}
	/**
	 * @return Returns the publicYear.
	 */
	public String getPublicYear() {
		return publicYear;
	}
	/**
	 * @param publicYear The publicYear to set.
	 */
	public void setPublicYear(String publicYear) {
		this.publicYear = publicYear;
	}
	/**
	 * @return Returns the publisher.
	 */
	public String getPublisher() {
		return publisher;
	}
	/**
	 * @param publisher The publisher to set.
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String toString(){
//		String retVal="<font face=\"sans-serif\" size=\"-1\">\n";
//		retVal+="<i>autor</i>: "+autor+"<br>\n";
//		retVal+="<i>naslov</i>: "+title+"<br>\n";
//		retVal+="<i>izdavac</i>: "+publisher+"<br>\n";
//		retVal+="<i>godina</i>: "+publicYear+"<br>\n";
//		retVal+="<i>jezik dela</i>: "+language+"<br>\n";
//		retVal+="<i>zemlja</i>: "+country+"\n";
//        retVal+="</font>\n";
//		return retVal;
      String retVal="";
      if (autor != null && !autor.equals(""))
        retVal += autor + ". ";
      retVal += "<i>" + title + "</i>. ";
      retVal += publisher;
      if (!publicYear.equals("")) {
        if (!publisher.equals(""))
          retVal += ", ";
        retVal += publicYear + ". ";
      }
      //retVal+=country;
      return retVal;
	}
}
