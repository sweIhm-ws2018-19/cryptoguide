package cryptoguide.model;

import java.util.HashMap;

public class ToSymbolConverter {

    // This is a static class so we need a private constructor to hide the standard public one.
    private ToSymbolConverter() {
        throw new IllegalStateException("This is a static class, can't create a instance.");
    }

    private static final HashMap<String, String> currencyMap = new HashMap<String, String>()
    {
        {
            put("Bitcoin", "BTC");
            put("Ethereum", "ETH");
            put("XRP", "XRP");
            put("EOS", "EOS");
            put("Z Cash", "ZEC");
            put("Litecoin", "LTC");
            put("Dash", "DASH");
            put("Ethereum Classic", "ETC");
            put("QTUM", "QTUM");
            put("Dollar", "USD");
            put("US Dollar", "USD");
            put("Euro", "EUR");
            put("Yen", "YEN");
        }
    };

    public static String convert(String currency) {
        return currencyMap.get(currency);
    }
}
