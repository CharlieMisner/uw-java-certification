package edu.uw.cdm.account;

import edu.uw.ext.framework.account.*;
import edu.uw.ext.framework.order.Order;

import java.io.Serializable;

public class AccountCDM implements Account, Serializable {

    private Address address;
    private int balance;
    private CreditCard creditCard;
    private String email;
    private String fullName;
    private String name;
    private byte[] passwordHash;
    private String phone;

    public AccountCDM() {
    }

    public AccountCDM(int balance, String name, byte[] passwordHash) {
        this.balance = balance;
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public void reflectOrder(Order order, int executionPrice){
        this.balance += executionPrice;
    }

    public void registerAccountManager(AccountManager m){

    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {

        this.balance = balance;

    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard card){
        this.creditCard = card;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws AccountException {
        if (name.length() >= 8){
            this.name = name;
        } else {
            throw new AccountException("The name must be at least 8 characters.");
        }
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
