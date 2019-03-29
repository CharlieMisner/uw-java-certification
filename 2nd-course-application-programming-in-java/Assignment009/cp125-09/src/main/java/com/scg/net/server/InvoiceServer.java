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

    private ServerSocket server = null;
    private ObjectInputStream inputStream = null;
    private int port = 0;
    private static int threadCount = 1;
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
                CommandProcessor receiver;
                Socket client = server.accept();
                // Create receiver

                receiver = new CommandProcessor(client, clientList, consultantList, this);
                receiver.setThreadNumber(threadCount);
                receiver.setOutPutDirectoryName(this.outputDirectoryName);
                Thread receiverThread = new Thread(receiver);
                receiverThread.start();
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
    public synchronized void serviceConnection(Socket client, CommandProcessor receiver){

        try{
            // Takes objects from clients
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
            String currentCommandType = "";
            System.out.println("Client Connected");
            while(!currentCommandType.equals("DisconnectCommand")){
                    Command currentCommand = (Command)inputStream.readObject();
                    currentCommandType = getCommandType(currentCommand);
                    currentCommand.setReceiver(receiver);
                    currentCommand.execute();
                    if(currentCommandType.equals("ShutdownCommand")){
                        break;
                    }
            }
            inputStream.close();
        } catch (ClassNotFoundException exception) {
            logger.error("Class not found");
            currentCommandType = "DisconnectCommand";
        } catch (IOException exception){
            exception.printStackTrace();
            currentCommandType = "DisconnectCommand";
        }

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
     * Shutsdown the server.
     */
    private void shutdown(){
        try {
            System.out.println("Shutting down the server");
            inputStream.close();
            this.server.close();
        }catch (IOException exception) {
            exception.printStackTrace();
        }
        System.out.println("Server Shutdown Complete.");
    }

    public void incrementThreadCount() {
        threadCount++;
    }
}
