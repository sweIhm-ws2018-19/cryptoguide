package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import cryptoguide.other.AlexaTexts;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

/**
 * This handlers is triggered when the user wants to get help with the portfolio
 */
public class GetHelpPortfolioIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("GetHelpPortfolioIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GHPI_CTH, AlexaTexts.GHPI_SP)
                .withSpeech(AlexaTexts.GHPI_SP)
                .withShouldEndSession(false)
                .build();
    }
}
