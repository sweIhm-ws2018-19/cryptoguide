package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import cryptoguide.other.AlexaTexts;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class GetHelpRatesIntentHandlerTest {

    private GetHelpRatesIntentHandler handler;

    @Before
    public void setup() {
        handler = new GetHelpRatesIntentHandler();
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
        String responseString = response.getOutputSpeech().toString();
        assertTrue(responseString.contains(AlexaTexts.GHRI_SP));
    }
}
