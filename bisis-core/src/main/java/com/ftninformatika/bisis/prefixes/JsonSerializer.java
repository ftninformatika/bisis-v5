package com.ftninformatika.bisis.prefixes;

import com.google.gson.Gson;

import java.util.List;

public class JsonSerializer {

  public static String toJson(List<PrefixValue> prefixes) {
    return new Gson().toJson(prefixes);
  }

}
