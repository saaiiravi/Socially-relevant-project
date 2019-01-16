package com.arun.newtru;

import java.io.Serializable;

public class complaint implements Serializable{
    public String userid;
    public String complaintid;
    public String distributorname;
    public String complaint;
    public String customerName;
    public String timeofreport;
    public String customerAddress;
    public int isdone;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDistributorname() {
        return distributorname;
    }

    public void setDistributorname(String distributorname) {
        this.distributorname = distributorname;
    }

    public String getCustomerName() {return customerName;}

    public void setCustomerName(String name) { this.customerName = name;}

    public String getCustomerAddress() {return customerAddress;}

    public String getTimeofreport() {return timeofreport;}

    public void setTimeofreport(String timeofreport) {
        this.timeofreport = timeofreport;
    }

    public String getComplaintid() {return complaintid;}

    public void setComplaintid(String complaintid) {
        this.complaintid = complaintid;
    }

    public void setCustomerAddress(String address) {
        this.customerAddress = address;
    }

    public String getComplaint() {
        return complaint;
    }

    public int getIsdone() {return isdone;}

    public void setIsdone(int isdone) {this.isdone = isdone;}

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public complaint() {}

    public complaint(String userid, String distributorname, String complaint, String username, String timeofreport, String address) {
        this.userid = userid;
        this.distributorname = distributorname;
        this.complaint = complaint;
        this.customerName = username;
        this.timeofreport = timeofreport;
        this.customerAddress = address;
        this.isdone = 0;
    }
}