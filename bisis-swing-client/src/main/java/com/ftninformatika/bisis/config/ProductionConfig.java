package com.ftninformatika.bisis.config;

import com.ftninformatika.bisis.librarian.Librarian;

public class ProductionConfig extends AppConfig {
  public ProductionConfig(String serverUrl, Librarian librarian, String library, String token) {
    super("https://app.bisis.rs/bisisWS/", librarian, library, token);
  }

  public ProductionConfig() {
    this.setServerUrl("https://app.bisis.rs/bisisWS/");
  }
}
