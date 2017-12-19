package com.ftninformatika.bisis.config;

import com.ftninformatika.bisis.librarian.Librarian;

public class DevelopmentConfig extends AppConfig {

  public DevelopmentConfig(Librarian librarian, String library, String token) {
    super("http://127.0.0.1:8080/", librarian, library, token);
  }

  public DevelopmentConfig() {
    this.setServerUrl("http://127.0.0.1:8080/");
  }
}
