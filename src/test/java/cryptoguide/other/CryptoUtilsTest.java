package cryptoguide.other;

import org.junit.Assert;
import org.junit.Test;

public class CryptoUtilsTest {

    @Test(expected = IllegalArgumentException.class)
    public void doubleToSpeechTestError() {
        double test =  1.456;
        String res = CryptoUtils.doubleToSpeech(test, -10);
    }

    @Test
    public void doubleToSpeechNoDecimal() {
        double test = 1.456;
        String res = CryptoUtils.doubleToSpeech(test, 0);
        Assert.assertEquals("1", res);
    }

    @Test
    public void doubleToSpeechDecimal() {
        double test = 1.456;
        String res = CryptoUtils.doubleToSpeech(test, 3);
        Assert.assertEquals("1,456", res);
    }

    @Test
    public void doubleToSpeechAdditionalDecimal() {
        double test = 1.456;
        String res = CryptoUtils.doubleToSpeech(test, 14);
        Assert.assertEquals("1,456", res);
    }
}
