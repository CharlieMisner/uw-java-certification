package com.scg.domain;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

/**
 * @author CharlieM
 */
public class InvoiceLineItem {

    private LocalDate date;
    private Consultant consultant;
    private Skill skill;
    private int hours;

    public InvoiceLineItem(LocalDate date, Consultant consultant, Skill skill, int hours) {
        this.date = date;
        this.consultant = consultant;
        this.skill = skill;
        this.hours = hours;
    }

    public int getCharge(){
        return this.skill.getRate() * this.hours;
    }

    public LocalDate getDate() {
        return date;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return String.format("%-10s  %-17s  %-20s  %-5s %10s %n",
            this.date.toString(),
            this.consultant.getName().toString(),
            this.skill.toString(),
            this.hours,
            "$" + formatter.format(this.getCharge())
        );

    }
}
