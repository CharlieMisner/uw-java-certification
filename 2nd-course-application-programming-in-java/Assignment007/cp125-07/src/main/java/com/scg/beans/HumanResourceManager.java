package com.scg.beans;

import com.scg.domain.Consultant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.EventListenerList;
import java.beans.PropertyVetoException;
import java.time.LocalDate;

public class HumanResourceManager {

    private Logger logger= LoggerFactory.getLogger(BenefitManager.class);
    private EventListenerList benefitListenerList = new EventListenerList();
    private EventListenerList terminationListenerList = new EventListenerList();

    public HumanResourceManager() {
    }

    /**
     * Adds listener
     * @param l
     */
    public void addBenefitListener(BenefitListener l) {
        benefitListenerList.add(BenefitListener.class, l);
    }
    /**
     * Adds listener
     * @param l
     */
    public void addTerminationListener(TerminationListener l) {
        terminationListenerList.add(TerminationListener.class, l);
    }

    /**
     * Removes Listener
     * @param l
     */
    public void removeBenefitListener(BenefitListener l) {
        benefitListenerList.remove(BenefitListener.class, l);
    }
    /**
     * Removes Listener
     * @param l
     */
    public void removeTerminationListener(TerminationListener l) {
        terminationListenerList.remove(TerminationListener.class, l);
    }

    /**
     * Changes pay rate.
     * @param c
     * @param newPayRate
     */
    public void adjustPayRate(StaffConsultant c, int newPayRate) {
        try{
            c.setPayRate(newPayRate);
        } catch (PropertyVetoException e) {
            logger.info(String.format(e.getMessage()));
        }
    }

    /**
     * Changes sick leave.
     * @param c
     * @param newSickLeaveHours
     */
    public void adjustSickLeaveHours(StaffConsultant c, int newSickLeaveHours) {
        c.setSickLeaveHours(newSickLeaveHours);
    }

    /**
     * Changes vacation time.
     * @param c
     * @param newVacationHours
     */
    public void adjustVacationHours(StaffConsultant c, int newVacationHours) {
        c.setVacationHours(newVacationHours);
    }

    /**
     * Cancels dental.
     * @param c
     */
    public void cancelDental(Consultant c) {
        BenefitEvent benefitEvent = BenefitEvent.cancelDental(this, c, LocalDate.now());
        BenefitListener[] benefitListeners = benefitListenerList.getListeners(BenefitListener.class);
        for (BenefitListener benefitListener : benefitListeners){
            benefitListener.dentalCancellation(benefitEvent);
        }
    }

    /**
     * Cancels medical
     * @param c
     */
    public void cancelMedical(Consultant c) {
        BenefitEvent benefitEvent = BenefitEvent.cancelMedical(this, c, LocalDate.now());
        BenefitListener[] benefitListeners = benefitListenerList.getListeners(BenefitListener.class);
        for (BenefitListener benefitListener : benefitListeners){
            benefitListener.medicalCancellation(benefitEvent);
        }
    }

    /**
     * Enrolls dental
     * @param c
     */
    public void enrollDental(Consultant c) {
        BenefitEvent benefitEvent = BenefitEvent.enrollDental(this, c, LocalDate.now());
        BenefitListener[] benefitListeners = benefitListenerList.getListeners(BenefitListener.class);
        for (BenefitListener benefitListener : benefitListeners){
            benefitListener.dentalEnrollment(benefitEvent);
        }
    }

    /**
     * Enroll medical
     * @param c
     */
    public void enrollMedical(Consultant c) {
        BenefitEvent benefitEvent = BenefitEvent.enrollMedical(this, c, LocalDate.now());
        BenefitListener[] benefitListeners = benefitListenerList.getListeners(BenefitListener.class);
        for (BenefitListener benefitListener : benefitListeners){
            benefitListener.medicalEnrollment(benefitEvent);
        }
    }

    /**
     * Fire employee
     * @param c
     */
    public void terminate(Consultant c) {
        TerminationEvent terminationEvent = new TerminationEvent(this, c, false);
        TerminationListener[] terminationListeners = terminationListenerList.getListeners(TerminationListener.class);
        for (TerminationListener terminationListener : terminationListeners){
            terminationListener.forcedTermination(terminationEvent);
        }
    }

    /**
     * Employee quits
     * @param c
     */
    public void acceptResignation(Consultant c) {
        TerminationEvent terminationEvent = new TerminationEvent(this, c, true);
        TerminationListener[] terminationListeners = terminationListenerList.getListeners(TerminationListener.class);
        for (TerminationListener terminationListener : terminationListeners){
            terminationListener.voluntaryTermination(terminationEvent);
        }
    }
}
