package cryptoguide.other;

import java.text.DecimalFormat;
import java.util.Map;

public class CryptoUtils {

    CryptoUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String doubleToSpeech(double db, int decimal) {
        if(decimal < 0) {
            throw new IllegalArgumentException("decimal must be equal or greater than zero.");
        } else if(decimal == 0) {
            return new DecimalFormat("#").format(db);
        } else {
            String format = "#.";
            for(int i = 0; i < decimal; i++) {
                format = format.concat("#");
            }
            return new DecimalFormat(format).format(db);
        }
    }

    public static String currencyMapToSpeech(Map<String, Object> inputMap) {
        StringBuilder builder = new StringBuilder("In deinem Portfolio befinden sich ");
        if(inputMap.isEmpty()) {
            builder.append("aktuell keine Währungen.");
        } else {
            for(Map.Entry<String, Object> entry : inputMap.entrySet()) {
                builder.append((entry.getValue() + " " + entry.getKey() + " "));
            }
        }
        return builder.toString();
    }
}
