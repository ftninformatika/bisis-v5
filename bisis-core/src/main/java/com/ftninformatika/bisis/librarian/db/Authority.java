package com.ftninformatika.bisis.librarian.db;

import org.springframework.security.core.GrantedAuthority;


public enum Authority implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_RIS_USER,
    ROLE_RIS_ADMIN,
    ROLE_NABAVKA_DEZIDERATI,
    ROLE_NABAVKA_ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
