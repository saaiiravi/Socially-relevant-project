package com.arun.newtru;

import java.io.Serializable;

public class usernew implements Serializable {
    public String name;
    public String email;
    public String address;
    public String userid;
    public double defaultRequirement, lat, lang, currRequirement;
    public int msyear, msmonth, msdate;

    public usernew(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public double getDefaultRequirement() {
        return defaultRequirement;
    }

    public void setDefaultRequirement(double defaultRequirement) {
        this.defaultRequirement = defaultRequirement;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    public double getCurrRequirement() {
        return currRequirement;
    }

    public void setCurrRequirement(double currRequirement) {
        this.currRequirement = currRequirement;
    }

    public int getMsyear() {
        return msyear;
    }

    public void setMsyear(int msyear) {
        this.msyear = msyear;
    }

    public int getMsmonth() {
        return msmonth;
    }

    public void setMsmonth(int msmonth) {
        this.msmonth = msmonth;
    }

    public int getMsdate() {
        return msdate;
    }

    public void setMsdate(int msdate) {
        this.msdate = msdate;
    }

    public usernew(String address, double currRequirement, double defaultRequirement, String email,  double lat, double lang, int msdate, int msmonth, int msyear, String name, String userid){
        this.name=name;
        this.email=email;
        this.currRequirement = currRequirement;
        this.address=address;
        this.userid = userid;
        this.defaultRequirement=defaultRequirement;
        this.lat=lat;
        this.lang=lang;
        this.msdate=msdate;
        this.msmonth=msmonth;
        this.msyear=msyear;
    }
}
