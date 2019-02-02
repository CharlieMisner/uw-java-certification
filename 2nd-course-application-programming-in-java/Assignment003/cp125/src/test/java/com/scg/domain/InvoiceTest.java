package com.scg.domain;

import com.scg.util.ListFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    @BeforeEach
    public void init(){
        final List<ClientAccount> accounts = new ArrayList<>();
        final List<Consultant> consultants = new ArrayList<>();
        final List<TimeCard> timeCards = new ArrayList<>();
        ListFactory.populateLists(accounts, consultants, timeCards);
    }

    @Test
    void toReportString() {
    }

    @Test
    void getTotalHours() {
    }

    @Test
    void getTotalCharges() {
    }
}