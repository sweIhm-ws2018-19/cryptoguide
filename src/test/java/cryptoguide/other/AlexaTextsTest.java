package cryptoguide.other;

import org.junit.Test;

public class AlexaTextsTest {

    @Test(expected = IllegalStateException.class)
    public void constructorTest() {
        AlexaTexts texts = new AlexaTexts();
    }
}
