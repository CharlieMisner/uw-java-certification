package edu.uw.cdm.exchange;

import edu.uw.ext.framework.exchange.StockExchange;

import java.net.Socket;

public class CommandListener implements Runnable {

    private int port;
    private StockExchange exchange;

    public CommandListener(int port, StockExchange exchange) {
        this.port = port;
        this.exchange = exchange;
    }

    @Override
    public void run() {

    }

    public void terminate(){

    }
}
