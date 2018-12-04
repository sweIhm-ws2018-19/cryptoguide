package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import cryptoguide.model.CryptoCurrencyRateRetriever;
import cryptoguide.model.TimestampGenerator;
import cryptoguide.model.ToSymbolConverter;
import cryptoguide.other.AlexaTexts;
import cryptoguide.other.CryptoUtils;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

/**
 * This handler is triggered when the user wants to know the currencyrate of two specific currencies in the past
 */
public class GetPastCurrencyRateIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("GetPastCurrencyRateIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();

        String primaryCurrency = slots.get("primaryCurrency").getValue();
        String secondaryCurrency = slots.get("secondaryCurrency").getValue();
        String currentDate = slots.get("rateDate").getValue();

        String primarySymbol = ToSymbolConverter.convert(primaryCurrency.toLowerCase());
        String secondarySymbol = ToSymbolConverter.convert(secondaryCurrency.toLowerCase());
        Long date = TimestampGenerator.convertToTimeStamp(currentDate.toLowerCase());
        String speechText;

        if (primarySymbol == null || secondarySymbol == null || date == null) {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GPCRI_CTH_ERROR, AlexaTexts.GPCRI_SP_SC_ERROR)
                    .withSpeech(AlexaTexts.GPCRI_SP_SC_ERROR)
                    .withShouldEndSession(false)
                    .build();
        }

        double rate = CryptoCurrencyRateRetriever.getPastRate(primarySymbol, secondarySymbol, date);

        if (rate != 0.0) {
            String rateString;
            if(rate < 1.0) {
                rateString = CryptoUtils.doubleToSpeech(rate, 8);
            } else {
                rateString = CryptoUtils.doubleToSpeech(rate, 3);
            }
            speechText = "Der Kurs am " + date + "von " + primaryCurrency +  " zu " +  secondaryCurrency + " ist 1 zu " + rateString;
        } else {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GPCRI_CTH, AlexaTexts.GPCRI_SP_ERROR)
                    .withSpeech(AlexaTexts.GPCRI_SP_ERROR)
                    .withShouldEndSession(false)
                    .build();
        }

        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GPCRI_CTH, speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
