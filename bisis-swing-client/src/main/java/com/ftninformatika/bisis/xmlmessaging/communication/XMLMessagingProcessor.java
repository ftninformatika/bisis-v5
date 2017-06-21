package com.ftninformatika.bisis.xmlmessaging.communication;

import com.ftninformatika.bisis.xmlmessaging.actions.AbstractRemoteCall;
import org.dom4j.Document;


/* */
public interface XMLMessagingProcessor {
	public void setErrors(String text);
	public void processResponse(Document resp, AbstractRemoteCall caller, LibraryServerDesc lib);
}
