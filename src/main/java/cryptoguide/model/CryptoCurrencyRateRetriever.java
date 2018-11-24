package cryptoguide.model;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class CryptoCurrencyRateRetriever {

    private static final String CURRENT_RATE_REQUEST = "https://min-api.cryptocompare.com/data/price?";

    public static float getCurrentRate(String primaryCurrency, String secondaryCurrency) {

        float value = 0.0f;
        try {
            HttpURLConnection getRateConnection = (HttpURLConnection) new URL(CURRENT_RATE_REQUEST + "fsym=" + primaryCurrency + "&tsyms=" + secondaryCurrency).openConnection();
            getRateConnection.setRequestMethod("GET");
            InputStreamReader rateReader = new InputStreamReader(getRateConnection.getInputStream());
            JsonObject jsonResponse = Json.parse(rateReader).asObject();
            if (jsonResponse.get(secondaryCurrency) != null) {
                value = jsonResponse.get(secondaryCurrency).asFloat();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static float getPastRate(Date pastDate, String primaryCurrency, String secondaryCurrency) {
        return  0.0f;
    }

}
