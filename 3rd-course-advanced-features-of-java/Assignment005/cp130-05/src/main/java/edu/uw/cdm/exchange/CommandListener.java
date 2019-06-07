package edu.uw.cdm.exchange;

import edu.uw.ext.framework.exchange.StockExchange;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CommandListener implements Runnable {

    private int port;
    private StockExchange exchange;
    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private volatile boolean listening = true;

    public CommandListener(int port, StockExchange exchange) {
        this.port = port;
        this.exchange = exchange;
    }

    @Override
    public void run() {
        try(ServerSocket localSocket = new ServerSocket(this.port)) {
            this.serverSocket = localSocket;
            while (listening) {
                Socket socket = null;
                try {
                    socket = this.serverSocket.accept();
                } catch (SocketException exception) {
                    exception.printStackTrace();
                }

                if (socket == null) {
                    continue;
                }

                this.executorService.execute(new CommandHandler(socket, exchange));
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            this.terminate();
        }
    }

    public void terminate(){
        this.listening = false;

        try {
            if(this.serverSocket != null && !this.serverSocket.isClosed()){
                this.serverSocket.close();
            }
            this.serverSocket = null;
            if(!this.executorService.isShutdown()){
                this.executorService.shutdown();
                this.executorService.awaitTermination(3, TimeUnit.SECONDS);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (InterruptedException iException) {
            iException.printStackTrace();
        }

    }
}
