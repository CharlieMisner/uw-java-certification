package edu.uw.cdm.broker;

import edu.uw.ext.framework.account.AccountManager;
import edu.uw.ext.framework.broker.BrokerFactory;
import edu.uw.ext.framework.exchange.StockExchange;

public class BrokerCDMFactory implements BrokerFactory {

    public BrokerCDMFactory() {
    }

    public Broker newBroker(String name, AccountManager accountManager, StockExchange exchange){
        return new BrokerCDM(name, accountManager, exchange);
    }
}
