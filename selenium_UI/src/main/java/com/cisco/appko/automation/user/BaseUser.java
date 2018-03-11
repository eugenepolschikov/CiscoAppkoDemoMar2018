package com.cisco.appko.automation.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * entity representing User in simlified model, e.g
 * containing only
 */
public class BaseUser {
    private static final Logger log = LoggerFactory.getLogger(BaseUser.class);

    private String email;
    private String password;

    public BaseUser(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


    public String toString() {
        return this.getEmail() +
                "," + this.getPassword();

    }



}
