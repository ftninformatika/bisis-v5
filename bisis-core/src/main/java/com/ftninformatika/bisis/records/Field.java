package com.ftninformatika.bisis.records;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Field implements Serializable {

  /**
   * Default constructor.
   */
  public Field() {
    this("   ", ' ', ' ');
  }

  /**
   * Constructs a field with the given name.
   * @param name The name of the field
   */
  public Field(String name) {
    this(name, ' ', ' ');
  }
  
  /**
   * Constructs a field with the given name and indicators.
   * @param name The name of the field
   * @param ind1 The value of the first indicator
   * @param ind2 The value of the second indicator
   */
  public Field(String name, char ind1, char ind2) {
    this.name = name;
    this.ind1 = ind1;
    this.ind2 = ind2;
    subfields = new ArrayList<Subfield>();
  }

  /**
   * Returns the number of subfields in this field.
   * @return The number of subfields
   */
  public int getSubfieldCount() {
    return subfields.size();
  }

  /**
   * Retrives a subfield with the given index.
   * @param index The subfield index
   * @return The subfield with the given index; null if index is out of range
   */
  public Subfield getSubfield(int index) {
    if (index < subfields.size())
      return subfields.get(index);
    else
      return null;
  }

  /**
   * Retrieves the first subfield with the given name.
   * @param name The name of the subfield
   * @return The first subfield with the given name
   */
  public Subfield getSubfield(char name) {
    for (int i = 0; i < subfields.size(); i++) {
      Subfield sf = subfields.get(i);
      if (sf.getName() == name)
        return sf;
    }
    return null;
  }
  
  public String getSubfieldContent(char name){
  	if(getSubfield(name)!=null)
  		return getSubfield(name).getContent();
  	return null;
  }
  
  /**
   * Retrieves a list of all subfields with the given name.
   * @param name The name of the subfield
   * @return The list of subfields with the given name
   */
  public List<Subfield> getSubfields(char name) {
    List<Subfield> retVal = new ArrayList<Subfield>();
    for (int i = 0; i < subfields.size(); i++) {
      Subfield sf = subfields.get(i);
      if (sf.getName() == name)
        retVal.add(sf);
    }
    return retVal;
  }
  
  /**
   * Returns whether this field contains secondary fields in some of its
   * subfields
   * @return true if it does, otherwise false
   */
  public boolean containsSecondaryFields() {
    boolean retVal = false;
    Iterator<Subfield> it = subfields.iterator();
    while (it.hasNext()) {
      Subfield sf = it.next();
      if (sf.getSecField() != null)
        return true;
    }
    return retVal;
  }
  
  /**
   * Returns an array of letters representing this field's subfields, including
   * subfields of a possible secondary field
   * @return
   */
  public String getSubfieldNames() {
    StringBuffer retVal = new StringBuffer();
    Iterator<Subfield> it = subfields.iterator();
    while (it.hasNext()) {
      Subfield sf = it.next();
      retVal.append(sf.getName());
      if (sf.getSecField() != null)
        retVal.append(sf.getSecField().getSubfieldNames());
    }
    return retVal.toString();
  }
  
  /**
   * Adds a subfield to this field. The subfield is appended to the end of the 
   * list.
   * @param sf The subfield to be added
   */
  public void add(Subfield sf) {
    subfields.add(sf);
  }
  
  /**
   * Removes the given subfield from this field.
   * @param sf The subfield to be removed
   */
  public void remove(Subfield sf) {
    subfields.remove(sf);
  }
  
  /**
   * Removes subfield with the given name from this field.
   * @author dimicb
   * @param sfName
   */
  public void removeSubfield(char sfName){
  	Subfield sf = getSubfield(sfName);
  	if(sf!=null) remove(sf);
  }

  /**
   * Sorts the subfields in this field.
   */
  public void sort() {
    for (int i = 1; i < subfields.size(); i++) {
      for (int j = 0; j < subfields.size() - i; j++) {
        Subfield sf1 = subfields.get(j);
        Subfield sf2 = subfields.get(j+1);
        if (sf1.getName() > sf2.getName()) {
          subfields.set(j, sf2);
          subfields.set(j+1, sf1);
        }
      }
    }
    for (int i = 0; i < subfields.size(); i++) {
      Subfield sf = subfields.get(i);
      sf.sort();
    }
  }
  
  /**
   * Removes all empty subfields from this field.
   */
  public void pack() {
    Iterator<Subfield> it = subfields.iterator();
    while (it.hasNext()) {
      Subfield sf = it.next();
      sf.pack();
      if (sf.getSecField() == null && sf.getSubsubfieldCount() == 0 && 
          sf.getContent().trim().equals(""))
        it.remove();
    }
  }
  
  /**
   * Trims the contents of all contained subfields and subsubfields.
   */
  public void trim() {
    Iterator<Subfield> it = subfields.iterator();
    while (it.hasNext())
      it.next().trim();
  }

  /**
   * Returns a printable string representation of this field.
   */
  public String toString() {
    StringBuffer retVal = new StringBuffer();
    retVal.append("{");
    retVal.append(name);
    retVal.append(":");
    retVal.append(ind1);
    retVal.append(ind2);
    retVal.append(":");
    for (int i = 0; i < subfields.size(); i++) {
      retVal.append(subfields.get(i).toString());
    }
    retVal.append("}");
    return retVal.toString();
  }


  /**
   * @return Returns the ind1.
   */
  public char getInd1() {
    return ind1;
  }
  /**
   * @param ind1 The ind1 to set.
   */
  public void setInd1(char ind1) {
    this.ind1 = ind1;
  }
  /**
   * @return Returns the ind2.
   */
  public char getInd2() {
    return ind2;
  }
  /**
   * @param ind2 The ind2 to set.
   */
  public void setInd2(char ind2) {
    this.ind2 = ind2;
  }
  /**
   * @return Returns the name.
   */
  public String getName() {
    return name;
  }
  /**
   * @param name The name to set.
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * @return Returns the subfields.
   */
  public List<Subfield> getSubfields() {
    return subfields;
  }
  /**
   * @param subfields The subfields to set.
   */
  public void setSubfields(List<Subfield> subfields) {
    this.subfields = subfields;
  }
  
  public Field copy(){
  	Field f = new Field(name);
  	f.setInd1(ind1);
  	f.setInd2(ind2);
  	for(Subfield sf:subfields)
  		f.add(sf.copy());  	
  	return f;  	
  }
  
  /** the field name */
  private String name;
  /** the value of the first indicator */
  private char ind1;
  /** the value of the second indicator */
  private char ind2;
  /** the list of subfields */
  private List<Subfield> subfields;
}