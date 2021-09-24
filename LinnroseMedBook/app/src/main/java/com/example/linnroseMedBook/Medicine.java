package com.example.linnroseMedBook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Medicine {
    private Date startDate;
    private String name;
    private Integer doseAmount;
    private String doseUnit;
    private Integer dailyFreq;
    // TODO: add validation checks for these fields

    public Medicine(Date startDate, String name, Integer doseAmount, String doseUnit, Integer dailyFreq){
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
    public String getName(){
        return this.name;
    }
}
