package edu.uw.cdm.exchange;

import edu.uw.ext.framework.exchange.ExchangeAdapter;
import edu.uw.ext.framework.exchange.ExchangeEvent;
import edu.uw.ext.framework.exchange.StockExchange;

import static edu.uw.cdm.exchange.ProtocolConstants.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;

// 1 Exchange Network Adapter
// 2 Net Event Processor
// 3 Command Handler
// 4 Command Listener
// 5 Proxy

public class ExchangeNetworkAdapter implements ExchangeAdapter {

    private StockExchange exchange;
    private String multicastIP;
    private int multicastPort;
    private int commandPort;
    private CommandListener commandListener;
    private static int timeToLive = 2;
    private MulticastSocket multicastSocket;
    private DatagramPacket datagramPacket;

    public ExchangeNetworkAdapter(StockExchange exchange, String multicastIP, int multicastPort, int commandPort) throws UnknownHostException {
        this.exchange = exchange;
        this.multicastIP = multicastIP;
        this.multicastPort = multicastPort;
        this.commandPort = commandPort;

        InetAddress multicastGroup = InetAddress.getByName(this.multicastIP);
        byte[] buffer = {};
        this.datagramPacket = new DatagramPacket(buffer, 0, multicastGroup, this.multicastPort);
        try {
            this.multicastSocket = new MulticastSocket();
            this.multicastSocket.setTimeToLive(this.timeToLive);
        } catch (IOException exception){
            exception.printStackTrace();
        }
        this.commandListener = new CommandListener(this.commandPort, this.exchange);
        Executors.newSingleThreadExecutor().execute(this.commandListener);

        this.exchange.addExchangeListener(this);
    }

    @Override
    public synchronized void exchangeOpened(ExchangeEvent exchangeEvent) {
        String message = OPEN_EVNT;
        try {
            byte[] buffer = message.getBytes(ENCODING);
            this.datagramPacket.setData(buffer);
            this.datagramPacket.setLength(buffer.length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void exchangeClosed(ExchangeEvent exchangeEvent) {
        String message = CLOSED_EVNT;
        try {
            byte[] buffer = message.getBytes(ENCODING);
            this.datagramPacket.setData(buffer);
            this.datagramPacket.setLength(buffer.length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void priceChanged(ExchangeEvent exchangeEvent) {
        String symbol = exchangeEvent.getTicker();
        int price = exchangeEvent.getPrice();
        String message = String.join(ELEMENT_DELIMITER, PRICE_CHANGE_EVNT, symbol, Integer.toString(price));
        try {
            byte[] buffer = message.getBytes(ENCODING);
            this.datagramPacket.setData(buffer);
            this.datagramPacket.setLength(buffer.length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        this.exchange.removeExchangeListener(this);
        this.commandListener.terminate();
        this.multicastSocket.close();
    }
}
