package edu.uw.cdm.exchange;
import edu.uw.ext.framework.exchange.ExchangeEvent;
import edu.uw.ext.framework.exchange.ExchangeListener;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import javax.swing.event.EventListenerList;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import static edu.uw.cdm.exchange.ProtocolConstants.*;

public class NetEventProcessor implements Runnable{

    private static Logger logger = LoggerFactory.getLogger(NetEventProcessor.class);
    private String eventAddress ;
    private int eventPort;
    private static final int BUFFER_LENGTH = 2000;
    private EventListenerList eventListenerList = new EventListenerList();

    public NetEventProcessor(String eventAddress, int eventPort) {
        this.eventAddress = eventAddress;
        this.eventPort = eventPort;
    }

    public void run(){
        try{
            MulticastSocket socket = new MulticastSocket(eventPort);
            InetAddress eventGroup = InetAddress.getByName(eventAddress);
            socket.joinGroup(eventGroup);
            byte[] buffer = new byte[BUFFER_LENGTH];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

            while (!socket.isClosed()){
                socket.receive(datagramPacket);
                String message = new String(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength(), ENCODING);
                String[] eventElementsArray = message.split(ELEMENT_DELIMITER);
                String eventType = eventElementsArray[EVENT_ELEMENT];

                switch (eventType) {
                    case OPEN_EVNT:
                        fireEventFromListener(ExchangeEvent.newOpenedEvent(this));
                        break;
                    case CLOSED_EVNT:
                        fireEventFromListener(ExchangeEvent.newClosedEvent(this));
                        break;
                    case PRICE_CHANGE_EVNT:
                        String ticker = eventElementsArray[PRICE_CHANGE_EVNT_TICKER_ELEMENT];
                        int price = Integer.parseInt(eventElementsArray[PRICE_CHANGE_EVNT_TICKER_ELEMENT]);
                        fireEventFromListener(ExchangeEvent.newPriceChangedEvent(this, ticker, price));
                    default:
                        logger.warn("Error in NetEventProcessor run");
                        break;
                }

            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void addExchangeListener​(ExchangeListener listener){
        eventListenerList.add(ExchangeListener.class, listener);
    }

    public void removeExchangeListener​(ExchangeListener listener){
        eventListenerList.remove(ExchangeListener.class, listener);
    }

    private void fireEventFromListener(ExchangeEvent event) {
        ExchangeListener[] listeners;
        listeners = eventListenerList.getListeners(ExchangeListener.class);

        for (ExchangeListener listener: listeners) {
            switch (event.getEventType()){
                case OPENED:
                    listener.exchangeOpened(event);
                    break;
                case CLOSED:
                    listener.exchangeClosed(event);
                    break;
                case PRICE_CHANGED:
                    listener.priceChanged(event);
                    break;
                default:
                    logger.warn("Error in Net event processor fire from listeners");
            }
        }
    }
}
