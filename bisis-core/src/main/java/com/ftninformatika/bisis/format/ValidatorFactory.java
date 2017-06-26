package com.ftninformatika.bisis.format;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gint.util.file.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class ValidatorFactory comment.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class ValidatorFactory {

  public static UValidator getValidator(String name) {
    return (UValidator)validators.get(name);
  }
  
  private static Map validators = new HashMap();

  private static Log log = LogFactory.getLog(
			"test.com.gint.app.bisis.common.format.ValidatorFactory");

  static {
    try {
      log.info("Loading format validators");
      String dirName = "/com/gint/app/bisis4/format/validators";
      String files[] = FileUtils.listFiles(ValidatorFactory.class, dirName);
      for (int i = 0; i < files.length; i++) {
        if (files[i].endsWith(".class")) {
          String piece = files[i].substring(1).replace('/', '.');
          String className = piece.substring(0, piece.length() - 6);
          Object o = Class.forName(className).newInstance();
          if (o instanceof UValidator) {
            UValidator validator = (UValidator)o;
            List targets = validator.getTargets();
            for (int j = 0; j < targets.size(); j++)
              validators.put(targets.get(j), validator);
          }
        }
      }
    } catch (Exception ex) {
      log.fatal(ex);
    }
  }
}
