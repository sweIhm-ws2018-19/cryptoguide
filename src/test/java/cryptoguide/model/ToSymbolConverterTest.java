package cryptoguide.model;

import org.junit.Assert;
import org.junit.Test;

public class ToSymbolConverterTest {

    @Test
    public void ToSymbolConverterTest1() {
        String currency = "bitcoin";
        String symbol = "BTC";
        System.out.println(ToSymbolConverter.convert(currency));
        Assert.assertSame(ToSymbolConverter.convert(currency), symbol);
    }

    @Test
    public void ToSymbolConverterTest2() {
        String currency = "euro";
        String symbol = "EUR";
        System.out.println(ToSymbolConverter.convert(currency));
        Assert.assertSame(ToSymbolConverter.convert(currency), symbol);
    }

    @Test
    public void ToSymbolConverterTest3() {
        String currency = "nulltest";
        System.out.println(ToSymbolConverter.convert(currency));
        Assert.assertNull(ToSymbolConverter.convert(currency));
    }
}

