package com.scg.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListener;
/**
 * @author Charlie Misner
 */
public class BenefitManager implements BenefitListener, PropertyChangeListener, EventListener {

    private Logger logger= LoggerFactory.getLogger(BenefitManager.class);

    public BenefitManager() {
    }

    /**
     * Logs dental cancel
     * @param event
     */
    public void dentalCancellation(BenefitEvent event){
        logger.info(String.format("%s cancelled dental.", event.getConsultant().getName().toString()));
    };
    /**
     * Logs dental enroll
     * @param event
     */
    public void dentalEnrollment(BenefitEvent event){
        logger.info(String.format("%s enrolled in dental.", event.getConsultant().getName().toString()));
    };
    /**
     * Logs medical cancel
     * @param event
     */
    public void medicalCancellation(BenefitEvent event){
        logger.info(String.format("%s cancelled medical.", event.getConsultant().getName().toString()));
    };
    /**
     * Logs medical enroll
     * @param event
     */
    public void medicalEnrollment(BenefitEvent event){
        logger.info(String.format("%s enrolled in dental.", event.getConsultant().getName().toString()));
    };

    /**
     * Logs property change
     * @param event
     */
    public void propertyChange(PropertyChangeEvent event){
        logger.info(String.format("%s changed from %d to %d.", event.getPropertyName(), event.getOldValue(), event.getNewValue()));
    };

}
