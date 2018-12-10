package cryptoguide.model;

import java.util.HashMap;

/**
 * This class provides a map with currencies to their equivalent currency codes.
 */
public class ToSymbolConverter {

    // This is a static class so we need a protected constructor to hide the standard public one.
    ToSymbolConverter() {
        throw new IllegalStateException("This is a static class, can't create a instance.");
    }

    private static final HashMap<String, String> currencyMap = new HashMap<>();

    static{
        currencyMap.put("bitcoin", "BTC");
        currencyMap.put("xrp", "XRP");
        currencyMap.put("ethereum", "ETH");
        currencyMap.put("eos", "EOS");
        currencyMap.put("z cash", "ZEC");
        currencyMap.put("litecoin", "LTC");
        currencyMap.put("dash", "DASH");
        currencyMap.put("ethereum classic", "ETC");
        currencyMap.put("qtum", "QTUM");
        currencyMap.put("dollar", "USD");
        currencyMap.put("us dollar", "USD");
        currencyMap.put("euro", "EUR");
        currencyMap.put("yen", "YEN");
    }

    public static String convert(String currency) {
        return currencyMap.get(currency.toLowerCase());
    }
}
