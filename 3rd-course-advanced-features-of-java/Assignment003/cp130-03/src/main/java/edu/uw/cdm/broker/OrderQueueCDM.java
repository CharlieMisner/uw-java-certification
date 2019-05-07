package edu.uw.cdm.broker;

import edu.uw.ext.framework.broker.OrderQueue;
import edu.uw.ext.framework.order.Order;

import java.util.function.Consumer;

public class OrderQueueCDM<T,E extends Order> implements OrderQueue<T, E> {

    public void enqueue(E e) {

    }

    public E dequeue() {
        return null;
    }

    public void dispatchOrders() {

    }

    public void setOrderProcessor(Consumer<E> consumer) {

    }

    public void setThreshold(T t) {

    }

    public T getThreshold() {
        return null;
    }
}
