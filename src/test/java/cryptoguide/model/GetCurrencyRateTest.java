package cryptoguide.model;

import org.junit.Assert;
import org.junit.Test;

import java.text.DecimalFormat;

public class GetCurrencyRateTest {

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

}