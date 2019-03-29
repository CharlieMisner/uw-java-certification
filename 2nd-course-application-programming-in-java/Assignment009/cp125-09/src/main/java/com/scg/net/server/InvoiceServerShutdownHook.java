package com.scg.net.server;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Charlie Misner
 */
public class InvoiceServerShutdownHook extends Thread{

    List<ClientAccount> clientList;
    List<Consultant> consultantList;
    String outputDirectoryName;
    private static final Logger logger = LoggerFactory.getLogger(InvoiceServerShutdownHook.class);

    public InvoiceServerShutdownHook(List<ClientAccount> clientList, List<Consultant> consultantList, String outputDirectoryName){
        this.clientList = clientList;
        this.consultantList = consultantList;
        this.outputDirectoryName = outputDirectoryName;
    }

    public void run(){
        System.out.println("Executing shutdown hook");
        String clientListString = this.createClientListReportString(this.clientList);
        String consultantListString = this.createConsultantListReportString(this.consultantList);
        System.out.println("Client and consultant files saved");
        this.createListFiles(clientListString, consultantListString);
    }

    /**
     * Creates string of clients
     * @param clientList
     * @return
     */
    private String createClientListReportString(List<ClientAccount> clientList){
        StringBuilder string = new StringBuilder();
        for(ClientAccount client: clientList){
            string.append(String.format("%s %n", client.getName()));
        }
        return string.toString();
    }

    /**
     * Creates string of consultants
     * @param consultantList
     * @return
     */
    private String createConsultantListReportString(List<Consultant> consultantList){
        StringBuilder string = new StringBuilder();
        for(Consultant consultant: consultantList){
            string.append(String.format("%s %n", consultant.toString()));
        }
        return string.toString();
    }

    /**
     * Creates files.
     * @param clientListString
     * @param consultantListString
     */
    private void createListFiles(String clientListString, String consultantListString){
        File invoicesDirectory = new File(String.format("./target/%s", this.outputDirectoryName));
        invoicesDirectory.mkdir();

        String clientsFileName = String.format(
                "./target/%s/clients.txt",
                this.outputDirectoryName);
        try (PrintWriter fileWriter = new PrintWriter(clientsFileName, "ISO-8859-1")) {
            fileWriter.println(clientListString);
        } catch (final IOException ex) {
            logger.error("Unable to print clients.", ex);
        }

        String consultantsFileName = String.format(
                "./target/%s/consultants.txt",
                this.outputDirectoryName);
        try (PrintWriter fileWriter = new PrintWriter(consultantsFileName, "ISO-8859-1")) {
            fileWriter.println(consultantListString);
        } catch (final IOException ex) {
            logger.error("Unable to print consultants.", ex);
        }

    }
}
