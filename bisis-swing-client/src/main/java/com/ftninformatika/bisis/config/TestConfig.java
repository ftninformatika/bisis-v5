package com.ftninformatika.bisis.config;

import com.ftninformatika.bisis.librarian.Librarian;

public class TestConfig extends AppConfig {

  public TestConfig(Librarian librarian, String library, String token) {
    super("http://192.168.200.46:8080/", librarian, library, token);
  }

  public TestConfig() {
    this.setServerUrl("http://192.168.200.46:8080/");
  }
}
