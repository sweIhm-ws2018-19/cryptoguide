package cryptoguide.model;

import org.junit.Test;

public class PortfolioManagerTest {

    @Test(expected = IllegalStateException.class)
    public void constructorTest() {
        PortfolioManager manager = new PortfolioManager();
    }
}
