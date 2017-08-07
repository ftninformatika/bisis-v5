package com.ftninformatika.bisis.format;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a field definition in a UNIMARC format.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class UField implements Serializable {

  /**
   * Default constructor.
   */
  public UField() {
  }
  
  /**
   * Constructs the field with the given name.
   * @param name The field name.
   */
  public UField(String name) {
    this.name = name;
  }
  
  /**
   * Constructs the field with the given parameters.
   * @param name The field name.
   */
  public UField(String name, String description, boolean mandatory,
      boolean repeatable, boolean secondary) {
    this.name = name;
    this.description = description;
    this.mandatory = mandatory;
    this.repeatable = repeatable;
    this.secondary = secondary;
  }
  
  public UField shallowCopy() {
    UField retVal = new UField(name, description, mandatory, repeatable, 
        secondary);
    retVal.ind1 = ind1;
    retVal.ind2 = ind2;
    return retVal;
  }
  
  /** 
   * Return the number of subfields.
   */
  @JsonIgnore
  public int getSubfieldCount() {
    return subfields.size();
  }
  
  /**
   * Retrieves a subfield with the given name.
   * @param name The subfield name
   * @return The retrieved subfield; null if not found
   */
  public USubfield getSubfield(char name) {
    Iterator it = subfields.iterator();
    while (it.hasNext()) {
      USubfield sf = (USubfield)it.next();
      if (sf.getName() == name)
        return sf;
    }
    return null;
  }
  
  /**
   * Appends a subfield to the subfield list.
   * @param subfield The subfield to append
   */
  public void add(USubfield subfield) {
    subfields.add(subfield);
  }
  
  /**
   * Removes the subfield from the subfield list.
   * @param subfield The subfield to be removed
   */
  public void remove(USubfield subfield) {
    subfields.remove(subfield);
  }
  
  /**
   * @return Returns the ind1.
   */
  public UIndicator getInd1() {
    return ind1;
  }
  /**
   * @param ind1 The ind1 to set.
   */
  public void setInd1(UIndicator ind1) {
    this.ind1 = ind1;
  }
  /**
   * @return Returns the ind2.
   */
  public UIndicator getInd2() {
    return ind2;
  }
  /**
   * @param ind2 The ind2 to set.
   */
  public void setInd2(UIndicator ind2) {
    this.ind2 = ind2;
  }
  /**
   * @return Returns the mandatory.
   */
  public boolean isMandatory() {
    return mandatory;
  }
  /**
   * @param mandatory The mandatory to set.
   */
  public void setMandatory(boolean mandatory) {
    this.mandatory = mandatory;
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
   * @return Returns the repeatable.
   */
  public boolean isRepeatable() {
    return repeatable;
  }
  /**
   * @param repeatable The repeatable to set.
   */
  public void setRepeatable(boolean repeatable) {
    this.repeatable = repeatable;
  }
  /**
   * @return Returns the secondary.
   */
  public boolean isSecondary() {
    return secondary;
  }
  /**
   * @param secondary The secondary to set.
   */
  public void setSecondary(boolean secondary) {
    this.secondary = secondary;
  }
  /**
   * @return Returns the subfields.
   */
  public List<USubfield> getSubfields() {
    return subfields;
  }
  /**PubTypes.getFormat().
   * @param subfields The subfields to set.
   */
  public void setSubfields(List<USubfield> subfields) {
    this.subfields = subfields;
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
   * Returns a printable string representation of this field.
   */
  public String toString() {
    StringBuffer retVal = new StringBuffer();
    retVal.append('{');
    retVal.append(name);
    retVal.append("}{m:");
    retVal.append(mandatory);
    retVal.append("}{r:");
    retVal.append(repeatable);
    retVal.append("}{");
    retVal.append(description);
    retVal.append('}');
    if (subfields.size() > 0) {
      retVal.append('{');
      for (int i = 0; i < subfields.size(); i++)
        retVal.append(subfields.get(i));
      retVal.append('}');
    }
    return retVal.toString();
  }

  /**
   * Sorts the subfields in this field.
   */
  public void sort() {
    for (int i = 1; i < subfields.size(); i++) {
      for (int j = 0; j < subfields.size() - i; j++) {
        USubfield sf1 = subfields.get(j);
        USubfield sf2 = subfields.get(j+1);
        if (sf1.getName() > sf2.getName()) {
          subfields.set(j, sf2);
          subfields.set(j+1, sf1);
        }
      }
    }
    for (int i = 0; i < subfields.size(); i++) {
      USubfield sf = subfields.get(i);
      sf.sort();
    }
  }
public void addSubfield(USubfield s){
	this.subfields.add(s);
	
}
  /** field name */
  private String name;
  /** field description */
  private String description;
  /** first indicator */
  @JsonManagedReference
  private UIndicator ind1;
  @JsonManagedReference
  /** second indicator */
  private UIndicator ind2;
  /** list of available subfields */
  @JsonManagedReference(value = "field-subfields")
  private List<USubfield> subfields = new ArrayList<USubfield>();
  /** is this field mandatory */
  private boolean mandatory;
  /** is this field repeatable */
  private boolean repeatable;
  /** may this field be a secondary field */
  private boolean secondary;

}
