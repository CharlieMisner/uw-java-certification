package edu.uw.cdm.exchange;

import edu.uw.ext.framework.exchange.ExchangeListener;
import edu.uw.ext.framework.exchange.StockExchange;
import edu.uw.ext.framework.exchange.StockQuote;
import edu.uw.ext.framework.order.Order;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.concurrent.Executors;
import static edu.uw.cdm.exchange.ProtocolConstants.*;

public class ExchangeNetworkProxy implements StockExchange {

    private String eventIpAddress;
    private int eventPort;
    private String cmdIpAddress;
    private int cmdPort;
    private NetEventProcessor netEventProcessor;

    public ExchangeNetworkProxy(String eventIpAddress, int eventPort, String cmdIpAddress, int cmdPort) {
        this.eventIpAddress = eventIpAddress;
        this.eventPort = eventPort;
        this.cmdIpAddress = cmdIpAddress;
        this.cmdPort = cmdPort;
        this.netEventProcessor = new NetEventProcessor(this.eventIpAddress, this.eventPort);
        Executors.newSingleThreadExecutor().execute(this.netEventProcessor);
    }

    private String sendTcpCommand(String command) {
        String response = "";

        try {
            Socket socket = new Socket(this.cmdIpAddress, this.cmdPort);
            InputStream inputStream = socket.getInputStream();
            Reader reader = new InputStreamReader(inputStream, ENCODING);
            BufferedReader bufferedReader = new BufferedReader(reader);

            OutputStream outputStream = socket.getOutputStream();
            Writer writer = new OutputStreamWriter(outputStream, ENCODING);
            PrintWriter printWriter = new PrintWriter(writer, true);
            printWriter.println(command);
            response = bufferedReader.readLine();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public boolean isOpen() {
        String response = sendTcpCommand(GET_STATE_CMD);
        return OPEN_STATE.equals(response);
    }

    @Override
    public String[] getTickers() {
        String response = sendTcpCommand(GET_TICKERS_CMD);
        return response.split(ELEMENT_DELIMITER);
    }

    @Override
    public Optional<StockQuote> getQuote(String ticker) {
        String command = String.join(ELEMENT_DELIMITER, GET_QUOTE_CMD, ticker);
        String response = sendTcpCommand(command);
        int price = Integer.parseInt(response);

        if (price >= 0) {
            return Optional.of(new StockQuote(ticker, price));
        } else {
            return Optional.<StockQuote>empty();
        }
    }

    @Override
    public void addExchangeListener(ExchangeListener exchangeListener) {
        this.netEventProcessor.addExchangeListener​(exchangeListener);
    }

    @Override
    public void removeExchangeListener(ExchangeListener exchangeListener) {
        this.netEventProcessor.removeExchangeListener​(exchangeListener);
    }

    @Override
    public int executeTrade(Order order) {
        String orderType = (order.isBuyOrder()) ? BUY_ORDER : SELL_ORDER;
        String command = String.join(
                ELEMENT_DELIMITER,
                EXECUTE_TRADE_CMD,
                orderType,
                order.getAccountId(),
                order.getStockTicker(),
                Integer.toString(order.getNumberOfShares())
        );
        String response = sendTcpCommand(command);
        int price = Integer.parseInt(response);

        return price;
    }
}
