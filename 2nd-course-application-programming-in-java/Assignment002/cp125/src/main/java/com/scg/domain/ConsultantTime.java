package com.scg.domain;

import java.time.LocalDate;

/**
 * Consultant time class
 * @author Charlie Misner
 */
public class ConsultantTime {

    private LocalDate date;
    private Account account;
    private Skill skillType;
    private int hours;

    /**
     * Creates instance of consultant time with four properties.
     * @param date
     * @param account
     * @param skillType
     * @param hours
     */
    public ConsultantTime(LocalDate date, Account account, Skill skillType, int hours) {
        this.date = date;
        this.account = account;
        this.skillType = skillType;
        this.hours = hours;
    }

    /**
     * Returns string representation of Consultant Time.
     * @return
     */
    @Override
    public String toString() {
        return  date.toString() + ", " +
                account.toString() + ", " +
                skillType.toString() + ", " +
                hours;
    }

    /**
     * Returns unique hashcode for this object.
     * @return hashcode
     */
    @Override
    public int hashCode(){
        return 31 + this.toString().hashCode();
    }

    /**
     * Determines if objects are equal by comparing hashCodes.
     * @param object
     * @return
     */
    public boolean equals(Object object){
        return this.hashCode() == object.hashCode();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Skill getSkillType() {
        return skillType;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
