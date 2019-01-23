import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.function.Function;

@RunWith(JUnit4.class)
public class LambdasTest {

    @Test
    public void operatorsTest() {
        Function<Integer, Integer> count = (x -> x * x + 2 * x + 100);
        int result = count.apply(100);
        System.out.println(result);
        //result = (x ->  x * x + 2 * x + 100).apply(100);
    }

}
