package com.ftninformatika.bisis.config;

import com.ftninformatika.bisis.librarian.Librarian;

public class TestConfig extends AppConfig {

  public TestConfig(Librarian librarian, String library, String token) {
    super("http://94.130.24.103/bisisWS/", librarian, library, token);
  }

  public TestConfig() {
    this.setServerUrl("http://94.130.24.103/bisisWS/");
  }
}
