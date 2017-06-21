/*
 *  Created on 2005.4.4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ftninformatika.bisis.xmlmessaging.actions;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import com.ftninformatika.bisis.search.SearchFrame;
import com.ftninformatika.bisis.xmlmessaging.MessagingEnvironment;
import com.ftninformatika.bisis.xmlmessaging.MessagingError;
import com.ftninformatika.bisis.xmlmessaging.communication.CommunicationThread;
import com.ftninformatika.bisis.xmlmessaging.communication.LibraryServerDesc;
import com.ftninformatika.bisis.xmlmessaging.communication.XMLMessagingProcessor;
import com.ftninformatika.bisis.xmlmessaging.util.MessageUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;


/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ListServersActionCall extends AbstractRemoteCall {

	/* (non-Javadoc)
	 * @see com.gint.app.bisis.xmlmessaging.actions.AbstractRemoteCall#initiateAction(com.gint.app.bisis.xmlmessaging.client.BisisSearchForm, java.util.LinkedHashMap)
	 */
	public void initiateAction(XMLMessagingProcessor caller, LinkedHashMap requestParams) {
		try{
			LinkedHashMap libs = (LinkedHashMap)requestParams.get("libs");
			SearchFrame holdingForm = (SearchFrame)caller;
			String convId=(String)requestParams.get("convId");
			setConvId(convId);
			boolean compress=((Boolean)requestParams.get("compress")).booleanValue();
			if(libs.keySet().size()>0){
				Iterator it=(libs.keySet()).iterator();
				while(it.hasNext()){
					String keyValue=(String)it.next();
					if(MessagingEnvironment.DEBUG==1)
						System.out.println("keyValue="+keyValue);
					LibraryServerDesc thisLib=(LibraryServerDesc)libs.get(keyValue);
					Document doc=createListServersRequest(thisLib.getUrlAddress(),convId);
					if(MessagingEnvironment.DEBUG==1)
						System.out.println("izbacujem comm threadove");
					//CommunicationThread ct=new CommunicationThread(this,thisLib,doc,holdingForm,convId,MessagingEnvironment.LISTSERVERS, compress);
					CommunicationThread ct = new CommunicationThread();
					ct.start();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public synchronized Vector processResponse(Document retVal, LibraryServerDesc libServ, MessagingError me){
		if(MessagingEnvironment.DEBUG==1)
			System.out.println("LIstServers processing response");
		Object[] res;
		Vector results=null;
		if(retVal!=null){
			res=getListServersResponse(retVal,me);
			if(res==null){
				if(me.isActive()){
					me.setCode("\nSERVER: ["+libServ.getLibName()+" @ "+libServ.getUrlAddress()+"] "+me);
				}else{
					me.setActive(true);
					me.setCode("\tSpisak dodatnih servera trenutno nedostupan!");
				}
			}else{
				if(res.length>0){
					results=new Vector();
					for(int i=0; i<res.length; i++){
						results.add((LibraryServerDesc)res[i]);									
					}
				}
			}
		}else{
			me.setActive(true);
			me.setCode("\nSERVER: ["+libServ.getLibName()+" @ "+libServ.getUrlAddress()+"] \n\tServer nije korektno odgovorio na ListServers zahtev!");
		}
		return results;
	}
	
	/* This private function is currently unused. 
	 * It enables server list queries through the XML messaging protocol.
	 * This query has been replaced by the plain http get to servers.xml
	 */
	private Document createListServersRequest(String to, String convId){
		Document retVal=null;
		DOMElement bisisReq= MessageUtils.createBisisHeader(to,"ListServers","serverquery",convId);
		
        Element action=new DOMElement("action");
        action.addAttribute("name","ListServers");
        
        bisisReq.add(action);
        retVal=new DOMDocument(bisisReq);
		return retVal;
	}
	
	
	private LibraryServerDesc[] getListServersResponse(Document doc,MessagingError error){
		LibraryServerDesc []db=null;
		if(doc!=null){
			Element root=doc.getRootElement();
			Element content=(root.element("action")).element("servers");
			if(content!=null){
				try{
					List servers=content.elements("server");
					if(servers.size()>0){
						int sizeOfResponse=servers.size();
						if(MessagingEnvironment.DEBUG==1)
							sizeOfResponse++;
						db=new LibraryServerDesc[sizeOfResponse];
						for(int i=0;i<db.length;i++){
							Element oneServer=(Element)servers.get(i);
							int id;
							String serverName="";
							String serverUrl="";
							String serverInstitution="";
							try{
								id=Integer.parseInt(oneServer.attributeValue("id"));
							}catch(Exception e){
								id=-1;
							}
							Element name=oneServer.element("name");
							if(name!=null)
								serverName=name.getText();
							Element url=oneServer.element("url");
							if(url!=null)
								serverUrl=url.getText();
							Element institution=oneServer.element("institution");
							if(institution!=null)
								serverInstitution=institution.getText();
							db[i]=new LibraryServerDesc(id,serverName,serverUrl,serverInstitution);
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				try{
					String errCode=content.attributeValue("code");
					String errSeverity=content.attributeValue("severity");
					String errDescription=content.getText();
					String errLevel=content.getText();
					error.setActive(true);
					error.setCode(errCode);
					error.setDescription(errDescription);
					error.setLevel(errLevel);
					error.setSeverity(errSeverity);
				}catch(Exception e){
					error.setActive(true);
					error.setCode("Unknown Error");
					error.setDescription("Unknown Error");
					error.setLevel("1");
					error.setSeverity("SEVERE");
				}
			}	
		}
		if(MessagingEnvironment.DEBUG==1){
			System.out.println("adding localhost for testing");
			int i=0;
			if(db==null)
				db=new LibraryServerDesc[1];
			else
				i=db.length-1;
			db[i]=new LibraryServerDesc(1000,"localhost","http://localhost:8080/bisis4web/ExchangeServlet","Lokalni node");
		}
		return db;
	}

}
