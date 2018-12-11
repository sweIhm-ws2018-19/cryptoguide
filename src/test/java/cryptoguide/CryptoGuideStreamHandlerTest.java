package cryptoguide;

import com.amazon.ask.Skill;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CryptoGuideStreamHandlerTest {

    private CryptoGuideStreamHandler handler;

    @Before
    public void setup() {
        handler = new CryptoGuideStreamHandler();
    }

    @Test
    public void constructorTest() {
        Assert.assertNotNull(handler);
    }

    @Test
    public void getSkillTest() {
        Skill skill = CryptoGuideStreamHandler.getSkill();
        Assert.assertNotNull(skill);
    }

}
