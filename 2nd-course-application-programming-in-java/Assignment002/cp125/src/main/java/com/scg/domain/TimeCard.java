package com.scg.domain;

import java.time.LocalDate;

/**
 * Time card class
 * @author Charlie Misner
 */
public class TimeCard {

    private Consultant consultant;
    private LocalDate weekStartingDay;
    private int totalHours;
    private int totalBillableHours;
    private int totalNonBillableHours;

    public TimeCard(Consultant consultant, LocalDate weekStartingDay) {
        this.consultant = consultant;
        this.weekStartingDay = weekStartingDay;
    }

    public void addConsultantTime(ConsultantTime consultantTime){

    }

    public String toReportString() {
        return "This is a report";
    }

    @Override
    public String toString(){
        return "";
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public LocalDate getWeekStartingDay() {
        return weekStartingDay;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public int getTotalBillableHours() {
        return totalBillableHours;
    }

    public int getTotalNonBillableHours() {
        return totalNonBillableHours;
    }
}
