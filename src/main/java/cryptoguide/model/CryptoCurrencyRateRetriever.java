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
    CryptoCurrencyRateRetriever() {
        throw new IllegalStateException("This is a static class, can't create a instance.");
    }

    private static final String CURRENT_RATE_REQUEST = "https://min-api.cryptocompare.com/data/price?";
    private static final String PAST_RATE_REQUEST = "https://min-api.cryptocompare.com/data/pricehistorical?";
    private static final Logger logger = Logger.getLogger(CryptoCurrencyRateRetriever.class.getName());

    public static double getCurrentRate(String primaryCode, String secondaryCode) {
        if(primaryCode.equals(secondaryCode)) return 1.00;
        double value = 0.0;
        try {
            HttpsURLConnection getRateConnection = (HttpsURLConnection) new URL(CURRENT_RATE_REQUEST + "fsym=" + primaryCode + "&tsyms=" + secondaryCode).openConnection();
            getRateConnection.setRequestMethod("GET");
            InputStreamReader rateReader = new InputStreamReader(getRateConnection.getInputStream());
            JsonObject jsonResponse = Json.parse(rateReader).asObject();
            if (jsonResponse.get(secondaryCode) != null) {
                value = jsonResponse.get(secondaryCode).asDouble();
            }

        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not connect to the CryptoCompare-API or created a IOException during connection");
        }

        return value;
    }

    public static double getPastRate(String primaryCode, String secondaryCode, long timestamp) {
        if(primaryCode.equals(secondaryCode)) return 1.00;
        if(timestamp >= TimestampGenerator.getCurrentTimeStamp()) {
            return getCurrentRate(primaryCode, secondaryCode);
        } else {
            double value = 0.0;
            try {
                HttpsURLConnection getRateConnection = (HttpsURLConnection) new URL(PAST_RATE_REQUEST + "fsym=" + primaryCode + "&tsyms=" + secondaryCode + "&ts=" + timestamp + "&calculationType=Close").openConnection();
                getRateConnection.setRequestMethod("GET");
                InputStreamReader rateReader = new InputStreamReader(getRateConnection.getInputStream());
                JsonObject jsonResponse = Json.parse(rateReader).asObject();
                if (jsonResponse.get(primaryCode) != null && jsonResponse.get(primaryCode).asObject().get(secondaryCode) != null) {
                    value = jsonResponse.get(primaryCode).asObject().get(secondaryCode).asDouble();
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "Could not connect to the CryptoCompare-API or created a IOException during connection");
            }
            return value;
        }
    }

}
