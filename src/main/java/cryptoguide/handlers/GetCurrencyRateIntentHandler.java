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
 * This handler is triggered when the user wants to know the current currencyrate of two specific currencies
 */
public class GetCurrencyRateIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("GetCurrencyRateIntent"));
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
        String rateDate = slots.get("rateDate").getValue();

        if (primarySymbol == null || secondarySymbol == null) {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GCRI_CTH_ERROR, AlexaTexts.GCRI_SP_ERROR_INVALID_CURRENCY)
                    .withSpeech(AlexaTexts.GCRI_SP_ERROR_INVALID_CURRENCY)
                    .withShouldEndSession(false)
                    .build();
        }

        String speechText;
        double rate;
        if(rateDate == null) {
            //Aktuellen Kurs
            rate = CryptoCurrencyRateRetriever.getCurrentRate(primarySymbol, secondarySymbol);
        } else {
            //Vergangenen Kurs
            long timestamp = TimestampGenerator.convertToTimeStamp(rateDate);
            if(timestamp != -1) {
                rate = CryptoCurrencyRateRetriever.getPastRate(primarySymbol, secondarySymbol, timestamp);
            } else {
                return input.getResponseBuilder()
                        .withSimpleCard(AlexaTexts.GCRI_CTH_ERROR, AlexaTexts.GCRI_SP_ERROR_WRONGDATE)
                        .withSpeech(AlexaTexts.GCRI_SP_ERROR_WRONGDATE)
                        .withShouldEndSession(false)
                        .build();
            }
        }

        if(rate != 0.0) {
            String rateString;
            if(rate < 1.0) {
                rateString = CryptoUtils.doubleToSpeech(rate, 8);
            } else {
                rateString = CryptoUtils.doubleToSpeech(rate, 3);
            }
            speechText = "Der Kurs von " + primaryCurrency +  " zu " +  secondaryCurrency + " ist 1 zu " + rateString;
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
