package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
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

    private static final DbServer DATABASE = new DbServer(DB_URL, DB_USERNAME, DB_PASSWORD);;

    /**
     * Create invoices for the clients from the timecards.
     *
     * @return the created invoices
     */
    private static List<Invoice> createInvoices() {
        List<ClientAccount> accounts = DATABASE.getClients();
        List<TimeCard> timeCards = DATABASE.getTimeCards();
        final List<Invoice> invoices = new ArrayList<>();

        for (final ClientAccount account : accounts) {
            final Invoice invoice = DATABASE.getInvoice(account, TEST_INVOICE_MONTH, TEST_INVOICE_YEAR);
            invoices.add(invoice);
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

        // Use the list util methods
        Console console = System.console();
        try (PrintWriter consoleWrtr = (console != null) ? console.writer()
                : new PrintWriter(new OutputStreamWriter(System.out))) {

            List<Invoice> invoices = createInvoices();

            // Print them
            consoleWrtr.println();
            consoleWrtr.println("==================================================================================");
            consoleWrtr.println("=============================== I N V O I C E S ==================================");
            consoleWrtr.println("==================================================================================");
            consoleWrtr.println();
            printInvoices(invoices, consoleWrtr);

            // Now print it to a file
            try (PrintWriter fileWriter = new PrintWriter("invoices.txt", ENCODING)) {
                printInvoices(invoices, fileWriter);
            } catch (final IOException ex) {
                log.error("Unable to print invoice.", ex);
            }
        }
    }



}
