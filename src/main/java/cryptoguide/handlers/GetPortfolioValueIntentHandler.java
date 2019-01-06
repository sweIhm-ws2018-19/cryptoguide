package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import cryptoguide.model.*;
import cryptoguide.other.AlexaTexts;
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
        Map<String, Object> content = PortfolioManager.getPortfolioContent(input);

        float value = 0;
        String speech = "Es ist ein Fehler aufgetreten.";
        if (input.matches(intentName("GetPortfolioValueIntent"))) {
            for(Map.Entry<String, Object> entry : content.entrySet()) {
                int amount = Integer.parseInt((String) entry.getValue());
                value += CryptoCurrencyRateRetriever.getCurrentRate(entry.getKey(), currencyCode) * amount;
            }
            speech = "Dein Portfolio ist aktuell " + value + currency + " wert ";

        } else if (input.matches(intentName("GetPastPortfolioValueIntent"))) {
            long timestamp = TimestampGenerator.convertToTimeStamp(slots.get("rateDate").getValue());
            for(Map.Entry<String, Object> entry : content.entrySet()) {
                int amount = Integer.parseInt((String) entry.getValue());
                value += CryptoCurrencyRateRetriever.getPastRate(entry.getKey(), currencyCode, timestamp) * amount;
            }
            speech = "Dein Portfolio war in der Vergangenheit " + value + currency + " wert ";
        }

        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GPVI_CTH, speech)
                .withSpeech(speech)
                .withShouldEndSession(false)
                .build();

    }
}
