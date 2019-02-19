package com.scg.util;

import com.scg.domain.TimeCard;

import java.time.ZoneId;
import java.util.Comparator;

public class TimeCardConsultantComparator implements Comparator<TimeCard> {

    public int compare(TimeCard firstTimeCard, TimeCard secondTimeCard){

        int diff = 0;

        String firstConsultantName = firstTimeCard.getConsultant().toString();
        String secondConsultantName = secondTimeCard.getConsultant().toString();
        long firstBeginningDateMillis = firstTimeCard.getWeekStartingDay().atStartOfDay()
                .atZone(ZoneId.of("America/Los_Angeles")).toInstant().toEpochMilli();
        long secondBeginningDateMillis = secondTimeCard.getWeekStartingDay().atStartOfDay()
                .atZone(ZoneId.of("America/Los_Angeles")).toInstant().toEpochMilli();
        int firstTotalBillableHours = firstTimeCard.getTotalBillableHours();
        int secondTotalBillableHours = secondTimeCard.getTotalBillableHours();
        int firstNonBillableHours = firstTimeCard.getTotalNonBillableHours();
        int secondNonBillableHours = secondTimeCard.getTotalNonBillableHours();

        return 0;
    }

}
