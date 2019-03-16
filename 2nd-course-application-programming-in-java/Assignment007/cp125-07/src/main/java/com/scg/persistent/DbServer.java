package com.scg.persistent;

import com.scg.domain.*;
import com.scg.util.Address;
import com.scg.util.PersonalName;
import com.scg.util.StateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Charlie Misner
 */
public class DbServer {

    private Logger logger = LoggerFactory.getLogger(DbServer.class);
    private String dbUrl;
    private String username;
    private String password;
    private Connection connection;

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
    public void addClient(ClientAccount client){
        Statement statement;
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(generateClientInsertString(client));
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
    }

    /**
     * Adds consultant to DB
     * @param consultant
     */
    public void addConsultant(Consultant consultant){
        Statement statement;
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(generateConsultantInsertString(consultant));
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
    }

    /**
     * Adds timecard to DB
     * @param timeCard
     */
    public void addTimeCard(TimeCard timeCard){
        Statement statement;
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(generateTimeCardInsertString(timeCard));
            for (ConsultantTime consultantTime : timeCard.getConsultingHours()){
                System.out.println(consultantTime.toString());
                addHours(consultantTime, getTimeCardId(timeCard));
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
    }

    /**
     * Stores hours in database.
     * @param time
     * @param timeCardId
     */
    public void addHours(ConsultantTime time, int timeCardId) {
        Statement statement;
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.executeUpdate(generateHoursInsertString(time, timeCardId));
        } catch (SQLException exception) {
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
        List<ClientAccount> clients = new ArrayList<>();;
        try {
            statement = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = statement.executeQuery("SELECT * FROM clients");
            result.last();
            int numberOfRows = result.getRow();
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
                clients.add(clientAccount);
                System.out.println(clientAccount.getName());
                result.next();
            }

        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        return clients;
    }

    /**
     * Returns Invoices from db.
     * @return
     */
    public List<ClientAccount> getConsultants(){

        return new ArrayList<>();
    }

    /**
     * Returns invoices.
     * @param client
     * @param month
     * @param year
     * @return
     */
    public List<ClientAccount> getInvoice(ClientAccount client, Month month, int year){

        return new ArrayList<>();
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
