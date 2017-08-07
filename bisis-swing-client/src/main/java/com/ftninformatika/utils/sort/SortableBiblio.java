package com.ftninformatika.utils.sort;

/** Classes that implements Sortable interface for
 *  report Bibliografija
 *
 *  @author Tanja Tosic, ttosic@uns.ns.ac.yu
 *  @version 1.0
 *  @see Sortable
 */

import java.util.Vector;
import java.text.*;



public class SortableBiblio implements Sortable{
  private int id=-1;
  private Vector sortItems=new Vector();
  private RuleBasedCollator collator;

  /** Constructor.
   *
   *  @param id Document identifier.
   *  @param niz Vector of objects for sorting.
   */
  public SortableBiblio(int id,Vector niz) {
    this.id=id;
    this.sortItems=niz;
    this.collator = (RuleBasedCollator) SortUtils.getLatCyrCollator();
  }

  /** Compares two objects.
   *
   *  @param param The other object in comparison.
   *  @return
   *   -1 if <code>this</code> is a predecessor to <code>obj</code><br>
   *    0 if <code>this</code> and <code>obj</code> are equal (as indicated
   *      by the <code>equals</code> method<br>
   *    1 if <code>this</code> is a successor to <code>obj</code>
   */
  public int compareTo(Object param) {
    String first="", second="";

    SortableBiblio obj = (SortableBiblio) param;
    for (int i=0;i<sortItems.size();i++) {
      first=(String)sortItems.elementAt(i);
      second=obj.getSortItem(i);
      if (collator.compare(first,second) < 0){
        return -1;
      }
      if (collator.compare(first,second) > 0){
        return 1;
      }
    }
    return 0;
  }

  public int getID() {
    return id;
  }

  public String getSortItem(int index) {
    return (String) sortItems.elementAt(index);
  }
}