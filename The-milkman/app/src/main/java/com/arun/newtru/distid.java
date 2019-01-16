package com.arun.newtru;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class distid {
    public String id;
    public String name;
    public String pno;

    public distid(String id, String name, String pno) {
        this.id = id;
        this.name = name;
        this.pno = pno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public distid(){
    }

}
