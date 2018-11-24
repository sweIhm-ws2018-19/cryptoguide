package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import cryptoguide.model.CryptoCurrencyRateRetriever;
import cryptoguide.other.AlexaTexts;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class GetCurrentCurrencyRateIntentHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("GetCurrencyIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = AlexaTexts.GCCRI_SP_ERROR;
        float  rate = CryptoCurrencyRateRetriever.getCurrentRate("BTC", "EUR");
        if (rate != 0.0f) {
            speechText = "Der aktuelle Kurs von Bitcoin zu Euro ist 1 zu " + rate;
        }

        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GCCRI_CTH, speechText)
                .withSpeech(speechText)
                .build();
    }
}
