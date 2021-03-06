package com.example.gk.model;

public class SiteLocationModel {

    String username;
    String date;
    String siteName;
    String siteLocation;
    String checkIn;
    String checkOut;
    String mobile;

    public SiteLocationModel() {
    }

    public SiteLocationModel(String username, String date, String siteName, String siteLocation, String checkIn, String checkOut, String mobile) {
        this.username = username;
        this.date = date;
        this.siteName = siteName;
        this.siteLocation = siteLocation;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getSiteLocation() {
        return siteLocation;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public String getMobile() {
        return mobile;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setSiteLocation(String siteLocation) {
        this.siteLocation = siteLocation;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
