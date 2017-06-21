/*
 *  Created on 2004.11.11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ftninformatika.bisis.xmlmessaging.communication;

/**
 * @author mikiz
 *
 * This classcontains data about remote action,
 * its name, name of propriate response and service name
 */
public class RemoteActionDescription {
	private String actionName;
	private String responseName;
	private String serviceName;
	private String performer; 
	/**
	 * @return Returns the performer.
	 */
	public String getPerformer() {
		return performer;
	}
	/**
	 * @param actionName
	 * @param responseName
	 * @param serviceName
	 */
	public RemoteActionDescription(String actionName, String responseName,String performer,String serviceName) {
		super();
		this.actionName = actionName;
		this.responseName = responseName;
		this.performer=performer;
		this.serviceName = serviceName;
	}
	/**
	 * @return Returns the actionName.
	 */
	public String getActionName() {
		return actionName;
	}
	/**
	 * @param actionName The actionName to set.
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	/**
	 * @return Returns the responseName.
	 */
	public String getResponseName() {
		return responseName;
	}
	/**
	 * @param responseName The responseName to set.
	 */
	public void setResponseName(String responseName) {
		this.responseName = responseName;
	}
	/**
	 * @return Returns the serviceName.
	 */
	public String getServiceName() {
		return serviceName;
	}
	/**
	 * @param serviceName The serviceName to set.
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String toString(){
		return actionName+";"+responseName+";"+serviceName;
	}
}
