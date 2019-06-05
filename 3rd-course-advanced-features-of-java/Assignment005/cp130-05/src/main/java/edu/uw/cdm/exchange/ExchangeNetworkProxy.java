package edu.uw.cdm.exchange;

import edu.uw.ext.framework.exchange.ExchangeListener;
import edu.uw.ext.framework.exchange.StockExchange;
import edu.uw.ext.framework.exchange.StockQuote;
import edu.uw.ext.framework.order.Order;

import java.util.Optional;

public class ExchangeNetworkProxy implements StockExchange {

    private String eventIpAddress;
    private int eventPort;
    private String cmdIpAddress;
    private int cmdPort;

    public ExchangeNetworkProxy(String eventIpAddress, int eventPort, String cmdIpAddress, int cmdPort) {
        this.eventIpAddress = eventIpAddress;
        this.eventPort = eventPort;
        this.cmdIpAddress = cmdIpAddress;
        this.cmdPort = cmdPort;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public String[] getTickers() {
        return new String[0];
    }

    @Override
    public Optional<StockQuote> getQuote(String s) {
        return Optional.empty();
    }

    @Override
    public void addExchangeListener(ExchangeListener exchangeListener) {

    }

    @Override
    public void removeExchangeListener(ExchangeListener exchangeListener) {

    }

    @Override
    public int executeTrade(Order order) {
        return 0;
    }
}