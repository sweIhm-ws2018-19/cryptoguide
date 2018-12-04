package cryptoguide.model;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;

/**
 * This class creates timestamps by processing the AMAZON.Date slot types.
 * Inputs can be e.g 2016-07-17, 2016-01-XX, 2013-W11, 2013-W9
 */
public class TimestampGenerator {

    private TimestampGenerator() {
        throw new IllegalStateException("This is a static class, can't create a instance.");
    }

    public static long convertToTimeStamp(String input) {
        if (input == null) return -1;
        int inputLength = input.length();
        if((inputLength == 8 || inputLength == 7) && input.charAt(5) == 'W') {
            //Calculate the date of monday in this specific week
            int year = Integer.valueOf(input.substring(0,4));
            int week = Integer.valueOf(input.substring(6,inputLength));
            LocalDate date = LocalDate.ofYearDay(year, 20).with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            input = date.toString();
        } else if((inputLength == 11 || inputLength == 10) && input.endsWith("WE")) {
            //Calculate the date of sunday in this specific week
            int year = Integer.valueOf(input.substring(0,4));
            int week = Integer.valueOf(input.substring(6,inputLength - 3));
            LocalDate date = LocalDate.ofYearDay(year, 20).with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            input = date.toString();
        } else if(inputLength == 10) {
            if (input.endsWith("XX")) {
                //Calculate the date of the first
                input = input.replace("XX", "01");
            }
        }
        return getTimeStampForDateString(input);
    }

    static long getTimeStampForDateString(String dateString) {
        if(dateString.length() == 10) {
            //dateString has schema 2016-04-17
            int year = Integer.valueOf(dateString.substring(0,4));
            int month = Integer.valueOf(dateString.substring(5,7));
            int day = Integer.valueOf(dateString.substring(8, 10));
            LocalDate date = LocalDate.of(year, month, day);
            return date.atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond();
        }
        return -1;
    }

    static long getCurrentTimeStamp() {
        return Instant.now().getEpochSecond();
    }
}
