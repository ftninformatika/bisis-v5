package com.ftninformatika.bisis.editor.registries;

public class RegistryItem implements Comparable {

  public RegistryItem() {
  }

  public RegistryItem(int index, String text1, String text2) {
    this.text1 = text1;
    this.text2 = text2;
  }

  public int getIndex() {
    return index;
  }

  public String getText1() {
    return text1;
  }

  public String getText2() {
    return text2;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public void setText1(String text1) {
    this.text1 = text1;
  }
  
  public void setText2(String text2) {
    this.text2 = text2;
  }
  
  public int hashCode() {
    return text1.hashCode();
  }
  
  public boolean equals(Object o) {
    if (!(o instanceof RegistryItem))
      return false;
    RegistryItem i = (RegistryItem)o;
    return text1.equals(i.text1);
  }
  
  public int compareTo(Object o) {
    if (!(o instanceof RegistryItem))
      return 0;
    RegistryItem i = (RegistryItem)o;
    return text1.compareToIgnoreCase(i.text1);
  }
  
  public boolean isLike(RegistryItem i) {
    if (i.text1 != null)
      if (!text1.toUpperCase().startsWith(i.text1.toUpperCase()))
        return false;
    if (i.text2 != null)
      if (!text2.toUpperCase().startsWith(i.text2.toUpperCase()))
        return false;
    return true;
  }
  
  public String toString() {
    return "[" + index + "]" + text1 + "|" + text2;
  }
  
  private int index;
  private String text1;
  private String text2;
}
