package com.ftninformatika.bisis.format;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class UCoder comment.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class UCoder implements Serializable {

  /**
   * Defaut constructor does nothing.
   */
  public UCoder() {
  }
  
  /**
   * @param name The coder name.
   */
  public UCoder(int type, String name, boolean external) {
    this.type = type;
    this.name = name;
    this.external = external;
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
   * @return Returns the items.
   */
  public List<UItem> getItems() {
    return items;
  }
  /**
   * @param items The items to set.
   */
  public void setItems(List<UItem> values) {
    this.items = values;
  }
  /**
   * @return Returns the external.
   */
  public boolean isExternal() {
    return external;
  }
  /**
   * @param external The external to set.
   */
  public void setExternal(boolean external) {
    this.external = external;
  }
  /**
   * @return Returns the type.
   */
  public int getType() {
    return type;
  }
  /**
   * @param type The type to set.
   */
  public void setType(int type) {
    this.type = type;
  }
  /**
   * Retrieves a value by its code.
   * 
   * @param code The code to look up.
   * @return The corresponding value; null if not found.
   */
  public String getValue(String code) {
    String retVal = null;
    for (int i = 0; i < items.size(); i++) {
      UItem item = (UItem)items.get(i);
      if (item.getCode().equals(code)) {
        retVal = item.getValue();
        break;
      }
    }
    return retVal;
  }
  
  /**
   * Retrieves a code by its value.
   * 
   * @param code The value to look up.
   * @return The corresponding code; null if not found.
   */
  public String getCode(String value) {
    String retVal = null;
    for (int i = 0; i < items.size(); i++) {
      UItem item = (UItem)items.get(i);
      if (item.getValue().equals(value)) {
        retVal = item.getCode();
        break;
      }
    }
    return retVal;
  }

  /**
   * Retrieves a coder item by its code.
   * 
   * @param code The code to look up.
   * @return The corresponding coder item; null if not found.
   */
  public UItem getItem(String code) {
    UItem retVal = null;
    for (int i = 0; i < items.size(); i++) {
      UItem item = (UItem)items.get(i);
      if (item.getCode().equals(code)) {
        retVal = item;
        break;
      }
    }
    return retVal;
  }

  /**
   * Adds an item to the coder list.
   * 
   * @param item The item to be added.
   */
  public void addItem(UItem item) {
    items.add(item);
  }

  private int type;
  private String name;
  private List<UItem> items = new ArrayList<UItem>();
  private boolean external;
}
