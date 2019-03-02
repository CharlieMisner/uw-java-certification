package com.scg.util;

/**
 * @author CharlieM
 */
public class Address {

    private String streetNumber;
    private String city;
    private StateCode state;
    private String postalCode;

    public Address(String streetNumber, String city, StateCode state, String postalCode) {
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    /**
     * Return formatted address string.
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s %n %s, %s %s", this.streetNumber, this.city, this.state, this.postalCode);
    }

    /**
     * Returns unique hashcode.
     * @return
     */
    @Override
    public int hashCode() {
        return 31 + this.toString().hashCode();
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getCity() {
        return city;
    }

    public StateCode getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
