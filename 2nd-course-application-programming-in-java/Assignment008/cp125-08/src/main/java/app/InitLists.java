package app;

import static com.scg.util.ListFactory.TEST_INVOICE_MONTH;
import static com.scg.util.ListFactory.TEST_INVOICE_YEAR;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.Invoice;
import com.scg.domain.TimeCard;
import com.scg.util.DateRange;
import com.scg.util.ListFactory;
import com.scg.util.TimeCardListUtil;

public class InitLists {
    /** Character encoding to use. */
    private static final String ENCODING = "ISO-8859-1";

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger(InitLists.class);

    /**
     * Prevent instantiation.
     */
    private InitLists() {
    }

    /**
     * The application method.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) throws Exception {
        // Create lists to be populated by factory
        final List<ClientAccount> accounts = new ArrayList<>();
        final List<Consultant> consultants = new ArrayList<>();
        final List<TimeCard> timeCards = new ArrayList<>();
        ListFactory.populateLists(accounts, consultants, timeCards);
        // Print them
        ListFactory.printTimeCards(timeCards);

        // Use the list util methods
        Console console = System.console();
        try (PrintWriter consoleWrtr = (console != null) ? console.writer()
                : new PrintWriter(new OutputStreamWriter(System.out))) {

            Consultant carl = consultants.get(0);
            final List<TimeCard> selected = TimeCardListUtil.getTimeCardsForConsultant(timeCards, carl);
            final int count = selected.size();
            consoleWrtr.printf("Counted %d time cards for %s%n",count, carl);
            if (count != 2) {
                log.error(String.format("Bad time card count for %s", carl));
            }

            TimeCardListUtil.sortByStartDate(timeCards);
            consoleWrtr.println("Time cards by date:");
            for (TimeCard tc : timeCards) {
                consoleWrtr.printf("  %s, %s%n", tc.getWeekStartingDay(), tc.getConsultant());
            }

            TimeCardListUtil.sortByConsultantName(timeCards);
            consoleWrtr.println("Time cards by consultant:");
            for (TimeCard tc : timeCards) {
                consoleWrtr.printf("  %s, %s%n", tc.getWeekStartingDay(), tc.getConsultant());
            }

            accounts.clear();
            consultants.clear();
            timeCards.clear();

            ListFactory.populateLists(accounts, consultants, timeCards);

            ObjectOutputStream clientListOut = new ObjectOutputStream(new FileOutputStream("ClientList.ser"));
            ObjectOutputStream timeCardListOut = new ObjectOutputStream(new FileOutputStream("TimeCardList.ser"));

            serializeList(clientListOut, accounts);
            System.out.printf("%n Successfully Serialized Client List %n");
            serializeList(timeCardListOut, timeCards);
            System.out.printf("%n Successfully Serialized Time Card List %n %n");
        }
    }

    private static void serializeList(ObjectOutputStream outputStream, List list){
        try {
            outputStream.writeObject(list);
            outputStream.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
