package com.scg.persistent;

import com.scg.domain.*;
import com.scg.util.*;
import org.apache.derby.client.am.SqlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static com.scg.util.ListFactory.TEST_INVOICE_MONTH;
import static com.scg.util.ListFactory.TEST_INVOICE_YEAR;

/**
 * @author Charlie Misner
 */
public class DbServer {

    private Logger logger = LoggerFactory.getLogger(DbServer.class);
    private String dbUrl;
    private String username;
    private String password;
    private Connection connection;
    private List<ClientAccount> clients;
    private List<TimeCard> timeCards;

    public DbServer(String dbUrl, String username, String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
        try{
            this.connection = this.connectToDb();
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
    }

    /**
     * Adds client to DB
     * @param client
     */
    public void addClient(ClientAccount client) throws SQLException{
        Statement statement;
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(generateClientInsertString(client));
        } catch (SQLException exception) {
            connection.rollback();
            logger.error(exception.getMessage());
        }
    }

    /**
     * Adds consultant to DB
     * @param consultant
     */
    public void addConsultant(Consultant consultant) throws SQLException{
        Statement statement;
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(generateConsultantInsertString(consultant));
        } catch (SQLException exception) {
            connection.rollback();
            logger.error(exception.getMessage());
        }
    }

    /**
     * Adds timecard to DB
     * @param timeCard
     */
    public void addTimeCard(TimeCard timeCard) throws SQLException {
        Statement statement;
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(generateTimeCardInsertString(timeCard));
            for (ConsultantTime consultantTime : timeCard.getConsultingHours()){
                addHours(consultantTime, getTimeCardId(timeCard));
            }
        } catch (SQLException exception) {
            connection.rollback();
            logger.error(exception.getMessage());
        }
    }

    /**
     * Stores hours in database.
     * @param time
     * @param timeCardId
     */
    public void addHours(ConsultantTime time, int timeCardId) throws SQLException {
        Statement statement;
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(generateHoursInsertString(time, timeCardId));
        } catch (SQLException exception) {
            connection.rollback();
            logger.error(exception.getMessage());
        }
    }

    /**
     * Gets timecard id.
     * @param timeCard
     * @return
     */
    private int getTimeCardId(TimeCard timeCard){
        Statement statement;
        ResultSet result;
        int id = 0;
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery(generateTimeCardIdSelectString(timeCard));
            result.next();
            id = result.getInt("id");
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        return id;
    }

    private int getClientId(String name){
        Statement statement;
        ResultSet result;
        int id = 0;
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery(generateClientAccountIdSelectString(name));
            result.next();
            id = result.getInt("id");
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        return id;
    }

    /**
     * Returns clients from db.
     * @return
     */
    public List<ClientAccount> getClients(){
        Statement statement;
        ResultSet result;
        List<ClientAccount> clients = new ArrayList<>();
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery("SELECT * FROM clients");
            result.last();
            int numberOfRows = result.getRow();
            clients = new ArrayList<>(numberOfRows);
            result.first();
            for (int i=0; i < numberOfRows; i++){
                String name = result.getString("name");
                Address address = new Address(
                        result.getString("street"),
                        result.getString("city"),
                        StateCode.valueOf(result.getString("state")),
                        result.getString("postal_code")
                );
                PersonalName contact = new PersonalName(
                        result.getString("contact_last_name"),
                        result.getString("contact_first_name"),
                        result.getString("contact_middle_name")
                );
                ClientAccount clientAccount = new ClientAccount( name, contact, address);
                clients.add(result.getInt("id") - 1,clientAccount);
                result.next();
            }

        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        this.clients = clients;
        return clients;
    }

    public List<TimeCard> getTimeCards(){
        Statement statement;
        ResultSet result;
        List<TimeCard> timeCards = new ArrayList<>();
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery("SELECT * FROM timecards");
            result.last();
            int numberOfRows = result.getRow();
            timeCards = new ArrayList<>(numberOfRows);
            result.first();
            for (int i=0; i < numberOfRows; i++){
                TimeCard timecard = new TimeCard(
                        this.getConsultants().get(result.getInt("consultant_id")-1),
                        LocalDate.parse(result.getString("start_date"))
                );
                timeCards.add(result.getInt("id") - 1, timecard);
                result.next();
            }

        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        this.timeCards = timeCards;
        this.getBillableHours();
        this.getNonBillableHours();
        return timeCards;
    }

    /**
     * Returns Consultants from db.
     * @return
     */
    public List<Consultant> getConsultants(){
        Statement statement;
        ResultSet result;
        List<Consultant> consultants = new ArrayList<>();
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery("SELECT * FROM consultants");
            result.last();
            int numberOfRows = result.getRow();
            result.first();
            for (int i=0; i < numberOfRows; i++){
                PersonalName contact = new PersonalName(
                        result.getString("last_name"),
                        result.getString("first_name"),
                        result.getString("middle_name")
                );
                Consultant consultant = new Consultant(contact);
                consultants.add(consultant);
                result.next();
            }

        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        return consultants;
    }

    private List<ConsultantTime> getBillableHours(){
        Statement statement;
        ResultSet result;
        List<ConsultantTime> times = new ArrayList<>();
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery("SELECT * FROM billable_hours");
            result.last();
            int numberOfRows = result.getRow();
            result.first();
            for (int i=0; i < numberOfRows; i++){
                ConsultantTime time = new ConsultantTime(
                        LocalDate.parse(result.getString("date")),
                        this.clients.get(result.getInt("client_id") - 1),
                        Skill.valueOf(result.getString("skill")),
                        result.getInt("hours"));
                times.add(time);
                this.timeCards.get(result.getInt("timecard_id")-1).addConsultantTime(time);

                result.next();
            }

        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        return times;
    }

    private List<ConsultantTime> getNonBillableHours(){
        Statement statement;
        ResultSet result;
        List<ConsultantTime> times = new ArrayList<>();;
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery("SELECT * FROM non_billable_hours");
            result.last();
            int numberOfRows = result.getRow();
            result.first();
            for (int i=0; i < numberOfRows; i++){
                ConsultantTime time = new ConsultantTime(
                        LocalDate.parse(result.getString("date")),
                        NonBillableAccount.valueOf(result.getString("ACCOUNT_NAME")),
                        Skill.valueOf("UNKNOWN_SKILL"),
                        result.getInt("hours"));
                times.add(time);
                this.timeCards.get(result.getInt("timecard_id")-1).addConsultantTime(time);
                result.next();
            }

        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        return times;
    }

    /**
     * Returns invoices.
     * @param client
     * @param month
     * @param year
     * @return
     */
    public Invoice getInvoice(ClientAccount client, Month month, int year){
        List<TimeCard> timeCards = this.getTimeCards();
        final List<TimeCard> timeCardList = TimeCardListUtil
                .getTimeCardsForDateRange(timeCards, new DateRange(TEST_INVOICE_MONTH, TEST_INVOICE_YEAR));
        Invoice invoice = new Invoice(client, month, year);
        for (final TimeCard currentTimeCard : timeCardList) {
            invoice.extractLineItems(currentTimeCard);
        }

        return invoice;
    }

    /**
     * Connects to DB
     * @return
     */
    private Connection connectToDb() throws Exception{
        try {
            return DriverManager.getConnection(this.dbUrl, this.username, this.password);
        } catch (Exception exception) {
           throw exception;
        }
    }

    /**
     * Creates sql command for client insert
     * @param client
     * @return
     */
    private String generateClientInsertString(ClientAccount client){
        StringBuilder string = new StringBuilder();
        String propertiesList = String.format("('%s', '%s', '%s', '%s', '%s','%s', '%s', '%s')",
            client.getName(),
            client.getAddress().getStreetNumber(),
            client.getAddress().getCity(),
            client.getAddress().getState().toString(),
            client.getAddress().getPostalCode(),
            client.getContact().getLastName(),
            client.getContact().getFirstName(),
            client.getContact().getMiddleName()
        );
        string.append("INSERT INTO clients ");
        string.append("(name, street, city, state, postal_code,contact_last_name, contact_first_name, contact_middle_name) ");
        string.append("VALUES ");
        string.append(propertiesList);

        return string.toString();
    }

    /**
     * Creates sql to insert consultant
     * @param consultant
     * @return
     */
    private String generateConsultantInsertString(Consultant consultant){
        StringBuilder string = new StringBuilder();
        String propertiesList = String.format("('%s', '%s', '%s')",
            consultant.getName().getLastName(),
            consultant.getName().getFirstName(),
            consultant.getName().getMiddleName()
        );
        string.append("INSERT INTO consultants ");
        string.append("(last_name, first_name, middle_name) ");
        string.append("VALUES ");
        string.append(propertiesList);

        return string.toString();
    }

    /**
     * Creates sql to insert timecard
     * @param timeCard
     * @return
     */
    private String generateTimeCardInsertString(TimeCard timeCard){
        int consultantId = this.getConsultantId(timeCard.getConsultant());
        StringBuilder string = new StringBuilder();
        String propertiesList = String.format("(%d, '%s')",
            consultantId,
            timeCard.getWeekStartingDay().toString()
        );
        string.append("INSERT INTO timecards ");
        string.append("(consultant_id, start_date) ");
        string.append("VALUES ");
        string.append(propertiesList);

        return string.toString();
    }

    /**
     * Creates sql to create hours
     * @param time
     * @param timeCardId
     * @return
     */
    private String generateHoursInsertString(ConsultantTime time, int timeCardId){
        StringBuilder string = new StringBuilder();

        if(time.getAccount().isBillable()){
            int clientId = getClientId(time.getAccount().getName());
            String propertiesList = String.format("(%d, %d, '%s', '%s', %d)",
                    clientId,
                    timeCardId,
                    time.getDate().toString(),
                    time.getSkillType().name(),
                    time.getHours()
            );
            string.append("INSERT INTO billable_hours ");
            string.append("(client_id, timecard_id, date, skill, hours) ");
            string.append("VALUES ");
            string.append(propertiesList);
        } else {
            String propertiesList = String.format("('%s', %d, '%s', %d)",
                    ((NonBillableAccount)time.getAccount()).name(),
                    timeCardId,
                    time.getDate(),
                    time.getHours()
            );
            string.append("INSERT INTO non_billable_hours  ");
            string.append("(account_name, timecard_id, date, hours) ");
            string.append("VALUES ");
            string.append(propertiesList);
        }
        return string.toString();
    }

    /**
     * Gets consultant Id
     * @param consultant
     * @return
     */
    private int getConsultantId (Consultant consultant){
        Statement statement;
        ResultSet result;
        int id = 0;
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery(generateConsultantIdSelectString(consultant));
            result.next();
            id = result.getInt("id");
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        return id;
    }

    private String generateConsultantIdSelectString(Consultant consultant){
        StringBuilder string = new StringBuilder();
        string.append("SELECT id ");
        string.append("FROM consultants ");
        string.append(String.format("WHERE last_name = '%s' ", consultant.getName().getLastName()));
        string.append(String.format("AND first_name = '%s' ", consultant.getName().getFirstName()));
        string.append(String.format("AND middle_name = '%s' ", consultant.getName().getMiddleName()));

        return string.toString();
    }

    private String generateTimeCardIdSelectString(TimeCard timeCard){
        StringBuilder string = new StringBuilder();
        string.append("SELECT id ");
        string.append("FROM timecards ");
        string.append(String.format("WHERE start_date = '%s' ", timeCard.getWeekStartingDay().toString()));
        string.append(String.format("AND consultant_id = %s ", getConsultantId(timeCard.getConsultant())));

        return string.toString();
    }

    private String generateClientAccountIdSelectString(String name){
        StringBuilder string = new StringBuilder();
        string.append("SELECT id ");
        string.append("FROM clients ");
        string.append(String.format("WHERE name = '%s' ", name));

        return string.toString();
    }

}
