package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.PersonalName;

/**
 * Client account class
 * @author Charlie Misner
 */
public class ClientAccount implements Account, Comparable<ClientAccount> {

    private String name;
    private PersonalName contact;
    private Address address;

    /**
     * Creates instance of Client Account with name and contact.
     * @param name
     * @param contact
     */
    public ClientAccount(String name, PersonalName contact, Address address) {
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    /**
     * Enables comparison of two client accounts.
     * @param clientAccount
     * @return
     */
    public int compareTo(ClientAccount clientAccount){
        int diff = this.getName().compareTo(clientAccount.getName());
        diff = (diff == 0) ? this.getContact().toString().compareTo(clientAccount.getContact().toString()): diff;
        diff = (diff == 0) ? this.getAddress().toString().compareTo(clientAccount.getAddress().toString()): diff;

        return diff;
    }

    /**
     * Returns the client name.
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns true, client accounts are billable.
     * @return
     */
    @Override
    public boolean isBillable() {
        return true;
    }

    /**
     * Gets the contact.
     * @return
     */
    public PersonalName getContact() {
        return contact;
    }

    /**
     * Sets the contact.
     * @param contact
     */
    public void setContact(PersonalName contact) {
        this.contact = contact;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
