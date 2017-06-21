/*
 *  Created on 2004.10.3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ftninformatika.bisis.xmlmessaging.util;

import java.util.Date;

import com.ftninformatika.bisis.xmlmessaging.MessagingEnvironment;
import org.dom4j.dom.DOMElement;
import org.dom4j.dom.DOMNamespace;
import org.dom4j.Namespace;


/**
 * @author mikiz
 *
 * class is intended to be used as one stop palce for preparing the Bisis XML exchange documents
 */
public class MessageUtils {
	static final char BISIS_FIELD_SEPARATOR    ='\036';
	static final char BISIS_SUBFIELD_SEPARATOR ='\037';
	static final char BISIS_SUBSUBFIELD_SEPARATOR ='\\';
	
	public static String reqSchemaURL="http://bisis.uns.ac.rs:8080/bisisXMLM/bisis_req.xsd";
	public static String respSchemaURL="http://bisis.uns.ac.rs:8080/bisisXMLM/bisis_resp.xsd";
	public static String thisLibNode="bisisClient@localhost";
	
	public static DOMElement createBisisHeader(String toServer, String requestedAction, String requestedParams,String convId){
		DOMElement bisisReq=new DOMElement("bisis_request");
		Namespace nmsp=DOMNamespace.get("xsi","http://www.w3.org/2001/XMLSchema-instance");
        bisisReq.addAttribute("xsi:noNamespaceSchemaLocation", MessagingEnvironment.getReqSchemaURL());
        bisisReq.add(nmsp);
        DOMElement header=new DOMElement("header");
        DOMElement from=new DOMElement("from");
        if(MessagingEnvironment.DEBUG==1)
        	System.out.println("Messaging: "+MessagingEnvironment.getMyLibServer().getUrlAddress());
        from.setText(MessagingEnvironment.getMyLibServer().getUrlAddress());
        header.add(from);
        DOMElement to=new DOMElement("to");
        to.setText(toServer);
        header.add(to);
        DOMElement datestamp=new DOMElement("datestamp");
        datestamp.setText(new Date().toGMTString());
        header.add(datestamp);
        DOMElement convid=new DOMElement("conversation_id");
        convid.setText(convId);
        header.add(convid);
        bisisReq.add(header);
        
        DOMElement action=new DOMElement("action");
        action.setAttribute("name",requestedAction);
        DOMElement params=new DOMElement("params");
        params.setAttribute("name",requestedParams);
		return bisisReq;
	}
}
