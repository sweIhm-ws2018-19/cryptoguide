package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import cryptoguide.other.AlexaTexts;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class GetCurrencyCodeIntentHandlerTest {

    private GetCurrencyCodeIntentHandler handler;

    @Before
    public void setup() {
        handler = new GetCurrencyCodeIntentHandler();
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
        assertTrue(response.getOutputSpeech().toString().contains(AlexaTexts.GCCI_CTH));
    }

    @Test
    public void handleTestCurrencyIsNull() {
        final Response response = TestUtil.parameterizedTestForHandle(handler, null, null, "0", "0");
        assertTrue(response.getOutputSpeech().toString().contains(AlexaTexts.GCCI_SP_ERROR));
    }

}
