package com.ftninformatika.bisis.format;

import com.fasterxml.jackson.annotation.*;
import com.google.gson.annotations.Expose;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class USubfield comment.
 * 
 * @author mbranko@uns.ns.ac.yu
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property  = "id",
        scope     = USubfield.class)
public class USubfield implements Serializable {
  
  /**
   * Default constructor does nothing.
   */
  public USubfield() {
  }
  
  /**
   * @param owner The owner field of this subfield.
   * @param name Subfield name.
   */
  public USubfield(UField owner, char name) {
    this.owner = owner;
    this.name = name;
  }
  
  /**
   * @param owner The owner field of this subfield.
   * @param name Subfield name.
   * @param mandatory Subfield mandatory flag.
   * @param repeatable Subfield repeatable flag.
   */
  public USubfield(UField owner, char name, boolean mandatory,
      boolean repeatable) {
    this.owner = owner;
    this.name = name;
    this.mandatory = mandatory;
    this.repeatable = repeatable;
  }
  
  public USubfield shallowCopy() {
    USubfield retVal = new USubfield(owner, name, mandatory, repeatable);
    retVal.coder = coder;
    retVal.defaultValue = defaultValue;
    retVal.description = description;
    retVal.length = length;
    retVal.subsubfields = subsubfields;
    retVal.validator = validator;
    return retVal;
  }
  
  /**
   * Retrieves a subsubfield with the given name.
   * @param name The name of the subsubfield
   * @return The retrieved subsubfield; null if not found
   */
  public USubsubfield getSubsubfield(char name) {
    Iterator it = subsubfields.iterator();
    while (it.hasNext()) {
      USubsubfield ssf = (USubsubfield)it.next();
      if (ssf.getName() == name)
        return ssf;
    }
    return null;
  }
  
  /**
   * Appends a subsubfield to the list.
   * @param subsubfield The subsubfield to append
   */
  public void add(USubsubfield subsubfield) {
    subsubfields.add(subsubfield);
  }

  /**
   * Removes the subsubfield from the list.
   * @param subsubfield The subsubfield to be removed
   */
  public void remove(USubsubfield subsubfield) {
    subsubfields.remove(subsubfield);
  }
  
  /**
   * @return Returns the coder.
   */
  public UCoder getCoder() {
    return coder;
  }
  /**
   * @param coder The coder to set.
   */
  public void setCoder(UCoder coder) {
    this.coder = coder;
  }
  /**
   * @return Returns the defaultValue.
   */
  public String getDefaultValue() {
    return defaultValue;
  }
  /**
   * @param defaultValue The defaultValue to set.
   */
  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }
  /**
   * @return Returns the length.
   */
  public int getLength() {
    return length;
  }
  /**
   * @param length The length to set.
   */
  public void setLength(int length) {
    this.length = length;
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
   * @return Returns the owner.
   */
  public UField getOwner() {
    return owner;
  }
  /**
   * @param owner The owner to set.
   */
  public void setOwner(UField owner) {
    this.owner = owner;
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
   * @return Returns the subsubfields.
   */
  public List<USubsubfield> getSubsubfields() {
    return subsubfields;
  }
  /**
   * @param subsubfields The subsubfields to set.
   */
  public void setSubsubfields(List<USubsubfield> subsubfields) {
    this.subsubfields = subsubfields;
  }
  /**
   * @return Returns the validator.
   */
  public UValidator getValidator() {
    return validator;
  }
  /**
   * @param validator The validator to set.
   */
  public void setValidator(UValidator validator) {
    this.validator = validator;
  }
  
  /**
   * Checks whether this subfield contains subsubfields.
   * 
   * @return true if it does; false otherwise
   */
  public boolean containsSubsubfelds() {
    return subsubfields.size() > 0;
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
   * Returns a printable string representation of this subfield.
   */

  public String toString() {
    StringBuffer retVal = new StringBuffer();
    retVal.append('[');
    retVal.append(name);
    retVal.append("][m:");
    retVal.append(mandatory);
    retVal.append("][r:");
    retVal.append(repeatable);
    retVal.append("][");
    retVal.append(description);
    retVal.append(']');
    if (subsubfields.size() > 0) {
      retVal.append('[');
      for (int i = 0; i < subsubfields.size(); i++)
        retVal.append(subsubfields.get(i));
      retVal.append(']');
    }
    return retVal.toString();
  }

  /**
   * Sorts the subsubfields of this subfield.
   */
  public void sort() {
    if (subsubfields.size() > 0) {
      for (int i = 1; i < subsubfields.size(); i++) {
        for (int j = 0; j < subsubfields.size() - i; j++) {
          USubsubfield ssf1 = (USubsubfield)subsubfields.get(j);
          USubsubfield ssf2 = (USubsubfield)subsubfields.get(j+1);
          if (ssf1.getName() > ssf2.getName()) {
            subsubfields.set(j, ssf2);
            subsubfields.set(j+1, ssf1);
          }
        }
      }
    }
  } 
  public void addSubsubfields(USubsubfield ss){
	  this.subsubfields.add(ss);
  }

  /** subfield owner (the field) */
  //@JsonManagedReference
  //@JsonIdentityReference(alwaysAsId = true)
  private UField owner;
  /** subfield name */
  private char name;
  /** subfield description */
  private String description;
  /** is subfield mandatory */
  private boolean mandatory;
  /** is subfield repeatable */
  private boolean repeatable;
  /** maximum content length (0=unlimited) */
  private int length;
  /** default content value */
  private String defaultValue;
  /** coder for subfield content */
  private UCoder coder;
  /** validator for subfield content */
  private UValidator validator;
  @JsonManagedReference(value = "subfield-subsubfields")
  private List<USubsubfield> subsubfields = new ArrayList<USubsubfield>();
  
}
