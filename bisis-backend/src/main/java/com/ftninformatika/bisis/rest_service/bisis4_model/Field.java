package com.ftninformatika.bisis.rest_service.bisis4_model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Field implements Serializable {


  
  /** the field name */
  private String name;
  /** the value of the first indicator */
  private char ind1;
  /** the value of the second indicator */
  private char ind2;
  /** the list of subfields */
  private List<Subfield> subfields;
}