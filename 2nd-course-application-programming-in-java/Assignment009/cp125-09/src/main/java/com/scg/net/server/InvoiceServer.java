package com.scg.net.server;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.net.cmd.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class InvoiceServer {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceServer.class);

    private ServerSocket server = null;
    private int port;
    private static int threadCount = 1;
    private static int shutdownCount = 1;
    private List<ClientAccount> clientList;
    private List<Consultant> consultantList;
    private String outputDirectoryName;

    public InvoiceServer(int port, List<ClientAccount> clientList, List<Consultant> consultantList, String outputDirectoryName){
        this.port = port;
        this.clientList = clientList;
        this.consultantList = consultantList;
        this.outputDirectoryName = outputDirectoryName;
        this.addInvoiceServerShutdownHook();
    }

    /**
     * Runs server.
     */
    public void run(){
        try{
            // Runs server on port
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            // Connects client and Server
            while(!server.isClosed()){
                CommandProcessor receiver;
                Socket client = server.accept();
                // Create receiver

                receiver = new CommandProcessor(client, clientList, consultantList, this);
                receiver.setThreadNumber(threadCount);
                receiver.setOutPutDirectoryName(this.outputDirectoryName);
                Thread receiverThread = new Thread(receiver);
                receiverThread.start();
            }
        } catch (IOException exception) {
        }

    }

    /**
     * Manages connection
     * @param client
     */
    public synchronized void serviceConnection(Socket client, CommandProcessor receiver){

        try{
            // Takes objects from clients
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
            System.out.println("Client Connected");
            while(!client.isClosed()){
                    Command currentCommand = (Command)inputStream.readObject();
                    currentCommand.setReceiver(receiver);
                    currentCommand.execute();
            }
            inputStream.close();
            client.close();
        } catch (ClassNotFoundException exception) {
            logger.error("Class not found");
        } catch (IOException exception){
            exception.printStackTrace();
        }

    }

    /**
     * Shutsdown the server.
     */
    public synchronized void shutdown(){
        try {
            this.server.close();
        }catch (IOException exception) {
            exception.printStackTrace();
        }
        System.out.println("Server Shutdown Complete");
    }

    public synchronized void incrementThreadCount() {
        threadCount++;
    }

    private void addInvoiceServerShutdownHook(){
        InvoiceServerShutdownHook hook = new InvoiceServerShutdownHook(
                this.clientList,
                this.consultantList,
                "client-consultant-data");
        Runtime.getRuntime().addShutdownHook(hook);
    }
}
