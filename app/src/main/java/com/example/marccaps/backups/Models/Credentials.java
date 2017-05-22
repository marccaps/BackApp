package com.example.marccaps.backups.Models;

/**
 * Created by MarcCaps on 21/5/17.
 */

public class Credentials {

    private String user;
    private String password;


    public Credentials(String username, String password) {
        this.user = username;
        this.password = password;
    }

    public String getUsername() {
        return user;
    }

    public void setUsername(String username) {
        this.user = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
