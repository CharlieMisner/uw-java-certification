package com.scg.beans;

import java.io.Serializable;
import java.util.EventListener;

public interface TerminationListener extends EventListener, Serializable {

    /**
     * Fires employee.
     * @param evt
     */
    void forcedTermination(TerminationEvent evt);

    /**
     * Employee quit.
     * @param evt
     */
    void voluntaryTermination(TerminationEvent evt);

}
