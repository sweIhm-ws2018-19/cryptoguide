package cryptoguide.model;

import java.util.HashMap;

public class ToSymbolConverter {

    // This is a static class so we need a private constructor to hide the standard public one.
    private ToSymbolConverter() {
        throw new IllegalStateException("This is a static class, can't create a instance.");
    }

    private static final HashMap<String, String> currencyMap = new HashMap<>();

    static{
        currencyMap.put("Bitcoin", "BTC");
        currencyMap.put("XRP", "XRP");
        currencyMap.put("Ethereum", "ETH");
        currencyMap.put("EOS", "EOS");
        currencyMap.put("Z Cash", "ZEC");
        currencyMap.put("Litecoin", "LTC");
        currencyMap.put("Dash", "DASH");
        currencyMap.put("Ethereum Classic", "ETC");
        currencyMap.put("QTUM", "QTUM");
        currencyMap.put("Dollar", "USD");
        currencyMap.put("US Dollar", "USD");
        currencyMap.put("Euro", "EUR");
        currencyMap.put("Yen", "YEN");
    }


    public static String convert(String currency) {
        return currencyMap.get(currency);
    }
}
