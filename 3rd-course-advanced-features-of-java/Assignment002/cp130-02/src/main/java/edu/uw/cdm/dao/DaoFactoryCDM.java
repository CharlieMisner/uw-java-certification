package edu.uw.cdm.dao;

import edu.uw.ext.framework.dao.AccountDao;
import edu.uw.ext.framework.dao.DaoFactory;

public class DaoFactoryCDM implements DaoFactory {

    public DaoFactoryCDM() {
    }

    public AccountDao getAccountDao(){
        return new AccountDaoCDM();
    }
}
