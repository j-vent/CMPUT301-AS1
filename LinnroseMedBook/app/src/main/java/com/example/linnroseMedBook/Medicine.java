package com.example.linnroseMedBook;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Medicine implements Serializable {
    private Date startDate;
    private String name;
    private Double doseAmount;
    private String doseUnit;
    private Integer dailyFreq;
    // TODO: add validation checks for these fields

    public Medicine(Date startDate, String name, Double doseAmount, String doseUnit, Integer dailyFreq){
        this.startDate = startDate;
        this.name = name;
        this.doseAmount = doseAmount;
        this.doseUnit = doseUnit;
        this.dailyFreq = dailyFreq;

    }
    public String getDate(){
        // present in yyyy-mm-dd format
        System.out.println("date " + this.startDate);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(this.startDate);
    }

    public void setDate(Date date){
        this.startDate = date;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Double getDoseAmt(){
        return this.doseAmount;
    }

    public void setDoseAmt(Double doseAmount){
        this.doseAmount = doseAmount;
    }

    public String getDoseUnit(){
        return this.doseUnit;
    }

    public void setDoseUnit(String doseUnit){
        this.doseUnit = doseUnit;
    }

    public Integer getDailyFrequency(){
        return this.dailyFreq;
    }

    public void setDailyFreq(Integer dailyFreq){
        this.dailyFreq = dailyFreq;
    }
}
