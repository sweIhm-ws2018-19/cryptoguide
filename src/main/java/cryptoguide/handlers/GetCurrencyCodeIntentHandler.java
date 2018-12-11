package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import cryptoguide.model.ToSymbolConverter;
import cryptoguide.other.AlexaTexts;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

/**
 * This handler is triggered when the user wants to know a currency code to a specific currency.
 */
public class GetCurrencyCodeIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("GetCurrencyCodeIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();

        String currency = slots.get("primaryCurrency").getValue();

        if (currency == null) {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GCCI_CTH, AlexaTexts.GCCI_SP_ERROR)
                    .withSpeech(AlexaTexts.GCCI_SP_ERROR)
                    .withShouldEndSession(false)
                    .build();
        }

        String code = ToSymbolConverter.convert(currency.toLowerCase());
        code = code.replaceAll(".(?!$)", "$0 ");
        String speech = "Der Währungscode zu " + currency + " lautet " + code;
        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GCCI_CTH, speech)
                .withSpeech(speech)
                .withShouldEndSession(false)
                .build();

    }
}
