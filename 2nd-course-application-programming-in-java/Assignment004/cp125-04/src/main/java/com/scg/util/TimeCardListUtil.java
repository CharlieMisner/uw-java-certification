package com.scg.util;

import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;

import java.sql.Time;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Charlie Misner
 */
public class TimeCardListUtil {

    //Comparator for timecards by consultant name.
    private static Comparator<TimeCard> compareByConsultantName = (TimeCard tc1, TimeCard tc2) ->
            tc1.getConsultant().getName().toString().compareTo(tc2.getConsultant().getName().toString());

    //Comparator for timecards by start date.
    private static Comparator<TimeCard> compareByStartDate = (TimeCard tc1, TimeCard tc2) ->
            tc1.getWeekStartingDay().compareTo(tc2.getWeekStartingDay());

    /**
     * Filters out time cards based on consultant name.
     * @param timeCards
     * @param consultant
     * @return
     */
    public static List<TimeCard> getTimeCardsForConsultant(List<TimeCard> timeCards, Consultant consultant){
        return timeCards.stream().filter(tc -> tc.getConsultant().equals(consultant)).collect(Collectors.toList());
    }

    /**
     * Filters time cards by date range.
     * @param timeCards
     * @param dateRange
     * @return
     */
    public static List<TimeCard>getTimeCardsForDateRange(List<TimeCard> timeCards, DateRange dateRange){
        return timeCards.stream().filter(tc -> dateRange.isInRange(tc.getWeekStartingDay())).collect(Collectors.toList());
    }

    /**
     * Sorts time cards by consultant name.
     * @param timeCards
     */
    public static void sortByConsultantName(List<TimeCard> timeCards){
        Collections.sort(timeCards, compareByConsultantName);
    }

    /**
     * Sorts time cards by start date.
     * @param timeCards
     */
    public static void	sortByStartDate(List<TimeCard> timeCards){
        Collections.sort(timeCards, compareByStartDate);
    }
}
