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
 * This handlers is triggered when the user wants to know the current currencyrate of two specific currencies
 */
public class GetCurrencyRateIntentHandler implements RequestHandler {

    private static final String GET_CURRENCY_RATE_INTENT = "GetCurrencyRateIntent";
    private static final String GET_PAST_CURRENCY_RATE_INTENT = "GetPastCurrencyRateIntent";
    private static final String CONVERT_CURRENCY_AMOUNT_INTENT = "ConvertCurrencyAmountIntent";
    private static final String GET_CURRENCY_TREND_INTENT = "GetCurrencyTrendIntent";

    @Override
    public boolean canHandle(HandlerInput input) {
        return (input.matches(intentName(GET_CURRENCY_RATE_INTENT)) || input.matches(intentName(GET_PAST_CURRENCY_RATE_INTENT))
                || input.matches(intentName(CONVERT_CURRENCY_AMOUNT_INTENT)) || input.matches(intentName(GET_CURRENCY_TREND_INTENT)) );
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

        if (!checkInput(primarySymbol, secondarySymbol)) {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GCRI_CTH_ERROR, AlexaTexts.GCRI_SP_ERROR_INVALID_CURRENCY)
                    .withSpeech(AlexaTexts.GCRI_SP_ERROR_INVALID_CURRENCY)
                    .withShouldEndSession(false)
                    .build();
        }

        String speechText;
        double rate = 0.0;
        int amount = 0;
        double pastRate = 0.0;
        double rateTrend = 0.0;
        if (isACurrentRateIntent(input)) {
            //Aktuellen Kurs
            rate = CryptoCurrencyRateRetriever.getCurrentRate(primarySymbol, secondarySymbol);
        }
        if (input.matches(intentName(CONVERT_CURRENCY_AMOUNT_INTENT))) {
            //Kurs mit Menge
            String amountString = slots.get("amount").getValue();
            amount = Integer.parseInt(amountString);
            if (amount > 0) {
                rate = rate * amount;
            } else {
                return input.getResponseBuilder()
                        .withSimpleCard(AlexaTexts.GCRI_CTH_ERROR, AlexaTexts.GCRI_SP_ERROR_AMOUNT_TOO_SMALL)
                        .withSpeech(AlexaTexts.GCRI_SP_ERROR_AMOUNT_TOO_SMALL)
                        .withShouldEndSession(false)
                        .build();
            }
        }
        if (isAPastRateIntent(input)) {
            //Vergangenen Kurs
            String rateDate = slots.get("rateDate").getValue();
            long timestamp = TimestampGenerator.convertToTimeStamp(rateDate);
            if(timestamp != -1) {
                pastRate = CryptoCurrencyRateRetriever.getPastRate(primarySymbol, secondarySymbol, timestamp);
            } else {
                return input.getResponseBuilder()
                        .withSimpleCard(AlexaTexts.GCRI_CTH_ERROR, AlexaTexts.GCRI_SP_ERROR_WRONGDATE)
                        .withSpeech(AlexaTexts.GCRI_SP_ERROR_WRONGDATE)
                        .withShouldEndSession(false)
                        .build();
            }
        }

        if (input.matches(intentName(GET_CURRENCY_TREND_INTENT)) && (rate != 0)) {
            double rateDiff = rate - pastRate;
            rateTrend =  (100 * rateDiff) / rate;
        }

        if(isValidRate(rate, pastRate)) {
            speechText = createSpeechText(input, rate, pastRate, rateTrend, primaryCurrency, secondaryCurrency, amount);
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

    private String createSpeechText(HandlerInput input, double rate, double pastRate, double rateTrend, String primaryCurrency, String secondaryCurrency, int amount) {
        String rateString;
        String speechText = "";
        String pastRateString;
        String rateTrendString = CryptoUtils.doubleToSpeech(Math.abs(rateTrend), 2);
        if(rate < 1.0 || pastRate < 1.0) {
            rateString = CryptoUtils.doubleToSpeech(rate, 8);
            pastRateString = CryptoUtils.doubleToSpeech(pastRate, 8);
        } else {
            rateString = CryptoUtils.doubleToSpeech(rate, 3);
            pastRateString = CryptoUtils.doubleToSpeech(pastRate, 3);
        }
        if (input.matches(intentName(GET_CURRENCY_RATE_INTENT))) {
            speechText = "Der aktuelle Kurs von " + primaryCurrency +  " zu " +  secondaryCurrency + " ist 1 zu " + rateString;
        }
        if (input.matches(intentName(CONVERT_CURRENCY_AMOUNT_INTENT))) {
            if(amount == 1) {
                speechText = amount + " " + primaryCurrency + "ist zurzeit " + rateString + secondaryCurrency +  " wert.";
            } else {
                speechText = amount + " " + primaryCurrency + "sind zurzeit " + rateString + secondaryCurrency +  " wert.";
            }
        }
        if (input.matches(intentName(GET_PAST_CURRENCY_RATE_INTENT))) {
            speechText = "Der Kurs von " + primaryCurrency +  " zu " +  secondaryCurrency + " war 1 zu " + pastRateString;
        }
        if (input.matches(intentName(GET_CURRENCY_TREND_INTENT))) {
            if (rateTrend >= 0) {
                speechText = "Der Kurs von " + primaryCurrency +  " zu " +  secondaryCurrency + " ist von " +  pastRateString + " auf " + rateString
                + " gestiegen, was einer Steigerung von " + rateTrendString + " Prozent entspricht.";
            } else {
                speechText = "Der Kurs " + primaryCurrency +  " zu " +  secondaryCurrency + " ist von " +  pastRateString + " auf " + rateString
                        + " gesunken, was einem Kursverlust von " + rateTrendString + " Prozent entspricht.";
            }
        }
        return speechText;
    }

    private boolean checkInput(String primarySymbol, String secondarySymbol) {
        return primarySymbol != null && secondarySymbol != null;
    }

    private boolean isACurrentRateIntent(HandlerInput input) {
        return input.matches(intentName(CONVERT_CURRENCY_AMOUNT_INTENT)) || input.matches(intentName(GET_CURRENCY_RATE_INTENT)) || input.matches(intentName(GET_CURRENCY_TREND_INTENT));
    }

    private boolean isAPastRateIntent(HandlerInput input) {
        return input.matches(intentName(GET_PAST_CURRENCY_RATE_INTENT)) || input.matches(intentName(GET_CURRENCY_TREND_INTENT));
    }

    private boolean isValidRate(double rate, double pastRate) {
        return rate != 0.0 || pastRate != 0.0;
    }
}
