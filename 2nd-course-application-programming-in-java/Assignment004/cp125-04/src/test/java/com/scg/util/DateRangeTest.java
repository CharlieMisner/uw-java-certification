package com.scg.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.scg.util.ListFactory.TEST_INVOICE_MONTH;
import static com.scg.util.ListFactory.TEST_INVOICE_YEAR;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test for the DateRange class.
 *
 * @author Russ Moul
 */
public final class DateRangeTest {

    /** Test start date. */
    private LocalDate startDate;

    /** Test end date. */
    private LocalDate endDate;

    /**
     * Test the various constructors.
     */
    @Test
    public void testConstructors() {
        DateRange dateRange = new DateRange(startDate, endDate);
        assertNotNull(dateRange, "DateRange(Date, Date) failed.");
        assertEquals(startDate, dateRange.getStartDate());
        assertEquals(endDate, dateRange.getEndDate());

        dateRange = new DateRange(TEST_INVOICE_MONTH, TEST_INVOICE_YEAR);
        assertEquals(startDate, dateRange.getStartDate());
        assertEquals(endDate, dateRange.getEndDate());
    }

    /**
     * Initialize start and end month dates.
     */
    @BeforeEach
    public void setUp() {
        startDate = LocalDate.of(TEST_INVOICE_YEAR, TEST_INVOICE_MONTH, 1);
        endDate = startDate.plusDays(startDate.lengthOfMonth()-1);
    }

    /**
     * Test the isInDange method.
     */
    @Test
    public void testIsInRange() {
        final DateRange dateRange = new DateRange(startDate, endDate);
        assertTrue(dateRange.isInRange(startDate));

        DateRange testDateRange = new DateRange(Month.FEBRUARY, TEST_INVOICE_YEAR);
        assertFalse(testDateRange.isInRange(startDate));

        String yearStr = Integer.toString(TEST_INVOICE_YEAR);
        testDateRange = new DateRange(yearStr + "-02-01", yearStr + "-02-28");
        assertFalse(testDateRange.isInRange(startDate));

        testDateRange = new DateRange(yearStr + "-02-26", yearStr + "-03-04");
        assertTrue(testDateRange.isInRange(startDate));
    }
}
