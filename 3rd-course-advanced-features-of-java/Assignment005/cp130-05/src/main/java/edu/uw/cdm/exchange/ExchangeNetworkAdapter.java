package edu.uw.cdm.exchange;

import edu.uw.ext.framework.exchange.ExchangeAdapter;
import edu.uw.ext.framework.exchange.ExchangeEvent;
import edu.uw.ext.framework.exchange.StockExchange;

public class ExchangeNetworkAdapter implements ExchangeAdapter {

    private StockExchange exchng;
    private String multicastIP;
    private int multicastPort;
    private int commandPort;

    public ExchangeNetworkAdapter(StockExchange exchng, String multicastIP, int multicastPort, int commandPort) {
        this.exchng = exchng;
        this.multicastIP = multicastIP;
        this.multicastPort = multicastPort;
        this.commandPort = commandPort;
    }

    @Override
    public void exchangeOpened(ExchangeEvent exchangeEvent) {

    }

    @Override
    public void exchangeClosed(ExchangeEvent exchangeEvent) {

    }

    @Override
    public void priceChanged(ExchangeEvent exchangeEvent) {

    }

    @Override
    public void close() throws Exception {

    }
}
