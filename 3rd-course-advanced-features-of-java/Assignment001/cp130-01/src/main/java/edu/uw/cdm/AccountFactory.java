package edu.uw.cdm;

import edu.uw.ext.framework.account.Account;

public class AccountFactory {

    public Account newAccount(String accountName, byte[] hashedPassword, int initialBalance){
        AccountImplemented accountImplemented = new AccountImplemented();
        accountImplemented.setName(accountName);
        accountImplemented.setPasswordHash(hashedPassword);
        accountImplemented.setBalance(initialBalance);

        return new AccountImplemented();
    }
}
