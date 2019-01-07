package cryptoguide.model;

import org.junit.Test;

public class RequestEnvelopeHelperTest {

    @Test(expected = IllegalStateException.class)
    public void constructorTest() {
        RequestEnvelopeHelper test = new RequestEnvelopeHelper();
    }
}
