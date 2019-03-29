package com.scg.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.EventListener;
/**
 * @author Charlie Misner
 */
public class CompensationManager implements PropertyChangeListener, VetoableChangeListener, EventListener {

    private Logger logger= LoggerFactory.getLogger(CompensationManager.class);

    public CompensationManager() {
    }

    /**
     * property change.
     * @param evt
     */
    public void propertyChange(PropertyChangeEvent evt){
        logger.info(String.format("%s changed from %d to %d.", evt.getPropertyName(), evt.getOldValue(), evt.getNewValue()));
    };

    /**
     * performs veto check
     * @param evt
     * @throws PropertyVetoException
     */
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException{

        Integer oldValue = (int)evt.getOldValue();
        Integer newValue = (int)evt.getNewValue();

        if (this.isGreaterThanFivePercentChange(oldValue, newValue)) {
            throw new PropertyVetoException("Pay rate Change is greater than Five percent, not permitted.", evt);
        }

    }

    /**
     * Checks if change is greater than 5%
     * @param oldValue
     * @param newValue
     * @return
     */
    private boolean isGreaterThanFivePercentChange(int oldValue, int newValue) {
        float percentChange;
        if(oldValue > 0){
            percentChange = (((float)newValue - oldValue)/oldValue)*100;
        } else {
            percentChange = 0;
        }

        return percentChange > 5;
    }
}
