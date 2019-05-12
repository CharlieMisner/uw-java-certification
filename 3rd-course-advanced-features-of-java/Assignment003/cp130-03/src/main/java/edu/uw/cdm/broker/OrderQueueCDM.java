package edu.uw.cdm.broker;

import edu.uw.ext.framework.broker.OrderQueue;
import edu.uw.ext.framework.order.Order;

import java.lang.reflect.ParameterizedType;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * Order queue.
 * @param <T>
 * @param <E>
 */
public class OrderQueueCDM<T,E extends Order> implements OrderQueue<T, E> {

    private T threshold;
    private BiPredicate<T,E> filter;
    private TreeSet<E> orderQueue;
    private E currentDequeueOrder;
    private Consumer<E> orderProcessor;

    public OrderQueueCDM(T threshold, BiPredicate<T, E> filter) {
        this.threshold = threshold;
        this.filter = filter;
        this.orderQueue = new TreeSet<>();
    }

    public OrderQueueCDM(T threshold, BiPredicate<T, E> filter, Comparator<E> comparator) {
        this.threshold = threshold;
        this.filter = filter;
        this.orderQueue = new TreeSet<>(comparator);
    }

    /**
     * Adds orders to queue.
     * @param order
     */
    public void enqueue(E order) {
        orderQueue.add(order);
        dispatchOrders();
    }

    public E dequeue() {
        this.currentDequeueOrder = null;
        if(this.orderQueue.size() > 0) {
            this.currentDequeueOrder = orderQueue.first();
            Boolean queueIsWithinThreshold = this.filter.test(threshold, this.currentDequeueOrder);
            if (queueIsWithinThreshold) {
                this.orderQueue.remove(this.currentDequeueOrder);
            } else {
                this.currentDequeueOrder = null;
            }
        }
        return this.currentDequeueOrder;
    }

    /**
     * Dispatches orders.
     */
    public void dispatchOrders() {
        this.dequeue();
        while (this.currentDequeueOrder != null){
            orderProcessor.accept(this.currentDequeueOrder);
            this.dequeue();
        }
    }

    public void setOrderProcessor(Consumer<E> consumer) {
        this.orderProcessor = consumer;
    }

    public void setThreshold(T threshold) {
        this.threshold = threshold;
        dispatchOrders();
    }

    public T getThreshold() {
        return this.threshold;
    }
}
