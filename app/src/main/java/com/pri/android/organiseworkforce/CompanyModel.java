package com.pri.android.organiseworkforce;

/**
 * Created by Parth on 21-01-2017.
 */

public class CompanyModel {

    private String userType;
    private String email;
    private String photoUrl;
    private String uid;

    String companyName;
    String tinNumber;
    String phoneNumber;

    public CompanyModel(){

    }

    public void setUserObject(UserObject userObject) {
        userType = userObject.getUserType();
        email = userObject.getEmail();
        photoUrl = userObject.getPhotoUrl();
        uid = userObject.getUid();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
