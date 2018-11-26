package cryptoguide.model;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CryptoCurrencyRateRetriever {

    // This is a static class so we need a private constructor to hide the standard public one.
    private CryptoCurrencyRateRetriever() {
        throw new IllegalStateException("This is a static class, can't create a instance.");
    }

    private static final String CURRENT_RATE_REQUEST = "https://min-api.cryptocompare.com/data/price?";
    private static final Logger logger = Logger.getLogger(CryptoCurrencyRateRetriever.class.getName());

    public static float getCurrentRate(String primaryCurrency, String secondaryCurrency) {
        float value = 0.0f;
        try {
            HttpsURLConnection getRateConnection = (HttpsURLConnection) new URL(CURRENT_RATE_REQUEST + "fsym=" + primaryCurrency + "&tsyms=" + secondaryCurrency).openConnection();
            getRateConnection.setRequestMethod("GET");
            InputStreamReader rateReader = new InputStreamReader(getRateConnection.getInputStream());
            JsonObject jsonResponse = Json.parse(rateReader).asObject();
            if (jsonResponse.get(secondaryCurrency) != null) {
                value = jsonResponse.get(secondaryCurrency).asFloat();
            }

        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not connect to the CryptoCompare-API or created a IOException during connection");
        }

        return value;
    }

}
