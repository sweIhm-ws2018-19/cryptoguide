package cryptoguide.other;

import java.text.DecimalFormat;

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
}
