package com.ftninformatika.bisis.config;

import com.ftninformatika.bisis.librarian.Librarian;

public class DevTestConfig  extends AppConfig  {
    public DevTestConfig(Librarian librarian, String library, String token) {
        super("https://admintest.bisis.rs/bisisWS/", librarian, library, token);
    }

    public DevTestConfig() {
        this.setServerUrl("https://admintest.bisis.rs/bisisWS/");
    }
}
