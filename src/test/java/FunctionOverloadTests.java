import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Optional;

@RunWith(JUnit4.class)
public class FunctionOverloadTests {

    static class MyClass {
        public static String toString(Object value) {
            return "Object: " + value;
        }

        public static String toString(Optional<String> value) {
            return "Option: " + value;
        }

        public static String toString(Optional<String> ... values) {
            return "Array: " + Arrays.toString(values);
        }
    }


    public void log(String str) {
        System.out.println(str);
    }

    @Test
    // Also has refactoring problems
    public void test1() {
        log(MyClass.toString("Test1"));
        log(MyClass.toString(Optional.of("Test2")));
        log(MyClass.toString(Optional.empty()));
        log(MyClass.toString(Optional.of("Test3"), Optional.empty()));
        //log(MyClass.toString(null));
    }
}
