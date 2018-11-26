package cryptoguide.model;

import org.junit.Assert;
import org.junit.Test;

public class ToSymbolConverterTest {

    @Test
    public void ToSymbolConverterTest1() {
        String Currency = "bitcoin";
        String symbol = "BTC";
        System.out.println(ToSymbolConverter.convert(Currency));
        Assert.assertSame(ToSymbolConverter.convert(Currency), symbol);
    }

    @Test
    public void ToSymbolConverterTest2() {
        String Currency = "euro";
        String symbol = "EUR";
        System.out.println(ToSymbolConverter.convert(Currency));
        Assert.assertSame(ToSymbolConverter.convert(Currency), symbol);
    }

    @Test
    public void ToSymbolConverterTest3() {
        String Currency = "nulltest";
        System.out.println(ToSymbolConverter.convert(Currency));
        Assert.assertNull(ToSymbolConverter.convert(Currency));
    }
}

