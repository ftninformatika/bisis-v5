package com.ftninformatika.bisis.hitlist.formatters;


import com.ftninformatika.bisis.records.Record;

/**
 * Represents a record formatter.
 *
 * @author mbranko@uns.ns.ac.yu
 */
public interface RecordFormatter {
  
  public String toASCII(Record record, String locale);
  public String toHTML(Record record, String locale);

}
