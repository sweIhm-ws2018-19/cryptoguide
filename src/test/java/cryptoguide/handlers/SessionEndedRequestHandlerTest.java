package cryptoguide.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class SessionEndedRequestHandlerTest {

    private SessionEndedRequestHandler handler;

    @Before
    public void setup() {
        handler = new SessionEndedRequestHandler();
    }

    @Test
    public void canHandleTest() {
        HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    public void handleTest() {
        HandlerInput input = TestUtil.mockHandlerInput("Bitcoin", "Euro", null ,null);
        Optional<Response> handlerOptional = handler.handle(input);
        Optional<Response> builderOptional = input.getResponseBuilder().build();
        handlerOptional.orElseThrow(RuntimeException::new);
        builderOptional.orElseThrow(RuntimeException::new);
        Response handlerResponse = handlerOptional.get();
        Response builderResponse = builderOptional.get();
        assertEquals(handlerResponse, builderResponse);
    }

}
