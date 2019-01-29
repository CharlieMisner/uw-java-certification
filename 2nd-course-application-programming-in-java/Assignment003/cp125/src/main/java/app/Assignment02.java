package app;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.Month;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.ConsultantTime;
import com.scg.domain.NonBillableAccount;
import com.scg.domain.Skill;
import com.scg.domain.TimeCard;
import com.scg.util.PersonalName;

/**
 * Assignment 02 application.
 */
public final class Assignment02 {

    /** Number of hours in a standard working day. */
    private static final int STD_WORK_DAY = 8;

    /** Some overtime hours. */
    private static final int OT_HOURS = 4;

    /** The start month for our test cases. */
    private static final Month TEST_MONTH = Month.FEBRUARY;

    /** The first Monday of the test month. */
    private static final int TEST_START_FIRST_WEEK = 27;

    /** The test year. */
    private static final int TEST_YEAR = 2017;

    /** The test year. */
    private static final int NUMBER_OF_TIMECARDS = 4;

    /** Index to the first client. */
    private static final int FIRST_CLIENT_NDX = 0;

    /** Index to the second client. */
    private static final int SECOND_CLIENT_NDX = 1;

    /**
     * This class' logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(Assignment02.class);

    /**
     * Prevent instantiation.
     */
    private Assignment02() {
    }

    /**
     * Create some client account instances.
     *
     * @return the created client accounts
     */
    private static ClientAccount[] createClientAccounts() {
        final ClientAccount[] accounts = {
            new ClientAccount("Acme Industries",
                    new PersonalName("Coyote", "Wiley")),
            new ClientAccount("FooBar Enterprises",
                    new PersonalName("Sam", "Yosemite"))
        };
        return accounts;
    }

    /**
     * Confirm the time card hours.
     * 
     * @param timeCard the time card
     * @param exTotalHours expected total hours
     * @param exBillableHours expected billable hours
     */
    private static void confirmHours(final TimeCard timeCard, final int exTotalHours, final int exBillableHours) {
        int cardTotalHours = timeCard.getTotalHours();
        int cardBillableHours = timeCard.getTotalBillableHours();
        int cardNonbillableHours = timeCard.getTotalNonBillableHours();
        int exNonBillableHours = exTotalHours - exBillableHours;

        if (cardTotalHours != exTotalHours) {
            logger.error(String.format("Time card total hours for %s are incorrect, expected %d but was %d",
                                       timeCard.getConsultant().getName(),
                                       exTotalHours, cardTotalHours));
        }
        if (cardBillableHours != exBillableHours) {
            logger.error(String.format("Time card billable hours for %s are incorrect, expected %d but was %d",
                                       timeCard.getConsultant().getName(),
                                       exBillableHours, cardBillableHours));
        }
        if (cardNonbillableHours != exTotalHours - exBillableHours) {
            logger.error(String.format("Time card non-billable hours for %s are incorrect, expected %d but was %d",
                                       timeCard.getConsultant().getName(),
                                       exNonBillableHours, cardNonbillableHours));
        }

    }

    /**
     * Create some time card instances.  Also, creates the consultants the
     * time cards are for.
     *
     * @param clients the clients to create time cards for
     * @param month the month to create the time cards for
     * @param startDay the first day on the time card
     * @param year the year for the invoice
     *
     * @return the created time cards
     */
    private static TimeCard[] createTimeCards(final ClientAccount[] clients,
                                             final Month month, final int startDay,
                                             final int year) {
        int cardNdx = 0;
        // Create some Consultants
        final Consultant programmer = new Consultant(new PersonalName("Coder", "Carl"));
        final Consultant systemAnalyst = new Consultant(new PersonalName("Architect", "Ann", "S."));

        final TimeCard[] timeCards = new TimeCard[NUMBER_OF_TIMECARDS];

        LocalDate startDate = LocalDate.of(year, month, startDay);

        // Create some TimeCards
        // The first one
        TimeCard timeCard = new TimeCard(programmer, startDate);
        LocalDate currentDay = startDate;
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[FIRST_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[FIRST_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        logger.trace(String.format("First TimeCard created: %s", timeCard.toReportString()));
        timeCards[cardNdx++] = timeCard;
        
        confirmHours(timeCard, 40, 40);
        
        // The second one
        currentDay = startDate.plusWeeks(1);
        timeCard = new TimeCard(programmer, currentDay);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[FIRST_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[FIRST_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY + OT_HOURS));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, NonBillableAccount.VACATION,
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        logger.trace(String.format("Second TimeCard created: %s", timeCard.toReportString()));
        timeCards[cardNdx++] = timeCard;

        confirmHours(timeCard, 44, 36);

        // The third one
        currentDay = startDate.plusWeeks(1);

        timeCard = new TimeCard(systemAnalyst, currentDay);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, NonBillableAccount.SICK_LEAVE,
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        logger.trace(String.format("Third TimeCard created: %s", timeCard.toReportString()));
        timeCards[cardNdx++] = timeCard;

        confirmHours(timeCard, 48, 40);

        // The forth one
        currentDay = startDate.plusWeeks(2);

        timeCard = new TimeCard(systemAnalyst, currentDay);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay,
                NonBillableAccount.BUSINESS_DEVELOPMENT, Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay,
                NonBillableAccount.BUSINESS_DEVELOPMENT, Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        currentDay = currentDay.plusDays(1);
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        logger.trace(String.format("Forth TimeCard created: %s", timeCard.toReportString()));
        timeCards[cardNdx] = timeCard;

        confirmHours(timeCard, 40, 24);

        return timeCards;
    }

    /**
     * Print the time card instances.
     *
     * @param timeCards the time cards to print
     * @param out The output stream; can be System.out or a text file.
     */
    private static void printTimeCards(final TimeCard[] timeCards, final PrintStream out) {
        for (final TimeCard timeCard : timeCards) {
            out.print(timeCard.toReportString());
        }
    }

    /**
     * The application method.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        final ClientAccount[] clients = createClientAccounts();
        final TimeCard[] cards = createTimeCards(clients,
                TEST_MONTH, TEST_START_FIRST_WEEK, TEST_YEAR);
        // Print 'em
        printTimeCards(cards, System.out);
    }

}
