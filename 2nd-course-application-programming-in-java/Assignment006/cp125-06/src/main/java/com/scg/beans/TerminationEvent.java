package com.scg.beans;

import com.scg.domain.Consultant;

import java.util.EventObject;
/**
 * @author Charlie Misner
 */
public class TerminationEvent extends EventObject {

    private Consultant consultant;
    private boolean isVoluntary;

    /**
     * Constructor
     * @param source
     * @param consultant
     * @param voluntary
     */
    public TerminationEvent(Object source, Consultant consultant, boolean voluntary){
        super(source);
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
