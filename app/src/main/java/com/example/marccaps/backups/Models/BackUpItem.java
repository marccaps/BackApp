package com.example.marccaps.backups.Models;

/**
 * Created by MarcCaps on 17/4/17.
 */

public class BackUpItem {

    private String name;
    private String address;
    private int photoId;

    public BackUpItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}