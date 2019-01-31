import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.function.BiFunction;
import java.util.function.Function;

import static utils.Utils.log;

@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
@RunWith(JUnit4.class)
public class Example2 {

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
        log(lambdaOneParam(10, v -> v.toString()));
        log(lambdaTwoParams(10, 20, (v1, v2) -> v1 + ", " + v2));
        log(lambdaThreeParams(10, 20, 30, (v1, v2, v3) -> v1 + ", " + v2 + ", " + v3));
    }

    @Test
    public void testLambdaProblems() {

        /*
        lambdaOneParam(10, v -> {
            val a = "55";
            return a + "";
        });
        */

        log(lambdaOneParam(10, v -> this.toString()));
    }

    @Test
    public void operatorsTest() {
        Function<Integer, Integer> count = (x -> x * x + 2 * x + 100);
        int result = count.apply(100);
        log(result);
        //result = (x ->  x * x + 2 * x + 100).apply(100);
    }

}
