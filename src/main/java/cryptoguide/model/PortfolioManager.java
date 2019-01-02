package cryptoguide.model;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;

import java.util.Map;

public class PortfolioManager {

    // This is a static class so we need a protected constructor to hide the standard public one.
    PortfolioManager() {
        throw new IllegalStateException("This is a static class, can't create a instance.");
    }

    public static Map<String, Object> getPortfolioContent(HandlerInput input) {
        return input.getAttributesManager().getPersistentAttributes();
    }

    public static int getAmount(HandlerInput input, String currencyCode) {
        Map<String, Object> all = getPortfolioContent(input);
        if(!all.containsKey(currencyCode)) {
            return 0;
        } else {
            Object obj = all.get(currencyCode);
            return Integer.valueOf((String) obj).intValue();
        }
    }

    public static boolean addAmount(HandlerInput input, String currencyCode, int amount) {
        if(amount <= 0) {
            return false;
        } else {
            int amountInDB = getAmount(input, currencyCode);
            AttributesManager manager = input.getAttributesManager();
            Map<String, Object> attributes = manager.getPersistentAttributes();
            attributes.put(currencyCode, String.valueOf(amount + amountInDB));
            manager.setPersistentAttributes(attributes);
            manager.savePersistentAttributes();
            return true;
        }
    }

    public static boolean removeAmount(HandlerInput input, String currencyCode, int amount) {
        if (amount <= 0) {
            return false;
        } else {
            int amountInDB = getAmount(input, currencyCode);
            int newValue = amountInDB - amount;
            if (newValue < 0) {
                return false;
            } else {
                AttributesManager manager = input.getAttributesManager();
                Map<String, Object> attributes = manager.getPersistentAttributes();
                if (newValue == 0) {
                    attributes.remove(currencyCode);
                } else {
                    attributes.put(currencyCode, String.valueOf(newValue));
                }
                manager.setPersistentAttributes(attributes);
                manager.savePersistentAttributes();
                return true;
            }
        }
    }

}
