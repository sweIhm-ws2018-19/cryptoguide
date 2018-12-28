package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import cryptoguide.model.PortfolioManager;
import cryptoguide.other.AlexaTexts;
import cryptoguide.other.CryptoUtils;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class GetPortfolioContentIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return (input.matches(intentName("GetPortfolioContentIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Map<String, Object> content = PortfolioManager.getPortfolioContent(input);
        String speech = CryptoUtils.currencyMapToSpeech(content);

        return input.getResponseBuilder()
                .withSimpleCard(AlexaTexts.GPCI_CTH, speech)
                .withSpeech(speech)
                .withShouldEndSession(false)
                .build();
    }
}
