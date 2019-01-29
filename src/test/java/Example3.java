import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.function.BiFunction;
import java.util.function.Function;

import static junit.framework.TestCase.assertEquals;

@RunWith(JUnit4.class)
public class Example3 {

    @FunctionalInterface
    interface TriFunction<A, B, C, D> {
        D apply(A a, B b, C c);
    }

    String lambdaOneParam(Object value, Function<Object, String> transform) {
        return transform.apply(value);
    }

    String lambdaTwoParams(Object value1, Object value2, BiFunction<Object, Object, String> transform) {
        return transform.apply(value1, value2);
    }

    String lambdaThreeParams(Object value1, Object value2, Object value3, TriFunction<Object, Object, Object, String> transform) {
        return transform.apply(value1, value2,value3);
    }

    @Test
    public void testLambdas() {
        assertEquals("10", lambdaOneParam(10, v -> v.toString()));
        assertEquals("10, 20", lambdaTwoParams(10, 20, (v1, v2) -> v1 + ", " + v2));
        assertEquals("10, 20, 30", lambdaThreeParams(10, 20, 30, (v1, v2, v3) ->
            v1 + ", " + v2 + ", " + v3)
        );
    }

    @Test
    public void operatorsTest() {
        Function<Integer, Integer> count = (x -> x * x + 2 * x + 100);
        int result = count.apply(100);
        System.out.println(result);
        //result = (x ->  x * x + 2 * x + 100).apply(100);
    }

}