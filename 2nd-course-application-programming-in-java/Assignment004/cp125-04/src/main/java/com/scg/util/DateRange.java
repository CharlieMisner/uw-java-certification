package com.scg.util;

import java.time.LocalDate;
import java.time.Month;

public class DateRange {

    private LocalDate startDate;
    private LocalDate endDate;
    private Month month;
    private int year;
    private String start;
    private String end;

    public DateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DateRange(Month month, int year) {
        this.month = month;
        this.year = year;
    }

    public DateRange(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public boolean isInRange(LocalDate date){
        return true;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }


}
