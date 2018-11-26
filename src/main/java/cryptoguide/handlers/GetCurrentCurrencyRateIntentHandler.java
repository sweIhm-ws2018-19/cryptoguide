package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import cryptoguide.model.CryptoCurrencyRateRetriever;
import cryptoguide.model.ToSymbolConverter;
import cryptoguide.other.AlexaTexts;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class GetCurrentCurrencyRateIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("GetCurrentCurrencyRateIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();

        // Get our two currencies
        Slot primaryCurrency = slots.get("primaryCurrency");
        Slot secondaryCurrency = slots.get("secondaryCurrency");
        String primarySymbol = ToSymbolConverter.convert(primaryCurrency.toString());
        String secondarySymbol = ToSymbolConverter.convert(primaryCurrency.toString());
        String speechText = AlexaTexts.GCCRI_SP_ERROR;

        if (primarySymbol == null || secondarySymbol == null) {
            return input.getResponseBuilder()
                    .withSimpleCard("Währung ungültig", AlexaTexts.GCCRI_SP_SC_ERROR)
                    .withSpeech(AlexaTexts.GCCRI_SP_SC_ERROR)
                    .build();
        }

        float  rate = CryptoCurrencyRateRetriever.getCurrentRate(primarySymbol, secondarySymbol);
        if (rate != 0.0f) {
            speechText = "Der aktuelle Kurs von " + primaryCurrency.toString() +  "zu" +  secondaryCurrency.toString() + "ist 1 zu " + rate;
        }

        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GCCRI_CTH, speechText)
                .withSpeech(speechText)
                .build();
    }
}
