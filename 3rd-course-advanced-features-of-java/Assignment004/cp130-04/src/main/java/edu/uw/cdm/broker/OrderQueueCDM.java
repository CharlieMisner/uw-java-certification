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

/**
 * Order queue.
 * @param <T>
 * @param <E>
 */
public class OrderQueueCDM<T,E extends Order> implements OrderQueue<T, E>, Runnable {

    private T threshold;
    private BiPredicate<T,E> filter;
    private TreeSet<E> orderQueue;
    //private E order;
    private Consumer<E> orderProcessor;
    private Thread queueThread;
    private ReentrantLock queueLock = new ReentrantLock();
    private ReentrantLock processorLock = new ReentrantLock();
    private Condition condition = this.queueLock.newCondition();

    public OrderQueueCDM(T threshold, BiPredicate<T, E> filter) {
        this.threshold = threshold;
        this.filter = filter;
        this.orderQueue = new TreeSet<>();
    }

    public OrderQueueCDM(T threshold, BiPredicate<T, E> filter, Comparator<E> comparator) {
        this.threshold = threshold;
        this.filter = filter;
        this.orderQueue = new TreeSet<>(comparator);
        this.queueThread = new Thread(this);
        this.queueThread.setDaemon(true);
        this.queueThread.start();
    }

    public void run(){
        while (true){
            E order;
            this.queueLock.lock();
            try {
                while ((order = this.dequeue()) == null){
                    try{
                        this.condition.await();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            } finally {
                this.queueLock.unlock();
            }

            this.processorLock.lock();
            try{
                if(this.orderProcessor != null){
                    this.orderProcessor.accept(order);
                }
            } finally {
                this.processorLock.unlock();
            }
        }
    }

    /**
     * Adds orders to queue.
     * @param order
     */
    public void enqueue(E order) {
        this.queueLock.lock();
        try {
            if(orderQueue.add(order)){
                dispatchOrders();
            }
        } finally {
            this.queueLock.unlock();
        }
    }

    public E dequeue() {
        E order = null;
        this.queueLock.lock();

        try {
            if(!this.orderQueue.isEmpty()) {
                order = orderQueue.first();
                Boolean queueIsWithinThreshold = this.filter.test(threshold, order);
                if (queueIsWithinThreshold) {
                    this.orderQueue.remove(order);
                } else {
                    order = null;
                }
            }
        } finally {
            this.queueLock.unlock();
        }

        return order;
    }

    /**
     * Dispatches orders.
     */
    public void dispatchOrders() {

        this.queueLock.lock();
        try{
            this.condition.signal();
        } finally {
            this.queueLock.unlock();
        }

//        this.dequeue();
//        while (this.currentDequeueOrder != null){
//            orderProcessor.accept(this.currentDequeueOrder);
//            this.dequeue();
//        }
    }

    public void setOrderProcessor(Consumer<E> consumer) {
        this.processorLock.lock();
        try {
            this.orderProcessor = consumer;
        } finally {
            this.processorLock.unlock();
        }
    }

    @Override
    public void setThreshold(T threshold) {
        this.threshold = threshold;
        dispatchOrders();
    }

    public T getThreshold() {
        return this.threshold;
    }
}