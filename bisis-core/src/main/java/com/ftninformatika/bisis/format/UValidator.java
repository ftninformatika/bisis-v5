package com.ftninformatika.bisis.format;

import java.io.Serializable;
import java.util.List;

/**
 * Interface UValidator comment.
 * 
 * @author mbranko@uns.ac.rs
 */
public interface UValidator extends Serializable {
  /** 
   * Returns a list of strings identifying subfields or subsubfields this 
   * validator applies to.
   * 
   * @return The list of strings such as "101b", "996dl", etc.
   */
  public List<String> getTargets();
  /** 
   * Checks whether the given string is valid.
   * 
   * @param content The content to be checked.
   * @return Empty string if the content is valid, otherwise the error message. 
   */
  public String isValid(String content);
  /**
   * Checks whether the given string is valid.
   * 
   * @param content The content to be checked.
   * @throws UValidatorException When content is invalid
   */
  public void validate(String content) throws UValidatorException;
}
