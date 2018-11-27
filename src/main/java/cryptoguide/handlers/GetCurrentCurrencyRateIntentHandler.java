package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import cryptoguide.model.CryptoCurrencyRateRetriever;
import cryptoguide.model.ToSymbolConverter;
import cryptoguide.other.AlexaTexts;

import java.text.DecimalFormat;
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
        String speechText = AlexaTexts.GCCRI_SP_ERROR;

        if (primarySymbol == null || secondarySymbol == null) {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GCCRI_CTH_ERROR, AlexaTexts.GCCRI_SP_SC_ERROR)
                    .withSpeech(AlexaTexts.GCCRI_SP_SC_ERROR)
                    .build();
        }

        float rate = CryptoCurrencyRateRetriever.getCurrentRate(primarySymbol, secondarySymbol);
        if (rate != 0.0f) {
            String rateString;
            DecimalFormat tenformat = new DecimalFormat("#.##########");
            DecimalFormat twoformat = new DecimalFormat("#.##");
            if(rate < 1.0f) {
                rateString = tenformat.format(rate);
            } else {
                rateString = twoformat.format(rate);
            }
            speechText = "Der aktuelle Kurs von " + primaryCurrency +  " zu " +  secondaryCurrency + " ist 1 zu " + rateString;
        }

        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GCCRI_CTH, speechText)
                .withSpeech(speechText)
                .build();
    }
}
