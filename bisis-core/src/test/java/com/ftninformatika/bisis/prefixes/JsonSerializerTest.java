package com.ftninformatika.bisis.prefixes;


import com.ftninformatika.bisis.records.Record;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class JsonSerializerTest {

  private static Record record1;

  @BeforeClass
  public static void setup() {
    try {
      //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
      Gson gson = new Gson();
      record1 = gson.fromJson(new BufferedReader(new InputStreamReader(
          ClassLoader.getSystemResourceAsStream("records/record1.json"),
          "UTF8")), Record.class);
    } catch (Exception ex) {
      System.out.println(ex);
      fail("Test file not found.");
    }
  }

  @Test
  public void toJson1ShouldWork() {
    List<PrefixValue> prefixes = PrefixConverter.toPrefixes(record1, null);
    String json = JsonSerializer.toJson1(prefixes);
    assertTrue("Record not properly serialized to prefixes",
        json.indexOf("\"prefName\":\"AU\",\"value\":\"Alfred ADLER\"") != -1);
  }

  @Test
  public void toJson2ShouldWork() {
    List<PrefixValue> prefixes = PrefixConverter.toPrefixes(record1, null);
    String json = JsonSerializer.toJson2(prefixes);
    assertTrue("Record not properly serialized to prefixes",
        json.indexOf("\"AU\": \"Alfred ADLER\"") != -1);

  }
}
