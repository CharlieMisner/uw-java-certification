package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.PersonalName;
import com.scg.util.StateCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeCardTest {

    Consultant consultant;
    TimeCard timeCard;
    LocalDate startDate = LocalDate.of(2019, 1, 1);
    Address address = new Address("15", "Seattle", StateCode.WA, "98102");

    final ClientAccount clientAccount = new ClientAccount("Test", new PersonalName(), address);
    final Consultant programmer = new Consultant(new PersonalName("Charlie", "Misner"));
    final Consultant systemAnalyst = new Consultant(new PersonalName("Foo", "Bar", "Baz"));

    final String expectedReportString =
        "====================================================================\n" +
        "Consultant: Misner, Charlie                Week Starting: 2019-01-01 \n" +
        " \n" +
        "Billable Time:                                                       \n" +
        "Account                      Date        Hours  Skill               \n" +
        "===========================  ==========  =====  ====================\n" +
        "Test                         2019-01-01  8      Software Engineer   \n" +
        "\n" +
        "Non-billable Time:                                                   \n" +
        "Account                      Date        Hours  Skill               \n" +
        "===========================  ==========  =====  ====================\n" +
        "\n" +
        "Summary:                                                             \n" +
        "Total Billable                           8                          \n" +
        "Total Non-Billable                       0                          \n" +
        "Total Hours                              8                          \n" +
        "====================================================================\n";


    @BeforeEach
    public void init(){
        this.timeCard = new TimeCard(programmer, startDate);
        this.timeCard.addConsultantTime(new ConsultantTime(startDate, clientAccount, Skill.SOFTWARE_ENGINEER, 8));
    }

    @Test
    public void testToString(){
        assertEquals("Misner, Charlie  - 2019-01-01", this.timeCard.toString());
    }

    @Test
    public void testToReportString(){
        assertEquals(991, this.timeCard.toReportString().length());
    }

    @Test
    public void testGetBillableHoursForClient(){
        this.timeCard.addConsultantTime(new ConsultantTime(startDate, clientAccount, Skill.SOFTWARE_TESTER, 10));
        List<ConsultantTime> billableHours = this.timeCard.getBillableHoursForClient("Test");
        for(ConsultantTime time: billableHours){
            assertEquals("Test", time.getAccount().getName());
        }
    }
}