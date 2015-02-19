package com.zhuang.bowen.hospitalreservation;
/**
 * Created by Bowem on 2015-02-19.
 */
public class Patient {

    private String name;
    private String id;
    private boolean isMsgSent;
    private boolean isCalled;
    private String phone;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    Patient(String name, String number,String phone,String birthday,boolean bcalled, boolean bTexted){
        this.name = name;
        this.id = number;
        this.phone = phone;
        this.birthday = birthday;
        this.isMsgSent = bTexted;
        this.isCalled = bcalled;
    }

    private String birthday;

    public Patient(String name) {
        this.name = name;
    }

    public Patient() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isMsgSent() {
        return isMsgSent;
    }

    public void setMsgSent(boolean isMsgSent) {
        this.isMsgSent = isMsgSent;
    }

    public boolean isCalled() {
        return isCalled;
    }

    public void setCalled(boolean isCalled) {
        this.isCalled = isCalled;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



}
