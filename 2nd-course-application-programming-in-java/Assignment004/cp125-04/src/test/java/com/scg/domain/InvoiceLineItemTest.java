package com.scg.domain;

import com.scg.util.PersonalName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceLineItemTest {

    final Consultant programmer = new Consultant(new PersonalName("Charlie", "Misner"));
    private InvoiceLineItem invoiceLineItem;

    @BeforeEach
    public void init(){
        invoiceLineItem = new InvoiceLineItem(LocalDate.of(2019,01,01), programmer, Skill.SOFTWARE_ENGINEER, 8);
    }

    @Test
    public void testToString() {
        assertEquals(-391793010, invoiceLineItem.toString().hashCode());
    }
}