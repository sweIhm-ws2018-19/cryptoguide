package cryptoguide.model;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Slot;

import java.util.Map;

public class RequestEnvelopeHelper {

    RequestEnvelopeHelper() {
        throw new IllegalStateException("Static class, can't create an instance.");
    }

    public static Map<String, Slot> getSlots (HandlerInput input) {
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        return intent.getSlots();
    }
}
