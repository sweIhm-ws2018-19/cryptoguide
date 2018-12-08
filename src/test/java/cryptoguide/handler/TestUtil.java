package cryptoguide.handler;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.response.ResponseBuilder;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

class TestUtil {

    public static HandlerInput mockHandlerInput(String primaryCurrency, String secondaryCurrency, String rateDate, String currencyAmount) {

        final AttributesManager attributesManagerMock = Mockito.mock(AttributesManager.class);


        // Mock Slots
        final RequestEnvelope requestEnvelopeMock = RequestEnvelope.builder()
                .withRequest(IntentRequest.builder()
                        .withIntent(Intent.builder()
                                .putSlotsItem("primaryCurrency", Slot.builder()
                                        .withName("primaryCurrency")
                                        .withValue(primaryCurrency)
                                        .build())
                                .putSlotsItem("secondaryCurrency", Slot.builder()
                                        .withName("secondaryCurrency")
                                        .withValue(secondaryCurrency)
                                        .build())
                                .putSlotsItem("rateDate", Slot.builder()
                                        .withName("rateDate")
                                        .withValue(rateDate)
                                        .build())
                                .putSlotsItem("currencyAmount", Slot.builder()
                                        .withName("currencyAmount")
                                        .withValue(currencyAmount)
                                        .build())
                                .build())
                        .build())
                .build();

        // Mock Handler input attributes
        final HandlerInput input = Mockito.mock(HandlerInput.class);
        when(input.getAttributesManager()).thenReturn(attributesManagerMock);
        when(input.getResponseBuilder()).thenReturn(new ResponseBuilder());
        when(input.getRequestEnvelope()).thenReturn(requestEnvelopeMock);
        return input;
    }

    public static Response standardTestForHandle(RequestHandler handler) {

        final Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("LIST_OF_CURRENCIES", "Test");
        final HandlerInput inputMock = TestUtil.mockHandlerInput("BTC", "asdf", "0", "0");
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();

        assertFalse(response.getShouldEndSession());
        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());
        return response;

    }
}