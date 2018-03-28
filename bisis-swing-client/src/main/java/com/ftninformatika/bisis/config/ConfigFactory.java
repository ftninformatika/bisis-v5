package com.ftninformatika.bisis.config;

import java.util.HashMap;
import java.util.Map;

public class ConfigFactory {

  public static AppConfig getConfig(ConfigType cfg) {
    return configMap.get(cfg);
  }

  private static Map<ConfigType, AppConfig> configMap = new HashMap<>();

  static {
    configMap.put(ConfigType.DEVELOPMENT, new DevelopmentConfig());
    configMap.put(ConfigType.TEST, new TestConfig());
    configMap.put(ConfigType.STAGING, null);
    configMap.put(ConfigType.PRODUCTION, new ProductionConfig());
  }
}
