package com.ftninformatika.bisis.auth.security.service;


public interface TokenService {

    String getToken(String username, String password);
}
