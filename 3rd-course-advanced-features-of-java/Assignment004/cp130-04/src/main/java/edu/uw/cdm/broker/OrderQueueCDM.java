package edu.uw.cdm.broker;

import edu.uw.ext.framework.broker.OrderQueue;
import edu.uw.ext.framework.order.Order;

import java.lang.reflect.ParameterizedType;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

//locksplitting
//Use locks!!!! instead of synchronize
    //Lock and condition
    //Lock to lock, condition to notify to do work.


/**
 * Order queue.
 * @param <T>
 * @param <E>
 */
public class OrderQueueCDM<T,E extends Order> implements OrderQueue<T, E>, Runnable {

    private T threshold;
    private BiPredicate<T,E> filter;
    private TreeSet<E> orderQueue;
    private E currentDequeueOrder;
    private Consumer<E> orderProcessor;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Thread queueThread = new Thread(this);

    public OrderQueueCDM(T threshold, BiPredicate<T, E> filter) {
        this.threshold = threshold;
        this.filter = filter;
        this.orderQueue = new TreeSet<>(); //Comparator.naturalOrder()
    }

    public OrderQueueCDM(T threshold, BiPredicate<T, E> filter, Comparator<E> comparator) {
        this.threshold = threshold;
        this.filter = filter;
        this.orderQueue = new TreeSet<>(comparator);
        queueThread.start();
    }

    public void run(){
        this.dequeue();
        while (this.currentDequeueOrder != null){
            orderProcessor.accept(this.currentDequeueOrder);
            this.dequeue();
        }
    }

    /**
     * Adds orders to queue.
     * @param order
     */
    public void enqueue(E order) {
        this.lock.lock();
        try {
            orderQueue.add(order);
            System.out.println("ENQUEUE CALLED");
            this.condition.signal();
        } finally {
            this.lock.unlock();
        }
    }

    public E dequeue() {
        this.lock.lock();
        this.currentDequeueOrder = null;
        // NOT THREAD SAFE
        try {
            System.out.println("TEST 1");
            if (this.orderQueue.size() > 0) {
                System.out.println("TEST 2");
                this.currentDequeueOrder = orderQueue.first();
                Boolean queueIsWithinThreshold = this.filter.test(threshold, this.currentDequeueOrder);
                if (queueIsWithinThreshold) {
                    this.orderQueue.remove(this.currentDequeueOrder);
                } else {
                    this.currentDequeueOrder = null;
                }
            }
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
        // NOT THREAD SAFE
        return this.currentDequeueOrder;
    }

    /**
     * Dispatches orders.
     */
    public void dispatchOrders() {

    }

    public void setOrderProcessor(Consumer<E> consumer) {
        this.orderProcessor = consumer;
    }

    public void setThreshold(T threshold) {
        System.out.println("THRESHOLD CALLED");
        this.lock.lock();
        try {
            this.threshold = threshold;
            this.condition.signal();
        } finally {
            this.lock.unlock();
        }
    }

    public T getThreshold() {
        return this.threshold;
    }
}
