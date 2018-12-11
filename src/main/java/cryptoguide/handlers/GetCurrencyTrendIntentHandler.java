package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import cryptoguide.model.CryptoCurrencyRateRetriever;
import cryptoguide.model.TimestampGenerator;
import cryptoguide.model.ToSymbolConverter;
import cryptoguide.other.AlexaTexts;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

/**
 * This handler is triggered when the user wants to know the currency trend since a certain date.
 */
public class GetCurrencyTrendIntentHandler implements RequestHandler {
    private static final String GET_CURRENCY_TREND_INTENT = "GetCurrencyTrendIntent";

    @Override
    public boolean canHandle(HandlerInput input) {
        return false;
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

        if (primarySymbol == null || secondarySymbol == null) {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GCRI_CTH_ERROR, AlexaTexts.GCRI_SP_ERROR_INVALID_CURRENCY)
                    .withSpeech(AlexaTexts.GCRI_SP_ERROR_INVALID_CURRENCY)
                    .withShouldEndSession(false)
                    .build();
        }
        String speechText;
        String rateDate = slots.get("rateDate").getValue();
        long timestamp = TimestampGenerator.convertToTimeStamp(rateDate);
        double ratePast = CryptoCurrencyRateRetriever.getPastRate(primaryCurrency, secondaryCurrency, timestamp);
        double rateCurrent = CryptoCurrencyRateRetriever.getCurrentRate(primaryCurrency, secondaryCurrency);
        double rateDiff = rateCurrent - ratePast;
        double rateTrend = 0;
        if (input.matches(intentName(GET_CURRENCY_TREND_INTENT))) {

            //Kursentwicklung
            if (rateDiff > 0) {
                rateTrend = (ratePast / 100) * rateDiff;
            } else if (rateDiff < 0) {
                rateTrend = (rateCurrent / 100) * rateDiff;

            }

        } else {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GCRI_CTH_ERROR, AlexaTexts.GCRI_SP_ERROR_WRONGDATE)
                    .withSpeech(AlexaTexts.GCRI_SP_ERROR_WRONGDATE)
                    .withShouldEndSession(false)
                    .build();
        }

        if (rateTrend != 0.0) {
            speechText = createSpeechText(input, rateTrend, primaryCurrency, secondaryCurrency);
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

    private String createSpeechText(HandlerInput input, double rateTrend, String primaryCurrency, String secondaryCurrency) {
        String speechText = "";
        if (input.matches(intentName(GET_CURRENCY_TREND_INTENT))) {
            speechText = "Der Trend von " + primaryCurrency + " zu " + secondaryCurrency + " beträgt " + rateTrend;
        }
        return speechText;
    }
}

