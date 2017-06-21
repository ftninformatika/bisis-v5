/*
 * Created on 2004.11.10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ftninformatika.bisis.xmlmessaging;

import com.ftninformatika.bisis.xmlmessaging.communication.LibraryServerDesc;
import com.ftninformatika.bisis.xmlmessaging.communication.RemoteActionDescription;
import com.ftninformatika.bisis.xmlmessaging.communication.ThreadDispatcher;

import java.util.LinkedHashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MessagingEnvironment {
	private static LinkedHashMap<String, LibraryServerDesc> libServers;
	private static LinkedHashMap<String, RemoteActionDescription> actions;
	private static LibraryServerDesc myLibServer;
	private static String mainURL;
	private static ThreadDispatcher threadDispatcher;
	public static int DEBUG=0;
	
	
	private static String reqSchemaURL="http://bisis.uns.ac.rs/schemas/bisisXMLM/bisis_req.xsd";
	private static String respSchemaURL="http://bisis.uns.ac.rs/schemas/bisisXMLM/bisis_resp.xsd";
	private static String thisLibNode="client@testhost";
	
	public static final String CPAID="1";
	public static final int SEARCHREQUEST=1;
	public static final int RETRIEVEREQUEST=2;
	public static final int LISTSERVERS=3;
	
	static{
		ResourceBundle rb=ResourceReader.getRB();
		threadDispatcher=new ThreadDispatcher();
		//load data about available servers
		try{
			libServers=new LinkedHashMap<String, LibraryServerDesc>();
			reqSchemaURL=rb.getString("xmlRequestSchema");
			respSchemaURL=rb.getString("xmlResponseSchema");
            mainURL=rb.getString("mainURL");
			thisLibNode=rb.getString("thisLibNode");
		}catch(MissingResourceException mre){
			//mre.printStackTrace();
		}
		
		//load data about htis node
		try{
			String libServ=rb.getString("thisLibNode");
			String []libServerData=libServ.split(";");
			if(DEBUG==1)
				for(int j=0;j<libServerData.length;j++)
					System.out.println(libServerData[j]);
			try{
				int libId=Integer.parseInt(libServerData[0]);
				myLibServer=new LibraryServerDesc(libId,libServerData[1],libServerData[2],libServerData[3]);
				libServers.remove(myLibServer.getUrlAddress());
			}catch(Exception e){
				e.printStackTrace();
			}
			if(DEBUG==1)
				System.out.println("My library node: "+myLibServer);
		}catch(MissingResourceException mre){
			myLibServer=new LibraryServerDesc();
		}
		
		//load data about action specs
		try{
			actions=new LinkedHashMap<String, RemoteActionDescription>();
			String actionDesc;
			int i=1;
			while((actionDesc=rb.getString("action"+i))!=null){
				String []actionData=actionDesc.split(";");
				try{
					RemoteActionDescription newAction=new RemoteActionDescription(actionData[0],actionData[1],actionData[2],actionData[3]);
					actions.put(actionData[0],newAction);
					//System.out.println("Nova akcija:"+newAction);
				}catch(Exception e){
					e.printStackTrace();
				}
				i++;
			}
		}catch(MissingResourceException mre){
			//mre.printStackTrace();
		}
		
	}
	/**
	 * @return Returns the reqSchemaURL.
	 */
	public static String getReqSchemaURL() {
		return reqSchemaURL;
	}
	/**
	 * @return Returns the respSchemaURL.
	 */
	public static String getRespSchemaURL() {
		return respSchemaURL;
	}
	/**
	 * @return Returns the thisLibNode.
	 */
	public static String getThisLibNode() {
		return thisLibNode;
	}
	/**
	 * @return Returns the libServers.
	 */
	public static LinkedHashMap<String, LibraryServerDesc> getLibServers() {
		return libServers;
	}
	/**
	 * @return Returns the myLibServer.
	 */
	public static LibraryServerDesc getMyLibServer() {
		return myLibServer;
	}
	/**
	 * @return Returns the actions.
	 */
	public static LinkedHashMap getActions() {
		return actions;
	}
	/**
	 * @return Returns the mainURL.
	 */
	public static String getMainURL() {
		return mainURL;
	}
	public static ThreadDispatcher getThreadDispatcher() {
		return threadDispatcher;
	}
}
