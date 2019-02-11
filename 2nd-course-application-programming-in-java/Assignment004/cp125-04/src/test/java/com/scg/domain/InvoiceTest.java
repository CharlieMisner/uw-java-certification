package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.ListFactory;
import com.scg.util.PersonalName;
import com.scg.util.StateCode;
import com.sun.org.apache.xpath.internal.operations.String;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.scg.util.ListFactory.TEST_INVOICE_MONTH;
import static com.scg.util.ListFactory.TEST_INVOICE_YEAR;
import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    final List<TimeCard> timeCards = new ArrayList<>();
    private Invoice invoice;
    private Address address = new Address("15", "Seattle", StateCode.WA, "98102");
    private PersonalName personalName = new PersonalName("Charlie", "Misner", "D");
    private ClientAccount testClientAccount = new ClientAccount("Client", personalName, address);
    private TimeCard timeCard;
    private LocalDate startDate = LocalDate.of(2019, 1, 1);

    final ClientAccount clientAccount = new ClientAccount("Test", new PersonalName(), address);
    final Consultant programmer = new Consultant(new PersonalName("Charlie", "Misner"));

    private java.lang.String expectedResultString = "The Small Consulting Group \n" +
            " 1616 Index Ct. \n" +
            " Renton, WA 98058 \n" +
            " \n" +
            "Invoice for: \n" +
            " Test \n" +
            " 15 \n" +
            " Seattle, WA 98102 \n" +
            " ,   \n" +
            "\n" +
            "Date        Consultant         Skill                 Hours     Charge \n" +
            "---------------------------------------------------------------------\n" +
            "2019-01-01  Misner, Charlie    Software Engineer     8      $1,200.00 \n" +
            "\n" +
            "Total:\n" +
            "$1,200.00\n" +
            "\n" +
            "The Small Consulting Group \n" +
            "Page:  1\n" +
            "=====================================================================\n\n";

    @BeforeEach
    public void init(){

        this.timeCard = new TimeCard(programmer, startDate);
        this.timeCard.addConsultantTime(new ConsultantTime(startDate, clientAccount, Skill.SOFTWARE_ENGINEER, 8));
        invoice = new Invoice(clientAccount, startDate.getMonth(), 2019);
        invoice.extractLineItems(this.timeCard);
    }

    @Test
    void toReportString() {
        assertEquals(-320642315, this.invoice.toReportString().hashCode());
    }

    @Test
    void getTotalHours() {
        assertEquals(8, this.invoice.getTotalHours());
    }

    @Test
    void getTotalCharges() {
        assertEquals(1200, this.invoice.getTotalCharges());
    }

}