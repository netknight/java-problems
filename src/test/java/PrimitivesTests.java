import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class PrimitivesTests {

    public double pow(Number n, int times) {
        // 1. Where Number operations: sum, diff, multiply, devide???
        // 2. Long & Integer has some operation but duplicate Math functons? For what?

        return Math.pow((double)n, times);
    }

    @Test
    public void testCastAndBoxing() {
        long a = 1;
        Long b = 2L; // WTF L, why not needed on primitive?
        int c = 3;
        Integer d = 4;
        int r1 = (int)(a + b + c + d);

        Long r2 = b + d;

        assertEquals(10, r1);
        //assertEquals(6L, r2); // Won't compile
        assertEquals(9, pow(c, 2)); // WTF??!

        //assertEquals(1, a.longVal()); // Won't compile
    }




}
