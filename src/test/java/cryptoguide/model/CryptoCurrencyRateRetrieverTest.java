package cryptoguide.model;

import org.junit.Assert;
import org.junit.Test;

public class CryptoCurrencyRateRetrieverTest {

    @Test(expected = IllegalStateException.class)
    public void constructorTest() {
        CryptoCurrencyRateRetriever retriever = new CryptoCurrencyRateRetriever();
    }

    @Test
    public void GetCurrencyRateTest1() {
        String firstCurrency = "BTC";
        String secondCurrency = "USD";
        double test = CryptoCurrencyRateRetriever.getCurrentRate(firstCurrency, secondCurrency);
        Assert.assertTrue(test != 0.0f);
    }

    @Test
    public void GetCurrencyRateTest2() {
        String firstCurrency = "EUR";
        String secondCurrency = "ETH";
        double test = CryptoCurrencyRateRetriever.getCurrentRate(firstCurrency, secondCurrency);
        Assert.assertNotEquals(test,0.0f);
    }

    @Test
    public void GetCurrencyRateTest3() {
        String firstCurrency = "LTC";
        String secondCurrency = "BTC";
        double test = CryptoCurrencyRateRetriever.getCurrentRate(firstCurrency, secondCurrency);
        Assert.assertNotEquals(test,0.0f);
    }

    @Test
    public void GetCurrencyRateTest4() {
        String firstCurrency = "BTC";
        String secondCurrency = "BTC";
        double test = CryptoCurrencyRateRetriever.getCurrentRate(firstCurrency, secondCurrency);
        Assert.assertEquals(1.0f, test,0.0f);
    }

    @Test
    public void GetCurrencyRateTest5() {
        String firstCurrency = "sadboi";
        String secondCurrency = "BTC";
        double test = CryptoCurrencyRateRetriever.getCurrentRate(firstCurrency, secondCurrency);
        Assert.assertEquals(0.0f, test,0.0f);
    }

    @Test
    public void GetCurrencyRateTest6() {
        String firstCurrency = "BTC";
        String secondCurrency = "asdfasdf";
        double test = CryptoCurrencyRateRetriever.getCurrentRate(firstCurrency, secondCurrency);
        Assert.assertEquals(0.0f, test, 0.0f);
    }

    @Test
    public void GetCurrencyRateTest7() {
        String firstCurrency = "errupwerüü";
        String secondCurrency = "asdfasdf";
        double test = CryptoCurrencyRateRetriever.getCurrentRate(firstCurrency, secondCurrency);
        Assert.assertEquals(0.0f, test, 0.0f);
    }

    @Test
    public void GetPastRateTestInvalid() {
        String firstCurrency = "notACurrency";
        String secondCurrency = "BTC";
        long timestamp = TimestampGenerator.convertToTimeStamp("2016-12-09");
        double test = CryptoCurrencyRateRetriever.getPastRate(firstCurrency, secondCurrency, timestamp);
        Assert.assertEquals(0.0f, test, 0.0f);
    }


    @Test
    public void GetPastRateTestFuture() {
        String firstCurrency = "BTC";
        String secondCurrency = "EUR";
        long timestamp = TimestampGenerator.convertToTimeStamp("2999-12-09");
        double test = CryptoCurrencyRateRetriever.getPastRate(firstCurrency, secondCurrency, timestamp);
        double test_actual = CryptoCurrencyRateRetriever.getCurrentRate(firstCurrency, secondCurrency);
        Assert.assertEquals(test_actual, test, 0);
    }


    @Test
    public void GetPastRateTestPast() {
        String firstCurrency = "BTC";
        String secondCurrency = "EUR";
        long timestamp = TimestampGenerator.convertToTimeStamp("2016-12-09");
        double expected = 734.11;
        double actual = CryptoCurrencyRateRetriever.getPastRate(firstCurrency, secondCurrency, timestamp);
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void GetPastRateTestFarPast() {
        String firstCurrency = "BTC";
        String secondCurrency = "EUR";
        long timestamp = TimestampGenerator.convertToTimeStamp("1970-12-09");
        double expected = 0.00;
        double actual = CryptoCurrencyRateRetriever.getPastRate(firstCurrency, secondCurrency, timestamp);
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void GetPastRateTestSameCurrency() {
        String firstCurrency = "USD";
        String secondCurrency = "USD";
        long timestamp = TimestampGenerator.convertToTimeStamp("2013-11-18");
        double actual = CryptoCurrencyRateRetriever.getPastRate(firstCurrency, secondCurrency, timestamp);
        Assert.assertEquals(1, actual, 0);
    }
}