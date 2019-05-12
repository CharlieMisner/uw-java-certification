package edu.uw.cdm.broker;

import edu.uw.cdm.account.AccountCDM;
import edu.uw.ext.framework.account.Account;
import edu.uw.ext.framework.account.AccountException;
import edu.uw.ext.framework.account.AccountManager;
import edu.uw.ext.framework.broker.Broker;
import edu.uw.ext.framework.broker.BrokerException;
import edu.uw.ext.framework.broker.OrderManager;
import edu.uw.ext.framework.broker.OrderQueue;
import edu.uw.ext.framework.exchange.ExchangeEvent;
import edu.uw.ext.framework.exchange.ExchangeListener;
import edu.uw.ext.framework.exchange.StockExchange;
import edu.uw.ext.framework.exchange.StockQuote;
import edu.uw.ext.framework.order.*;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;

public class BrokerCDM implements Broker, ExchangeListener, EventListener {

    private String brokerName;
    private AccountManager accountManager;
    private StockExchange exchange;
    private OrderQueue<Boolean, Order> orderQueue;
    private HashMap<String, OrderManager> orderManagerHashMap;

    private BrokerCDM(String brokerName,  StockExchange exchange, AccountManager accountManager) {
        this.brokerName = brokerName;
        this.accountManager = accountManager;
        this.exchange = exchange;
    }

    public BrokerCDM(String brokerName, AccountManager accountManager, StockExchange exchange) {
        this.brokerName = brokerName;
        this.accountManager = accountManager;
        this.exchange = exchange;
        this.orderQueue = new OrderQueueCDM<>(exchange.isOpen(),(threshold, order) -> threshold);
        this.orderQueue.setOrderProcessor(this::executeOrder);
        initializeOrderManagers();
        exchange.addExchangeListener(this);
    }

    public void close() {
        this.exchange.removeExchangeListener(this);
        this.orderManagerHashMap = null;
        try {
            this.accountManager.close();
        } catch (AccountException e) {
            e.printStackTrace();
        }
    }

    public Account createAccount(String username, String password, int balance) {
        try {
            return this.accountManager.createAccount(username, password, balance);
        } catch (AccountException exception) {
            exception.printStackTrace();
            return new AccountCDM();
        }
    }

    private OrderManager createOrderManager(String ticker, int initialPrice) {
        return new OrderManagerCDM(ticker, initialPrice);
    }

    public void deleteAccount(String username) {
        try {
            this.accountManager.deleteAccount(username);
        } catch (AccountException exception) {
            exception.printStackTrace();
        }
    }

    public void exchangeClosed(ExchangeEvent event) {
        this.orderQueue.setThreshold(false);
    }

    public void exchangeOpened(ExchangeEvent event) {
        this.orderQueue.setThreshold(true);
    }

    private void executeOrder(Order order) {
        int price = exchange.executeTrade(order);
        try {
            Account account = this.accountManager.getAccount(order.getAccountId());
            account.reflectOrder(order, price);
        } catch (AccountException e) {
            e.printStackTrace();
        }
    }

    public Account getAccount(String username, String password) throws BrokerException {
        try {
            if (this.accountManager.validateLogin(username, password)){
                return this.accountManager.getAccount(username);
            } else {
                throw new BrokerException("The Password Was Not Correct");
            }
        } catch (AccountException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getName() {
        return this.brokerName;
    }

    private void initializeOrderManagers() {
        this.orderManagerHashMap = new HashMap<>();
        Consumer<StopBuyOrder> stopBuyOrderToOrderProcessor = (StopBuyOrder order) -> this.orderQueue.enqueue(order);
        Consumer<StopSellOrder> stopSellOrderToOrderProcessor = (StopSellOrder order) -> this.orderQueue.enqueue(order);
        for(String stockTicker : this.exchange.getTickers()){
            Optional<StockQuote> quoteOptional = this.exchange.getQuote(stockTicker);
            quoteOptional.ifPresent(this.addOrderToMap(stopBuyOrderToOrderProcessor,stopSellOrderToOrderProcessor));
        }
    }

    public void placeOrder(MarketBuyOrder order) {
        this.orderQueue.enqueue(order);
    }


    public void placeOrder(MarketSellOrder order) {
        this.orderQueue.enqueue(order);
    }

    public void placeOrder(StopBuyOrder order) {
        this.orderQueue.enqueue(order);
    }

    public void placeOrder(StopSellOrder order) {
        this.orderQueue.enqueue(order);
    }

    public void priceChanged(edu.uw.ext.framework.exchange.ExchangeEvent event) {
        OrderManager orderManager = orderManagerHashMap.get(event.getTicker());
        orderManager.adjustPrice(event.getPrice());
    }

    public Optional<StockQuote> requestQuote(String symbol) {
        Optional<StockQuote> quote = this.exchange.getQuote(symbol);

        return quote;
    }


    private Consumer<? super StockQuote> addOrderToMap(Consumer<StopBuyOrder>stopBuy, Consumer<StopSellOrder>stopSell){
        return (quote) -> {
            int price = quote.getPrice();
            String ticker = quote.getTicker();
            OrderManager orderManager = this.createOrderManager(ticker, price);
            orderManager.setBuyOrderProcessor(stopBuy);
            orderManager.setSellOrderProcessor(stopSell);
            this.orderManagerHashMap.put(ticker, orderManager);
        };
    }
}
