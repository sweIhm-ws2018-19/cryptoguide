package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import cryptoguide.model.PortfolioManager;
import cryptoguide.model.RequestEnvelopeHelper;
import cryptoguide.model.ToSymbolConverter;
import cryptoguide.other.AlexaTexts;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class RemoveFromPortfolioIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return (input.matches(intentName("RemoveFromPortfolioIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        Map<String, Slot> slots = RequestEnvelopeHelper.getSlots(input);
        String currencyToRemove = slots.get("primaryCurrency").getValue();
        String amountAsString = slots.get("amount").getValue();
        int amountToRemove = Integer.parseInt(amountAsString);

        if (currencyToRemove == null || amountAsString == null) {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.RFPI_CTH, AlexaTexts.GCCI_SP_ERROR)
                    .withSpeech(AlexaTexts.GCCI_SP_ERROR)
                    .withShouldEndSession(false)
                    .build();
        }

        if (amountToRemove <= 0) {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.RFPI_CTH, AlexaTexts.RFPI_AMOUNTLOW_ERROR)
                    .withSpeech(AlexaTexts.GCCI_SP_ERROR)
                    .withShouldEndSession(false)
                    .build();
        }

        String currencyCode = ToSymbolConverter.convert(currencyToRemove.toLowerCase());

        if(PortfolioManager.removeAmount(input, currencyCode, amountToRemove)) {
            String speech = "Ich habe " + amountAsString + " " + currencyToRemove + " aus dem Portfolio entfernt.";
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.RFPI_CTH, speech)
                    .withSpeech(speech)
                    .withShouldEndSession(false)
                    .build();
        } else {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.RFPI_CTH, AlexaTexts.RFPI_UNKNOWN_ERROR)
                    .withSpeech(AlexaTexts.RFPI_UNKNOWN_ERROR)
                    .withShouldEndSession(false)
                    .build();
        }
    }
}
