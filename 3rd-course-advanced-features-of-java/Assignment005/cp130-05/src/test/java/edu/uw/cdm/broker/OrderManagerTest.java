package edu.uw.cdm.broker;

import test.AbstractOrderManagerTest;
import edu.uw.ext.framework.broker.OrderManager;

public class OrderManagerTest extends AbstractOrderManagerTest {
    protected final OrderManager createOrderManager(final String ticker, final int initPrice) {
        return new OrderManagerCDM(ticker, initPrice);
    }
}
