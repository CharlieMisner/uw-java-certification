package edu.uw.cdm.broker;

import edu.uw.cdm.account.AccountCDM;
import edu.uw.ext.framework.account.AccountManager;
import edu.uw.ext.framework.broker.Broker;
import edu.uw.ext.framework.broker.OrderManager;
import edu.uw.ext.framework.exchange.StockExchange;
import edu.uw.ext.framework.exchange.StockQuote;
import edu.uw.ext.framework.order.MarketBuyOrder;

import java.util.Optional;

public class BrokerCDM implements Broker {

    private String brokerName;
    private AccountManager accountManager;
    private StockExchange exchange;

    public BrokerCDM(String brokerName, AccountManager accountManager, StockExchange exchange) {
        this.brokerName = brokerName;
        this.accountManager = accountManager;
        this.exchange = exchange;
    }

    public void close() {
    }

    public edu.uw.ext.framework.account.Account createAccount(String username, String password, int balance) {
        return new AccountCDM();
    }

    protected OrderManager createOrderManager(String ticker, int initialPrice) {
        return new OrderManagerCDM(ticker, initialPrice);
    }

    public void deleteAccount(String username) {
    }

    public void exchangeClosed(edu.uw.ext.framework.exchange.ExchangeEvent event) {
    }

    public void exchangeOpened(edu.uw.ext.framework.exchange.ExchangeEvent event) {
    }

    protected void executeOrder(edu.uw.ext.framework.order.Order order) {
    }

    public edu.uw.ext.framework.account.Account getAccount(String username, String password) {
        return new AccountCDM();
    }

    public String getName() {
        return this.brokerName;
    }

    protected void initializeOrderManagers() {
    }

    public void placeOrder(MarketBuyOrder order) {
    }


    public void placeOrder(edu.uw.ext.framework.order.MarketSellOrder order) {
    }

    ;

    public void placeOrder(edu.uw.ext.framework.order.StopBuyOrder order) {
    }

    ;

    public void placeOrder(edu.uw.ext.framework.order.StopSellOrder order) {
    }

    ;

    public void priceChanged(edu.uw.ext.framework.exchange.ExchangeEvent event) {
    }

    ;

    public Optional<StockQuote> requestQuote(String symbol) {
        Optional<StockQuote> quote = this.exchange.getQuote(symbol);

        return quote;
    }

}
