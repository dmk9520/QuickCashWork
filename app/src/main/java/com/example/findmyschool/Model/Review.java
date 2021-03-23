package com.example.findmyschool.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Review implements Serializable {

    @SerializedName("date")
    private String date;
    @SerializedName("fromid")
    private String fromid;
    @SerializedName("fromname")
    private String fromname;
    @SerializedName("fromprofile")
    private String fromprofile;
    @SerializedName("id")
    private String id;
    @SerializedName("review")
    private String review;
    @SerializedName("star")
    private String star;
    @SerializedName("toid")
    private String toid;
    @SerializedName("toname")
    private String toname;
    @SerializedName("toprofile")
    private String toprofile;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid;
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname;
    }

    public String getFromprofile() {
        return fromprofile;
    }

    public void setFromprofile(String fromprofile) {
        this.fromprofile = fromprofile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getToid() {
        return toid;
    }

    public void setToid(String toid) {
        this.toid = toid;
    }

    public String getToname() {
        return toname;
    }

    public void setToname(String toname) {
        this.toname = toname;
    }

    public String getToprofile() {
        return toprofile;
    }

    public void setToprofile(String toprofile) {
        this.toprofile = toprofile;
    }

}