package com.company;

import java.util.*;
import java.text.*;

public class Interactions {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static int counter = 0;
    private String id;
    private Date date;
    private String leadID;
    private String method;
    private String potential;

    public Interactions(Date date, String leadID ,String method, String potential) {
        this.id = "inter_"+String.format("%03d",counter+1);
        this.date = date;
        this.leadID = leadID;
        this.method = method;
        this.potential = potential;
        counter++;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Interactions.counter = counter;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLeadID() {
        return leadID;
    }

    public void setLeadID(String leadID) {
        this.leadID = leadID;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPotential() {
        return potential;
    }

    public void setPotential(String potential) {
        this.potential = potential;
    }

    public int getYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.date);
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    public int getMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.date);
        int month = calendar.get(Calendar.MONTH);
        return month;
    }
}
