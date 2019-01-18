import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings("UnstableApiUsage")
@RunWith(JUnit4.class)
public class CollectionsTests {

    @Test
    public void testArray() {
        //Optional<String>[] array = {Optional.of("A")}; // Doesn't work

        Optional<String>[] array = new Optional[10];
        array[0] = Optional.of("zero");
        System.out.println(array);
    }

    @Test
    public void collectionToInteger1() {
        ImmutableList<String> list = ImmutableList.of("1", "2", "3");

        ImmutableList<Integer> result = list.stream().map(Integer::valueOf).collect(ImmutableList.toImmutableList());
        System.out.println(result);
    }

    @Test
    public void collectionToInteger2() {
        ImmutableList<String> list = ImmutableList.of("1", "2", "3", "s");

        // Type inference is bad!
        ImmutableList<Integer> result = list.stream().<Optional<Integer>>map(v -> {
            try {
                return Optional.of(Integer.valueOf(v));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }).filter(Optional::isPresent).map(Optional::get).collect(ImmutableList.toImmutableList());
        System.out.println(result);
    }

    @Test
    public void testStreams1() {
        List<String> list = new ArrayList<>();
        list.add("milk");
        list.add("bread");
        list.add("sausage");
        list = list.subList(0, 2);
        Stream<String> stream = list.stream();
        list.add("eggs");
        stream.forEach(System.out::println);
    }

}
