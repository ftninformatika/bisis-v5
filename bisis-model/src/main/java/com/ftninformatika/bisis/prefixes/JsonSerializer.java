package com.ftninformatika.bisis.prefixes;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.List;

public class JsonSerializer {

  public static String toJson1(List<PrefixValue> prefixes) {
    return new Gson().toJson(prefixes);
  }

  public static String toJson2(List<PrefixValue> prefixes) {
    StringBuilder sb = new StringBuilder();
    sb.append("{ ");
    Iterator<PrefixValue> iter = prefixes.iterator();
    if (iter.hasNext()) {
      PrefixValue pv = iter.next();
      pieceJson2(sb, pv);
      while (iter.hasNext()) {
        sb.append(", ");
        pv = iter.next();
        pieceJson2(sb, pv);
      }
    }
    sb.append(" }");
    return sb.toString();
  }

  private static void pieceJson2(StringBuilder sb, PrefixValue pv) {
    sb.append("\"");
    sb.append(pv.prefName);
    sb.append("\": \"");
    sb.append(pv.value);
    sb.append("\"");
  }

}
