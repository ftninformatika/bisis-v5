package com.ftninformatika.utils;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.apache.log4j.Logger;

public class NetUtils {

  public static String getMACAddress() {
    return macAddress;
  }
  
  public static String getIPv4Address() {
    return ipv4Address;
  }
  
  public static String getIPv6Address() {
    return ipv6Address;
  }
  
  public static String getHostName() {
    return hostName;
  }
  
  private static String hostName;
  private static String ipv4Address;
  private static String ipv6Address;
  private static String macAddress;
  private static Logger log = Logger.getLogger(NetUtils.class);
 
  static {
    try {
//      InetAddress myAddr = InetAddress.getLocalHost();
//      ipAddress = myAddr.getHostAddress();
//      hostName = myAddr.getHostName();
//      NetworkInterface ni = NetworkInterface.getByInetAddress(myAddr);
//      byte[] macAddr = ni.getHardwareAddress();
//      macAddress = MAC2String(macAddr);
    	
    	Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();  
    	if(interfaces.hasMoreElements())  {  
        NetworkInterface nif = interfaces.nextElement();  
        while ((nif.getHardwareAddress() == null || nif.isLoopback()) && interfaces.hasMoreElements()){
        	nif = interfaces.nextElement();
        }
        byte[] macAddr = nif.getHardwareAddress();
      	macAddress = MAC2String(macAddr);
      	Enumeration<InetAddress> inetAddresses = nif.getInetAddresses();
      	while (inetAddresses.hasMoreElements()) {
      		 InetAddress inetAddr = inetAddresses.nextElement();
      		 if (inetAddr instanceof Inet4Address){
      			ipv4Address = inetAddr.getHostAddress();
      		 } else if (inetAddr instanceof Inet6Address){
      			ipv6Address = inetAddr.getHostAddress();
      		 } 
      		 hostName = inetAddr.getHostName();  
      	}
    	}
    } catch (Exception ex) {
      log.fatal(ex);
    }
    log.info("MAC adresa: " + macAddress);
    log.info("IPv4 adresa: " + ipv4Address);
    log.info("IPv6 adresa: " + ipv6Address);
  }
  
  public static void main(String[] args) throws Exception {
    System.out.println(hostName);
    System.out.println(ipv4Address);
    System.out.println(ipv6Address);
    System.out.println(macAddress);
  }
  
  private static String MAC2String(byte[] mac) {
    StringBuffer retVal = new StringBuffer();
    int count = 0;
    for (byte b: mac) {
      if (count++ > 0)
        retVal.append(':');
      int i = (b >= 0) ? (int)b : (int)b + 256;
      String piece = Integer.toHexString(i).toUpperCase();
      if (piece.length() < 2)
        retVal.append('0');
      retVal.append(piece);
      
    }
    return retVal.toString();
  }
}
