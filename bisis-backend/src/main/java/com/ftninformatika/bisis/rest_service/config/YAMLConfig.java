package com.ftninformatika.bisis.rest_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author badf00d21  7.10.19.
 * Bean with custom properties in application.yml
 */
@Configuration
public class YAMLConfig {

    private @Autowired
    Environment environment;

    @Bean
    public String getServerOrigin() {
        return environment.getProperty("serverOrigin");
    }

    @Bean
    public String getOpacOrigin() { return environment.getProperty("opacOrigin"); }
}
