package com.ftninformatika.utils.xml;

import java.io.*;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.*;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.DocumentType;

/** Utility class providing methods for XML document
 *  manipulation.
 *
 *  @author Branko Milosavljevic, mbranko@uns.ac.rs
 *  @version 1.1
 */
public class XMLUtils {

  public static Document getDocumentFromString(String xml) {
    Document retVal = null;
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      retVal = db.parse(getInputSourceFromString(xml));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return retVal;
  }
  
  /**
   * Forms an <code>InputSource</code> for parsing XML documents
   * from a string. Returns <code>null</code> if any of the exceptions
   * are thrown.
   * 
   * @param xml String with the XML document.
   * @return An <code>InputSource</code> representation.
   */
  public static InputSource getInputSourceFromString(String xml) {
    InputSource retVal = null;
    try {
      retVal = new InputSource(new StringReader(xml));
    } catch (Exception ex) {
    }
    return retVal;
  }
  
  /** Forms an <code>InputSource</code> for parsing XML documents
   *  from a file. Assumes a default character encoding. Returns
   *  <code>null</code> if any of the exceptions is thrown.
   *
   * @param fileName The name of the file.
   * @return An <code>InputSource</code> representation.
   */
  public static InputSource getInputSource(String fileName) {
    InputSource retVal = null;
    try {
      retVal = new InputSource(
        new BufferedReader(
          new FileReader(fileName)));
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return retVal;
  }

  /** Forms an <code>InputSource</code> for parsing XML documents
   *  from a file. Assumes a default character encoding. Returns
   *  <code>null</code> if any of the exceptions is thrown.
   *
   * @param file The file.
   * @return An <code>InputSource</code> representation.
   */
  public static InputSource getInputSource(File file) {
    InputSource retVal = null;
    try {
      retVal = new InputSource(
        new BufferedReader(
          new FileReader(file)));
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return retVal;
  }

  /** Forms an <code>InputSource</code> for parsing XML documents
   *  from a file. Returns
   *  <code>null</code> if any of the exceptions is thrown.
   *
   * @param fileName The name of the file.
   * @param enc Character encoding to use.
   * @return An <code>InputSource</code> representation.
   */
  public static InputSource getInputSource(String fileName, String enc) {
    InputSource retVal = null;
    try {
      retVal = new InputSource(
        new BufferedReader(
          new InputStreamReader(
            new FileInputStream(fileName), enc)));
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return retVal;
  }

  /** Forms an <code>InputSource</code> for parsing XML documents
   *  from a file. Returns
   *  <code>null</code> if any of the exceptions is thrown.
   *
   * @param file The file.
   * @param enc Character encoding to use.
   * @return An <code>InputSource</code> representation.
   */
  public static InputSource getInputSource(File file, String enc) {
    InputSource retVal = null;
    try {
      retVal = new InputSource(
        new BufferedReader(
          new InputStreamReader(
            new FileInputStream(file), enc)));
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return retVal;
  }

  static java.io.PrintStream out = System.out;
  static boolean canonical = false;
  static String Encoding = "default";

  /** Returns the first element with the given name from a document.
   * 
   *  @param doc Document to be searched
   *  @param name The element name to search for
   *  @return Node representing the element
   */
  public static Node getNodeByName(Document doc, String name) {
    Node retVal = null;
    NodeList nl = doc.getElementsByTagName(name);
    if (nl != null && nl.getLength() != 0)
      retVal = nl.item(0);
    return retVal;
  }

  /** Returns the first child element with the given name from an element.
   *
   *  @param node The parent element to be searched
   *  @param name The element name to search for
   *  @return Node representing the element
   */
  public static Node getSubnodeByName(Node node, String name) {
    Node retVal = null;
    NodeList nl = node.getChildNodes();
    if (nl != null && nl.getLength() != 0) {
      for (int i = 0; i < nl.getLength(); i++)
        if (nl.item(i).getNodeName().equals(name)) {
          retVal = nl.item(i);
          break;
        }
    }
    return retVal;
  }

  /** Returns all child <code>Node</code>s with the given name stored in a
   *  <code>Vector</code>.
   *
   *  @param node The parent node to be searched for
   *  @param name The element name to search for
   *  @return <code>Vector</code> of found </code>Node</code>s
   */
  public static Vector getSubnodesByName(Node node, String name) {
    Vector retVal = null;
    NodeList nl = node.getChildNodes();
    if (nl != null && nl.getLength() != 0) {
      for (int i = 0; i < nl.getLength(); i++) {
        if (nl.item(i).getNodeName().equals(name)) {
          if (retVal == null)
            retVal = new Vector();
          retVal.addElement(nl.item(i));
        }
      }
    }
    return retVal;
  }

  /** Prints the specified node, recursively.
   *
   *  @param node Node to be printed
   */
  public static void print(Node node) {
    // is there anything to do?
    if (node == null) {
      return;
    }
    System.out.println("");
      
    int type = node.getNodeType();
    switch ( type ) {
      // print document
      case Node.DOCUMENT_NODE: {
        /*
        if (!canonical) {
          if (Encoding.equalsIgnoreCase("DEFAULT"))
            Encoding = "UTF-8";
          else if(Encoding.equalsIgnoreCase("Unicode"))
            Encoding = "UTF-16";
          else 
            Encoding = MIME2Java.reverse(Encoding);
            out.println("<?xml version=\"1.0\" encoding=\"" +
              Encoding + "\"?>");
        }
        */
        print(((Document)node).getDocumentElement());
        out.flush();
        break;
      }
      // print element with attributes
      case Node.ELEMENT_NODE: {
        out.print('<');
        out.print(node.getNodeName());
        Attr attrs[] = sortAttributes(node.getAttributes());
        for (int i = 0; i < attrs.length; i++) {
          Attr attr = attrs[i];
          out.print(' ');
          out.print(attr.getNodeName());
          out.print("=\"");
          out.print(normalize(attr.getNodeValue()));
          out.print('"');
        }
        out.print('>');
        NodeList children = node.getChildNodes();
        if (children != null) {
          int len = children.getLength();
          for (int i = 0; i < len; i++) {
            print(children.item(i));
          }
        }
        break;
      }
      // handle entity reference nodes
      case Node.ENTITY_REFERENCE_NODE: {
        if (canonical) {
          NodeList children = node.getChildNodes();
          if (children != null) {
            int len = children.getLength();
            for (int i = 0; i < len; i++) {
              print(children.item(i));
            }
          }
        } else {
          out.print('&');
          out.print(node.getNodeName());
          out.print(';');
        }
        break;
      }
      // print cdata sections
      case Node.CDATA_SECTION_NODE: {
        if (canonical) {
          out.print(normalize(node.getNodeValue()));
        } else {
          out.print("<![CDATA[");
          out.print(node.getNodeValue());
          out.print("]]>");
        }
        break;
      }
      // print DocumentType sections
      case Node.DOCUMENT_TYPE_NODE: {
        out.print("<!DOCTYPE ");
        out.print(((DocumentType)node).getName());
        out.print(" SYSTEM ");
        out.print(((DocumentType)node).getSystemId());
        out.print(">");
        break;
      }
      // print text
      case Node.TEXT_NODE: {
        out.print(normalize(node.getNodeValue()));
        break;
      }
      // print processing instruction
      case Node.PROCESSING_INSTRUCTION_NODE: {
        out.print("<?");
        out.print(node.getNodeName());
        String data = node.getNodeValue();
        if (data != null && data.length() > 0) {
          out.print(' ');
          out.print(data);
        }
        out.print("?>");
        break;
      }
    }
    if (type == Node.ELEMENT_NODE) {
      out.print("</");
      out.print(node.getNodeName());
      out.print('>');
    }
    out.flush();
  }

  /** Returns a sorted list of attributes.
   *
   *  @param attrs A map of attributes
   *  @return A sorted array of attributes 
   */
  protected static Attr[] sortAttributes(NamedNodeMap attrs) {
    int len = (attrs != null) ? attrs.getLength() : 0;
    Attr array[] = new Attr[len];
    for (int i = 0; i < len; i++) {
      array[i] = (Attr)attrs.item(i);
    }
    /*    
    for (int i = 0; i < len - 1; i++) {
      String name  = array[i].getNodeName();
      int index = i;
      for (int j = i + 1; j < len; j++) {
        String curName = array[j].getNodeName();
        if (curName.compareTo(name) < 0) {
          name  = curName;
          index = j;
        }
      }
      if (index != i) {
        Attr temp    = array[i];
        array[i]     = array[index];
        array[index] = temp;
      }
    }
    */    
    return (array);
  }

  /** Normalizes the given string.
   *
   *  @param s The string to normalize
   *  @return The normalized string
   */
  public static String normalize(String s) {
    StringBuffer str = new StringBuffer();
  
    int len = (s != null) ? s.length() : 0;
    for (int i = 0; i < len; i++) {
      char ch = s.charAt(i);
      switch (ch) {
        case '<': {
          str.append("&lt;");
          break;
        }
        case '>': {
          str.append("&gt;");
          break;
        }
        case '&': {
          str.append("&amp;");
          break;
        }
        case '"': {
          str.append("&quot;");
          break;
        }
        case '\r':
        case '\n': 
        // else, default append char
        default: {
          str.append(ch);
        }
      }
    }
    return str.toString();
  }

}