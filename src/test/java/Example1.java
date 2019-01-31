import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings({"ArrayEquals", "SameParameterValue", "SimplifiableJUnitAssertion"})
@RunWith(JUnit4.class)
public class Example1 {

    private void log(String str) {
        System.out.println(str);
    }

    private double pow(Number n, int times) {
        // 1. Where Number operations: sum, diff, multiply, divide???
        // 2. Long & Integer has some operation but duplicate Math functions? For what?

        return Math.pow((double)n, times);
    }

    @Test
    // Why primitives and autoboxing are needed at all?
    public void testCastAndBoxing() {
        long a = 1;
        Long b = 2L; // WTF L, why not needed on primitive?
        int c = 3;
        Integer d = 4;

        int r1 = (int)(a + b + c + d); // Why cast is needed here?

        Long r2 = b + d; // And why not needed here?

        assertEquals(10, r1);
        assertTrue(6L == r2);
        //assertEquals(6L, r2); // Won't compile
        //assertEquals(1, a.longValue()); // Won't compile (forget about OOP)
        assertEquals(9, pow(c, 2)); // WTF??!

        // How many boxing & unboxing are used during this test?
    }


    @Test
    public void testBigDecimals() {
        BigDecimal a = new BigDecimal(1);
        BigDecimal b = new BigDecimal(2);

        assertEquals(b, a.multiply(new BigDecimal("2.0")));
    }

    @Test
    // 1. Why Array is not a general object and doesn't have functions?
    // 2. Why not Iterable? Any advantages over collections?
    public void testArrays() {
        String[] array1 = {"A", "B"};
        String[] array2 = {"A", "B"};
        assertTrue(array1.equals(array2));

        //Optional<String>[] array = {Optional.of("A")}; // Doesn't work

        // The only way to create array of parametrized elements
        Optional<String>[] array = new Optional[10];
        array[0] = Optional.of("zero");
        System.out.println(array.toString());
        System.out.println(Arrays.toString(array));
    }

    static class MyClass {
        static String toString(Object value) {
            return "Object: " + value;
        }

        // TODO: comment this function and behaviour will change without any warning
        static String toString(Optional<String> value) {
            return "Option: " + value;
        }

        @SafeVarargs
        static String toString(Optional<String> ... values) {
            return "Array: " + Arrays.toString(values);
        }
    }

    @Test
    // Also has refactoring problems
    public void testFunctionOverload() {
        Optional<String>[] array = new Optional[10];
        array[0] = Optional.of("Test4");

        log(MyClass.toString("Test1"));
        log(MyClass.toString(Integer.MAX_VALUE));
        log(MyClass.toString(Optional.of("Test2")));
        log(MyClass.toString(Optional.empty()));
        log(MyClass.toString(Optional.of("Test3"), Optional.empty()));
        log(MyClass.toString(array));
        //log(MyClass.toString(null)); // won't compile

        Function<String, String> f = (s) -> s;
        log(MyClass.toString(f));
        //log(MyClass.toString((s) -> s)); // won't compile
    }

}
