//package com.ftninformatika.bisis.rest_service.config;
//
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.env.YamlPropertySourceLoader;
//import org.springframework.context.ApplicationContextInitializer;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.PropertySource;
//import org.springframework.core.io.Resource;
//
//import java.io.IOException;
//
///**
// * Created by Petar on 8/8/2017.
// */
//@Configuration
//@EnableConfigurationProperties
//public class YamlFileApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//
//
//
//    @Override
//    public void initialize(ConfigurableApplicationContext applicationContext) {
//        try {
//            Resource resource = applicationContext.getResource("classpath:applicaiton.yml");
//            YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();
//            PropertySource<?> yamlTestProperties = yamlTestProperties = (PropertySource<?>) sourceLoader.load("yamlTestProperties", resource);
//            applicationContext.getEnvironment().getPropertySources().addFirst(yamlTestProperties);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}