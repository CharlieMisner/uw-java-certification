package com.scg.net.server;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.net.cmd.AbstractCommand;
import com.scg.net.cmd.AddClientCommand;
import com.scg.net.cmd.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class InvoiceServer {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceServer.class);
    private final String SHUTDOWN_COMMAND = "ShutdownCommand";

    private Socket client = null;
    private ServerSocket server = null;
    private ObjectInputStream inputStream = null;
    private int port = 0;
    private CommandProcessor receiver;
    private List<ClientAccount> clientList;
    private List<Consultant> consultantList;
    private String outputDirectoryName;

    public InvoiceServer(int port, List<ClientAccount> clientList, List<Consultant> consultantList, String outputDirectoryName){
        this.port = port;
        this.clientList = clientList;
        this.consultantList = consultantList;
        this.outputDirectoryName = outputDirectoryName;
    }

    public void run(){
        try{
            // Runs server on port
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            // Connects client and Server
            client = server.accept();
            // Takes objects from clients
            this.inputStream = new ObjectInputStream(client.getInputStream());
            // Create receiver
            this.receiver = new CommandProcessor(client, clientList, consultantList, this);
            this.serviceConnection(client);

        } catch (IOException exception) {
            logger.error(exception.getMessage());
        }

    }

    public void serviceConnection(Socket client){
        boolean isShutdown = false;
        System.out.println("Client Connected");
        while(!isShutdown){
            try{
                Command currentCommand = (Command)this.inputStream.readObject();
                currentCommand.setReceiver(this.receiver);
                currentCommand.execute();

                isShutdown = true;
            } catch (ClassNotFoundException exception) {
                exception.printStackTrace();
                isShutdown = true;
            }catch (IOException exception) {
                exception.printStackTrace();
                isShutdown = true;
            }
        }
    }

    public void shutDown(){

    }
}
