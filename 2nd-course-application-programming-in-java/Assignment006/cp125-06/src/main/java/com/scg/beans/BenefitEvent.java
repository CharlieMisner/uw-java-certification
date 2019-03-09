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

    public BenefitEvent(Object source, Consultant consultant, LocalDate effectiveDate){
        super(source);
        this.consultant = consultant;
        this.effectiveDate = effectiveDate;
    }

    public static BenefitEvent cancelDental(Object source, Consultant consultant, LocalDate effectiveDate){

        return new BenefitEvent(source, consultant, effectiveDate);
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
}
