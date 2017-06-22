package com.ftninformatika.bisis.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Subfield implements Serializable {

  private char name;
  private String content;
  @JsonIgnore
  private List<Subsubfield> subsubfields;
}