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

        if (currencyToRemove == null) {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GCCI_CTH, AlexaTexts.GCCI_SP_ERROR)
                    .withSpeech(AlexaTexts.GCCI_SP_ERROR)
                    .withShouldEndSession(false)
                    .build();
        }
        String currencyCode = ToSymbolConverter.convert(currencyToRemove.toLowerCase());

        PortfolioManager.removeAmount(input, currencyCode, amountToRemove);

        String speech = "Ich habe " + amountAsString + currencyToRemove + " aus dem Portfolio entfernt.";
        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GCCI_CTH, speech)
                .withSpeech(speech)
                .withShouldEndSession(false)
                .build();
    }
}
