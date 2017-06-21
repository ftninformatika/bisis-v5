/*
 *  Created on 2005.4.4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ftninformatika.bisis.xmlmessaging.actions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import com.ftninformatika.bisis.xmlmessaging.BriefInfoModel;
import com.ftninformatika.bisis.xmlmessaging.MessagingEnvironment;
import com.ftninformatika.bisis.xmlmessaging.MessagingError;
import com.ftninformatika.bisis.xmlmessaging.communication.CommunicationThread;
import com.ftninformatika.bisis.xmlmessaging.communication.LibraryServerDesc;
import com.ftninformatika.bisis.xmlmessaging.communication.XMLMessagingProcessor;
import com.ftninformatika.bisis.xmlmessaging.util.MessageUtils;
import com.ftninformatika.bisis.xmlmessaging.util.RecordBriefs;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;


/**
 * @author mikiz
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SearchRequestActionCall extends AbstractRemoteCall {

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.gint.app.bisis.xmlmessaging.actions.AbstractRemoteCall#initiateAction
   * (java.util.LinkedHashMap)
   */
  public synchronized void initiateAction(XMLMessagingProcessor callerForm,
      LinkedHashMap requestParams) {
    // TODO Auto-generated method stub
    try {
      LinkedHashMap libs = (LinkedHashMap) requestParams.get("libs");
      String[] prefixes = (String[]) requestParams.get("prefixes");
      String[] values = (String[]) requestParams.get("values");
      String[] operators = (String[]) requestParams.get("operators");
      String convId = (String) requestParams.get("convId");
      setConvId(convId);
      query = (String) requestParams.get("query");
      boolean compress = ((Boolean) requestParams.get("compress"))
          .booleanValue();
      if (libs.keySet().size() > 0) {
        Iterator it = (libs.keySet()).iterator();
        while (it.hasNext()) {
          String keyValue = (String) it.next();
          if (MessagingEnvironment.DEBUG == 1)
            System.out.println("keyValue=" + keyValue);
          LibraryServerDesc thisLib = (LibraryServerDesc) libs.get(keyValue);
          Document doc = createQuery(prefixes, values, operators, thisLib
              .getUrlAddress(), convId);
          CommunicationThread ct = new CommunicationThread(this, thisLib, doc,
              callerForm, convId, MessagingEnvironment.SEARCHREQUEST, compress);
          ct.start();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Document createQuery(String[] prefixes, String[] values,
      String[] operators, String to, String convId) {
    Document retVal = null;
    DOMElement bisisReq = MessageUtils.createBisisHeader(to, "PerformSearch",
        "searchquery", convId);
    DOMElement action = new DOMElement("action");
    action.addAttribute("name", "PerformSearch");
    DOMElement params = new DOMElement("params");
    params.addAttribute("name", "searchquery");

    
    String lastOper = "";
    for (int i = 0; i < 5; i++) {
      if (!(values[i]).equals("")) {
        if (i > 0 && !lastOper.isEmpty()) {
          Element op = new DOMElement("operator");
          op.addAttribute("type", lastOper);
          params.add(op);
        }
        Element pref = new DOMElement("prefix");
        int colon = 0;
        if ((colon = prefixes[i].indexOf(":")) != -1)
          prefixes[i] = prefixes[i].substring(0, colon);
        pref.addAttribute("name", prefixes[i]);
        // values[i]=Charset.convertFromUnicode(values[i],"UTF-8");
        // pref.setText(values[i]);
        try {
          pref.setText(URLEncoder.encode(values[i], "UTF-8"));
        } catch (UnsupportedEncodingException uee) {
          pref.setText(values[i]);
        }
        params.add(pref);
        if (i < 4)
          lastOper = operators[i];
      }
    }
    action.add(params);
    bisisReq.add(action);
    retVal = new DOMDocument(bisisReq);
    retVal.setXMLEncoding("UTF-8");
    return retVal;
  }

  public synchronized Vector processResponse(Document retVal,
      LibraryServerDesc libServ, MessagingError me) {
    RecordBriefs[] res;
    Vector<BriefInfoModel> results = null;
    if (retVal != null) {
      res = getSearchResponse(retVal, me);
      if (res == null) {
        if (me.isActive()) {
          if (MessagingEnvironment.DEBUG == 1)
            System.out.println("Ima gresaka!!!!");
          me.setCode("\nSERVER: [" + libServ.getLibName() + " @ "
              + libServ.getUrlAddress() + "] " + me);
        } else {
          me.setActive(true);
          me.setCode("\nSERVER: [" + libServ.getLibName() + " @ "
              + libServ.getUrlAddress()
              + "] \n\tNema razultata za navedeni upit!");
        }
      } else {
        if (res.length > 0) {
          results = new Vector<BriefInfoModel>();
          for (int i = 0; i < res.length; i++) {
            BriefInfoModel oneEntry = new BriefInfoModel(false, libServ, res[i]);
            results.add(oneEntry);
          }
        }
      }
    } else {
      me.setActive(true);
      me.setCode("\nSERVER: [" + libServ.getLibName() + " @ "
          + libServ.getUrlAddress() + "] \n\tNo response from server!");
    }
    return results;
  }

  private RecordBriefs[] getSearchResponse(Document doc, MessagingError error) {
    RecordBriefs[] db = null;
    if (doc != null) {
      Element root = doc.getRootElement();
      Element content = (root.element("action")).element("content");
      if (content != null) {
        int count = 0;
        try {
          count = Integer.parseInt(content.attributeValue("count"));
          List dbriefs = content.elements("record_brief");
          for (int i = 0; i < dbriefs.size(); i++) {
            if (db == null)
              db = new RecordBriefs[count];
            Element recBrief = (Element) dbriefs.get(i);
            int recId = 0;
            recId = Integer.parseInt(recBrief.attributeValue("recId"));
            String autor = recBrief.elementText("author");
            String title = recBrief.elementText("title");
            String publisher = recBrief.elementText("publisher");
            String pubYear = recBrief.elementText("publication_year");
            String language = recBrief.elementText("language");
            String country = recBrief.elementText("country");
            db[i] = new RecordBriefs(recId, autor, title, publisher, pubYear,
                language, country);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        content = (root.element("action")).element("error");
        try {
          String errCode = content.attributeValue("code");
          String errSeverity = content.attributeValue("severity");
          String errDescription = content.getText();
          String errLevel = content.getText();
          error.setActive(true);
          error.setCode(errCode);
          error.setDescription(errDescription);
          error.setLevel(errLevel);
          error.setSeverity(errSeverity);
        } catch (Exception e) {
          error.setActive(true);
          error.setCode("Unknown Error");
          error.setDescription("Unknown Error");
          error.setLevel("1");
          error.setSeverity("SEVERE");
        }
      }
    }
    return db;
  }

  public String getQuery() {
    return query;
  }

  private String query;
}
