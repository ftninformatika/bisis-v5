package com.ftninformatika.bisis.prefixes;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Represents a configuration of prefix settings.
 * 
 * @author mbranko@uns.ac.rs
 */
public interface PrefixConfig {
  public PrefixHandler getPrefixHandler();
  public PrefixMap getPrefixMap();
  public Set<String> getAllPrefixes();
  public Set<String> getSortPrefixes();
  public String getWordDelimiters();
  public String getSentenceDelimiters();
  public String getAllDelimiters();
  public List<PrefixValue> getPrefixNames();
  public List<PrefixValue> getPrefixNames(Locale locale);
  public String getPrefixName(String prefix);
  public String getPrefixName(String prefix, Locale locale);
}
