package com.example.gk.model;

public class RegisterModel {
    String username;
    String dob;
    String emailID;
    String mobileNo;
    String address;
    String password;
    String uniqueID;
    String status;

    public RegisterModel(){
    }

    public RegisterModel(String username, String dob, String emailID, String mobileNo,
                         String address, String password, String uniqueID,String status) {
        this.username = username;
        this.dob = dob;
        this.emailID = emailID;
        this.mobileNo = mobileNo;
        this.address = address;
        this.password = password;
        this.uniqueID = uniqueID;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getDob() {
        return dob;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getUniqueID() {

        return uniqueID;
    }
}
