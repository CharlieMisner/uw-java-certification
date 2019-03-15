package com.scg.beans;

import com.scg.domain.Consultant;
import com.scg.util.PersonalName;

import java.beans.*;
import java.io.Serializable;

/**
 * @author Charlie Misner
 */
public class StaffConsultant extends Consultant implements Serializable {

    private int payRate;
    private int sickLeaveHours;
    private int vacationHours;

    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);

    private static String PAY_RATE_PROPERTY_NAME = "payRate";
    private static String SICK_LEAVE_HOURS_PROPERTY_NAME = "sickLeaveHours";
    private static String VACATION_HOURS_PROPERTY_NAME = "vacationHours";

    /**
     * Constructor
     * @param name
     * @param rate
     * @param sickLeave
     * @param vacation
     */
    public StaffConsultant(PersonalName name, int rate, int sickLeave, int vacation){
        super(name);
        this.payRate = rate;
        this.sickLeaveHours = sickLeave;
        this.vacationHours = vacation;
    }

    /**
     * Add listener.
     * @param l
     */
    public void addPropertyChangeListener(PropertyChangeListener l){
        changeSupport.addPropertyChangeListener(l);
    }
    /**
     * Add listener.
     * @param l
     */
    public void addPayRateListener(PropertyChangeListener l){
        changeSupport.addPropertyChangeListener(PAY_RATE_PROPERTY_NAME, l);
    }
    /**
     * Add listener.
     * @param l
     */
    public void addSickLeaveHoursListener(PropertyChangeListener l){
        changeSupport.addPropertyChangeListener(SICK_LEAVE_HOURS_PROPERTY_NAME, l);
    }
    /**
     * Add listener.
     * @param l
     */
    public void addVacationHoursListener(PropertyChangeListener l){
        changeSupport.addPropertyChangeListener(VACATION_HOURS_PROPERTY_NAME, l);
    }
    /**
     * Add listener.
     * @param l
     */
    public void addVetoableChangeListener(VetoableChangeListener l){
        vetoableChangeSupport.addVetoableChangeListener(PAY_RATE_PROPERTY_NAME, l);
    };
    /**
     * Remove listener.
     * @param l
     */
    public void removePayRateListener(PropertyChangeListener l){
        changeSupport.removePropertyChangeListener(PAY_RATE_PROPERTY_NAME, l);
    };
    /**
     * Remove listener.
     * @param l
     */
    public void removePropertyChangeListener(PropertyChangeListener l){
        changeSupport.removePropertyChangeListener(l);
    };
    /**
     * Remove listener.
     * @param l
     */
    public void removeSickLeaveHoursListener(PropertyChangeListener l){
        changeSupport.removePropertyChangeListener(SICK_LEAVE_HOURS_PROPERTY_NAME, l);
    };
    /**
     * Remove listener.
     * @param l
     */
    public void removeVacationHoursListener(PropertyChangeListener l){
        changeSupport.removePropertyChangeListener(VACATION_HOURS_PROPERTY_NAME, l);
    };
    /**
     * Remove listener.
     * @param l
     */
    public void removeVetoableChangeListener(VetoableChangeListener l){
        vetoableChangeSupport.removeVetoableChangeListener(PAY_RATE_PROPERTY_NAME, l);
    };

    public int getPayRate() {
        return payRate;
    }

    public void setPayRate(int payRate) throws PropertyVetoException {
        try{
            vetoableChangeSupport.fireVetoableChange(PAY_RATE_PROPERTY_NAME, this.payRate, payRate);
            changeSupport.firePropertyChange(PAY_RATE_PROPERTY_NAME, this.payRate, payRate);
            this.payRate = payRate;
        } catch (PropertyVetoException e) {
            throw e;
        }

    }

    public int getSickLeaveHours() {
        return sickLeaveHours;
    }

    public void setSickLeaveHours(int sickLeaveHours) {
        changeSupport.firePropertyChange(SICK_LEAVE_HOURS_PROPERTY_NAME, this.sickLeaveHours, sickLeaveHours);
        this.sickLeaveHours = sickLeaveHours;
    }

    public int getVacationHours() {
        return vacationHours;
    }

    public void setVacationHours(int vacationHours) {
        changeSupport.firePropertyChange(VACATION_HOURS_PROPERTY_NAME, this.vacationHours, vacationHours);
        this.vacationHours = vacationHours;
    }
}
