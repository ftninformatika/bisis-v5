package com.ftninformatika.bisis.prefixes;

import java.util.List;

/**
 * Represents a map from subfields to prefixes.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public interface PrefixMap {

  /**
   * Returns a list of prefix names (strings) mapped to the given subfield.
   * 
   * @param subfieldName the four-character name of the subfield.
   * @return The list of corresponding prefixes.
   */
  public List<String> getPrefixes(String subfieldName);
  
  /**
   * Returns a list of subfield names (strings) mapped to the given prefix.
   * 
   * @param prefix the two-character name of the prefix
   * @return The list of corresponding subfields (four-character names)
   */
  public List<String> getSubfields(String prefix);
}
