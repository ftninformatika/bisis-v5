package com.ftninformatika.bisisauthentication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {

    private String username;
    private String password;
    private String refreshToken;

    public AuthenticationRequest(String username, String password, String refreshToken) {
        this.username = username;
        this.password = password;
        this.refreshToken = refreshToken;
    }
}
