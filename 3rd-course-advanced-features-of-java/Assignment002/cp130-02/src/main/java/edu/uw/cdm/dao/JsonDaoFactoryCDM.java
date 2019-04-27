package edu.uw.cdm.dao;

import edu.uw.ext.framework.dao.AccountDao;
import edu.uw.ext.framework.dao.DaoFactory;

public class JsonDaoFactoryCDM implements DaoFactory {

    public JsonDaoFactoryCDM() {
    }

    public AccountDao getAccountDao(){
        return new JsonAccountDaoCDM();
    }
}
