package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.StateCode;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author CharlieM
 */
public class Invoice implements Serializable {

    private ClientAccount account;
    private int year;
    private Month month;
    private int totalHours;
    private int totalCharges;
    private String businessName;
    private Address businessAddress;
    private List<InvoiceLineItem> lineItems = new ArrayList<InvoiceLineItem>();
    private InvoiceHeader header;
    private InvoiceFooter footer;
    private int pageQty;

    public Invoice(ClientAccount account, Month month, int year) {
        this.account = account;
        this.year = year;
        this.month = month;
        this.getProperties();
        header = new InvoiceHeader(
                this.businessName,
                this.businessAddress,
                this.account,
                LocalDate.now(),
                LocalDate.of(this.year, this.month.getValue(), 01)
        );
        footer = new InvoiceFooter(this.businessName);
        this.toReportString();
    }

    /**
     * Generates pages for report and assembles them into a report string.
     * @return
     */
    public String toReportString() {
        StringBuilder reportString = new StringBuilder();
        List<List<InvoiceLineItem>> paginatedLineItems = new ArrayList<>();

        for(int i=0; i < lineItems.size(); i += 5) {
            int endOfPageIndex = i + Math.min(5, lineItems.size() - i);
            paginatedLineItems.add(lineItems.subList(i, endOfPageIndex));
        }
        this.pageQty = paginatedLineItems.size();
        for (List<InvoiceLineItem> pageLineItems: paginatedLineItems){
            reportString.append(this.createReportPage(pageLineItems));
            this.footer.incrementPageNumber();
        }
        if (paginatedLineItems.size() == 0){
            this.pageQty = 1;
            reportString.append(this.createReportPage(new ArrayList<>()));
        }
        return reportString.toString();
    }

    /**
     * Creates individual report pages with 5 line items each.
     * @param pageLineItems
     * @return
     */
    public String createReportPage(List<InvoiceLineItem> pageLineItems){
        StringBuilder page = new StringBuilder();

        page.append(this.header.toString());

        page.append(String.format("%-10s  %-17s  %-20s  %-5s %10s %n",
                "Date",
                "Consultant",
                "Skill",
                "Hours",
                "Charge"
        ));
        page.append(String.format(
                "---------------------------------------------------------------------%n"
        ));
        for (InvoiceLineItem lineItem : pageLineItems){
            page.append(lineItem.toString());
        }

        if(this.pageQty == this.footer.getPageNumber()){
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            page.append(String.format("%nTotal:%n$%s%n", formatter.format(this.getTotalCharges())));
        }
        page.append(this.footer.toString());
        return page.toString();
    }

    /**
     * Extracts the line items.
     *
     * @param timeCard
     */
    public void extractLineItems(TimeCard timeCard){
        timeCard.getConsultingHours()
            .stream()
             .filter(time -> this.account.getName().equals(time.getAccount().getName())
                        && this.month.equals(time.getDate().getMonth()))
            .forEach(time -> {
                lineItems.add(new InvoiceLineItem(time.getDate(), timeCard.getConsultant(), time.getSkillType(), time.getHours()));
            });
    }

    public ClientAccount getClientAccount() {
        return account;
    }

    private void calculateTotals(){

    }

    public int getTotalHours(){
        this.totalHours = 0;
        for (InvoiceLineItem lineItem : this.lineItems){
            this.totalHours += lineItem.getHours();
        }
        return totalHours;
    }

    public int getTotalCharges() {
        this.totalCharges = 0;
        for (InvoiceLineItem lineItem : this.lineItems){
            this.totalCharges += lineItem.getCharge();
        }
        return totalCharges;
    }

    /**
     * Extracts the properties from the properties file.
     */
    private void getProperties(){
        Properties properties = new Properties();
        try{
            String fileName = "invoice.properties";
            InputStream input = Invoice.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(input);
            this.businessName = properties.getProperty("business.name");
            this.businessAddress = new Address(
                properties.getProperty("business.street"),
                properties.getProperty("business.city"),
                StateCode.valueOf(properties.getProperty("business.state")),
                properties.getProperty("business.zip")
            );
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
