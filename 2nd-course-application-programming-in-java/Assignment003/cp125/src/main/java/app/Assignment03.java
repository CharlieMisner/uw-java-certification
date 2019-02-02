package app;

import static com.scg.util.ListFactory.TEST_INVOICE_MONTH;
import static com.scg.util.ListFactory.TEST_INVOICE_YEAR;

import java.io.Console;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.Invoice;
import com.scg.domain.TimeCard;
import com.scg.util.ListFactory;

/**
 * Assignment 03 application.
 */
public final class Assignment03 {
	/** Character encoding to use. */
	private static final String ENCODING = "ISO-8859-1";

	/** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger(Assignment03.class);

    /**
     * Prevent instantiation.
     */
    private Assignment03() {
    }

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
        final List<Invoice> invoices = new ArrayList<>(accounts.size());
        for (final ClientAccount account : accounts) {
            final Invoice invoice = new Invoice(account, TEST_INVOICE_MONTH, TEST_INVOICE_YEAR);
            invoices.add(invoice);
            for (final TimeCard currentTimeCard : timeCards) {
                invoice.extractLineItems(currentTimeCard);
            }
        }

        return invoices;
    }

    /**
     * Print the invoice to a PrintStream.
     *
     * @param invoices the invoices to print
     * @param out the print stream; can be System.out or a text file.
     * @throws UnsupportedEncodingException
     */
    private static void printInvoices(final List<Invoice> invoices, final OutputStream out)
    	throws UnsupportedEncodingException {
    	    try (PrintStream writer = new PrintStream(out, true, ENCODING)) {
            for (final Invoice invoice : invoices) {
                writer.println(invoice.toReportString());
            }
        }
    }

    /**
     * Confirm the invoice totals.
     *
     * @param exHours the expected hours
     * @param exCharges the expected charges
     * @param invoice the invoice
     */
    private static void confirmTotals(final int exHours, final int exCharges,
                                      final Invoice invoice) {
        final int invHours = invoice.getTotalHours();
        final int invCharges = invoice.getTotalCharges();
        if (invHours != exHours) {
            log.error(String.format("Invoice hours for %s are incorrect, expected %d but was %d",
                                    invoice.getClientAccount().getName(),
                                    exHours, invHours));
        }
        if (invCharges != exCharges) {
            log.error(String.format("Invoice charges for %s are incorrect, expected %d but was %d",
                                    invoice.getClientAccount().getName(),
                                    exCharges, invCharges));
        }
    }

    /**
     * The application method.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        // Create lists to be populated by factory
        final List<ClientAccount> accounts = new ArrayList<>();
        final List<Consultant> consultants = new ArrayList<>();
        final List<TimeCard> timeCards = new ArrayList<>();
        ListFactory.populateLists(accounts, consultants, timeCards);
        // Print them
        ListFactory.printTimeCards(timeCards);

        // Create the Invoices
        final List<Invoice> invoices = createInvoices(accounts, timeCards);

        // Print them
        PrintStream console = System.out;
        console.printf("%n==================================================================================%n");
        console.printf("=============================== I N V O I C E S ==================================%n");
        console.printf("==================================================================================%n%n");
        Invoice invoice = invoices.get(0);
        confirmTotals(16, 2400, invoice);
        console.printf("%s%n", invoice.toReportString());
        invoice = invoices.get(1);
        confirmTotals(108, 19400, invoice);
        console.printf("%s%n", invoice.toReportString());

        // Now print it to a file
        try (FileOutputStream out = new FileOutputStream("invoices.txt")) {
            printInvoices(invoices, out);
        } catch (final IOException ex) {
            log.error("Unable to print invoice.", ex);
        }
    }
}
