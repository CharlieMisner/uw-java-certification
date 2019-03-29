package com.scg.net.server;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.Invoice;
import com.scg.domain.TimeCard;
import com.scg.net.cmd.*;
import com.scg.util.DateRange;
import com.scg.util.TimeCardListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.scg.util.ListFactory.TEST_INVOICE_YEAR;

/**
 * @author Charlie Misner
 */
public class CommandProcessor implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(CommandProcessor.class);
    private List<ClientAccount> clientList;
    private List<Consultant> consultantList;
    private List<TimeCard> timeCardList = new ArrayList<>();
    private String outPutDirectoryName;
    private Socket client;
    private InvoiceServer server;
    private int threadNumber;

    CommandProcessor(Socket connection, List<ClientAccount> clientList, List<Consultant> consultantList, InvoiceServer server){
        this.clientList = clientList;
        this.consultantList = consultantList;
        this.server = server;
        this.client = connection;
    }

    /**
     * Executes commands.
     */
    public void run(){
        this.server.serviceConnection(this.client, this);
    }

    /**
     * Executes add client command
     * @param command
     */
    public void execute(AddClientCommand command){
        System.out.println(command.getTarget().getClass().getSimpleName()+" received on Thread " + this.threadNumber);
        clientList.add(command.getTarget());
    }

    /**
     * Executes add client command
     * @param command
     */
    public void execute(AddConsultantCommand command){
        System.out.println(command.getTarget().getClass().getSimpleName()+" received on Thread " + this.threadNumber);
        consultantList.add(command.getTarget());
    }

    /**
     * Executes add timecard command.
     * @param command
     */
    public void execute(AddTimeCardCommand command){
        System.out.println(command.getTarget().getClass().getSimpleName()+" received on Thread " + this.threadNumber);
        timeCardList.add(command.getTarget());
    }

    /**
     * Executes create invoices command.
     * @param command
     */
    public void execute(CreateInvoicesCommand command){
        System.out.println(command.getClass().getSimpleName()+" received on Thread " + this.threadNumber);
        this.createInvoices(clientList, timeCardList, command.getTarget());
    }

    /**
     * Executes disconnect command.
     * @param command
     */
    public void execute(DisconnectCommand command){
        System.out.println(command.getClass().getSimpleName()+" received ");
    }

    /**
     * Executes shutdown command.
     * @param command
     */
    public void execute(ShutdownCommand command){
        System.out.println(command.getClass().getSimpleName()+" received on Thread " + this.threadNumber);
    }

    public void setOutPutDirectoryName(String outPutDirectoryName){
        this.outPutDirectoryName = outPutDirectoryName + this.threadNumber;
    }

    /**
     * Creates the invoices.
     * @param accounts
     * @param timeCards
     * @param date
     */
    private void createInvoices(final List<ClientAccount> accounts,
                                                final List<TimeCard> timeCards, LocalDate date) {
        final List<Invoice> invoices = new ArrayList<>();

        final List<TimeCard> timeCardList = TimeCardListUtil
                .getTimeCardsForDateRange(timeCards, new DateRange(date.getMonth(), TEST_INVOICE_YEAR));
        for (final ClientAccount account : accounts) {
            final Invoice invoice = new Invoice(account, date.getMonth(), TEST_INVOICE_YEAR);
            invoices.add(invoice);
            for (final TimeCard currentTimeCard : timeCardList) {
                invoice.extractLineItems(currentTimeCard);
            }
        }

        //Create subdirectory.
        File invoicesDirectory = new File(String.format("./target/%s", this.outPutDirectoryName));
        invoicesDirectory.mkdir();

        for (final Invoice invoice : invoices) {
            String invoiceFileName = String.format(
                    "./target/%s/%s-%s-Invoice.txt",
                    this.outPutDirectoryName,
                    invoice.getClientAccount().getName().replaceAll("\\s+", ""),
                    date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            try (PrintWriter fileWriter = new PrintWriter(invoiceFileName, "ISO-8859-1")) {
                fileWriter.println(invoice.toReportString());
                System.out.println("Invoice printed on Thread " + this.threadNumber);
            } catch (final IOException ex) {
                logger.error("Unable to print invoice.", ex);
            }
        }
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
        this.server.incrementThreadCount();
    }
}
