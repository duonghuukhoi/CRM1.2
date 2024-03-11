package com.company;
import java.util.*;
import java.text.*;


public class Leads {

    private static int counter = 0;
    private String id;
    private String name;
    private Date dob;
    private boolean gender;
    private String phone;
    private String email;
    private String address;

    public Leads(String name, Date dob, boolean gender,String phone  ,String email, String address) {
        this.id = "Lead_"+String.format("%03d",counter+1);
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        counter++;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Leads.counter = counter;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getAge(){
        Date now = new Date();

        int days = (int)(( now.getTime() - this.dob.getTime())
                / (1000 * 60 * 60 * 24)) ;
        int age = (int)(days/365.25);
        return age;
    }

}
