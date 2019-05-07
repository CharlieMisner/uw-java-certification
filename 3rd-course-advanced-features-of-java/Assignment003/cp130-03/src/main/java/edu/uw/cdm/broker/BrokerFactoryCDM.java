package edu.uw.cdm.broker;

import edu.uw.ext.framework.account.AccountManager;
import edu.uw.ext.framework.broker.Broker;
import edu.uw.ext.framework.broker.BrokerFactory;
import edu.uw.ext.framework.exchange.StockExchange;

public class BrokerFactoryCDM implements BrokerFactory {

    public BrokerFactoryCDM() {
    }

    public Broker newBroker(String name, AccountManager accountManager, StockExchange exchange){
        return new BrokerCDM(name, accountManager, exchange);
    }
}
