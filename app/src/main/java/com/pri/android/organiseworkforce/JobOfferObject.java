package com.pri.android.organiseworkforce;

/**
 * Created by Priyanshu on 20-01-2017.
 */

public class JobOfferObject {
    public String getCompanyName() {
        return companyName;
    }

    private String companyName;
    private String jobProfile;
    private String experience;
    private String workingTime;
    private String duration;
    private String overtimePay;
    private String basicPay;
    private int numOfEmployees;
    private String medicalBenefits;
    private String key;
    private String timestamp;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getJobProfile() {
        return jobProfile;
    }

    public void setJobProfile(String jobProfile) {
        this.jobProfile = jobProfile;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getOvertimePay() {
        return overtimePay;
    }

    public void setOvertimePay(String overtimePay) {
        this.overtimePay = overtimePay;
    }

    public String getBasicPay() {
        return basicPay;
    }

    public void setBasicPay(String basicPay) {
        this.basicPay = basicPay;
    }

    public int getNumOfEmployees() {
        return numOfEmployees;
    }

    public void setNumOfEmployees(int numOfEmployees) {
        this.numOfEmployees = numOfEmployees;
    }

    public String getMedicalBenefits() {
        return medicalBenefits;
    }

    public void setMedicalBenefits(String medicalBenefits) {
        this.medicalBenefits = medicalBenefits;
    }
}
