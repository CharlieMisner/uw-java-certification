package com.scg.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

/**
 * @author Charlie Misner
 */
public class DateRange {

    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Constructor with two dates.
     * @param startDate
     * @param endDate
     */
    public DateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Constructor for month.
     * @param month
     * @param year
     */
    public DateRange(Month month, int year) {
        this.startDate = LocalDate.of(year, month.getValue(), 1);
        this.endDate = LocalDate.of(year, month.getValue()+1, 1).minusDays(1);
    }

    /**
     * Constructor with date strings.
     * @param start
     * @param end
     */
    public DateRange(String start, String end) {
        this.startDate = LocalDate.parse(start);
        this.endDate = LocalDate.parse(end);
    }

    /**
     * Determines if date falls within range.
     * @param date
     * @return
     */
    public boolean isInRange(LocalDate date){
        long startMillis = this.startDate.atStartOfDay()
                .atZone(ZoneId.of("America/Los_Angeles")).toInstant().toEpochMilli();
        long endMillis = this.endDate.atStartOfDay()
                .atZone(ZoneId.of("America/Los_Angeles")).toInstant().toEpochMilli();
        long dateMillis = date.atStartOfDay()
                .atZone(ZoneId.of("America/Los_Angeles")).toInstant().toEpochMilli();

        if(startMillis <= dateMillis && endMillis >= dateMillis) {
            return true;
        } else {
            return false;
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }


}
