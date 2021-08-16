package com.ftninformatika.bisis.format;

/**
 * Class FormatSerializer comment.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public interface FormatSerializer {
  
  public static final String TYPE_PLAIN  = "text/plain";
  public static final String TYPE_HTML   = "text/html";
  public static final String TYPE_LATEX  = "text/latex";
  public static final String TYPE_XML    = "text/xml";
  public static final String TYPE_SCHEMA = "text/xmlschema";
  
  public String getType();
  public String serializeFormat(UFormat fieldSet);
  public String serializeCoders(UFormat fieldSet);
}
