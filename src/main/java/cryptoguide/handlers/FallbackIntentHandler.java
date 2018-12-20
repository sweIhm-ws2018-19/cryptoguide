package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import cryptoguide.other.AlexaTexts;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

// 2018-July-09: AMAZON.FallbackIntent is only currently available in en-US locale.
//              This handlers will not be triggered except in that locale, so it can be
//              safely deployed for any locale.
public class FallbackIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.FallbackIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        return input.getResponseBuilder()
                .withSpeech(AlexaTexts.FBI_SP)
                .withSimpleCard(AlexaTexts.FBI_CTH, AlexaTexts.FBI_CTT)
                .withReprompt(AlexaTexts.FBI_SP)
                .withShouldEndSession(false)
                .build();
    }

}
