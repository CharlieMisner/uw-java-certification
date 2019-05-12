package edu.uw.cdm.broker;

import edu.uw.ext.framework.broker.OrderManager;
import edu.uw.ext.framework.broker.OrderQueue;
import edu.uw.ext.framework.order.StopBuyOrder;
import edu.uw.ext.framework.order.StopSellOrder;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class OrderManagerCDM implements OrderManager {

    private String stockTickerSymbol;
    private int price;
    private OrderQueue<Integer, StopBuyOrder> stopBuyOrderQueue;
    private OrderQueue<Integer, StopSellOrder> stopSellOrderQueue;

    public OrderManagerCDM(String stockTickerSymbol) {
        this.stockTickerSymbol = stockTickerSymbol;
    }

    public OrderManagerCDM(String stockTickerSymbol, int price) {
        this.stockTickerSymbol = stockTickerSymbol;
        this.price = price;

        this.stopBuyOrderQueue = new OrderQueueCDM<>(this.price,
                this.getStopBuyOrderFilter(),
                this.getStopBuyOrderComparatorAscending());

        this.stopSellOrderQueue = new OrderQueueCDM<>(this.price,
                this.getStopSellOrderFilter(),
                this.getStopSellOrderComparatorDescending());
    }

    public void adjustPrice(int price) {
        this.stopSellOrderQueue.setThreshold(price);
        this.stopBuyOrderQueue.setThreshold(price);
    }

    public String getSymbol() {
        return this.stockTickerSymbol;
    }

    public void queueOrder(edu.uw.ext.framework.order.StopBuyOrder order) {
        this.stopBuyOrderQueue.enqueue(order);
    }

    public void queueOrder(edu.uw.ext.framework.order.StopSellOrder order) {
        this.stopSellOrderQueue.enqueue(order);
    }

    public void setBuyOrderProcessor(Consumer<StopBuyOrder> processor) {
        this.stopBuyOrderQueue.setOrderProcessor(processor);
    }

    public void setSellOrderProcessor(Consumer<StopSellOrder> processor) {
        this.stopSellOrderQueue.setOrderProcessor(processor);
    }

    private BiPredicate<Integer, StopSellOrder> getStopSellOrderFilter(){
        return (threshold, order) -> order.getPrice() >= threshold;
    }

    private BiPredicate<Integer, StopBuyOrder> getStopBuyOrderFilter(){
        return (threshold, order) -> order.getPrice() <= threshold;
    }

    private Comparator<StopBuyOrder> getStopBuyOrderComparatorAscending(){
        return Comparator.comparing(StopBuyOrder::getPrice).thenComparing(StopBuyOrder::compareTo);
    }

    private Comparator<StopSellOrder> getStopSellOrderComparatorDescending(){
        return Comparator.comparing(StopSellOrder::getPrice).reversed().thenComparing(StopSellOrder::compareTo);
    }
}
