package cryptoguide.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Slot;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class TestUtil {

    public static HandlerInput mockHandlerInput(String primaryCurrency, String secondaryCurrency, String rateDate, String currencyAmount) {
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
        when(input.getRequestEnvelope()).thenReturn(requestEnvelopeMock);
        return input;
    }
}