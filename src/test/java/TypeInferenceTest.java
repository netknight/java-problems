import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RunWith(JUnit4.class)
public class TypeInferenceTest {

    public static <T> String myconcat(T ... values) {
        return Arrays.stream(values).map(Object::toString).collect(Collectors.joining(","));
    }

    static class MyClass {


        public final String value;

        MyClass(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    @Test
    public void test1() {
        System.out.println(myconcat(
            new MyClass("one"),
            new MyClass("two"),
            new MyClass("three"),
            new MyClass("four"),
            new MyClass("five"),
            new MyClass("six")
        ));
    }

}
