package edu.uw.cdm.account;

import edu.uw.ext.framework.account.Account;
import edu.uw.ext.framework.account.AccountException;
import edu.uw.ext.framework.account.AccountFactory;

public class AccountFactoryCDM implements AccountFactory {

    public AccountFactoryCDM() {
    }

    public Account newAccount(String accountName, byte[] hashedPassword, int initialBalance){

        if(initialBalance >= 100000){
            AccountCDM accountCDM = new AccountCDM(initialBalance, accountName, hashedPassword);
            try {
                accountCDM.setName(accountName);
                accountCDM.setPasswordHash(hashedPassword);
                accountCDM.setBalance(initialBalance);
            } catch (AccountException exception) {
                accountCDM = null;
            }
            return accountCDM;
        } else {
            return null;
        }
    }
}
