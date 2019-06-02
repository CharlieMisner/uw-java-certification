package edu.uw.cdm.account;

import edu.uw.ext.framework.account.*;
import edu.uw.ext.framework.order.Order;

import java.io.Serializable;

public class AccountCDM implements Account, Serializable {

    private AddressCDM address;
    private int balance;
    private CreditCardCDM creditCard;
    private String email;
    private String fullName;
    private String name;
    private byte[] passwordHash;
    private String phone;
    private final String MARKET_BUY_ORDER = "MarketSellOrder";
    private final String STOP_SELL_ORDER = "StopSellOrder";

    public AccountCDM() {
    }

    public AccountCDM(int balance, String name, byte[] passwordHash) {
        this.balance = balance;
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public void reflectOrder(Order order, int executionPrice){
        String orderType = order.getClass().getSimpleName();
        if(orderType.equals(MARKET_BUY_ORDER) || orderType.equals(STOP_SELL_ORDER)){
            this.balance += executionPrice;
        } else {
            this.balance -= executionPrice;
        }

    }

    public void registerAccountManager(AccountManager m){

    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = (AddressCDM)address;
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
        this.creditCard = (CreditCardCDM)card;
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
