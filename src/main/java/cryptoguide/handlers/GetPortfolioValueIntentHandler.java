package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import cryptoguide.model.CryptoCurrencyRateRetriever;
import cryptoguide.model.RequestEnvelopeHelper;
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

        String currency = RequestEnvelopeHelper.getPrimaryCurrencyString(input);

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
        while(itr.hasNext())
        {
            Map.Entry<String, Object> entry = itr.next();
            float amount = (float) entry.getValue();
            value += CryptoCurrencyRateRetriever.getCurrentRate(entry.getKey(), currencyCode) * amount;
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }

        String speech = "Dein Portfolio ist aktuell " + value + currency + " wert ";
        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GCCI_CTH, speech)
                .withSpeech(speech)
                .withShouldEndSession(false)
                .build();

    }
}
