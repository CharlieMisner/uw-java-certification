package edu.uw.cdm.exchange;

import edu.uw.ext.framework.exchange.StockExchange;
import edu.uw.ext.framework.exchange.StockQuote;
import edu.uw.ext.framework.order.MarketBuyOrder;
import edu.uw.ext.framework.order.MarketSellOrder;
import edu.uw.ext.framework.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

import static edu.uw.cdm.exchange.ProtocolConstants.*;

public class CommandHandler implements Runnable{
    Socket socket;
    private static Logger logger = LoggerFactory.getLogger(CommandHandler.class);
    StockExchange realExchange;

    public CommandHandler(Socket socket, StockExchange realExchange) {
        this.socket = socket;
        this.realExchange = realExchange;
    }

    public void run(){
        try {
            Socket localSocket = this.socket;
            InputStream inputStream = localSocket.getInputStream();
            Reader reader = new InputStreamReader(inputStream, ENCODING);
            BufferedReader bufferedReader = new BufferedReader(reader);

            OutputStream outputStream = localSocket.getOutputStream();
            Writer writer = new OutputStreamWriter(outputStream, ENCODING);
            PrintWriter printWriter = new PrintWriter(writer, true);

            String message = bufferedReader.readLine();
            if(message == null) {
                message = "";
            }

            String[] elementArray = message.split(ELEMENT_DELIMITER);
            String command = elementArray[CMD_ELEMENT];

            switch (command) {
                case GET_STATE_CMD:
                    getState(printWriter);
                    break;

                case GET_TICKERS_CMD:
                    getTickers(printWriter);
                    break;

                case GET_QUOTE_CMD:
                    getQuote(printWriter, elementArray);
                    break;

                case EXECUTE_TRADE_CMD:
                    executeTrade(elementArray, printWriter);
                    break;

                default:
                    logger.error("Error in Command Handler");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getState(PrintWriter printWriter){
        String response  = this.realExchange.isOpen() ? OPEN_STATE : CLOSED_STATE;
        printWriter.println(response);
    }

    private void getTickers(PrintWriter printWriter){
        String[] tickerArray = this.realExchange.getTickers();
        String tickerString = String.join(ELEMENT_DELIMITER, tickerArray);
        printWriter.println(tickerString);
    }

    private void getQuote(PrintWriter printWriter, String[] elements) {
        String ticker = elements[QUOTE_CMD_TICKER_ELEMENT];
        Optional<StockQuote> quote = this.realExchange.getQuote(ticker);
        if(!quote.isPresent()){
            printWriter.println(quote.get().getPrice());
        } else {
            printWriter.println(INVALID_STOCK);
        }
    }

    private void executeTrade (String[] elements, PrintWriter printWriter){
        if (this.realExchange.isOpen()) {
            String orderType = elements[EXECUTE_TRADE_CMD_TYPE_ELEMENT];
            String accountId = elements[EXECUTE_TRADE_CMD_ACCOUNT_ELEMENT];
            String ticker = elements[EXECUTE_TRADE_CMD_TICKER_ELEMENT];
            String shares = elements[EXECUTE_TRADE_CMD_SHARES_ELEMENT];
            int quantity = Integer.parseInt(shares);

            Order order;

            if (BUY_ORDER.equals(orderType)) {
                order = new MarketBuyOrder(accountId, quantity, ticker);
            } else {
                order = new MarketSellOrder(accountId, quantity, ticker);
            }

            int price = this.realExchange.executeTrade(order);
            printWriter.println(price);
        } else {
            printWriter.println(0);
        }
    }
}
