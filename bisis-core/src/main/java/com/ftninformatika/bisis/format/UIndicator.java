package com.ftninformatika.bisis.format;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class UIndicator comment.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class UIndicator implements Serializable {
  
  /**
   * Default constructor does nothing. 
   */
  public UIndicator() {
  }

  /**
   * @param description Indicator description.
   */
  public UIndicator(String description) {
    this.description = description;
  }
  
  /**
   * @param description Indicator description.
   * @param defaultValue Default value.
   */
  public UIndicator(int index,String description, String defaultValue) {
	this.index = index;  
    this.description = description;
    this.defaultValue = defaultValue;
  }
 
  public UIndicator(int index,String description, String defaultValue,UField owner) {
		this.index = index;  
	    this.description = description;
	    this.defaultValue = defaultValue;
	    this.owner = owner;
	  }

  /**
 * @return Returns the index.
 */
public int getIndex() {
	return index;
}

/**
 * @param index The index to set.
 */
public void setIndex(int index) {
	this.index = index;
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
   * @return Returns the values.
   */
  public List<UItem> getValues() {
    return values;
  }
  /**
   * @param values The values to set.
   */
  public void setValues(List<UItem> values) {
    this.values = values;
  }
  /**
   * Adds an indicator value.
   * 
   * @param item Value item to be added.
   */
  public void addValue(UItem item) {
    values.add(item);
  }
  /**
   * Removes an indicator value.
   * 
   * @param item Value item to be removed.
   */
  public void removeValue(UItem item) {
    values.remove(item);
  }
  /**
   * Retrieves a value item by its code.
   * 
   * @param code The code of the requested value item.
   * @return The requested value item; null if not found.
   */
  public UItem getValue(String code) {
    UItem retVal = null;
    for (int i = 0; i < values.size(); i++) {
      UItem item = (UItem)values.get(i);
      if (item.getCode().equals(code)) {
        retVal = item;
        break;
      }
    }
    return retVal;
  }
  
public UField getOwner() {
	return owner;
}

public void setOwner(UField owner) {
	this.owner = owner;
}

public String toString(){
	 return "ind"+getIndex()+"-"+description;
	    
  }
  
 
  
  /** Indicator index (Bojana)*/
  private int index;
  /** Indicator description. */
  private String description;
  /** Default value. */
  private String defaultValue;
  /** indicator owner (field) (bojana)*/
  private UField owner;
  /** The list of possible values, as a list of <code>UItem</code>s. */
  private List<UItem> values = new ArrayList<UItem>();

}
