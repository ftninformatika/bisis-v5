package com.ftninformatika.bisis.service;



/**
 * Created by Petar on 6/15/2017.
 */
public class AuthenticationRequest {
    final String username;
    final String password;
    
    public AuthenticationRequest(String u, String p){
    	username = u;
    	password = p;
    }
}
