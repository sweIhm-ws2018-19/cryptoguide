package cryptoguide.model;

import org.junit.Assert;
import org.junit.Test;

public class TimestampGeneratorTest {

    @Test
    public void getTimeStampForDateStringTestInvalidString() {
        String dateString = "1831-12-0";
        long result = TimestampGenerator.getTimeStampForDateString(dateString);
        Assert.assertEquals(-1, result);
    }

    @Test
    public void getTimeStampForDateStringTestValidDate() {
        String dateString = "2016-12-12";
        long result = TimestampGenerator.getTimeStampForDateString(dateString);
        Assert.assertEquals(1481500800, result);
    }

    @Test
    public void getTimeStampForDateStringTestValidDate2() {
        String dateString = "1970-11-18";
        long result = TimestampGenerator.getTimeStampForDateString(dateString);
        Assert.assertEquals(27734400, result);
    }

    @Test
    public void convertToTimestampInvalidArgs() {
        String input = "1971-1-8";
        long result = TimestampGenerator.convertToTimeStamp(input);
        Assert.assertEquals(-1, result);
    }

    @Test
    public void convertToTimestampAlreadyDate() {
        String input = "1971-01-08";
        long result = TimestampGenerator.convertToTimeStamp(input);
        Assert.assertEquals(32140800, result);
    }

    @Test
    public void convertToTimestampWithWeekTwo() {
        String input = "2016-W21";
        long result = TimestampGenerator.convertToTimeStamp(input);
        Assert.assertEquals(1463961600, result);
    }

    @Test
    public void convertToTimestampWithWeekOne() {
        String input = "2016-W2";
        long result = TimestampGenerator.convertToTimeStamp(input);
        Assert.assertEquals(1452470400, result);
    }

    @Test
    public void convertToTimestampWithWeekTwoAndWeekend() {
        String input = "2016-W37-WE";
        long result = TimestampGenerator.convertToTimeStamp(input);
        Assert.assertEquals(1474156800, result);
    }

    @Test
    public void convertToTimestampWithWeekOneAndWeekend() {
        String input = "2016-W3-WE";
        long result = TimestampGenerator.convertToTimeStamp(input);
        Assert.assertEquals(1453593600, result);
    }
}
