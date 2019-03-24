package com.scg.net.client;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.cmd.*;
import com.scg.util.Address;
import com.scg.util.PersonalName;
import com.scg.util.StateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
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

    /**
     * Runs the client.
     */
    public void run(){
        try {
            this.socket = new Socket(this.host, this.port);
            System.out.println("Client started");
            System.out.println("Connecting to Server ...");
            //Create output stream.
            this.output = new ObjectOutputStream(this.socket.getOutputStream());
            //Send commands with data.
            this.sendClients(this.output);
            this.sendConsultants(this.output);
            this.sendTimeCards(this.output);
            //Send command to create invoices.
            this.createInvoices(this.output);
            //Send disconnect command.
            this.sendDisconnect(this.output);
            this.sendShutdown(this.output);
            System.out.println("Sending Disconnect");
//            try {
//                this.socket.close();
//                this.output.close();
//            }catch(IOException i) {
//                logger.error(i.getMessage());
//            }
        } catch (UnknownHostException exception){
            logger.error(exception.getMessage());
        } catch (IOException ioException){
            logger.error(ioException.getMessage());
        }

        try {
            socket.close();
            output.close();
        }catch(IOException i) {
            logger.error(i.getMessage());
        }
    }

    /**
     * Sends clients.
     * @param output
     */
    private void sendClients(ObjectOutputStream output){
        ClientAccount firstClient = new ClientAccount("Delta Tau Chi",
                new PersonalName("Jon", "Belushi"),
                new Address("A", "NY", StateCode.WA, "98074"));
        ClientAccount secondClient = new ClientAccount("WSU",
                new PersonalName("Mike", "Leach"),
                new Address("Stadium Way", "Pullman", StateCode.WA, "99163"));
        ClientAccount thirdClient = new ClientAccount("SpaceX",
                new PersonalName("Elon", "Musk"),
                new Address("Somewhere in the desert", "Launchpad", StateCode.CA, "12345"));

        try {
            AbstractCommand<ClientAccount> firstClientCommand = new AddClientCommand(firstClient);
            AbstractCommand<ClientAccount> secondClientCommand = new AddClientCommand(secondClient);
            AbstractCommand<ClientAccount> thirdClientCommand = new AddClientCommand(thirdClient);
            output.writeObject(firstClientCommand);
            output.writeObject(secondClientCommand);
            output.writeObject(thirdClientCommand);
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    /**
     * Sends consultants.
     * @param output
     */
    private void sendConsultants(ObjectOutputStream output){
        Consultant firstConsultant = new Consultant(new PersonalName("Bill", "Murray"));
        Consultant secondConsultant = new Consultant(new PersonalName("Dan", "Aykroyd"));
        Consultant thirdConsultant = new Consultant(new PersonalName("Will", "Ferrel"));
        try {
            AbstractCommand<Consultant> firstConsultantCommand = new AddConsultantCommand(firstConsultant);
            AbstractCommand<Consultant> secondConsultantCommand = new AddConsultantCommand(secondConsultant);
            AbstractCommand<Consultant> thirdConsultantCommand = new AddConsultantCommand(thirdConsultant);
            output.writeObject(firstConsultantCommand);
            output.writeObject(secondConsultantCommand);
            output.writeObject(thirdConsultantCommand);
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    /**
     * Sends timecards.
     * @param output
     */
    private void sendTimeCards(ObjectOutputStream output){
        for (TimeCard timeCard : timeCardList){
            try {
                AbstractCommand<TimeCard> timeCardCommand = new AddTimeCardCommand(timeCard);
                output.writeObject(timeCardCommand);
            } catch (IOException ioException){
                ioException.printStackTrace();
            }
        }
    }

    /**
     * Creates invoices.
     * @param output
     */
    private void createInvoices(ObjectOutputStream output){
        LocalDate date = LocalDate.of(2017,03,01);
        try {
            AbstractCommand<LocalDate> createInvoicesCommand = new CreateInvoicesCommand(date);
            output.writeObject(createInvoicesCommand);
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    /**
     * Send disconnect.
     * @param output
     */
    private void sendDisconnect(ObjectOutputStream output){
        try {
            AbstractCommand<Void> disconnectCommand = new DisconnectCommand();
            output.writeObject(disconnectCommand);
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    /**
     * Send shutdown.
     * @param output
     */
    private void sendShutdown(ObjectOutputStream output){
        try {
            AbstractCommand<Void> shutdownCommand = new ShutdownCommand();
            output.writeObject(shutdownCommand);
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
