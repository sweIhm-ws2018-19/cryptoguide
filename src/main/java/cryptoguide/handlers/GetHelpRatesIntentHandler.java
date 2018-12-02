package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import cryptoguide.other.AlexaTexts;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

/**
 * This handler is triggered when the user needs help with the rates.
 */
public class GetHelpRatesIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("GetHelpRatesIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GHRI_CTH, AlexaTexts.GHRI_SP)
                .withSpeech(AlexaTexts.GHRI_SP)
                .withShouldEndSession(false)
                .build();
    }
}
