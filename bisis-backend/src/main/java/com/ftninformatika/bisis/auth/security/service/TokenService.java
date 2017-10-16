package com.ftninformatika.bisis.auth.security.service;


public interface TokenService {

    String getToken(String username, String password);

    String getMemberToken(String username, String password);

    String getLibrary(String username, String password);
}
