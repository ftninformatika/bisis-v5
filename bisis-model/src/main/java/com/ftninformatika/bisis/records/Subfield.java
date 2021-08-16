package com.ftninformatika.bisis.records;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Subfield implements Serializable {

  /**
   * Default constructor.
   */
  public Subfield() {
    name = ' ';
    content = "";
    subsubfields = new ArrayList<Subsubfield>();
    secField = null;
  }

  /**
   * Constructs a subfield with the given name.
   * @param name The name of the subfield
   */
  public Subfield(char name) {
    this.name = name;
    content = "";
    subsubfields = new ArrayList<Subsubfield>();
    secField = null;
  }
  
  public Subfield(char name, String content) {
    this.name = name;
    this.content = content;
    subsubfields = new ArrayList<Subsubfield>();
    secField = null;
  }

  /**
   * Returns the number of subsubfields in this subfield.
   * @return The number of subsubfields
   */
  @JsonIgnore
  public int getSubsubfieldCount() {
    return subsubfields.size();
  }

  /**
   * Retrieves a subsubfield with the given index.
   * @param index The index of the subsubfield
   * @return The subsubfield at the given index; null if index is out of range
   */
  public Subsubfield getSubsubfield(int index) {
    if (index < subsubfields.size())
      return subsubfields.get(index);
    else
      return null;
  }

  /**
   * Retrieves the first subsubfield with the given name.
   * @param name The name of the subsubfield
   * @return The first subsubfield with the given name
   */
  public Subsubfield getSubsubfield(char name) {
    for (int i = 0; i < subsubfields.size(); i++) {
      Subsubfield ssf = subsubfields.get(i);
      if (ssf.getName() == name)
        return ssf;
    }
    return null;
  }
  
  /**
   * Appends the given subsubfield to the list.
   * @param ssf The subsubfield to add
   */
  public void add(Subsubfield ssf) {
    subsubfields.add(ssf);
  }
  
  /**
   * Removes the given subsubfield from the list.
   * @param ssf The subsubfield to be removed
   */
  public void remove(Subsubfield ssf) {
    subsubfields.remove(ssf);
  }

  /**
   * Sorts the subfield contents (the list of subsubfields).
   */
  public void sort() {
    if (subsubfields.size() > 0) {
      for (int i = 1; i < subsubfields.size(); i++) {
        for (int j = 0; j < subsubfields.size() - i; j++) {
          Subsubfield ssf1 = subsubfields.get(j);
          Subsubfield ssf2 = subsubfields.get(j+1);
          if (ssf1.getName() > ssf2.getName()) {
            subsubfields.set(j, ssf2);
            subsubfields.set(j+1, ssf1);
          }
        }
      }
    }
  }
  
  /**
   * Removes all empty content from this subfield.
   */
  public void pack() {
    if (secField != null) {
      secField.pack();
      if (secField.getSubfieldCount() == 0)
        secField = null;
    } else if (subsubfields.size() > 0) {
      Iterator<Subsubfield> it = subsubfields.iterator();
      while (it.hasNext()) {
        Subsubfield ssf = it.next();
        if (ssf.getContent().trim().equals(""))
          it.remove();
      }
    }
  }

  /**
   * Trims the contents of this subfield, its secondary field or its 
   * subsubfields.
   */
  public void trim() {
    if (secField != null) {
      secField.trim();
    } else if (subsubfields.size() > 0) {
      Iterator<Subsubfield> it = subsubfields.iterator();
      while (it.hasNext())
        it.next().trim();
    } else {
      content = content.trim();
    }
  }

  /**
   * Returns a printable string representation of this subfield.
   */
  public String toString() {
    StringBuffer retVal = new StringBuffer();
    retVal.append("[");
    retVal.append(name);
    retVal.append("]");
    if (secField != null) {
      retVal.append(secField.toString());
    } else if (subsubfields.size() > 0) {
      for (int i = 0; i < subsubfields.size(); i++) {
        retVal.append(subsubfields.get(i).toString());
      }
    } else {
      retVal.append(content);
    }
    return retVal.toString();
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
  /**
   * @return Returns the secField.
   */
  public Field getSecField() {
    return secField;
  }
  /**
   * @param secField The secField to set.
   */
  public void setSecField(Field secField) {
    this.secField = secField;
  }
  /**
   * @return Returns the subsubfields.
   */
  public List<Subsubfield> getSubsubfields() {
    return subsubfields;
  }
  /**
   * @param subsubfields The subsubfields to set.
   */
  public void setSubsubfields(List<Subsubfield> subsubfields) {
    this.subsubfields = subsubfields;
  }
  
  public Subfield copy(){
  	Subfield sf = new Subfield(name);
  	sf.setContent(content);
  	if(secField!=null)
  		sf.setSecField(secField.copy());
  	return sf;
  }
  
  /** the name of this subfield */
  private char name;
  /** subfield content; an empty string if the subfield is empty */
  private String content;
  /** the list of subsubfields */
  private List<Subsubfield> subsubfields;
  /** a secondary field contained by this subfield */
  private Field secField;
}