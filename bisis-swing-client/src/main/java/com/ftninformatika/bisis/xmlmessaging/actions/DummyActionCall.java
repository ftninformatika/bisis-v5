/*
 *  Created on 2005.4.4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ftninformatika.bisis.xmlmessaging.actions;

import java.util.LinkedHashMap;
import java.util.Vector;

import com.ftninformatika.bisis.xmlmessaging.MessagingError;
import com.ftninformatika.bisis.xmlmessaging.communication.LibraryServerDesc;
import com.ftninformatika.bisis.xmlmessaging.communication.XMLMessagingProcessor;
import org.dom4j.Document;


/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DummyActionCall extends AbstractRemoteCall {

	/* (non-Javadoc)
	 * @see com.gint.app.bisis.xmlmessaging.actions.AbstractRemoteCall#initiateAction(com.gint.app.bisis.xmlmessaging.client.BisisSearchForm, java.util.LinkedHashMap)
	 */
	public void initiateAction(XMLMessagingProcessor callerForm, LinkedHashMap requestParams) {
			//caller.processResponse(null,this,null);
	}
	
	public Vector processResponse(Document response, LibraryServerDesc lib, MessagingError me){
		me.setActive(true);
		me.setCode("\nPozvana nepostojeca operacija - interna greska!");
		return null;
	}

}
