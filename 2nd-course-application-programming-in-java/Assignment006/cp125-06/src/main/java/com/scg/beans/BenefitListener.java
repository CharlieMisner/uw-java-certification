package com.scg.beans;

import java.io.Serializable;

public interface BenefitListener extends Serializable {

    /**
     * Cancels dental.
     * @param event
     */
    void dentalCancellation(BenefitEvent event);

    /**
     * Enrolls dental.
     * @param event
     */
    void dentalEnrollment(BenefitEvent event);

    /**
     * Cancels medical.
     * @param event
     */
    void medicalCancellation(BenefitEvent event);

    /**
     * Enrolls Medical.
     * @param event
     */
    void medicalEnrollment(BenefitEvent event);


}
