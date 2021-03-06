package com.scg.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Time card class
 * @author Charlie Misner
 */
public class TimeCard {

    private Consultant consultant;
    private LocalDate weekStartingDay;
    private List<ConsultantTime> billableConsultantTimes = new ArrayList<ConsultantTime>();
    private List<ConsultantTime> nonBillableConsultantTimes = new ArrayList<ConsultantTime>();
    private int totalHours;
    private int totalBillableHours;
    private int totalNonBillableHours;

    public TimeCard(Consultant consultant, LocalDate weekStartingDay) {
        this.consultant = consultant;
        this.weekStartingDay = weekStartingDay;
    }

    /**
     * Adds consultant time to the timeCard instance.
     * @param consultantTime
     */
    public void addConsultantTime(ConsultantTime consultantTime){
        if (consultantTime.getAccount().isBillable()){
            this.billableConsultantTimes.add(consultantTime);
        } else {
            this.nonBillableConsultantTimes.add(consultantTime);
        }
        this.updateHours(consultantTime.getAccount().isBillable(), consultantTime.getHours());
    }

    /**
     * Updates the cumulative hours fields when a consultantTime is added.
     * @param accountIsBillable
     * @param consultantHours
     */
    private void updateHours(boolean accountIsBillable, int consultantHours){
        this.totalHours += consultantHours; //Add consultant hours to totalHours.
        this.totalBillableHours += accountIsBillable ? consultantHours : 0; //If billable add to totalBillableHours.
        this.totalNonBillableHours += !accountIsBillable ? consultantHours : 0; //If non-billable add to totalNonBillableHours.
    }

    /**
     * Returns a string with the consultant name and the week starting day.
     * @return
     */
    @Override
    public String toString(){
        return this.consultant.toString() + " - " + this.weekStartingDay.toString();
    }

    /**
     * Generates the time card report string.
     * @return reportString
     */
    public String toReportString() {
        String reportString = this.createCardBreakString();
        reportString += this.createCardHeaderString();
        reportString += String.format("%-68s %n", "Billable Time:");
        reportString += this.createTableString("Account", "Date", "Hours", "Skill");
        reportString += this.createTableHeaderLines();
        for (ConsultantTime consultantTime : this.billableConsultantTimes){
            reportString += this.createTableDataEntry(consultantTime);
        }
        reportString += this.lineBreak();
        reportString += String.format("%-68s %n", "Non-billable Time:");
        reportString += this.createTableString("Account", "Date", "Hours", "Skill");
        reportString += this.createTableHeaderLines();
        for (ConsultantTime consultantTime : this.nonBillableConsultantTimes){
            reportString += this.createTableDataEntry(consultantTime);
        }
        reportString += this.lineBreak();
        reportString += String.format("%-68s %n", "Summary:");
        reportString += this.createTableString("Total Billable", "", String.valueOf(this.totalBillableHours), "");
        reportString += this.createTableString("Total Non-Billable", "", String.valueOf(this.totalNonBillableHours), "");
        reportString += this.createTableString("Total Hours", "", String.valueOf(this.totalHours), "");
        reportString += this.createCardBreakString();
        return reportString;
    }

    /**
     * Prints a blank line.
     * @return
     */
    private String lineBreak(){
        return String.format("%n");
    }

    /**
     * Creates string to print at top and bottom of each time card
     * @return
     */
    private String createCardBreakString(){
        char[] chars = new char[68];
        Arrays.fill(chars, '=');
        String breakString = new String(chars) + "%n";
        String formattedBreakString = String.format(breakString);
        return formattedBreakString;
    }

    /**
     * Creates timecard header string
     * @return
     */
    private String createCardHeaderString(){
        String name = consultant.toString();
        String startingDay = weekStartingDay.toString();
        String consultantString = String.format("Consultant: %s", name);
        String startingString = String.format("Week Starting: %s %n", startingDay);
        String headerString = "%-40s %30s %n";
        String formattedHeaderString = String.format(headerString, consultantString, startingString);
        return formattedHeaderString;
    }

    /**
     * Retuns formatted string with columns for table.
     * @param firstCol
     * @param secondCol
     * @param thirdCol
     * @param fourthCol
     * @return
     */
    private String createTableString(String firstCol, String secondCol, String thirdCol, String fourthCol){
        return String.format("%-27s  %-10s  %-5s  %-20s%n", firstCol, secondCol, thirdCol, fourthCol);
    }

    /**
     * Returns report string for lines under table column labels.
     * @return
     */
    private String createTableHeaderLines(){
        String first = "===========================";
        String second = "==========";
        String third = "=====";
        String fourth = "====================";
        return this.createTableString(first, second, third, fourth);
    }

    /**
     * Generates formatted string for table from a ConsultantTime instance.
     * @param consultantTime
     * @return
     */
    private String createTableDataEntry(ConsultantTime consultantTime){
        String account = consultantTime.getAccount().getName().toString();
        String date = consultantTime.getDate().toString();
        String hours = String.valueOf(consultantTime.getHours());
        String skill = consultantTime.getSkillType().toString();
        return this.createTableString(account, date, hours, skill);
    }

    /**
     * Gets an array of consultant times based on client name.
     * @param clientName
     * @return
     */
    public List<ConsultantTime> getBillableHoursForClient(String clientName){
        List<ConsultantTime> billableHoursForClient =new ArrayList<ConsultantTime>();
        for(ConsultantTime time: this.billableConsultantTimes){
            if(clientName == time.getAccount().getName()){
                billableHoursForClient.add(time);
            }
        }
        return billableHoursForClient;
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
