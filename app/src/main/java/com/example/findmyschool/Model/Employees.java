package com.example.findmyschool.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Employees implements Serializable {

    @SerializedName("about")
    private String about;
    @SerializedName("achivement")
    private String achivement;
    @SerializedName("age")
    private String age;
    @SerializedName("birth")
    private String birth;
    @SerializedName("certificate")
    private List<Certificate> certificate = new ArrayList<>();
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("state")
    private String state;
    @SerializedName("education")
    private List<Education> education = new ArrayList<>();
    @SerializedName("email")
    private String email;
    @SerializedName("experince")
    private String experince;
    @SerializedName("favjob")
    private String favjob;
    @SerializedName("find")
    private String find;
    @SerializedName("gender")
    private String gender;
    @SerializedName("hobbie")
    private String hobbie;
    @SerializedName("id")
    private String id;
    @SerializedName("interested")
    private String interested;
    @SerializedName("job")
    private List<Job> job = new ArrayList<>();
    @SerializedName("language")
    private String language;
    @SerializedName("latlong")
    private String latlong;
    @SerializedName("location")
    private String location;
    @SerializedName("name")
    private String name;
    @SerializedName("number")
    private String number;
    @SerializedName("skill")
    private String skill;
    @SerializedName("profile")
    private String profile;
    @SerializedName("review")
    private String review = "";
    @SerializedName("reviewpost")
    private String reviewpost = "";
    @SerializedName("speak")
    private String speak;
    @SerializedName("qualification")
    private String qualification;
    @SerializedName("medium")
    private String medium;
    @SerializedName("resume")
    private String resume = "";
    @SerializedName("resumesend")
    private String resumesend = "";


    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAchivement() {
        return achivement;
    }

    public void setAchivement(String achivement) {
        this.achivement = achivement;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public List<Certificate> getCertificate() {
        return certificate;
    }

    public void setCertificate(List<Certificate> certificate) {
        this.certificate = certificate;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExperince() {
        return experince;
    }

    public void setExperince(String experince) {
        this.experince = experince;
    }

    public String getFind() {
        return find;
    }

    public void setFind(String find) {
        this.find = find;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHobbie() {
        return hobbie;
    }

    public void setHobbie(String hobbie) {
        this.hobbie = hobbie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterested() {
        return interested;
    }

    public void setInterested(String interested) {
        this.interested = interested;
    }

    public List<Job> getJob() {
        return job;
    }

    public void setJob(List<Job> job) {
        this.job = job;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFavjob() {
        return favjob;
    }

    public void setFavjob(String favjob) {
        this.favjob = favjob;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSpeak() {
        return speak;
    }

    public void setSpeak(String speak) {
        this.speak = speak;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getResumesend() {
        return resumesend;
    }

    public void setResumesend(String resumesend) {
        this.resumesend = resumesend;
    }
}