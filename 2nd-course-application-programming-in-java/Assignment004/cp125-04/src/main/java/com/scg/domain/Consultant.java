package com.scg.domain;

import com.scg.util.PersonalName;

public class Consultant {

    private PersonalName name;

    public Consultant(PersonalName name) {
        this.name = name;
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
