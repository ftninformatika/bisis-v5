package com.ftninformatika.bisis.format;

import com.ftninformatika.utils.string.LatCyrUtils;

import java.io.Serializable;

/**
 * Class UItem comment.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class UItem implements Serializable {

  /**
   * Default constructor does nothing.
   */
  public UItem() {
  }

  /**
   * @param code The code to set.
   * @param value The value to set.
   */
  public UItem(String code, String value) {
    this.code = code;
    this.value = value;
  }

  /**
   * @return Returns the code.
   */
  public String getCode() {
    return code;
  }
  /**
   * @param code The code to set.
   */
  public void setCode(String code) {
    this.code = code;
  }
  /**
   * @return Returns the value.
   */
  public String getValue() {
    return value;
  }
  /**
   * @param value The value to set.
   */
  public void setValue(String value) {
    this.value = value;
  }
  
  @Override
  public String toString() {
    return code+"-"+value;
  }

  public String toJsonString() {
    return "{\ncode: '" + code + "' ,\nname: '" + LatCyrUtils.toCyrillic(value.substring(0, 1).toUpperCase() + value.substring(1)) +"'\n},\n";
  }

  private String code;
  private String value;
}
