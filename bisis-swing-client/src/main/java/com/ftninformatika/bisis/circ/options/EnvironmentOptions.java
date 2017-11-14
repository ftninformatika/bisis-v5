package com.ftninformatika.bisis.circ.options;

import options.OptionsDocument;
import org.apache.xmlbeans.XmlOptions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;



public class EnvironmentOptions {

  private static OptionsDocument doc = null;
  private static InputStream input;
	
  public static OptionsDocument setDoc(String xml){

  	if (xml != null){
  		try{
  			input = new ByteArrayInputStream(xml.getBytes("UTF-8"));
  		}catch(Exception e){
  			input = new ByteArrayInputStream(xml.getBytes());
  		}
		} else {
			input = EnvironmentOptions.class.getResourceAsStream("/circ-options.xml");
		}
  	
    try{
    	doc = OptionsDocument.Factory.parse(input);
    }catch(Exception e){
    	e.printStackTrace();
      doc = OptionsDocument.Factory.newInstance();
      doc.addNewOptions();
      doc.getOptions().addNewClient();
      doc.getOptions().getClientArray(0).setMac("default");
      doc.getOptions().getClientArray(0).addNewGeneral();
      doc.getOptions().getClientArray(0).addNewUserid();
      doc.getOptions().getClientArray(0).addNewCtlgno();
      doc.getOptions().getClientArray(0).addNewRevers();
    }
    return doc;
  }
  
  public static OptionsDocument getDoc(){
  	return doc;
  }
  
  public static void loadOptions(OptionsFrame frame, int row){
		frame.setMacAddress(doc.getOptions().getClientArray(row).getMac());
		frame.setNote(doc.getOptions().getClientArray(row).getNote());
		frame.setChkdNonCtlgNo(doc.getOptions().getClientArray(row).getGeneral().getNonCtlgNo());
		frame.setChkdBlockedCard(doc.getOptions().getClientArray(row).getGeneral().getBlockedCard());
		frame.setChkdAutoReturn(doc.getOptions().getClientArray(row).getGeneral().getAutoReturn());
		frame.setChkdDefaultZipCity(doc.getOptions().getClientArray(row).getGeneral().getDefaultZipCity());
		frame.setDefaultCity(doc.getOptions().getClientArray(row).getGeneral().getDefaultCity());
		frame.setDefaultZip(doc.getOptions().getClientArray(row).getGeneral().getDefaultZip());
		frame.setCmbSizeValue(doc.getOptions().getClientArray(row).getGeneral().getFontSize());
		frame.setChkdMaximize(doc.getOptions().getClientArray(row).getGeneral().getMaximize());
		frame.setCmbLookAndFeelValue(doc.getOptions().getClientArray(row).getGeneral().getLookAndFeel());
		frame.setCmbThemeValue(doc.getOptions().getClientArray(row).getGeneral().getTheme());
		frame.setLocationValue(doc.getOptions().getClientArray(row).getGeneral().getLocation());
		frame.setUserIDLength(doc.getOptions().getClientArray(row).getUserid().getLength());
		frame.setChkdPrefix(doc.getOptions().getClientArray(row).getUserid().getPrefix());
		frame.setPrefixLength(doc.getOptions().getClientArray(row).getUserid().getPrefixLength());
		frame.setDefaultPrefix(doc.getOptions().getClientArray(row).getUserid().getDefaultPrefix());
		frame.setChkdSeparator(doc.getOptions().getClientArray(row).getUserid().getSeparator());
		frame.setSeparator(doc.getOptions().getClientArray(row).getUserid().getSeparatorSign());
		frame.setUserIDInput(doc.getOptions().getClientArray(row).getUserid().getInputUserid());
		frame.setCtlgNoLength(doc.getOptions().getClientArray(row).getCtlgno().getLengthCtlgno());
		frame.setChkdLocation(doc.getOptions().getClientArray(row).getCtlgno().getLocationCtlgno());
		frame.setLocationLength(doc.getOptions().getClientArray(row).getCtlgno().getLocationLength());
		frame.setDefaultLocation(doc.getOptions().getClientArray(row).getCtlgno().getDefaultLocation());
		frame.setChkdBook(doc.getOptions().getClientArray(row).getCtlgno().getBook());
		frame.setBookLength(doc.getOptions().getClientArray(row).getCtlgno().getBookLength());
		frame.setDefaultBook(doc.getOptions().getClientArray(row).getCtlgno().getDefaultBook());
		frame.setChkdSeparators(doc.getOptions().getClientArray(row).getCtlgno().getSeparators());
		frame.setSeparator1(doc.getOptions().getClientArray(row).getCtlgno().getSeparator1());
		frame.setSeparator2(doc.getOptions().getClientArray(row).getCtlgno().getSeparator2());
		frame.setCtlgnoInput(doc.getOptions().getClientArray(row).getCtlgno().getInputCtlgno());
		frame.setLibraryName(doc.getOptions().getClientArray(row).getRevers().getLibraryName());
		frame.setLibraryAddress(doc.getOptions().getClientArray(row).getRevers().getLibraryAddress());
    frame.setChkdReversSelected(doc.getOptions().getClientArray(row).getRevers().getSelected());
		frame.setReversHeight(doc.getOptions().getClientArray(row).getRevers().getHeight());
		frame.setReversWidth(doc.getOptions().getClientArray(row).getRevers().getWidth());
		frame.setReversRowCount(doc.getOptions().getClientArray(row).getRevers().getRowCount());
		frame.setReversCount(doc.getOptions().getClientArray(row).getRevers().getCount());
	}
  
  public static void loadDefaults(OptionsFrame frame){
    String mac = doc.getOptions().getClientArray(0).getMac();
    int i = 0;
    while(!mac.equals("default") && i < doc.getOptions().sizeOfClientArray()){
      mac = doc.getOptions().getClientArray(i).getMac();
      i++;
    }
    loadOptions(frame, i--);
    frame.setMacAddress("");
    frame.setNote("");
  }
	
	public static void saveOptions(OptionsFrame frame, int rrow){
		int row = rrow;
		if (row == -1){
		  doc.getOptions().addNewClient();
		  row = doc.getOptions().sizeOfClientArray()-1;
		  doc.getOptions().getClientArray(row).addNewGeneral();
		  doc.getOptions().getClientArray(row).addNewUserid();
		  doc.getOptions().getClientArray(row).addNewCtlgno();
		  doc.getOptions().getClientArray(row).addNewRevers();
		}
		doc.getOptions().getClientArray(row).setMac(frame.getMacAddress());
		doc.getOptions().getClientArray(row).setNote(frame.getNote());
		doc.getOptions().getClientArray(row).getGeneral().setNonCtlgNo(frame.isChkdNonCtlgNo());
		doc.getOptions().getClientArray(row).getGeneral().setBlockedCard(frame.isChkdBlockedCard());
		doc.getOptions().getClientArray(row).getGeneral().setAutoReturn(frame.isChkdAutoReturn());
		doc.getOptions().getClientArray(row).getGeneral().setDefaultZipCity(frame.isChkdDefaultZipCity());
		doc.getOptions().getClientArray(row).getGeneral().setDefaultCity(frame.getDefaultCity());
		doc.getOptions().getClientArray(row).getGeneral().setDefaultZip(frame.getDefaultZip());
		doc.getOptions().getClientArray(row).getGeneral().setFontSize(frame.getCmbSizeValue());
		doc.getOptions().getClientArray(row).getGeneral().setMaximize(frame.isChkdMaximize());
		doc.getOptions().getClientArray(row).getGeneral().setLookAndFeel(frame.getCmbLookAndFeelValue());
		doc.getOptions().getClientArray(row).getGeneral().setTheme(frame.getCmbThemeValue());
		doc.getOptions().getClientArray(row).getGeneral().setLocation(frame.getLocationValue());
		doc.getOptions().getClientArray(row).getUserid().setLength(frame.getUserIDLength());
		doc.getOptions().getClientArray(row).getUserid().setPrefix(frame.isChkdPrefix());
		doc.getOptions().getClientArray(row).getUserid().setPrefixLength(frame.getPrefixLength());
		doc.getOptions().getClientArray(row).getUserid().setDefaultPrefix(frame.getDefaultPrefix());
		doc.getOptions().getClientArray(row).getUserid().setSeparator(frame.isChkdSeparator());
		doc.getOptions().getClientArray(row).getUserid().setSeparatorSign(frame.getSeparator());
		doc.getOptions().getClientArray(row).getUserid().setInputUserid(frame.getUserIDInput());
		doc.getOptions().getClientArray(row).getCtlgno().setLengthCtlgno(frame.getCtlgNoLength());
		doc.getOptions().getClientArray(row).getCtlgno().setLocationCtlgno(frame.isChkdLocation());
		doc.getOptions().getClientArray(row).getCtlgno().setLocationLength(frame.getLocationLength());
		doc.getOptions().getClientArray(row).getCtlgno().setDefaultLocation(frame.getDefaultLocation());
		doc.getOptions().getClientArray(row).getCtlgno().setBook(frame.isChkdBook());
		doc.getOptions().getClientArray(row).getCtlgno().setBookLength(frame.getBookLength());
		doc.getOptions().getClientArray(row).getCtlgno().setDefaultBook(frame.getDefaultBook());
		doc.getOptions().getClientArray(row).getCtlgno().setSeparators(frame.isChkdSeparators());
		doc.getOptions().getClientArray(row).getCtlgno().setSeparator1(frame.getSeparator1());
		doc.getOptions().getClientArray(row).getCtlgno().setSeparator2(frame.getSeparator2());
		doc.getOptions().getClientArray(row).getCtlgno().setInputCtlgno(frame.getCtlgnoInput());
		doc.getOptions().getClientArray(row).getRevers().setLibraryName(frame.getLibraryName());
		doc.getOptions().getClientArray(row).getRevers().setLibraryAddress(frame.getLibraryAddress());
    	doc.getOptions().getClientArray(row).getRevers().setSelected(frame.isChkdReversSelected());
		doc.getOptions().getClientArray(row).getRevers().setHeight(frame.getReversHeight());
		doc.getOptions().getClientArray(row).getRevers().setWidth(frame.getReversWidth());
		doc.getOptions().getClientArray(row).getRevers().setRowCount(frame.getReversRowCount());
		doc.getOptions().getClientArray(row).getRevers().setCount(frame.getReversCount());
	}
  
  public static String save(){
    XmlOptions opts = new XmlOptions();
    opts.setSavePrettyPrint();
    try {
    	ByteArrayOutputStream doctext = new ByteArrayOutputStream();
			doc.save(doctext,opts);
			//boolean ok = Manager.saveEnv(doctext.toString("UTF-8"));
			return doctext.toString("UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
  }
}
