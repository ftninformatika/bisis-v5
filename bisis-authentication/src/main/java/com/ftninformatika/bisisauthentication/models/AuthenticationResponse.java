package com.ftninformatika.bisisauthentication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationResponse implements Serializable {

    private String username;
    private String name;
    private String token;
    private List<String> roles;
    private String library;
    private String department;

}
