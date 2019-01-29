package com.scg.util;

/**
 * Personal name class
 * @author Charlie Misner
 */
public class PersonalName {

    private String firstName;
    private String middleName;
    private String lastName;

    /**
     * Constructor with no inputs.
     */
    public PersonalName() {
        this.firstName = "";
        this.lastName = "";
        this.middleName = "";
    }

    /**
     * Contructor with first and last name.
     * @param firstName
     * @param lastName
     */
    public PersonalName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = "";
    }

    /**
     * Contructor with all three names.
     * @param firstName
     * @param middleName
     * @param lastName
     */
    public PersonalName(String lastName, String firstName, String middleName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    /**
     * Override toString
     * @return Last, First, Middle
     */
    @Override
    public String toString(){
        return this.lastName + ", " + this.firstName + " " + this.middleName;
    }

    @Override
    public int hashCode(){
        return 31 + this.toString().hashCode();
    }

    /**
     * Returns true if result of toString is equal.
     * @param otherName
     * @return
     */
    public boolean equals(PersonalName otherName) {
        return this.toString() == otherName.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
