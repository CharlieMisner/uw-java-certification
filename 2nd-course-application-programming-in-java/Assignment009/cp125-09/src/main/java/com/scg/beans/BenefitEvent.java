package com.scg.beans;

import com.scg.domain.Consultant;

import java.time.LocalDate;
import java.util.EventObject;
import java.util.Optional;

public class BenefitEvent extends EventObject {

    private Consultant consultant;
    public Optional<Boolean> dentalStatus;
    public Optional<Boolean> medicalStatus;
    private LocalDate effectiveDate;

    /**
     * Constructor
     * @param source
     * @param consultant
     * @param effectiveDate
     */
    public BenefitEvent(Object source, Consultant consultant, LocalDate effectiveDate){
        super(source);
        this.consultant = consultant;
        this.effectiveDate = effectiveDate;
    }

    /**
     * Cancels dental.
     * @param source
     * @param consultant
     * @param effectiveDate
     * @return
     */
    public static BenefitEvent cancelDental(Object source, Consultant consultant, LocalDate effectiveDate){
        BenefitEvent benefitEvent = new BenefitEvent(source, consultant, effectiveDate);
        benefitEvent.dentalStatus = Optional.of(false);
        return benefitEvent;
    }

    /**
     * Cancels medical.
     * @param source
     * @param consultant
     * @param effectiveDate
     * @return
     */
    public static BenefitEvent cancelMedical(Object source, Consultant consultant, LocalDate effectiveDate){
        BenefitEvent benefitEvent = new BenefitEvent(source, consultant, effectiveDate);
        benefitEvent.medicalStatus = Optional.of(false);
        return benefitEvent;
    }

    /**
     * Enrolls in dental.
     * @param source
     * @param consultant
     * @param effectiveDate
     * @return
     */
    public static BenefitEvent enrollDental(Object source, Consultant consultant, LocalDate effectiveDate){
        BenefitEvent benefitEvent = new BenefitEvent(source, consultant, effectiveDate);
        benefitEvent.dentalStatus = Optional.of(false);
        return benefitEvent;
    }

    /**
     * Enrolls in medical.
     * @param source
     * @param consultant
     * @param effectiveDate
     * @return
     */
    public static BenefitEvent enrollMedical(Object source, Consultant consultant, LocalDate effectiveDate){
        BenefitEvent benefitEvent = new BenefitEvent(source, consultant, effectiveDate);
        benefitEvent.medicalStatus = Optional.of(false);
        return benefitEvent;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public Optional<Boolean> dentalStatus(){
        return this.dentalStatus;
    }

    public Optional<Boolean> medicalStatus(){
        return this.medicalStatus;
    }
}
