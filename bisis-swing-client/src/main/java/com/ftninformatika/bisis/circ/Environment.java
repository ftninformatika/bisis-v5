package com.ftninformatika.bisis.circ;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.ftninformatika.utils.NetUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Environment extends DefaultHandler{
	
	private String nonCtlgNo = "";
	private String blockedCard = "";
	private String autoReturn = "";
	private String defaultZipCity = "";
	private String defaultCity = "";
	private String defaultZip = "";
	private String fontSize = "";
	private String maximize = "";
	private String lookAndFeel = "";
	private String theme = "";
	private String location = "";
	private String useridLength = "";
	private String useridPrefix = "";
	private String useridPrefixLength = "";
	private String useridDefaultPrefix = "";
	private String useridSeparator = "";
	private String useridSeparatorSign = "";
	private String useridInput = "";
	private String ctlgnoLength = "";
	private String ctlgnoLocation = "";
	private String ctlgnoLocationLength = "";
	private String ctlgnoDefaultLocation = "";
	private String ctlgnoBook = "";
	private String ctlgnoBookLength = "";
	private String ctlgnoDefaultBook = "";
	private String ctlgnoSeparators = "";
	private String ctlgnoSeparator1 = "";
	private String ctlgnoSeparator2 = "";
	private String ctlgnoInput = "";
	private String reversLibraryName = "";
	private String reversLibraryAddress = "";
  private String reversSelected = "";
	private String reversHeight = "";
	private String reversWidth = "";
	private String reversRowCount = "";
	private String reversCount = "";
  private boolean found = false;
  private boolean notfound = false;
  private int current = 0;
  private InputSource xmlsource;
  private String mac;
  private String xml;
  private static Log log = LogFactory.getLog(Environment.class.getName());
	
	
	public Environment(String xml){
		this.xml = xml;
		if (xml != null){
			xmlsource = new InputSource(new StringReader(xml));
		} else {
			xmlsource = new InputSource(Environment.class.getResourceAsStream("/circ-options.xml"));
		}
    mac = NetUtils.getMACAddress();
  }
  
  public void setNonCtlgNo(boolean value){
    if (value == true)
      nonCtlgNo = "true";
		nonCtlgNo = "false";
	}
	
	public boolean getNonCtlgNo(){
    if (nonCtlgNo.equals("true"))
      return true;
    return false;
	}
	
	public void setBlockedCard(boolean value){
    if (value == true)
      blockedCard = "true";
    blockedCard = "false";
	}
	
	public boolean getBlockedCard(){
    if (blockedCard.equals("true"))
      return true;
    return false;
	}
	
	public void setAutoReturn(boolean value){
    if (value == true)
      autoReturn = "true";
    autoReturn = "false";
	}
	
	public boolean getAutoReturn(){
    if (autoReturn.equals("true"))
      return true;
    return false;
	}
	
	public void setDefaultZipCity(boolean value){
    if (value == true)
      defaultZipCity = "true";
    defaultZipCity = "false";
	}
	
	public boolean getDefaultZipCity(){
    if (defaultZipCity.equals("true"))
      return true;
    return false;
	}
	
	public void setDefaultCity(String value){
		defaultCity = value;
	}
	
	public String getDefaultCity(){
		return defaultCity;
	}
	
	public void setDefaultZip(String value){
		defaultZip = value;
	}
	
	public String getDefaultZip(){
		return defaultZip;
	}
	
	public void setFontSize(String value){
		fontSize = value;
	}
	
	public int getFontSize(){
		return Integer.parseInt(fontSize);
	}
	
	public void setMaximize(boolean value){
    if (value == true)
      maximize = "true";
    maximize = "false";
	}
	
	public boolean getMaximize(){
    if (maximize.equals("true"))
      return true;
    return false;
	}
	
	public void setLookAndFeel(String value){
		lookAndFeel = value;
	}
	
	public String getLookAndFeel(){
		return lookAndFeel;
	}
	
	public void setTheme(String value){
		theme = value;
	}
	
	public String getTheme(){
		return theme;
	}
	
	public void setLocation(String value){
		location = value;
	}
	
	public int getLocation(){
		return Integer.parseInt(location);
	}
	
	public void setUseridLength(String value){
		useridLength = value;
	}
	
	public int getUseridLength(){
		return Integer.parseInt(useridLength);
	}
	
	public void setUseridPrefixLength(String value){
		useridPrefixLength = value;
	}
	
	public int getUseridPrefixLength(){
		return Integer.parseInt(useridPrefixLength);
	}
	
	public void setUseridDefaultPrefix(String value){
		useridDefaultPrefix = value;
	}
	
	public int getUseridDefaultPrefix(){
		return Integer.parseInt(useridDefaultPrefix);
	}
	
	public void setUseridPrefix(boolean value){
    if (value == true)
      useridPrefix = "true";
    useridPrefix = "false";
	}
	
	public boolean getUseridPrefix(){
    if (useridPrefix.equals("true"))
      return true;
    return false;
	}
	
	public void setUseridSeparator(boolean value){
    if (value == true)
      useridSeparator = "true";
    useridSeparator = "false";
	}
	
	public boolean getUseridSeparator(){
    if (useridSeparator.equals("true"))
      return true;
    return false;
	}
	
	public void setUseridSeparatorSign(String value){
		useridSeparatorSign = value;
	}
	
	public String getUseridSeparatorSign(){
		return useridSeparatorSign;
	}
	
	public void setUseridInput(String value){
		useridInput = value;
	}
	
	public int getUseridInput(){
		return Integer.parseInt(useridInput);
	}
	
	public void setCtlgnoLength(String value){
		ctlgnoLength = value;
	}
	
	public int getCtlgnoLength(){
		return Integer.parseInt(ctlgnoLength);
	}
	
	public void setCtlgnoLocation(boolean value){
    if (value == true)
      ctlgnoLocation = "true";
    ctlgnoLocation = "false";
	}
	
	public boolean getCtlgnoLocation(){
    if (ctlgnoLocation.equals("true"))
      return true;
    return false;
	}
	
	public void setCtlgnoLocationLength(String value){
		ctlgnoLocationLength = value;
	}
	
	public int getCtlgnoLocationLength(){
		return Integer.parseInt(ctlgnoLocationLength);
	}
	
	public void setCtlgnoDefaultLocation(String value){
		ctlgnoDefaultLocation = value;
	}
	
	public int getCtlgnoDefaultLocation(){
		return Integer.parseInt(ctlgnoDefaultLocation);
	}
	
	public void setCtlgnoBook(boolean value){
    if (value == true)
      ctlgnoBook = "true";
    ctlgnoBook = "false";
	}
	
	public boolean getCtlgnoBook(){
    if (ctlgnoBook.equals("true"))
      return true;
    return false;
	}
	
	public void setCtlgnoBookLength(String value){
		ctlgnoBookLength = value;
	}
	
	public int getCtlgnoBookLength(){
		return Integer.parseInt(ctlgnoBookLength);
	}
	
	public void setCtlgnoDefaultBook(String value){
		ctlgnoDefaultBook = value;
	}
	
	public int getCtlgnoDefaultBook(){
		return Integer.parseInt(ctlgnoDefaultBook);
	}
	
	public void setCtlgnoSeparators(boolean value){
    if (value == true)
      ctlgnoSeparators = "true";
    ctlgnoSeparators = "false";
	}
	
	public boolean getCtlgnoSeparators(){
    if (ctlgnoSeparators.equals("true"))
      return true;
    return false;
	}
	
	public void setCtlgnoSeparator1(String value){
		ctlgnoSeparator1 = value;
	}
	
	public String getCtlgnoSeparator1(){
		return ctlgnoSeparator1;
	}
	
	public void setCtlgnoSeparator2(String value){
		ctlgnoSeparator2 = value;
	}
	
	public String getCtlgnoSeparator2(){
		return ctlgnoSeparator2;
	}
	
	public void setCtlgnoInput(String value){
		ctlgnoInput = value;
	}
	
	public int getCtlgnoInput(){
		return Integer.parseInt(ctlgnoInput);
	}
	
	public void setReversLibraryName(String value){
		reversLibraryName = value;
	}
	
	public String getReversLibraryName(){
		return reversLibraryName;
	}
	
	
	public void setReversLibraryAddress(String value){
		reversLibraryAddress = value;
	}
	
	public String getReversLibraryAddress(){
		return reversLibraryAddress;
	}
  
  public void setReversSelected(boolean value){
    if (value == true)
      reversSelected = "true";
    reversSelected = "false";
  }
  
  public boolean getReversSelected(){
    if (reversSelected.equals("true"))
      return true;
    return false;
  }
	
	public void setReversHeight(String value){
		reversHeight = value;
	}
	
	public String getReversHeight(){
		return reversHeight;
	}
	
	public void setReversWidth(String value){
		reversWidth = value;
	}
	
	public String getReversWidth(){
		return reversWidth;
	}
	
	public void setReversRowCount(String value){
		reversRowCount = value;
	}
	
	public String getReversRowCount(){
		return reversRowCount;
	}
	
	public void setReversCount(String value){
		reversCount = value;
	}
	
	public String getReversCount(){
		return reversCount;
	}
   
  public int loadOptions() {
    try {
      SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
      parser.parse(xmlsource, this);
      if (!found){
        notfound = true;
        if (xml != null){
    			xmlsource = new InputSource(new StringReader(xml));
    		} else {
    			xmlsource = new InputSource(Environment.class.getResourceAsStream("/circ-options.xml"));
    		}
        parser.parse(xmlsource, this);
      }
      return 1;
    } catch (Exception ex) {
      log.fatal(ex);
      return 0;
    }
  }
  
  public void startDocument() throws SAXException {
  }
    
  public void endDocument() throws SAXException {
  }

  public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {
    if (qName.equals("client")) {
      String name = attrs.getValue("mac").toUpperCase();
      if (name.equals(mac)){
        found = true;
      } else if (notfound && name.equals("DEFAULT")){
        found = true;
      } else {
        found = false;
      }
    } else if (qName.equals("nonCtlgNo")) {
      current = 1;
    } else if (qName.equals("blockedCard")) {
      current = 2;
    } else if (qName.equals("autoReturn")) {
      current = 3;
    } else if (qName.equals("defaultZipCity")) {
      current = 4;
    } else if (qName.equals("defaultCity")) {
      current = 5;
    } else if (qName.equals("defaultZip")) {
      current = 6;
    } else if (qName.equals("fontSize")) {
      current = 7;
    } else if (qName.equals("maximize")) {
      current = 8;
    } else if (qName.equals("lookAndFeel")) {
      current = 9;
    } else if (qName.equals("theme")) {
      current = 10;
    } else if (qName.equals("location")) {
      current = 11;
    } else if (qName.equals("length")) {
      current = 12;
    } else if (qName.equals("prefix")) {
      current = 13;
    } else if (qName.equals("prefixLength")) {
      current = 14;
    } else if (qName.equals("defaultPrefix")) {
      current = 15;
    } else if (qName.equals("separator")) {
      current = 16;
    } else if (qName.equals("separatorSign")) {
      current = 17;
    } else if (qName.equals("inputUserid")) {
      current = 18;
    } else if (qName.equals("lengthCtlgno")) {
      current = 19;
    } else if (qName.equals("locationCtlgno")) {
      current = 20;
    } else if (qName.equals("locationLength")) {
      current = 21;
    } else if (qName.equals("defaultLocation")) {
      current = 22;
    } else if (qName.equals("book")) {
      current = 23;
    } else if (qName.equals("bookLength")) {
      current = 24;
    } else if (qName.equals("defaultBook")) {
      current = 25;
    } else if (qName.equals("separators")) {
      current = 26;
    } else if (qName.equals("separator1")) {
      current = 27;
    } else if (qName.equals("separator2")) {
      current = 28;
    } else if (qName.equals("inputCtlgno")) {
      current = 29;
    } else if (qName.equals("libraryName")) {
      current = 30;
    } else if (qName.equals("libraryAddress")) {
      current = 31;
    } else if (qName.equals("height")) {
      current = 32;
    } else if (qName.equals("width")) {
      current = 33;
    } else if (qName.equals("rowCount")) {
      current = 34;
    } else if (qName.equals("count")) {
      current = 35;
    } else if (qName.equals("selected")) {
      current = 36;
    }
  }
    
  public void endElement(String namespaceURI, String lName, String qName) throws SAXException {
    current = 0;
  }

  public void characters(char[] buf, int offset, int len) throws SAXException {  
    if (found){
      switch (current){
        case 1: nonCtlgNo = new String(buf, offset, len).trim();break;
        case 2: blockedCard = new String(buf, offset, len).trim();break;
        case 3: autoReturn = new String(buf, offset, len).trim();break;
        case 4: defaultZipCity = new String(buf, offset, len).trim();break;
        case 5: defaultCity = new String(buf, offset, len).trim();break;
        case 6: defaultZip = new String(buf, offset, len).trim();break;
        case 7: fontSize = new String(buf, offset, len).trim();break;
        case 8: maximize = new String(buf, offset, len).trim();break;
        case 9: lookAndFeel = new String(buf, offset, len).trim();break;
        case 10: theme = new String(buf, offset, len).trim();break;
        case 11: location = new String(buf, offset, len).trim();break;
        case 12: useridLength = new String(buf, offset, len).trim();break;
        case 13: useridPrefix = new String(buf, offset, len).trim();break;
        case 14: useridPrefixLength = new String(buf, offset, len).trim();break;
        case 15: useridDefaultPrefix = new String(buf, offset, len).trim();break;
        case 16: useridSeparator = new String(buf, offset, len).trim();break;
        case 17: useridSeparatorSign = new String(buf, offset, len).trim();break;
        case 18: useridInput = new String(buf, offset, len).trim();break;
        case 19: ctlgnoLength = new String(buf, offset, len).trim();break;
        case 20: ctlgnoLocation = new String(buf, offset, len).trim();break;
        case 21: ctlgnoLocationLength = new String(buf, offset, len).trim();break;
        case 22: ctlgnoDefaultLocation = new String(buf, offset, len).trim();break;
        case 23: ctlgnoBook = new String(buf, offset, len).trim();break;
        case 24: ctlgnoBookLength = new String(buf, offset, len).trim();break;
        case 25: ctlgnoDefaultBook = new String(buf, offset, len).trim();break;
        case 26: ctlgnoSeparators = new String(buf, offset, len).trim();break;
        case 27: ctlgnoSeparator1 = new String(buf, offset, len).trim();break;
        case 28: ctlgnoSeparator2 = new String(buf, offset, len).trim();break;
        case 29: ctlgnoInput = new String(buf, offset, len).trim();break;
        case 30: reversLibraryName = new String(buf, offset, len).trim();break;
        case 31: reversLibraryAddress = new String(buf, offset, len).trim();break;
        case 32: reversHeight = new String(buf, offset, len).trim();break;
        case 33: reversWidth = new String(buf, offset, len).trim();break;
        case 34: reversRowCount = new String(buf, offset, len).trim();break;
        case 35: reversCount = new String(buf, offset, len).trim();break;
        case 36: reversSelected = new String(buf, offset, len).trim();break;
      }
    }
  }
	
}
