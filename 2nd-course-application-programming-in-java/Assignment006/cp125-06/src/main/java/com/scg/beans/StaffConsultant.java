package com.scg.beans;

import com.scg.domain.Consultant;
import com.scg.util.PersonalName;

import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.io.Serializable;

public class StaffConsultant extends Consultant implements Serializable {

    private int payRate;
    private int sickLeaveHours;
    private int vacationHours;

    public StaffConsultant(PersonalName name, int rate, int sickLeave, int vacation){
        super(name);
    }

    public void addPayRateListener(PropertyChangeListener l){};

    public void addPropertyChangeListener(PropertyChangeListener l){};

    public void addSickLeaveHoursListener(PropertyChangeListener l){};

    public void addVacationHoursListener(PropertyChangeListener l){};

    public void addVetoableChangeListener(VetoableChangeListener l){};

    public void removePayRateListener(PropertyChangeListener l){};

    public void removePropertyChangeListener(PropertyChangeListener l){};

    public void removeSickLeaveHoursListener(PropertyChangeListener l){};

    public void removeVacationHoursListener(PropertyChangeListener l){};

    public void removeVetoableChangeListener(VetoableChangeListener l){};

    public int getPayRate() {
        return payRate;
    }

    public void setPayRate(int payRate) {
        this.payRate = payRate;
    }

    public int getSickLeaveHours() {
        return sickLeaveHours;
    }

    public void setSickLeaveHours(int sickLeaveHours) {
        this.sickLeaveHours = sickLeaveHours;
    }

    public int getVacationHours() {
        return vacationHours;
    }

    public void setVacationHours(int vacationHours) {
        this.vacationHours = vacationHours;
    }
}
