package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import cryptoguide.model.CryptoCurrencyRateRetriever;
import cryptoguide.model.ToSymbolConverter;
import cryptoguide.other.AlexaTexts;
import cryptoguide.other.CryptoUtils;

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
        String primaryCurrency = slots.get("primaryCurrency").getValue();
        String secondaryCurrency = slots.get("secondaryCurrency").getValue();
        String primarySymbol = ToSymbolConverter.convert(primaryCurrency.toLowerCase());
        String secondarySymbol = ToSymbolConverter.convert(secondaryCurrency.toLowerCase());
        String speechText;

        if (primarySymbol == null || secondarySymbol == null) {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GCCRI_CTH_ERROR, AlexaTexts.GCCRI_SP_SC_ERROR)
                    .withSpeech(AlexaTexts.GCCRI_SP_SC_ERROR)
                    .withShouldEndSession(false)
                    .build();
        }

        double rate = CryptoCurrencyRateRetriever.getCurrentRate(primarySymbol, secondarySymbol);
        if (rate != 0.0) {
            String rateString;
            if(rate < 1.0) {
                rateString = CryptoUtils.doubleToSpeech(rate, 8);
            } else {
                rateString = CryptoUtils.doubleToSpeech(rate, 3);
            }
            speechText = "Der aktuelle Kurs von " + primaryCurrency +  " zu " +  secondaryCurrency + " ist 1 zu " + rateString;
        } else {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GCCRI_CTH, AlexaTexts.GCCRI_SP_ERROR)
                    .withSpeech(AlexaTexts.GCCRI_SP_ERROR)
                    .withShouldEndSession(false)
                    .build();
        }

        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GCCRI_CTH, speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
