package edu.uw.cdm.broker;

import edu.uw.ext.framework.broker.OrderManager;
import edu.uw.ext.framework.order.StopBuyOrder;
import edu.uw.ext.framework.order.StopSellOrder;

import java.util.function.Consumer;

public class OrderManagerCDM implements OrderManager {

    private String stockTickerSymbol;
    private int price;

    public OrderManagerCDM(String stockTickerSymbol) {
        this.stockTickerSymbol = stockTickerSymbol;
    }

    public OrderManagerCDM(String stockTickerSymbol, int price) {
        this.stockTickerSymbol = stockTickerSymbol;
        this.price = price;
    }

    public void adjustPrice(int price) {
    }

    public String getSymbol() {
        return "string";
    }

    public void queueOrder(edu.uw.ext.framework.order.StopBuyOrder order) {
    }

    public void queueOrder(edu.uw.ext.framework.order.StopSellOrder order) {
    }

    public void setBuyOrderProcessor(Consumer<StopBuyOrder> processor) {
    }

    public void setSellOrderProcessor(Consumer<StopSellOrder> processor) {
    }
}
