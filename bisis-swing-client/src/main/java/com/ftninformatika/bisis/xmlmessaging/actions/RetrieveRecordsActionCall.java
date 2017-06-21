/*
 *  Created on 2005.4.4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ftninformatika.bisis.xmlmessaging.actions;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordFactory;
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
import org.dom4j.io.XMLWriter;


/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RetrieveRecordsActionCall extends AbstractRemoteCall {

	/* (non-Javadoc)
	 * @see com.gint.app.bisis.xmlmessaging.actions.AbstractRemoteCall#initiateAction(com.gint.app.bisis.xmlmessaging.client.BisisSearchForm, java.util.LinkedHashMap)
	 */
	public void initiateAction(XMLMessagingProcessor callerForm, LinkedHashMap requestParams) {
		try{
			LinkedHashMap hits = (LinkedHashMap)requestParams.get("hits");
			String convId=(String)requestParams.get("convId");
			setConvId(convId);
			boolean compress=((Boolean)requestParams.get("compress")).booleanValue();
			if(hits.keySet().size()>0){
				Iterator it=(hits.keySet()).iterator();
				while(it.hasNext()){
					LibraryServerDesc keyValue=(LibraryServerDesc)it.next();
					if(MessagingEnvironment.DEBUG==1)
						System.out.println("keyValue="+keyValue);
					LinkedHashMap recordsFromOneAddress=(LinkedHashMap)hits.get(keyValue);
					Document doc=createRecordsRequest(recordsFromOneAddress,keyValue.getUrlAddress(),convId);
					CommunicationThread ct=new CommunicationThread(this,keyValue,doc,callerForm,convId,MessagingEnvironment.RETRIEVEREQUEST, compress);
					ct.start();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private Document createRecordsRequest(LinkedHashMap selectedHits,String to, String convId){
		Document retVal=null;
		DOMElement bisisReq= MessageUtils.createBisisHeader(to,"RetrieveRecords","records",convId);
		
        DOMElement action=new DOMElement("action");
        action.addAttribute("name","RetrieveRecords");
        DOMElement params=new DOMElement("params");
        params.addAttribute("name","records");
       
        Iterator it=selectedHits.values().iterator();
        int i=0;
        while(it.hasNext()){
        	DOMElement recId=new DOMElement("recordId");
        	recId.addAttribute("id",(String)it.next());
        	params.add(recId);
        	i++;
        }
        action.add(params);
        bisisReq.add(action);
        retVal=new DOMDocument(bisisReq);
		return retVal;
	}
	
	public synchronized Vector processResponse(Document retVal,LibraryServerDesc libServ,MessagingError me){
		Object[] res;
		Vector results=null;
		if(retVal!=null){
			res=getRetrieveRecordsResponse(retVal,me);
			if(res==null){
				if(me.isActive()){
					me.setCode("\n\tUnable to retrieve records"+me);
				}else{
					me.setActive(true);
					me.setCode("\n\tNo results returned!");
				}
			}else{
				if(res.length>0){
					results=new Vector();
					for(int i=0; i<res.length; i++){
						results.add(res[i]);									
					}
				}
			}
		}else{
			me.setActive(true);
			me.setCode("\n\tNo response from server!");
		}
		return results;
	}

	private Record[] getRetrieveRecordsResponse(Document doc, MessagingError error){
		
		Record []db=null;
		if(doc!=null){
			Element root=doc.getRootElement();
			Element content=(root.element("action")).element("unimarc");
			if(content!=null){
				List records=content.elements("record");
				if(MessagingEnvironment.DEBUG==1)
					System.out.println("Record list length: "+records.size());
				if(records.size()>0){
					db=new Record[records.size()];
					for(int i=0;i<db.length;i++){
						StringWriter sw=null;
						XMLWriter outputter=null;
						try {
							sw = new StringWriter();
							outputter = new XMLWriter(sw);
					        
							outputter.write((Element)records.get(i));
				            String rec=sw.toString();
				            if(MessagingEnvironment.DEBUG==1)
				            	System.out.println("Primljeno: "+i+" -----------\n" +rec);
				            db[i]= RecordFactory.fromLooseXML(rec);
				            Iterator it = db[i].getFields().iterator();
				            while (it.hasNext()) {
				              Field f = (Field)it.next();
				              if (f.getName().startsWith("99"))
				                it.remove();
				            }
				            outputter.flush();
				            sw.flush();
				        }catch (Exception e) {
				           	e.printStackTrace();
				        }finally{
				        	try{
				        		outputter.close();
				        		sw.close();
				        	}catch(IOException e){
				        		//do nothing
				        	}
				        }
					}
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
		return db;
	}
}
