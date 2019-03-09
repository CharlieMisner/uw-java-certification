package com.scg.beans;

import com.scg.domain.Consultant;
import com.scg.util.PersonalName;

public class Eeoc {

    private static int forcedTerminationCount;
    private static int voluntaryTerminationCount;

    public Eeoc() {
    }

    public void forcedTermination(TerminationEvent evt){
        String consultantName = evt.getConsultant().getName().toString();
        System.out.printf("%n %s was fired.", consultantName);
        forcedTerminationCount++;
    }

    public void voluntaryTermination(TerminationEvent evt){
        String consultantName = evt.getConsultant().getName().toString();
        System.out.printf("%n %s was quit.", consultantName);
        voluntaryTerminationCount++;
    }

    public static int forcedTerminationCount() {
        return forcedTerminationCount;
    }

    public static int voluntaryTerminationCount() {
        return voluntaryTerminationCount;
    }
}
