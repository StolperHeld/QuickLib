package com.example.ro_en.quicklib.model;

/**
 * Created by RO_EN on 12.03.2018.
 */

public class Lists extends ListId{
    private String name;
    private String uid;

    public Lists() {

    }

    public Lists(String name, String uid) {
        this.name = name;
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }
}
