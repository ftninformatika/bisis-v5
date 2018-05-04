package com.ftninformatika.bisis.editor.registries;

import java.text.Collator;
import java.util.Comparator;

public class ItemComparator implements Comparator {

  public ItemComparator(Collator collator) {
    this.collator = collator;
  }
  
  public int compare(Object o1, Object o2) {
    RegistryItem i1 = (RegistryItem)o1;
    RegistryItem i2 = (RegistryItem)o2;
    return collator.compare(cleanString(i1.getText1()), cleanString(i2.getText1()));
  }
  
  public boolean equals(Object o) {
    ItemComparator i = (ItemComparator)o;
    return collator == i.collator;
  }  
  
  public static void main(String[] args) {
    Collator c = RegistryUtils.getLatCollator();
    System.out.println(c.compare("ANAB, MARKO", "ANA, JASMINA"));
  }
  
  private String cleanString(String str){
  	str = str.replace(",", "").replace("(", "").replace(")", "").replace("/", ""); 
  	return str;
  	
  }
  
  private Collator collator;
}
