package com.ftninformatika.bisis.rest_service.bisis4_model;

import java.util.HashSet;
import java.util.Set;

// Generated Jan 11, 2007 11:36:46 AM by Hibernate Tools 3.2.0.beta8


/**
 * Povez generated by hbm2java
 */
public class Povez implements java.io.Serializable {

  // Fields    

  private char povezId;

  private String povezOpis;

  private Set primercis = new HashSet(0);

  private Set godines = new HashSet(0);

  // Constructors

  /** default constructor */
  public Povez() {
  }

  /** minimal constructor */
  public Povez(char povezId, String povezOpis) {
    this.povezId = povezId;
    this.povezOpis = povezOpis;
  }

  /** full constructor */
  public Povez(char povezId, String povezOpis, Set primercis, Set godines) {
    this.povezId = povezId;
    this.povezOpis = povezOpis;
    this.primercis = primercis;
    this.godines = godines;
  }

  // Property accessors
  public char getPovezId() {
    return this.povezId;
  }

  public void setPovezId(char povezId) {
    this.povezId = povezId;
  }

  public String getPovezOpis() {
    return this.povezOpis;
  }

  public void setPovezOpis(String povezOpis) {
    this.povezOpis = povezOpis;
  }

  public Set getPrimercis() {
    return this.primercis;
  }

  public void setPrimercis(Set primercis) {
    this.primercis = primercis;
  }

  public Set getGodines() {
    return this.godines;
  }

  public void setGodines(Set godines) {
    this.godines = godines;
  }

}
