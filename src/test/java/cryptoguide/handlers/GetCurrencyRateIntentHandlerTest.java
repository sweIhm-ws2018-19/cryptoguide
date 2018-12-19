package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import cryptoguide.other.AlexaTexts;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class GetCurrencyRateIntentHandlerTest {

    private GetCurrencyRateIntentHandler handler;

    @Before
    public void setup() {
        handler = new GetCurrencyRateIntentHandler();
    }

    @Test
    public void canHandleTest() {
        HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    public void handleTest1() {
        final Response response = TestUtil.standardTestForHandle(handler);
        assertTrue(response.getOutputSpeech().toString().contains(AlexaTexts.GCRI_SP_ERROR_INVALID_CURRENCY));
    }

    @Test
    public void handleTest2() {

        final Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("LIST_OF_CURRENCIES", "Test");
        final HandlerInput inputMock = TestUtil.mockHandlerInput("Euro", "Bitcoin", "0", "0");
        when(inputMock.matches(intentName("GetCurrencyRateIntent"))).thenReturn(true);
        final Optional<Response> res = handler.handle(inputMock);
        assertTrue(res.isPresent());
        final Response response = res.get();

        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());

        assertTrue(response.getOutputSpeech().toString().contains(AlexaTexts.GCRI_SP_ERROR_INVALID_CURRENCY));
    }
}
