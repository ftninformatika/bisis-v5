package com.ftninformatika.bisis.config;

import com.ftninformatika.bisis.librarian.Librarian;

public class TestConfig extends AppConfig {

  public TestConfig(Librarian librarian, String library, String token) {
    super("https://test.bisis.rs/bisisWS/", librarian, library, token);
  }

  public TestConfig() {
    this.setServerUrl("https://test.bisis.rs/bisisWS/");
  }
}
