/*
 *  Created on 2004.10.25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ftninformatika.bisis.xmlmessaging.communication;

import javax.swing.SwingUtilities;
import javax.xml.soap.SOAPMessage;

import com.ftninformatika.bisis.search.SearchFrame;
import com.ftninformatika.bisis.xmlmessaging.MessagingEnvironment;
import com.ftninformatika.bisis.xmlmessaging.actions.AbstractRemoteCall;
import com.ftninformatika.bisis.xmlmessaging.actions.ListServersActionCall;
import com.ftninformatika.bisis.xmlmessaging.util.SOAPUtilClient;
import org.dom4j.Document;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommunicationThread extends Thread {
	
	private LibraryServerDesc libServ;
	private Document doc;
	private XMLMessagingProcessor callerForm;
	private SOAPUtilClient sender;
	private boolean compress;
	private AbstractRemoteCall owner;
	
	private boolean error=false;
	private String errorCode;
	int opType=0;
	private String convId;
	public CommunicationThread(){
		super();
		sender=new SOAPUtilClient();
		convId="";
	}
	
	
	
	/**
	 * @param owner
	 * @param libServ
	 * @param doc
	 * @param callerForm
	 * @param convId
	 * @param opType
	 * @param compress
	 */
	public CommunicationThread(AbstractRemoteCall owner,LibraryServerDesc libServ, Document doc, XMLMessagingProcessor callerForm,String convId, int opType, boolean compress) {
		super();
		this.owner=owner;
		this.libServ=libServ;
		this.doc=doc;
		this.callerForm=callerForm;
		this.opType=opType;
		this.convId=convId;
		this.compress=compress;
		sender=new SOAPUtilClient();
	}


	public void run(){
		try {
			SOAPMessage retVal=null;
			try{
				SOAPMessage msg=sender.prepareMessage(doc,libServ.getUrlAddress(),convId,opType,compress);
				retVal=sender.sendReceive(msg,libServ.getUrlAddress());
				//results=processResponse(retVal);
			}catch(Exception e){
				e.printStackTrace();
			  if(MessagingEnvironment.DEBUG==1)
					System.out.println(e+"\n"+retVal);
				error=true;
				errorCode="\nSERVER ["+libServ.getLibName()+" @ "+libServ.getUrlAddress()+"] Greska u komunikaciji!";
				callerForm.setErrors("\nSERVER ["+libServ.getLibName()+" @ "+libServ.getUrlAddress()+"] communication problems!");
			}
			if(error){
				final String errCode=new String(errorCode);
		        sleep( 1000);
		        Runnable errorUpdater = new Runnable() {
		          public void run() {
		        	  callerForm.setErrors(errCode);
		          }
		        };
		        SwingUtilities.invokeLater(errorUpdater);
			}else{
				final Document returned=sender.extractResponse(retVal);
		        Runnable resultUpdater = new Runnable() {
		        	public void run() {
		        		callerForm.processResponse(returned,owner,libServ);
		            }
		        };
			    SwingUtilities.invokeLater(resultUpdater);
			}
			if(MessagingEnvironment.DEBUG==1)
				System.out.println("Thread: "+libServ.getLibName()+" @ "+libServ.getUrlAddress()+" Finishing");
		} catch (InterruptedException interrupt) {
	         interrupt.printStackTrace();
	    }
	}
}
