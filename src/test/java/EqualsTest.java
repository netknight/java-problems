import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class EqualsTest {

    @Test
    public void testBigDecimals() {
        BigDecimal a = new BigDecimal(1);
        BigDecimal b = new BigDecimal(2);

        assertEquals(b, a.multiply(new BigDecimal("2.0")));
    }
}
