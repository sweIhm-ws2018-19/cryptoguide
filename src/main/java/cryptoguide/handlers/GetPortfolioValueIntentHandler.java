package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import cryptoguide.model.CryptoCurrencyRateRetriever;
import cryptoguide.model.RequestEnvelopeHelper;
import cryptoguide.model.TimestampGenerator;
import cryptoguide.model.ToSymbolConverter;
import cryptoguide.other.AlexaTexts;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class GetPortfolioValueIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return (input.matches(intentName("GetPortfolioValueIntent")) || input.matches(intentName("GetPastPortfolioValueIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        Map<String, Slot> slots = RequestEnvelopeHelper.getSlots(input);
        String currency = slots.get("primaryCurrency").getValue();

        if (currency == null) {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GCCI_CTH, AlexaTexts.GCCI_SP_ERROR)
                    .withSpeech(AlexaTexts.GCCI_SP_ERROR)
                    .withShouldEndSession(false)
                    .build();
        }
        String currencyCode = ToSymbolConverter.convert(currency.toLowerCase());

        Map<String, Object> persistentAttributes = input.getAttributesManager().getPersistentAttributes();
        Iterator<Map.Entry<String, Object>> itr = persistentAttributes.entrySet().iterator();
        float value = 0;

        if (input.matches(intentName("GetPortfolioValueIntent"))) {
            while (itr.hasNext()) {
                Map.Entry<String, Object> entry = itr.next();
                float amount = (int) entry.getValue();
                value += CryptoCurrencyRateRetriever.getCurrentRate(entry.getKey(), currencyCode) * amount;
            }
        } else if (input.matches(intentName("GetPastPortfolioValueIntent"))) {
            long timestamp = TimestampGenerator.convertToTimeStamp(slots.get("rateDate").getValue());
            while (itr.hasNext()) {
                Map.Entry<String, Object> entry = itr.next();
                float amount = (int) entry.getValue();
                value += CryptoCurrencyRateRetriever.getPastRate(entry.getKey(), currencyCode, timestamp) * amount;
            }
        }

        String speech = "Dein Portfolio ist aktuell " + value + currency + " wert ";
        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GCCI_CTH, speech)
                .withSpeech(speech)
                .withShouldEndSession(false)
                .build();

    }
}
