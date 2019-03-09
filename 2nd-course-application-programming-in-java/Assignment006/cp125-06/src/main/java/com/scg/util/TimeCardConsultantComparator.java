package com.scg.util;

import com.scg.domain.TimeCard;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.Comparator;

/**
 * @author Charlie Misner
 */
public class TimeCardConsultantComparator implements Comparator<TimeCard>, Serializable {

    /**
     * Compare two timecards
     * @param firstTimeCard
     * @param secondTimeCard
     * @return
     */
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

        // Sort by consultant.
        if(firstConsultantName.compareTo(secondConsultantName) > 0){
            diff = 1;
        } else if (firstConsultantName.compareTo(secondConsultantName) < 0){
            diff = -1;
        } else {

            //Sort by start data
            if(firstBeginningDateMillis > secondBeginningDateMillis) {
                diff = 1;
            } else if (firstBeginningDateMillis < secondBeginningDateMillis) {
                diff = -1;
            } else {

                //Sort by total billable hours
                if(firstTotalBillableHours > secondTotalBillableHours) {
                    diff = 1;
                } else if(firstTotalBillableHours < secondTotalBillableHours) {
                    diff = -1;
                } else {

                    //Sort by total non-billable hours
                    if(firstNonBillableHours > secondNonBillableHours) {
                        diff = 1;
                    } else if(firstNonBillableHours < secondNonBillableHours) {
                        diff = -1;
                    } else {
                        diff = 0;
                    }
                }
            }
        }

        return diff;
    }

}
