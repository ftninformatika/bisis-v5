package com.ftninformatika.bisis.service;

import java.io.Serializable;

/**
 * Represents a record author (a creator or a modifier).
 *  
 * @author mbranko@uns.ns.ac.yu
 */
@SuppressWarnings("serial")
public class Author implements Serializable {

  /**
   * Defaut constructor 
   */
  public Author() {
    this("", "");
    
  }
  
  /**
   * Constructs an author with the given username and institution.
   * @param username
   * @param institution
   */
  public Author(String username, String institution) {
    this.username = username;
    this.institution = institution;
  }
  
  /**
   * Constructs an author with the given username and institution stored in a
   * compact form: username@institution.
   * @param compact
   */
  public Author(String compact) {
    String[] pieces = compact.split("@");
    if (pieces.length != 2)
      return;
    username = pieces[0];
    institution = pieces[1];
  }
  
  /**
   * Retrieves the compact author representation: username@institution.
   */
  public String getCompact() {
    return username + "@" + institution;
  }

  /**
   * @return Returns the institution.
   */
  public String getInstitution() {
    return institution;
  }
  /**
   * @param institution The institution to set.
   */
  public void setInstitution(String institution) {
    this.institution = institution;
  }
  /**
   * @return Returns the username.
   */
  public String getUsername() {
    return username;
  }
  /**
   * @param username The username to set.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /** the librarian username */
  private String username;
  /** the name of the librarian's institution */
  private String institution;
}
