package com.example.findmyschool.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Certificate implements Serializable {

    @SerializedName("certiinsti")

    private String certiinsti;
    @SerializedName("certiname")

    private String certiname;
    @SerializedName("date")

    private String date;
    @SerializedName("id")

    private String id;

    public String getCertiinsti() {
        return certiinsti;
    }

    public void setCertiinsti(String certiinsti) {
        this.certiinsti = certiinsti;
    }

    public String getCertiname() {
        return certiname;
    }

    public void setCertiname(String certiname) {
        this.certiname = certiname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}