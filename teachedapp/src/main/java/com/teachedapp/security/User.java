package com.teachedapp.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class User extends org.springframework.security.core.userdetails.User {
    private int id;

    public User(String username, String password, int id, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
