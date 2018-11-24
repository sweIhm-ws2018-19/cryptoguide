package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import cryptoguide.model.CryptoCurrencyRateRetriever;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class GetCurrencyRateHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("GetCurrencyIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Es gab einen Fehler bei der Anfrage. Bitte versucher es erneut.";
        float  rate = CryptoCurrencyRateRetriever.getCurrentRate("BTC", "EUR");
        if (rate != 0.0f) {
            speechText = "Der aktuelle Kurs von Bitcoin zu Euro beträgt" + rate;
        }

        return input.getResponseBuilder()
                .withSimpleCard("Aktueller Kurs", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
