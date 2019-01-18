import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class EqualsTest {

    @Test
    public void testBigDecimals() {
        BigDecimal a = new BigDecimal(1);
        BigDecimal b = new BigDecimal(2);

        assertEquals(b, a.multiply(new BigDecimal("2.0")));
    }

    @Test
    public void testArrays() {
        String[] array1 = {"A", "B"};
        String[] array2 = {"A", "B"};
        assertTrue(array1.equals(array2));

    }
}
