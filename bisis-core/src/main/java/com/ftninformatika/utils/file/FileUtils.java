package com.ftninformatika.utils.file;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/** Utility class for file manipulation.
 *
 *  @author Branko Milosavljevic, mbranko@uns.ns.ac.yu
 *  @version 1.0
 */
public class FileUtils {

  /** Reads a character file into a string. Assumes default
   *  character encoding in the file. Returns <code>null</code> if
   *  any of the exceptions is thrown.
   *
   *  @param fileName The name of the file to read.
   *  @return The read file
   */
  public static String readTextFile(String fileName) {
    String lineSep = System.getProperty("line.separator");
    StringBuffer retVal = new StringBuffer();
    try {
      BufferedReader in = new BufferedReader(
        new FileReader(fileName));
      String line = null;
      while ((line = in.readLine()) != null) {
        retVal.append(line);
        retVal.append(lineSep);
      }
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return retVal.toString();
  }

  /** Reads a character file into a string. Assumes default
   *  character encoding in the file. Returns <code>null</code> if
   *  any of the exceptions is thrown.
   *
   *  @param file The file to read.
   *  @return The read file
   */
  public static String readTextFile(File file) {
    String lineSep = System.getProperty("line.separator");
    StringBuffer retVal = new StringBuffer();
    try {
      BufferedReader in = new BufferedReader(
        new FileReader(file));
      String line = null;
      while ((line = in.readLine()) != null) {
        retVal.append(line);
        retVal.append(lineSep);
      }
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return retVal.toString();
  }

  /** Reads a character file into a string. Uses the specified
   *  character encoding. Returns <code>null</code> if
   *  any of the exceptions is thrown.
   *
   *  @param fileName The name of the file to read.
   *  @return The read file
   */
  public static String readTextFile(String fileName, String enc) {
    String lineSep = System.getProperty("line.separator");
    StringBuffer retVal = new StringBuffer();
    try {
      BufferedReader in = new BufferedReader(
        new InputStreamReader(
          new FileInputStream(fileName), enc));
      String line = null;
      while ((line = in.readLine()) != null) {
        retVal.append(line);
        retVal.append(lineSep);
      }
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return retVal.toString();
  }

  /** Reads a character file into a string. Uses the specified
   *  character encoding. Returns <code>null</code> if
   *  any of the exceptions is thrown.
   *
   *  @param file The name of the file to read
   *  @param enc File encoding
   *  @return The read file
   */
  public static String readTextFile(File file, String enc) {
    String lineSep = System.getProperty("line.separator");
    StringBuffer retVal = new StringBuffer();
    try {
      BufferedReader in = new BufferedReader(
        new InputStreamReader(
          new FileInputStream(file), enc));
      String line = null;
      while ((line = in.readLine()) != null) {
        retVal.append(line);
        retVal.append(lineSep);
      }
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return retVal.toString();
  }
  
  /**
   * Writes a text file contained in a string. Uses platform default character
   * encoding.
   * 
   * @param fileName The name of the file
   * @param text File contents
   * @return <code>true</code> if operation was successful
   */
  public static boolean writeTextFile(String fileName, String text) {
    String enc = System.getProperty("file.encoding");
    return writeTextFile(fileName, enc, text);
  }
  
  /**
   * Writes a text file contained in a string. Uses the given character
   * encoding.
   * 
   * @param fileName The name of the file
   * @param enc Encoding to use
   * @param text File contents
   * @return <code>true</code> if operation was successful
   */
  public static boolean writeTextFile(String fileName, String enc, String text) {
    boolean retVal = true;
    try {
      BufferedWriter out = new BufferedWriter(
        new OutputStreamWriter(
          new FileOutputStream(fileName), enc));
      out.write(text);
      out.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      retVal = false;
    }
    return retVal;
  }
  
  /**
   * Retrieves the directory where the given class is located (a top-level 
   * package directory is assumed). If the class is stored within a JAR file, 
   * the directory where the JAR file is stored is returned.
   * 
   * @param clazz The class to search for
   * @return The full directory name where the class is stored
   */
  public static String getClassDir(Class clazz) {
    String className = clazz.getName().replace('.', '/');
    String jarName = clazz.getResource("/" + className + ".class").toString();
    try {
      jarName = URLDecoder.decode(jarName, "UTF-8");
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    java.io.File installDir = null;
    if (jarName.startsWith("jar:")) {
      installDir = new File(jarName.substring(10, jarName.indexOf('!')));
      String retVal = installDir.getParent();
      if (retVal.charAt(1) != ':')
        retVal = "/" + retVal;
      return retVal;
    } else {
      installDir = new File(jarName.substring(0, jarName.indexOf(className)));
      String path = installDir.getPath().replace('\\', '/').substring(6);
      if ("Linux".equals(System.getProperty("os.name")))
        path = "/" + path;
      return path;
    }
  }
  
  /**
   * Recursively retrieves contents of the given directory. The directory is 
   * loaded by the given class' classloader, so it can reside in the file system
   * or in a JAR file. Directory names end with a slash (/).
   * 
   * @param clazz The class to use
   * @param dirName The directory name
   * @return An array of file/dir names
   */
  public static String[] listFiles(Class clazz, String dirName) {
    try {
      List retVal = new ArrayList();
      URL url = clazz.getResource(dirName);
      if (url.toString().startsWith("jar:")) {
        JarURLConnection conn = (JarURLConnection)url.openConnection();
        JarFile jarFile = conn.getJarFile();
        Enumeration entries = jarFile.entries();
        String relative = dirName.substring(1);
        while (entries.hasMoreElements()) {
          JarEntry entry = (JarEntry)entries.nextElement();
          String name = entry.getName();
          if (name.startsWith(relative))
            retVal.add("/" + name);
        }
      } else {
        File dir = new File(url.getFile());
        if (dir.isDirectory())
          traverse(dirName, dir, retVal);
      }
      String[] names = new String[retVal.size()];
      for (int i = 0; i < names.length; i++)
        names[i] = (String)retVal.get(i);
      return names;
    } catch (Exception ex) {
      return new String[0];
    }
  }
  
  /**
   * Helper method for <code>listFiles</code>.
   * @param context Current context
   * @param dir Directory to traverse
   * @param dest Destination list for file/dir names
   */
  private static void traverse(String context, File dir, List dest) {
    File[] files = dir.listFiles();
    for (int i = 0; i < files.length; i++) {
      String fName = lastPart(files[i]);
      String name = context + "/" + fName;
      if (files[i].isDirectory()) {
        dest.add(name+"/");
        String newContext = context + "/" + lastPart(files[i]); 
        traverse(newContext, files[i], dest);
      } else if (files[i].isFile())
        dest.add(name);
    }
  }
  
  /**
   * Helper method for <code>listFiles</code>.
   */
  private static String lastPart(File file) {
    String temp = file.getAbsolutePath();
    return temp.substring(temp.lastIndexOf(fileSep) + 1);
  }
  
  private static char fileSep = System.getProperty("file.separator").charAt(0);
}