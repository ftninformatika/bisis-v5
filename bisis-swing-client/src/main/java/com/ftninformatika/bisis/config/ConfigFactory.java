package com.ftninformatika.bisis.config;

/**
 * Created by Petar on 6/20/2017.
 */
public class ConfigFactory {

    public static AppConfig getConfig(Config cfg) {
        switch (cfg) {
            case DEVELOPMENT:
                return new DevelopmentConfig();
            default:
                return new DevelopmentConfig();
        }
    }
}
