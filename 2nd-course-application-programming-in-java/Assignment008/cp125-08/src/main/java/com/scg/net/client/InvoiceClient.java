package com.scg.net.client;

import com.scg.domain.ClientAccount;
import com.scg.domain.TimeCard;
import com.scg.net.cmd.AbstractCommand;
import com.scg.net.cmd.AddClientCommand;
import com.scg.util.Address;
import com.scg.util.PersonalName;
import com.scg.util.StateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class InvoiceClient {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceClient.class);

    private Socket socket = null;
    private ObjectOutputStream output = null;
    private String host;
    private int port;
    private List<TimeCard> timeCardList;

    public InvoiceClient(String host, int port, List<TimeCard> timeCardList) {
        this.host = host;
        this.port = port;
        this.timeCardList = timeCardList;
    }

    public void run(){
        try {
            this.socket = new Socket(this.host, this.port);
            System.out.println("Client started");
            System.out.println("Connecting to Server ...");
            this.output = new ObjectOutputStream(this.socket.getOutputStream());
            this.sendClients(this.output);
        } catch (UnknownHostException exception){
            logger.error(exception.getMessage());
        } catch (IOException ioException){
            logger.error(ioException.getMessage());
        }
    }

    private void sendClients(ObjectOutputStream output){
        ClientAccount firstClient = new ClientAccount("Expeditors",
                new PersonalName("Joe", "Shmoe"),
                new Address("A", "NY", StateCode.WA, "98074"));
        try {
            AbstractCommand<ClientAccount> firstClientCommand = new AddClientCommand(firstClient);
            output.writeObject(firstClientCommand);
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
