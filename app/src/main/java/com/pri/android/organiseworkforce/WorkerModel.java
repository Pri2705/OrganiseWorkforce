package com.pri.android.organiseworkforce;

import java.io.Serializable;

/**
 * Created by Parth on 21-01-2017.
 */

public class WorkerModel implements Serializable {

    private String userType;
    private String email;
    private String photoUrl;
    private String uid;

    String name;
    String age;
    String education;
    String experience;
    String adhaarNumber;
    String phoneNumber;
    String accountNumber;

    String prefLocation;
    String prefJobProfile;
    String prefWorkingTime;
    String expectedPay;

    public WorkerModel() {

    }

    public void setUserObject(UserObject userObject) {
        userType = userObject.getUserType();
        email = userObject.getEmail();
        photoUrl = userObject.getPhotoUrl();
        uid = userObject.getUid();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAdhaarNumber() {
        return adhaarNumber;
    }

    public void setAdhaarNumber(String adhaarNumber) {
        this.adhaarNumber = adhaarNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPrefLocation() {
        return prefLocation;
    }

    public void setPrefLocation(String prefLocation) {
        this.prefLocation = prefLocation;
    }

    public String getPrefJobProfile() {
        return prefJobProfile;
    }

    public void setPrefJobProfile(String prefJobProfile) {
        this.prefJobProfile = prefJobProfile;
    }

    public String getPrefWorkingTime() {
        return prefWorkingTime;
    }

    public void setPrefWorkingTime(String prefWorkingTime) {
        this.prefWorkingTime = prefWorkingTime;
    }

    public String getExpectedPay() {
        return expectedPay;
    }

    public void setExpectedPay(String expectedPay) {
        this.expectedPay = expectedPay;
    }

    public String getUserType() {
        return userType;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getUid() {
        return uid;
    }
}
