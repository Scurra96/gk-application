package com.example.gk.model;

public class SiteLocationModel {

    String username;
    String dateAndTime;
    String siteLocation;
    String siteName;
    String checkIn_Out;

    public SiteLocationModel(String username, String dateAndTime, String siteLocation, String siteName, String checkIn_Out) {
        this.username = username;
        this.dateAndTime = dateAndTime;
        this.siteLocation = siteLocation;
        this.siteName = siteName;
        this.checkIn_Out = checkIn_Out;
    }

    public String getUsername() {
        return username;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getSiteLocation() {
        return siteLocation;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getCheckIn_Out() {
        return checkIn_Out;
    }
}
