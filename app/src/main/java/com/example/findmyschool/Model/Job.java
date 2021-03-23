package com.example.findmyschool.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Job implements Serializable {

    @SerializedName("cname")
    private String cname;
    @SerializedName("designation")
    private String designation;
    @SerializedName("edate")
    private String edate;
    @SerializedName("id")
    private String id;
    @SerializedName("sdate")
    private String sdate;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

}