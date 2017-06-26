package com.ftninformatika.bisis.format;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a version of the UNIMARC format.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class UFormat implements Serializable {

  /**
   * Default constructor.
   */
  public UFormat() {
  }
  
  /**
   * Constructs a format with the given name.
   * @param pubType Publication type code
   * @param name The format name
   */
  public UFormat(int pubType, String name) {
    this.pubType = pubType;
    this.name = name;
  }
  
  /**
   * Constructs a format with the given name and description.
   * @param pubType Publication type code
   * @param name The format name
   * @param description The format description
   */
  public UFormat(int pubType, String name, String description) {
    this.pubType = pubType;
    this.name = name;
    this.description = description;
  }
  
  /**
   * Checks whether the given field contains secondary fields (in its only 
   * subfield named '1').
   * @param fieldName The name of the field
   * @return True if the field contains secondary fields, otherwise false
   */
  public boolean containsSecondaryFields(String fieldName) {
    UField f = getField(fieldName);
    if (f == null)
      return false;
    USubfield sf = f.getSubfield('1');
    if (sf == null)
      return false;
    // TODO: implement this properly
    if ("Broj taga".equals(sf.getDescription()) || "Identifikacijski broj".equals(sf.getDescription()))
      return true;
    return false;
  }
  
  public boolean containsSubsubfields(String subfieldName) {
    USubfield sf = getSubfield(subfieldName);
    if (sf == null)
      return false;
    return sf.containsSubsubfelds();
  }
  
  /**
   * Retrieves a field by its current index in the field list.
   * @param index The index of the field
   * @return The field with the given index; null if index is out of range
   */
  public UField getField(int index) {
    if (index >= fields.size() || index < 0)
      return null;
    return (UField)fields.get(index);
  }
  
  /**
   * Retrives the field with given name from this format.
   * @param name The field name
   * @return The field with the given name
   */
  public UField getField(String name) {
    for (int i = 0; i < fields.size(); i++) {
      UField field = (UField)fields.get(i);
      if (field.getName().equals(name)) {
        return field;
      }
    }
    return null;
  }

  /**
   * Retrieves the subfield with the given name.
   * @param name The name of the subfield
   * @return The retrieved subfield
   */
  public USubfield getSubfield(String name) {
    if (name.length() != 4)
      return null;
    String fieldName = name.substring(0, 3);
    UField f = getField(fieldName);
    if (f == null)
      return null;
    return f.getSubfield(name.charAt(3));
  }
  
  /**
   * Retrieves the subsubfield with the given name.
   * @param name The name of the subsubfield
   * @return The retrieved subsubfield
   */
  public USubsubfield getSubsubfield(String name) {
    if (name.length() != 5)
      return null;
    String subfieldName = name.substring(0, 4);
    USubfield sf = getSubfield(subfieldName);
    if (sf == null)
      return null;
    return sf.getSubsubfield(name.charAt(4));
  }

  /**
   * Appends a field to the field list.
   * @param field The field to be added.
   */
  public void add(UField field) {
    fields.add(field);
  }
  
  /**
   * Removes the field from the field list. 
   * @param field
   */
  public void remove(UField field) {
    fields.remove(field);
  }
  
  /**
   * Returns a printable string representation of this format.
   */
  public String toString() {
    StringBuffer retVal = new StringBuffer();
    retVal.append('<');
    retVal.append(name);
    retVal.append("><");
    retVal.append(description);
    retVal.append('>');
    if (fields.size() > 0) {
      retVal.append('<');
      for (int i = 0; i < fields.size(); i++)
        retVal.append(fields.get(i));
      retVal.append('>');
    }
    return retVal.toString();
  }

  /**
   * Sorts the field list.
   */
  public void sort() {
    for (int i = 1; i < fields.size(); i++) {
      for (int j = 0; j < fields.size() - i; j++) {
        UField f1 = (UField)fields.get(j);
        UField f2 = (UField)fields.get(j+1);
        if (f1.getName().compareTo(f2.getName()) > 0) {
          fields.set(j, f2);
          fields.set(j+1, f1);
        }
      }
    }
    for (int i = 0; i < fields.size(); i++) {
      UField f = (UField)fields.get(i);
      f.sort();
    }
  }
  
  /**
   * @return Returns the description.
   */
  public String getDescription() {
    return description;
  }
  /**
   * @param description The description to set.
   */
  public void setDescription(String description) {
    this.description = description;
  }
  /**
   * @return Returns the fields.
   */
  public List<UField> getFields() {
    return fields;
  }
  /**
   * @param fields The fields to set.
   */
  public void setFields(List<UField> fields) {
    this.fields = fields;
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
   * @return Returns the pubType.
   */
  public int getPubType() {
    return pubType;
  }
  /**
   * @param pubType The pubType to set.
   */
  public void setPubType(int pubType) {
    this.pubType = pubType;
  }
  
  public void pack(){
  	for(UField uf:fields)
  		if(uf.getSubfieldCount()==0)
  			fields.remove(uf);
  }
  public void addField(UField f){
	  this.fields.add(f);
  }
  /** The format id = publication type code */
  private int pubType;
  /** The format name */
  private String name = "";
  /** The format description */
  private String description = "";
  /** The list of available fields in this format */
  private List<UField> fields = new ArrayList<UField>();
}
