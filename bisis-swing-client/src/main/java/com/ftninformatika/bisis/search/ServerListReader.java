/*
 * Created on 2005.4.6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ftninformatika.bisis.search;

import com.ftninformatika.bisis.xmlmessaging.MessagingEnvironment;
import com.ftninformatika.bisis.xmlmessaging.communication.LibraryServerDesc;
import com.ftninformatika.bisis.xmlmessaging.util.SOAPUtilClient;
import com.gint.util.file.INIFile;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Vector;

/**
 * @author mikiz
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ServerListReader {
	
	/* Function that will create list of available servers 
	 * by requesting servers.xml
	 */
	public static Vector<LibraryServerDesc> prepareServerList(){
		if(MessagingEnvironment.DEBUG==1)
			System.out.println("PrepareServerList called");
		Vector<LibraryServerDesc> result=null;
		Document received=readServersListData();
		if(received!=null){
			Element root=received.getRootElement();
			if(root.getName().equals("servers")){
				List servers=root.elements("server");
				if(servers.size()>0){
					result=new Vector<LibraryServerDesc>();
							//null;
					for(int i=0;i<servers.size();i++){
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
							serverName=name.getText().trim();
						Element url=oneServer.element("url");
						if(url!=null)
							serverUrl=url.getText().trim();
						Element institution=oneServer.element("institution");
						if(institution!=null)
							serverInstitution=institution.getText().trim();
						result.add(new LibraryServerDesc(id,serverName,serverUrl,serverInstitution));
					}
				}
			}
		}
		INIFile iniFile = //BisisApp.getINIFile();
							null;
			if(iniFile!=null){
		if(MessagingEnvironment.DEBUG==1){
				System.out.println("adding localhost for testing");
				System.out.println(iniFile.getString("local-netsearch", "serverHost"));
			}
			int localId=iniFile.getInt("local-netsearch", "serverId");
			String localHost=iniFile.getString("local-netsearch", "serverHost");
			String localURL=iniFile.getString("local-netsearch", "serverURL");
			String localName=iniFile.getString("local-netsearch", "serverName");
			if(localId>0 && !localHost.equals("") && !localURL.equals("") && !localName.equals("")){
				if(result==null)
					result=new Vector<LibraryServerDesc>();
				result.add(new LibraryServerDesc(localId,localHost,localURL,localName));
			}
		}
		return result;
	}
	
	private static Document readServersListData(){
		Document resp=null;
		SAXReader builder=new SAXReader();
		SOAPUtilClient.setProxyFromINI(true);
		try{
			URL testURL=new URL(MessagingEnvironment.getMainURL());
			URLConnection conn=testURL.openConnection();
			InputStream urlConnStream=conn.getInputStream();
			resp=builder.read(urlConnStream);
			urlConnStream.close();
		}catch(Exception e){
			e.printStackTrace();
			if(MessagingEnvironment.DEBUG==1)
				System.out.println("Pristup mrezi za razmenu trenutno onemogucen.");
		}
		SOAPUtilClient.setProxyFromINI(false);
		return resp;
	}
}
