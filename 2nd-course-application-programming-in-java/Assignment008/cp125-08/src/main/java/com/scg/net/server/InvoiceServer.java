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

    private Socket client = null;
    private ServerSocket server = null;
    private ObjectInputStream inputStream = null;
    private int port = 0;
    private CommandProcessor receiver;
    private List<ClientAccount> clientList;
    private List<Consultant> consultantList;
    private String outputDirectoryName;
    private String currentCommandType = "";

    public InvoiceServer(int port, List<ClientAccount> clientList, List<Consultant> consultantList, String outputDirectoryName){
        this.port = port;
        this.clientList = clientList;
        this.consultantList = consultantList;
        this.outputDirectoryName = outputDirectoryName;
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
            while(!this.currentCommandType.equals("ShutdownCommand")){
                client = server.accept();
                // Takes objects from clients
                this.inputStream = new ObjectInputStream(client.getInputStream());
                // Create receiver
                this.receiver = new CommandProcessor(client, clientList, consultantList, this);
                this.receiver.setOutPutDirectoryName(this.outputDirectoryName);
                this.serviceConnection(client);
            }
            this.shutdown();
        } catch (IOException exception) {
            logger.error(exception.getMessage());
        }

    }

    /**
     * Manages connection
     * @param client
     */
    public void serviceConnection(Socket client){
        this.currentCommandType = "";
        System.out.println("Client Connected");
        while(!currentCommandType.equals("DisconnectCommand")){
            try{
                //System.out.println(this.currentCommandType);
                Command currentCommand = (Command)this.inputStream.readObject();
                this.currentCommandType = getCommandType(currentCommand);
                currentCommand.setReceiver(this.receiver);
                currentCommand.execute();
                if(this.currentCommandType.equals("ShutdownCommand")){
                    break;
                }
            } catch (ClassNotFoundException exception) {
                logger.error("Class not found");
                this.disconnect();
                this.currentCommandType = "DisconnectCommand";
            } catch (IOException exception){
                exception.printStackTrace();
                this.disconnect();
                this.currentCommandType = "DisconnectCommand";
            }
        }
        this.disconnect();
    }

    /**
     * Gets command type.
     * @param command
     * @return
     */
    private String getCommandType(Command command){
        return command.getClass().getSimpleName();
    }

    /**
     * Disconnects the socket.
     */
    private void disconnect(){
        try {
            inputStream.close();
            client.close();
            System.out.println("Client Disconnected");
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Shutsdown the server.
     */
    private void shutdown(){
        try {
            System.out.println("Shutting down the server");
            client.close();
            inputStream.close();
            this.server.close();
        }catch (IOException exception) {
            exception.printStackTrace();
        }
        System.out.println("Server Shutdown Complete.");
    }
}
