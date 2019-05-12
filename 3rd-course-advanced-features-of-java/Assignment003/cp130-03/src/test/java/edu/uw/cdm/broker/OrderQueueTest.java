package edu.uw.cdm.broker;

import test.AbstractOrderQueueTest;
import edu.uw.ext.framework.broker.OrderQueue;

import java.util.Comparator;
import java.util.function.BiPredicate;

import edu.uw.ext.framework.order.Order;
import edu.uw.ext.framework.order.StopBuyOrder;
import edu.uw.ext.framework.order.StopSellOrder;
/******************************************************************************  Replace these imports with the import of your implementing classes.       ******************************************************************************/

/***  Concrete subclass of AbstractQueueTest, provides implementations of the*  createStopBuyOrderQueue, createStopSellOrderQueue and createAnyOrderQueue*  methods which create instances of "my" OrderQueue implementation class, using*  "my" Comparator implementations.*/
public class OrderQueueTest extends AbstractOrderQueueTest {
    /***  Creates an instance of "my" OrderQueue implementation class, using*  an instance of "my" implementation of Comparator that is intended to*  order StopBuyOrders.**  @param filter the OrderDispatch filter to be used**  @return a new OrderQueue instance*/
    @Override
    protected final OrderQueue<Integer, StopBuyOrder> createStopBuyOrderQueue(final BiPredicate<Integer, StopBuyOrder> filter) {/**********************************************************************  This needs to be an instance of your OrderQueue and Comparator.   **********************************************************************/
        final Comparator<StopBuyOrder> ascending = this.getStopBuyOrderComparatorAscending();
        return new OrderQueueCDM<>(0, filter, ascending);
    }

    /***  Creates an instance of "my" OrderQueue implementation class, using*  an instance of "my" implementation of Comparator that is intended to*  order StopSellOrders.**  @param filter the OrderDispatch filter to be used**  @return a new OrderQueue instance*/
    @Override
    protected final OrderQueue<Integer, StopSellOrder> createStopSellOrderQueue(final BiPredicate<Integer, StopSellOrder> filter) {/**********************************************************************  This needs to be an instance of your OrderQueue and Comparator.   **********************************************************************/
        final Comparator<StopSellOrder> descending = this.getStopSellOrderComparatorDescending();
        return new OrderQueueCDM<>(0, filter, descending);
    }

    /***  Creates an instance of "my" OrderQueue implementation class, the queue*  will order the Orders according to their natural ordering.**  @param filter the OrderDispatch filter to be used**  @return a new OrderQueue instance*/
    @Override
    protected final OrderQueue<Boolean, Order> createAnyOrderQueue(final BiPredicate<Boolean, Order> filter) {/**********************************************************************  This needs to be an instance of your OrderQueue.                  **********************************************************************/
        return new OrderQueueCDM<Boolean, Order>(true, filter);
    }

    /**
     * Method copied from OrderManagerCDM
     * @return
     */
    private Comparator<StopBuyOrder> getStopBuyOrderComparatorAscending(){
        return Comparator.comparing(StopBuyOrder::getPrice).thenComparing(StopBuyOrder::compareTo);
    }


    private Comparator<StopSellOrder> getStopSellOrderComparatorDescending(){
        return Comparator.comparing(StopSellOrder::getPrice).reversed().thenComparing(StopSellOrder::compareTo);
    }
}