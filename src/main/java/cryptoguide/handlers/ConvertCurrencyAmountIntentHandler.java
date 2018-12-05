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


/**
 * This handler is triggered when the user wants to know the current currencyrate of two specific currencies
 */
public class ConvertCurrencyAmountIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ConvertCurrencyAmountIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();

        // Get our two currencies and the date (if available)
        String primaryCurrency = slots.get("primaryCurrency").getValue();
        String secondaryCurrency = slots.get("secondaryCurrency").getValue();
        String primarySymbol = ToSymbolConverter.convert(primaryCurrency.toLowerCase());
        String secondarySymbol = ToSymbolConverter.convert(secondaryCurrency.toLowerCase());
        String amountString = slots.get("amount").getValue();
        int amount = Integer.parseInt(amountString);

        if (primarySymbol == null || secondarySymbol == null || amount == 0) {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GCRI_CTH_ERROR, AlexaTexts.GCRI_SP_ERROR_INVALID_CURRENCY)
                    .withSpeech(AlexaTexts.GCRI_SP_ERROR_INVALID_CURRENCY)
                    .withShouldEndSession(false)
                    .build();
        }

        String speechText;
        double rate;

        //Aktuellen Kurs
        rate = CryptoCurrencyRateRetriever.getCurrentRate(primarySymbol, secondarySymbol);
        rate = rate * amount;

        if(rate != 0.0) {
            String rateString;
            if(rate < 1.0) {
                rateString = CryptoUtils.doubleToSpeech(rate, 8);
            } else {
                rateString = CryptoUtils.doubleToSpeech(rate, 3);
            }
            speechText = amountString + primaryCurrency + "ist zurzeit" + rateString + secondaryCurrency +  " wert.";
        } else {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GCRI_CTH_ERROR, AlexaTexts.GCRI_SP_ERROR_CRYPTOCOMPARE)
                    .withSpeech(AlexaTexts.GCRI_SP_ERROR_CRYPTOCOMPARE)
                    .withShouldEndSession(false)
                    .build();
        }

        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GCRI_CTH, speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
