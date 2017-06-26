package com.ftninformatika.bisis.format;

import java.io.Serializable;

/**
 * Class USubsubfield comment.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class USubsubfield implements Serializable {

  /**
   * Default constructor does nothing.
   */
  public USubsubfield() {
  }
  
  /**
   * @param owner The owner subfield of this subsubfield.
   * @param name Subsubfield name.
   */
  public USubsubfield(USubfield owner, char name) {
    this.owner = owner;
    this.name = name;
  }
  
  /**
   * @param owner The owner subfield of this subsubfield.
   * @param name Subsubfield name.
   * @param mandatory The subsubfield mandatory flag.
   * @param repeatable The subsubfield mandatory flag.
   */
  public USubsubfield(USubfield owner, char name, boolean mandatory,
      boolean repeatable) {
    this.owner = owner;
    this.name = name;
    this.mandatory = mandatory;
    this.repeatable = repeatable;
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
  public USubfield getOwner() {
    return owner;
  }
  /**
   * @param owner The owner to set.
   */
  public void setOwner(USubfield owner) {
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
   * Returns the printable string representation of this subsubfield.
   */
  public String toString() {
    StringBuffer retVal = new StringBuffer();
    retVal.append('(');
    retVal.append(name);
    retVal.append(")(m:");
    retVal.append(mandatory);
    retVal.append(")(r:");
    retVal.append(repeatable);
    retVal.append(")(");
    retVal.append(description);
    retVal.append(')');
    return retVal.toString();
  }
  
  /** subsubfield owner (the field) */
  private USubfield owner;
  /** subsubfield name */
  private char name;
  /** subsubfield description */
  private String description;
  /** maximum content length (0=unlimited) */
  private int length;
  /** is subsubfield mandatory */
  private boolean mandatory;
  /** is subsubfield repeatable */
  private boolean repeatable;
  /** content default value */
  private String defaultValue;
  /** coder for subsubfield content */
  private UCoder coder;
  /** validator for subsubfield content */
  private UValidator validator;
}
