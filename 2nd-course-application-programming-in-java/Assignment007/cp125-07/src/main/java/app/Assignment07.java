package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Invoice;
import com.scg.domain.TimeCard;
import com.scg.persistent.DbServer;
import com.scg.util.DateRange;
import com.scg.util.TimeCardListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.scg.util.ListFactory.TEST_INVOICE_MONTH;
import static com.scg.util.ListFactory.TEST_INVOICE_YEAR;

public class Assignment07 {

    /** Character encoding to use. */
    private static final String ENCODING = "ISO-8859-1";

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger(Assignment05.class);

    private static final String DB_URL = "jdbc:derby://localhost:1527/memory:scgDb";
    private static final String DB_USERNAME = "student";
    private static final String DB_PASSWORD = "student";

    /**
     * Create invoices for the clients from the timecards.
     *
     * @param accounts the accounts to create the invoices for
     * @param timeCards the time cards to create the invoices from
     *
     * @return the created invoices
     */
    private static List<Invoice> createInvoices(final List<ClientAccount> accounts,
                                                final List<TimeCard> timeCards) {
        final List<Invoice> invoices = new ArrayList<>();

        final List<TimeCard> timeCardList = TimeCardListUtil
                .getTimeCardsForDateRange(timeCards, new DateRange(TEST_INVOICE_MONTH, TEST_INVOICE_YEAR));
        for (final ClientAccount account : accounts) {
            final Invoice invoice = new Invoice(account, TEST_INVOICE_MONTH, TEST_INVOICE_YEAR);
            invoices.add(invoice);
            for (final TimeCard currentTimeCard : timeCardList) {
                invoice.extractLineItems(currentTimeCard);
            }
        }

        return invoices;
    }

    /**
     * Print the invoice to a PrintWriter.
     *
     * @param invoices the invoices to print
     * @param writer the print writer; can be System.out or a text file.
     */
    private static void printInvoices(final List<Invoice> invoices, final PrintWriter writer) {
        for (final Invoice invoice : invoices) {
            writer.println(invoice.toReportString());
        }
    }


    public static void main(String[] args){
        // Create lists to be populated by factory
        List<ClientAccount> accounts = new ArrayList<>();
        List<TimeCard> timeCards = new ArrayList<>();

        // Use the list util methods
        Console console = System.console();
        try (PrintWriter consoleWrtr = (console != null) ? console.writer()
                : new PrintWriter(new OutputStreamWriter(System.out))) {

            DbServer dataBase = new DbServer(DB_URL, DB_USERNAME, DB_PASSWORD);

            accounts = dataBase.getClients();

            // Create the Invoices
//            List<Invoice> invoices = createInvoices(accounts, timeCards);
//
//            // Print them
//            consoleWrtr.println();
//            consoleWrtr.println("==================================================================================");
//            consoleWrtr.println("=============================== I N V O I C E S ==================================");
//            consoleWrtr.println("==================================================================================");
//            consoleWrtr.println();
//            printInvoices(invoices, consoleWrtr);
//
//            // Now print it to a file
//            try (PrintWriter fileWriter = new PrintWriter("invoices.txt", ENCODING)) {
//                printInvoices(invoices, fileWriter);
//            } catch (final IOException ex) {
//                log.error("Unable to print invoice.", ex);
//            }
        }
    }



}
