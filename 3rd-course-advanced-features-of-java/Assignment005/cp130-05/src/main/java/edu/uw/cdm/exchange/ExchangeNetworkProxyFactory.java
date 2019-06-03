package edu.uw.cdm.exchange;

import edu.uw.ext.framework.exchange.NetworkExchangeProxyFactory;
import edu.uw.ext.framework.exchange.StockExchange;

public class ExchangeNetworkProxyFactory implements NetworkExchangeProxyFactory {

    public ExchangeNetworkProxyFactory() {
    }

    public StockExchange newProxy(String multicastIP, int multicastPort, String commandIP, int commandPort) {
        return new ExchangeNetworkProxy(multicastIP, multicastPort, commandIP, commandPort);
    }
}
