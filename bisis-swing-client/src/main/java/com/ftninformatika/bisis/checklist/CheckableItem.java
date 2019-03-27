package com.ftninformatika.bisis.checklist;


import javax.swing.Icon;

public class CheckableItem {
  private Object obj;
  private boolean isSelected;
  private Icon icon;
  private String value;

  public CheckableItem(Object obj) {
    this.obj = obj;
    isSelected = false;
  }

  public void setValue(String value) { this.value = value;}

  public String getValue() {return this.value; }

  public void setSelected(boolean b) {
    isSelected = b;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public String toString() {
    return obj.toString();
  }

  public void setIcon(Icon icon) {
    this.icon = icon;
  }

  public Icon getIcon() {
    return icon;
  }

  public Object getObject() {
    return obj;
  }
}
