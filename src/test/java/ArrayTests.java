import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RunWith(JUnit4.class)
public class ArrayTests {

    @Test
    public void testStreams1() {
        List<String> list = new ArrayList<String>();
        list.add("milk");
        list.add("bread");
        list.add("sausage");
        list = list.subList(0, 2);
        Stream<String> stream = list.stream();
        list.add("eggs");
        stream.forEach(System.out::println);
    }

}
