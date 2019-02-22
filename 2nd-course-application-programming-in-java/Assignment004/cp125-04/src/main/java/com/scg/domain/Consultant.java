package com.scg.domain;

import com.scg.util.PersonalName;

public class Consultant implements Comparable<Consultant>{

    private PersonalName name;

    public Consultant(PersonalName name) {
        this.name = name;
    }

    /**
     * Enables comparison of two consultants.
     * @param consultant
     * @return
     */
    public int compareTo(Consultant consultant) {
        return this.name.toString().compareTo(consultant.getName().toString());
    }

    /**
     * toString override returns string representation of PersonalName;
     * @return
     */
    @Override
    public String toString(){
        return name.toString();
    }

    /**
     * Returns the personal name object.
     * @return
     */
    public PersonalName getName() {
        return name;
    }
}
