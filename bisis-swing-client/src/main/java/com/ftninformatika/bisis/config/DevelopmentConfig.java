package com.ftninformatika.bisis.config;

import com.ftninformatika.bisis.librarian.Librarian;

/**
 * Created by Petar on 6/20/2017.
 */
public class DevelopmentConfig extends AppConfig {
    public DevelopmentConfig(Librarian librarian, String library, String token) {
        super("127.0.0.1:8080", librarian, library, token);
    }

    public DevelopmentConfig() {
    }
}
