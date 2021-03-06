package com.ftninformatika.bisis.hitlist.formatters;


import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordFactory;

import javax.swing.*;

/**
 * Formats records in the full format.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class FullFormatter implements RecordFormatter {

  public String toASCII(Record record, String locale) {
    return RecordFactory.toFullFormat(0, record, false);
  }

  public String toHTML(Record record, String locale) {
    return "<code>" + RecordFactory.toFullFormat(0, record, true) + "</code>";
  }

}
