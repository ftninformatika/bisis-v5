package com.ftninformatika.bisis.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Subsubfield implements Serializable {

  /**
   * Default constructor.
   */
  public Subsubfield() {
    name = ' ';
    content = "";
  }

  /**
   * Constructs a subsubfield with the given name.
   * @param name The name of the subsubfield
   */
  public Subsubfield(char name) {
    this.name = name;
    content = "";
  }

  /**
   * Returns a printable string representation of this subsubfield.
   */
  public String toString() {
    StringBuffer retVal = new StringBuffer();
    retVal.append("<");
    retVal.append(name);
    retVal.append(">");
    retVal.append(content);
    return retVal.toString();
  }
  
  /**
   * Trims the content of this subsubfield.
   */
  public void trim() {
    content = content.trim();
  }

  /**
   * @return Returns the content.
   */
  public String getContent() {
    return content;
  }
  /**
   * @param content The content to set.
   */
  public void setContent(String content) {
    this.content = content;
  }
  /**
   * @return Returns the name.
   */
  public char getName() {
    return name;
  }
  /**
   * @param name The name to set.
   */
  public void setName(char name) {
    this.name = name;
  }
  /** subsubfield name */
  private char name;
  /** subsubfield content */
  private String content;
}