package com.scg.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NonBillableAccountTest {

    @Test
    public void testBusinessDevelopment(){
        assertEquals("Business Development", NonBillableAccount.BUSINESS_DEVELOPMENT.toString());
    }

    @Test
    public void testSickLeave(){
        assertEquals("Sick Leave", NonBillableAccount.SICK_LEAVE.toString());
    }

    @Test
    public void testVacation(){
        assertEquals("Vacation", NonBillableAccount.VACATION.toString());
    }

     @Test
    public void testIsBillable(){
        assertFalse(NonBillableAccount.BUSINESS_DEVELOPMENT.isBillable());
    }



}