package com.example.linnroseMedBook;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents Medicine
 */
public class Medicine implements Serializable {
    private Date startDate;
    private String name;
    private Double doseAmount;
    private String doseUnit;
    private Integer dailyFreq;

    public Medicine(Date startDate, String name, Double doseAmount, String doseUnit, Integer dailyFreq){
        this.startDate = startDate;
        this.name = name;
        this.doseAmount = doseAmount;
        this.doseUnit = doseUnit;
        this.dailyFreq = dailyFreq;
    }

    /** Gets start date of medication
     * @return String representation of Date in yyyy-MM-dd format
     */
    public String getDate(){
        System.out.println("date " + this.startDate);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(this.startDate);
    }

    /** Sets start date of medication
     * @param date the start date
     */
    public void setDate(Date date){
        this.startDate = date;
    }

    /** Gets name of medication
     * @return String representation of Date in yyyy-MM-dd format
     */
    public String getName(){
        return this.name;
    }

    /** Sets start date of medication
     * @param name the name of medication
     */
    public void setName(String name){
        this.name = name;
    }

    /** Gets dose amount
     * @return the dosage amount
     */
    public Double getDoseAmt(){
        return this.doseAmount;
    }

    /** Sets dose amount for medication
     * @param doseAmount the dosage for medication
     */
    public void setDoseAmt(Double doseAmount){
        this.doseAmount = doseAmount;
    }

    /** Gets dose unit
     * @return the dosage unit
     */
    public String getDoseUnit(){
        return this.doseUnit;
    }

    /** Sets dose amount for dose unit
     * @param doseUnit the dosage unit for medication
     */
    public void setDoseUnit(String doseUnit){
        this.doseUnit = doseUnit;
    }

    /** Gets daily dosage frequency of medication
     * @return the daily frequency of medication
     */
    public Integer getDailyFrequency(){
        return this.dailyFreq;
    }

    /** Sets daily frequency of medication
     * @param dailyFreq the daily frequency of medication
     */
    public void setDailyFreq(Integer dailyFreq){
        this.dailyFreq = dailyFreq;
    }
}
