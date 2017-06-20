package com.ftninformatika.bisis.config;

import com.ftninformatika.bisis.librarian.Librarian;

/**
 * Created by Petar on 6/20/2017.
 */
public class ProductionConfig extends AppConfig {
    public ProductionConfig(String serverUrl, Librarian librarian, String library, String token) {
        super(serverUrl, librarian, library, token);
    }
}
