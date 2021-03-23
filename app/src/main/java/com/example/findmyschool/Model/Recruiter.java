package com.example.findmyschool.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Recruiter implements Serializable {

    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;
    @SerializedName("country")
    private String country;
    @SerializedName("benefits")
    private String benefits;
    @SerializedName("description")
    private String description;
    @SerializedName("established")
    private String established;
    @SerializedName("gmail")
    private String gmail;
    @SerializedName("id")
    private String id;
    @SerializedName("indsturytype")
    private String indsturytype;
    @SerializedName("jobpost")
    private String jobpost = "";
    @SerializedName("latlong")
    private String latlong;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("name")
    private String name;
    @SerializedName("noemployees")
    private String noemployees;
    @SerializedName("profile")
    private String profile;
    @SerializedName("review")
    private String review = "";
    @SerializedName("reviewpost")
    private String reviewpost = "";
    @SerializedName("recruitername")
    private String recruitername;
    @SerializedName("resumepost")
    private String resumepost = "";
    @SerializedName("vision")
    private String vision = "";
    @SerializedName("mission")
    private String mission = "";

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstablished() {
        return established;
    }

    public void setEstablished(String established) {
        this.established = established;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndsturytype() {
        return indsturytype;
    }

    public void setIndsturytype(String indsturytype) {
        this.indsturytype = indsturytype;
    }

    public String getJobpost() {
        return jobpost;
    }

    public void setJobpost(String jobpost) {
        this.jobpost = jobpost;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoemployees() {
        return noemployees;
    }

    public void setNoemployees(String noemployees) {
        this.noemployees = noemployees;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewpost() {
        return reviewpost;
    }

    public void setReviewpost(String reviewpost) {
        this.reviewpost = reviewpost;
    }

    public String getRecruitername() {
        return recruitername;
    }

    public void setRecruitername(String recruitername) {
        this.recruitername = recruitername;
    }

    public String getResumePost() {
        return resumepost;
    }

    public void setResumePost(String resumepost) {
        this.resumepost = resumepost;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }
}