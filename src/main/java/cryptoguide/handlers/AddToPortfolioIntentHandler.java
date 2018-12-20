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

public class AddToPortfolioIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return (input.matches(intentName("AddToPortfolioIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        Map<String, Slot> slots = RequestEnvelopeHelper.getSlots(input);
        String currency = slots.get("primaryCurrency").getValue();
        String amountString = slots.get("amount").getValue();
        int amount = Integer.parseInt(amountString);

        if (currency == null) {
            return input.getResponseBuilder()
                    .withSimpleCard(AlexaTexts.GCCI_CTH, AlexaTexts.GCCI_SP_ERROR)
                    .withSpeech(AlexaTexts.GCCI_SP_ERROR)
                    .withShouldEndSession(false)
                    .build();
        }
        String currencyCode = ToSymbolConverter.convert(currency.toLowerCase());

        PortfolioManager.addAmount(input, currencyCode, amount);

        String speech = "Ich habe " + amountString + currency + " dem Portfolio hinzugefuegt.";
        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GCCI_CTH, speech)
                .withSpeech(speech)
                .withShouldEndSession(false)
                .build();

    }
}
