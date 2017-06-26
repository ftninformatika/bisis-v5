package com.ftninformatika.bisis.hitlist.formatters;

/**
 * Produces RecordFormatter objects.
 *
 * @author mbranko@uns.ns.ac.yu
 */
public class RecordFormatterFactory {
  
  public static final int FORMAT_BRIEF  = 1;
  public static final int FORMAT_DETAIL = 2;
  public static final int FORMAT_FULL   = 3;

  public static RecordFormatter getFormatter(int formatType) {
    switch (formatType) {
      case FORMAT_BRIEF:
        return brief;
      case FORMAT_DETAIL:
        return detail;
      case FORMAT_FULL:
        return full;
      default:
        return null;
    }
  }
  
  private static BriefFormatter brief = new BriefFormatter();
  private static DetailFormatter detail = new DetailFormatter();
  private static FullFormatter full = new FullFormatter();
}
