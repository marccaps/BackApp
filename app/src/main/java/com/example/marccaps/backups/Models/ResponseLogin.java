package com.example.marccaps.backups.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MarcCaps on 21/5/17.
 */

public class ResponseLogin {

    @SerializedName("success")
    @Expose
    private String success;

    public String getSuccess() {
        return success;
    }

}


