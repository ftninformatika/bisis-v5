package com.ftninformatika.bisis.format;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.util.xml.XMLUtils;

/**
 * Class FormatFactory comment.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class FormatFactory {

  public static UFormat getFormat(Connection conn) {
    UFormat fieldSet = new UFormat();
    fieldSet.setName("Sve");
    fieldSet.setDescription("Kompletan format");
    
    Statement stmt;
    ResultSet rset;
    HashMap firstIndicators = new HashMap();
    HashMap secondIndicators = new HashMap();
    HashMap extCoderTypes = new HashMap();
    HashMap internalCoders = new HashMap();
    HashMap subsubfieldCoders = new HashMap();
    HashMap subsubfields = new HashMap();
    HashMap subfields = new HashMap();
    try {

      // Retrieve first indicators
      stmt = conn.createStatement();
      rset = stmt.executeQuery(
          "SELECT po_poljeid, prindid, pi_naziv FROM prvi_indikator");
      while (rset.next()) {
        String key = rset.getString(1);
        UIndicator ind = (UIndicator)firstIndicators.get(key);
        if (ind == null) {
          ind = new UIndicator();
          firstIndicators.put(key, ind);
        }
        ind.getValues().add(new UItem(rset.getString(2), rset.getString(3)));
      }
      rset.close();
      stmt.close();
      
      // Retrieve second indicators
      stmt = conn.createStatement();
      rset = stmt.executeQuery(
          "SELECT po_poljeid, drindid, di_naziv FROM drugi_indikator");
      while (rset.next()) {
        String key = rset.getString(1);
        UIndicator ind = (UIndicator)secondIndicators.get(key);
        if (ind == null) {
          ind = new UIndicator();
          secondIndicators.put(key, ind);
        }
        ind.getValues().add(new UItem(rset.getString(2), rset.getString(3)));
      }
      rset.close();
      stmt.close();
      
      // Retrieve external coder types
      stmt = conn.createStatement();
      rset = stmt.executeQuery(
          "SELECT tesid, tes_naziv FROM tipekst_sifarnika");
      while (rset.next()) {
        Integer key = new Integer(rset.getInt(1));
        extCoderTypes.put(key, 
            new UCoder(key.intValue(), rset.getString(2), true));
      }
      rset.close();
      stmt.close();
      
      // Retrieve external coders
      stmt = conn.createStatement();
      rset = stmt.executeQuery(
          "SELECT tes_tesid, esid, es_naziv FROM eksterni_sifarnik");
      while (rset.next()) {
        Integer key = new Integer(rset.getInt(1));
        UCoder coder = (UCoder)extCoderTypes.get(key);
        coder.addItem(new UItem(rset.getString(2), rset.getString(3)));
      }
      rset.close();
      stmt.close();
      
      // Retrieve internal coders
      stmt = conn.createStatement();
      rset = stmt.executeQuery(
          "SELECT ppo_potpoljaid, ppo_po_poljeid, isid, is_naziv " +
          "FROM interni_sifarnik");
      while (rset.next()) {
        String key = rset.getString(2) + rset.getString(1);
        UCoder coder = (UCoder)internalCoders.get(key);
        if (coder == null) {
          coder = new UCoder();
          internalCoders.put(key, coder);
        }
        coder.addItem(new UItem(rset.getString(3), rset.getString(4)));
      }
      rset.close();
      stmt.close();
      
      // Retrieve internal coders for subsubfields
      stmt = conn.createStatement();
      rset = stmt.executeQuery(
          "SELECT ppp_potpid, ppp_ppo_potpoljaid, ppp_ppo_po_poljeid, " +
          "sifpotpid, sifpotp_naziv FROM sifarnik_potpotpolja");
      while (rset.next()) {
        String key = rset.getString(3) + rset.getString(2) + rset.getString(1);
        UCoder coder = (UCoder)subsubfieldCoders.get(key);
        if (coder == null) {
          coder = new UCoder();
          subsubfieldCoders.put(key, coder);
        }
        coder.addItem(new UItem(rset.getString(4), rset.getString(5)));
      }
      rset.close();
      stmt.close();
      
      // Retrieve subsubfields
      stmt = conn.createStatement();
      rset = stmt.executeQuery(
          "SELECT ppo_potpoljaid, ppo_po_poljeid, potpid, potp_naziv, " +
          "potpduzina, potponov, potpobavez, potpdefvr FROM potpotpolja");
      while (rset.next()) {
        USubsubfield ssf = new USubsubfield();

        String key = rset.getString(2) + rset.getString(1);
        List ssfList = (List)subsubfields.get(key);
        if (ssfList == null) {
          ssfList = new ArrayList();
          subsubfields.put(key, ssfList);
        }
        ssfList.add(ssf);

        ssf.setName(rset.getString(3).charAt(0));
        ssf.setDescription(rset.getString(4));
        ssf.setLength(rset.getInt(5));
        ssf.setRepeatable(rset.getInt(6) == 1);
        ssf.setMandatory(rset.getInt(7) == 1);
        ssf.setDefaultValue(rset.getString(8));
        String id = key + ssf.getName();
        ssf.setCoder((UCoder)subsubfieldCoders.get(id));
        ssf.setValidator(ValidatorFactory.getValidator(id));
      }
      rset.close();
      stmt.close();
      
      
      // Retrieve subfields
      stmt = conn.createStatement();
      rset = stmt.executeQuery(
          "SELECT po_poljeid, potpoljaid, obaveznostpp, ponovljivostpp, " +
          "pp_naziv, duzina, defvrednost, tes_tesid FROM potpolja");
      while (rset.next()) {
        USubfield sf = new USubfield();

        String key = rset.getString(1);
        List sfList = (List)subfields.get(key);
        if (sfList == null) {
          sfList = new ArrayList();
          subfields.put(key, sfList);
        }
        sfList.add(sf);
        
        sf.setName(rset.getString(2).charAt(0));
        sf.setMandatory(rset.getInt(3) == 1);
        sf.setRepeatable(rset.getInt(4) == 1);
        sf.setDescription(rset.getString(5));
        sf.setLength(rset.getInt(6));
        sf.setDefaultValue(rset.getString(7));
        Integer extCoder = new Integer(rset.getInt(8));
        String id = key + sf.getName();
        UCoder coder = (UCoder)extCoderTypes.get(extCoder);
        if (coder != null)
          sf.setCoder(coder);
        else {
          coder = (UCoder)internalCoders.get(id);
          sf.setCoder(coder);
        }
        sf.setValidator(ValidatorFactory.getValidator(id));
        
        List ssfList = (List)subsubfields.get(id);
        if (ssfList != null) {
          sf.setSubsubfields(ssfList);
          Iterator it = ssfList.iterator();
          while (it.hasNext()) {
            ((USubsubfield)it.next()).setOwner(sf);
          }
        }
      }
      rset.close();
      stmt.close();
      

      // Retrieve fields
      stmt = conn.createStatement();
      rset = stmt.executeQuery(
          "SELECT poljeid, po_naziv, obaveznost, ponovljivost, sekundarnost, " +
          "opis_pi, opis_di, def_pi, def_di FROM polje");
      while (rset.next()) {
        UField f = new UField(rset.getString(1));
        f.setDescription(rset.getString(2));
        f.setMandatory(rset.getInt(3) == 1);
        f.setRepeatable(rset.getInt(4) == 1);
        f.setSecondary(rset.getInt(5) == 1);
        UIndicator ind1 = (UIndicator)firstIndicators.get(f.getName());
        f.setInd1(ind1);
        if (ind1 != null) {
          ind1.setDescription(rset.getString(6));
          ind1.setDefaultValue(rset.getString(8));
        }
        UIndicator ind2 = (UIndicator)secondIndicators.get(f.getName());
        f.setInd2(ind2);
        if (ind2 != null) {
          ind2.setDescription(rset.getString(7));
          ind2.setDefaultValue(rset.getString(9));
        }
        
        List sfList = (List)subfields.get(f.getName());
        if (sfList != null) {
          f.setSubfields(sfList);
          Iterator i = sfList.iterator();
          while (i.hasNext()) {
            ((USubfield)i.next()).setOwner(f);
          }
        }
        
        fieldSet.add(f);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    //fieldSet.sort();
    return fieldSet;
  }
  
  public static UFormat getFormat(String xml) {
    try {
      SAXParser parser = factory.newSAXParser();
      FormatHandler handler = new FormatHandler();
      parser.parse(XMLUtils.getInputSourceFromString(xml), handler);
      return handler.getFormat();
    } catch (Exception ex) {
      log.fatal(ex);
      return null;
    }
  }
  
  public static UFormat getFormat(InputStream xml) {
    try {
      SAXParser parser = factory.newSAXParser();
      FormatHandler handler = new FormatHandler();
      parser.parse(xml, handler);
      return handler.getFormat();
    } catch (Exception ex) {
      log.fatal(ex);
      return null;
    }
  }
  
  public static UFormat getPubType(String xml) {
    try {
      SAXParser parser = factory.newSAXParser();
      PubTypeHandler handler = new PubTypeHandler();
      parser.parse(XMLUtils.getInputSourceFromString(xml), handler);
      return handler.getPubType();
    } catch (Exception ex) {
      log.fatal(ex);
      return null;
    }
  }
  
  public static UFormat getPubType(InputStream xml) {
    try {
      SAXParser parser = factory.newSAXParser();
      PubTypeHandler handler = new PubTypeHandler();
      parser.parse(xml, handler);
      return handler.getPubType();
    } catch (Exception ex) {
    	 ex.printStackTrace();
      log.fatal(ex);
      return null;
    }
  }
  
  static SAXParserFactory factory = SAXParserFactory.newInstance();
  static Log log = LogFactory.getLog(FormatFactory.class.getName());
}
