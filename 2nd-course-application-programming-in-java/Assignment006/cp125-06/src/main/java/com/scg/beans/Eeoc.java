package com.scg.beans;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EventListener;
/**
 * @author Charlie Misner
 */
public class Eeoc implements TerminationListener, EventListener {

    private Logger logger= LoggerFactory.getLogger(Eeoc.class);
    private int forcedTerminationCount;
    private int voluntaryTerminationCount;

    public Eeoc() {
    }

    /**
     * Employee fired event.
     * @param evt
     */
    public void forcedTermination(TerminationEvent evt){
        String consultantName = evt.getConsultant().getName().toString();
        logger.info(String.format("%s was fired.", consultantName));
        forcedTerminationCount++;
    }

    /**
     * Employee quit event.
     * @param evt
     */
    public void voluntaryTermination(TerminationEvent evt){
        String consultantName = evt.getConsultant().getName().toString();
        logger.info(String.format("%s just quit.", consultantName));
        voluntaryTerminationCount++;
    }
    /**
     * Returns forced termination count
     */
    public int forcedTerminationCount() {
        return forcedTerminationCount;
    }

    /**
     * Returns quit count.
     * @return
     */
    public int voluntaryTerminationCount() {
        return voluntaryTerminationCount;
    }
}
