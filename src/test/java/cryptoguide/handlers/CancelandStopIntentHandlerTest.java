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
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class CancelandStopIntentHandlerTest {

    private CancelandStopIntentHandler handler;

    @Before
    public void setup() {
        handler = new CancelandStopIntentHandler();
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
        assertTrue(response.getOutputSpeech().toString().contains(AlexaTexts.CSI_SP));
    }

    @Test
    public void handleTest2() {
        final Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("LIST_OF_CURRENCIES", "Test");
        final HandlerInput inputMock = TestUtil.mockHandlerInput("Bitcoin", "Dollar", "2018-07-07", "5");
        when(inputMock.matches(any())).thenReturn(true);
        final Optional<Response> res = handler.handle(inputMock);

        assertTrue(res.isPresent());
        final Response response = res.get();

        assertFalse(response.getShouldEndSession());
        assertNotEquals("Test", response.getReprompt());
        assertNotNull(response.getOutputSpeech());
        assertTrue(response.getOutputSpeech().toString().contains("Vergangenheit"));
    }


}
