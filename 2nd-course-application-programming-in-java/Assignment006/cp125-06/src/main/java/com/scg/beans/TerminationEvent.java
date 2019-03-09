package com.scg.beans;

import com.scg.domain.Consultant;

public class TerminationEvent {

    private Consultant consultant;
    private boolean isVoluntary;

    public TerminationEvent(Object source, Consultant consultant, boolean voluntary){
        this.consultant = consultant;
        this.isVoluntary = voluntary;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public boolean isVoluntary() {
        return isVoluntary;
    }
}
