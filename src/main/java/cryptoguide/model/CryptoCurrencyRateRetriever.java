package cryptoguide.model;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides the skill with crytocurrencyrates retrieved from cryptocompare.com
 */
public class CryptoCurrencyRateRetriever {

    // This is a static class so we need a private constructor to hide the standard public one.
    private CryptoCurrencyRateRetriever() {
        throw new IllegalStateException("This is a static class, can't create a instance.");
    }

    private static final String CURRENT_RATE_REQUEST = "https://min-api.cryptocompare.com/data/price?";
    private static final String PAST_RATE_REQUEST = "https://min-api.cryptocompare.com/data/pricehistorical?";
    private static final Logger logger = Logger.getLogger(CryptoCurrencyRateRetriever.class.getName());

    public static double getCurrentRate(String primaryCurrency, String secondaryCurrency) {
        if(primaryCurrency.equals(secondaryCurrency)) return 1.00;
        double value = 0.0;
        try {
            HttpsURLConnection getRateConnection = (HttpsURLConnection) new URL(CURRENT_RATE_REQUEST + "fsym=" + primaryCurrency + "&tsyms=" + secondaryCurrency).openConnection();
            getRateConnection.setRequestMethod("GET");
            InputStreamReader rateReader = new InputStreamReader(getRateConnection.getInputStream());
            JsonObject jsonResponse = Json.parse(rateReader).asObject();
            if (jsonResponse.get(secondaryCurrency) != null) {
                value = jsonResponse.get(secondaryCurrency).asDouble();
            }

        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not connect to the CryptoCompare-API or created a IOException during connection");
        }

        return value;
    }

    public static double getPastRate(String primaryCurrency, String secondaryCurrency, long timestamp) {
        if(primaryCurrency.equals(secondaryCurrency)) return 1.00;
        if(timestamp >= TimestampGenerator.getCurrentTimeStamp()) {
            return getCurrentRate(primaryCurrency, secondaryCurrency);
        } else {
            double value = 0.0;
            try {
                HttpsURLConnection getRateConnection = (HttpsURLConnection) new URL(PAST_RATE_REQUEST + "fsym=" + primaryCurrency + "&tsyms=" + secondaryCurrency + "&ts=" + timestamp).openConnection();
                getRateConnection.setRequestMethod("GET");
                InputStreamReader rateReader = new InputStreamReader(getRateConnection.getInputStream());
                JsonObject jsonResponse = Json.parse(rateReader).asObject();
                if (jsonResponse.get(primaryCurrency) != null && jsonResponse.get(primaryCurrency).asObject().get(secondaryCurrency) != null) {
                    value = jsonResponse.get(primaryCurrency).asObject().get(secondaryCurrency).asDouble();
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "Could not connect to the CryptoCompare-API or created a IOException during connection");
            }
            return value;
        }
    }

}
