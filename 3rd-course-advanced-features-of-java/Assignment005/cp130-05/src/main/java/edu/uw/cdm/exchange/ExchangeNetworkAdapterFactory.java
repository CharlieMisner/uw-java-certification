package edu.uw.cdm.exchange;

import edu.uw.ext.framework.exchange.ExchangeAdapter;
import edu.uw.ext.framework.exchange.NetworkExchangeAdapterFactory;
import edu.uw.ext.framework.exchange.StockExchange;

public class ExchangeNetworkAdapterFactory implements NetworkExchangeAdapterFactory {

    public ExchangeNetworkAdapterFactory() {
    }

    public ExchangeAdapter newAdapter(StockExchange exchange, String multicastIP, int multicastPort, int commandPort){
        return new ExchangeNetworkAdapter(exchange, multicastIP, multicastPort, commandPort);
    }
}
