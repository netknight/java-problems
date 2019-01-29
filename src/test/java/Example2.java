import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@SuppressWarnings("UnstableApiUsage")
@RunWith(JUnit4.class)
public class Example2 {

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

    @Test
    public void testCollectionVsStream() {
        ImmutableList<String> list = ImmutableList.of("1", "2", "3", "4", "5");
        // Why different types are used? How it is working: long vs int equals?
        assertTrue(list.size() == list.stream().filter(v -> !v.equals("0")).count());
    }

    @Test
    public void testCollections() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        assertFalse(list.contains(5)); // WTF type safety?
        list.remove(list); // WTF again!
        assertEquals(-1, list.indexOf(list)); // WTF twice!

        // Why Iterable doesn't have functions .map, .flatMap, .filter, .take, .exists etc.? But there is .forEach!

        HashMap<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        // Some more WTFs with Map...
        assertFalse(map.containsKey(map));
        assertFalse(map.containsValue(map));
        assertEquals(Integer.valueOf(5), map.getOrDefault(map, 5));
        map.remove(map, map);

        // Any compile-time immutable collections?
    }

    @Test
    public void testStreams() {
        ImmutableList<String> list = ImmutableList.of("1", "2", "3", "4", "5");

        // Why find by predicate is so complex? Usually it as simple as list.find(predicate)
        Optional<String> value = list.stream().filter(v -> Integer.valueOf(v).equals(4)).findFirst(); // No .findLast?
        assertEquals(Optional.of("4"), value);

        Object[] array1 = list.stream().map(Integer::valueOf).toArray(); // Why array of Objects?
        String[] array2 = list.toArray(new String[0]); // Why list has another syntax?
        System.out.println(Arrays.toString(array1));
        System.out.println(Arrays.toString(array2));

        ImmutableMap<String, Integer> map = ImmutableMap.of(
            "1", 1,
            "2", 2,
            "3", 3
        );

        // Such an easy operation becomes rocket science!
        Map<Integer, String> transformedMap = map.entrySet().stream().map(entry ->
            new AbstractMap.SimpleEntry<>(Integer.valueOf(entry.getKey()), String.valueOf(entry.getValue()))
        ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        System.out.println(transformedMap);
    }

}
