package com.scg.domain;

import com.scg.util.PersonalName;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Consultant implements Comparable<Consultant>, Serializable {

    private PersonalName name;

    public Consultant(PersonalName name) {
        this.name = name;
    }

    /**
     * Serialization proxy subclass.
     */
    private static final class SerializationProxy implements Serializable {

        private final String x;
        private final String y;
        private final String z;

        /**
         * Cosntructor
         * @param consultant
         */
        SerializationProxy(final Consultant consultant){
            final PersonalName name = consultant.getName();
            x = name.getLastName();
            y = name.getFirstName();
            z = name.getMiddleName();
        }

        /**
         * Returns serializable object.
         * @return
         */
        private Object readResolve(){
            return new Consultant(new PersonalName(x,y,z));
        }
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

    /**
     * Serialize class using serialization proxy.
     * @return
     */
    private Object writeReplace(){
        return new SerializationProxy(this);
    }

    /**
     * Throw exception if no proxy.
     * @param ois
     * @throws InvalidObjectException
     */
    private void readObject(ObjectInputStream ois)
            throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }
}
