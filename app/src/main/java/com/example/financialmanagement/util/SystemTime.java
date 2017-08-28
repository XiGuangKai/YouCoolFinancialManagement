package com.example.financialmanagement.util;

/**
 * Created by wangdy11 on 2017/7/25.
 */

public class SystemTime {

    private int Year;// 年
    private int Month;// 月
    private int Day;// 日

    public SystemTime(int year,int month,int day){
        super();
        this.Year = year;
        this.Month = month;
        this.Day = day;
    }

    public int getYear(){
        return Year;
    }

    public void setYear(int year){
        this.Year = year;
    }

    public int getMonth(){
        return Month;
    }

    public void setMonth(int month){
        this.Month = month;
    }

    public int getDay (){
        return Day;
    }

    public void setDay(int day){
        this.Day = day;
    }
}
