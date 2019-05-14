package edu.uw.cdm.account;

import edu.uw.ext.framework.account.AccountManager;
import edu.uw.ext.framework.account.AccountManagerFactory;
import edu.uw.ext.framework.dao.AccountDao;

public final class AccountManagerFactoryCDM implements AccountManagerFactory {
    public AccountManager newAccountManager(AccountDao dao){
        return new AccountManagerCDM(dao);
    };
}
