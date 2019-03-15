package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.persistent.DbServer;
import com.scg.util.ListFactory;
import com.scg.util.TimeCardListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InitDb {

    private static final String DB_URL = "jdbc:derby://localhost:1527/memory:scgDb";
    private static final String DB_USERNAME = "student";
    private static final String DB_PASSWORD = "student";

    public static void main(String[] args){
        Logger logger = LoggerFactory.getLogger(InitDb.class);

        // Create lists to be populated by factory
        final List<ClientAccount> accounts = new ArrayList<>();
        final List<Consultant> consultants = new ArrayList<>();
        final List<TimeCard> timeCards = new ArrayList<>();
        ListFactory.populateLists(accounts, consultants, timeCards);
        // Print them
        //ListFactory.printTimeCards(timeCards);

        // Use the list util methods
        Console console = System.console();
        try (PrintWriter consoleWrtr = (console != null) ? console.writer()
                : new PrintWriter(new OutputStreamWriter(System.out))) {

            Consultant carl = consultants.get(0);
            final List<TimeCard> selected = TimeCardListUtil.getTimeCardsForConsultant(timeCards, carl);
            final int count = selected.size();
            //consoleWrtr.printf("Counted %d time cards for %s%n",count, carl);
            if (count != 2) {
                //logger.error(String.format("Bad time card count for %s", carl));
            }

            TimeCardListUtil.sortByStartDate(timeCards);
            //consoleWrtr.println("Time cards by date:");
            for (TimeCard tc : timeCards) {
                //consoleWrtr.printf("  %s, %s%n", tc.getWeekStartingDay(), tc.getConsultant());
            }

            TimeCardListUtil.sortByConsultantName(timeCards);
            //consoleWrtr.println("Time cards by consultant:");
            for (TimeCard tc : timeCards) {
                //consoleWrtr.printf("  %s, %s%n", tc.getWeekStartingDay(), tc.getConsultant());
            }

            accounts.clear();
            consultants.clear();
            timeCards.clear();

            ListFactory.populateLists(accounts, consultants, timeCards);

            DbServer dataBase = new DbServer(DB_URL, DB_USERNAME, DB_PASSWORD);

            for (ClientAccount client: accounts) {
                dataBase.addClient(client);
            }

            for (Consultant consultant: consultants) {
                dataBase.addConsultant(consultant);
            }

            for (TimeCard timeCard : timeCards) {
                dataBase.addTimeCard(timeCard);
            }
        }
    }


}
