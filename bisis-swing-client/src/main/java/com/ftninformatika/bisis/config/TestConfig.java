package com.ftninformatika.bisis.config;

import com.ftninformatika.bisis.librarian.Librarian;

public class TestConfig extends AppConfig {

  public TestConfig(Librarian librarian, String library, String token) {
    super("http://147.91.168.59/bisisWS/", librarian, library, token);
  }

  public TestConfig() {
    this.setServerUrl("http://147.91.168.59/bisisWS/");
  }
}
