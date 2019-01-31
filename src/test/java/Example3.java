import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.val;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static utils.Utils.log;

@SuppressWarnings({"UnstableApiUsage", "SimplifiableJUnitAssertion"})
@RunWith(JUnit4.class)
public class Example3 {


    @Test
    public void testOptions() {
        // Both constructions are clumsy!
        Optional<String> a = Optional.of("AA");
        Optional<String> b = Optional.empty(); // None - would be shorter

        log(a.orElse(b.orElse("BB")));
        log(a.map(v -> b.orElse(v)));
        log(a.flatMap(v -> b.map(d -> v)));
        // Why Options aren't used everywhere?
        // Option is a collection of 0..1 elements
    }

    @Test
    public void testCollection() {
        ImmutableList<String> list = ImmutableList.of("1", "2", "3");

        ImmutableList<Integer> result = list.stream().map(Integer::valueOf).collect(ImmutableList.toImmutableList());
        log(result);
    }

    @Test
    public void testCollectionWithException() {
        ImmutableList<String> list = ImmutableList.of("1", "2", "3", "s");

        // Type inference is bad!
        ImmutableList<Integer> result = list.stream().<Optional<Integer>>map(v -> {
            try {
                return Optional.of(Integer.valueOf(v));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }).filter(Optional::isPresent).map(Optional::get).collect(ImmutableList.toImmutableList());
        log(result);

        //list.stream().map(URL::new).collect(ImmutableList.toImmutableList()); // Won't compile

        // Exception with Try (if you can't avoid exception)
        val result2 = list.stream().map(v -> Try.of(() -> Integer.valueOf(v))).filter(v -> v.isSuccess()).collect(ImmutableList.toImmutableList());
        log(result2);
    }

    @Test
    public void testCollectionValidationWithTry() {
        ImmutableList<String> list = ImmutableList.of("1", "2", "3", "s");

        val result = list.stream().map(v -> Try.of(() -> Integer.valueOf(v))).collect(ImmutableList.toImmutableList());
        val filtered = result.stream().filter(v -> v.isSuccess()).map(v -> v.get()).collect(ImmutableList.toImmutableList());
        val errors = result.stream().filter(v -> v.isFailure()).map(v -> v.getCause()).map(Throwable::getMessage).collect(ImmutableList.toImmutableList());
        log(result);
        log(filtered);
        log(errors);
    }

    @Test
    public void testCollectionValidationWithEither() {
        ImmutableList<String> list = ImmutableList.of("1", "2", "3", "s");

        val result = list.stream().map(v -> v.chars().allMatch(Character::isDigit) ? Either.right(Integer.valueOf(v)): Either.left(v)).collect(ImmutableList.toImmutableList());
        val filtered = result.stream().filter(v -> !v.isEmpty()).map(Either::get).collect(ImmutableList.toImmutableList());
        val errors = result.stream().filter(v -> v.isEmpty()).map(v -> v.getLeft()).collect(ImmutableList.toImmutableList());
        log(result);
        log(filtered);
        log(errors);
    }

    @Test
    // Example from ZeroTurnAround
    public void testStreamsPuzzle1() {
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
    public void testStreamPuzzle2() {
        log(Stream.of(-3, -2, -1, 0, 1, 2, 3).max(Math::max).get());
    }

    @Test
    public void testCollectionVsStream() {
        ImmutableList<String> list = ImmutableList.of("1", "2", "3", "4", "5");
        // Why different types are used? How it is working: long vs int equals?
        assertTrue(list.size() == list.stream().filter(v -> !v.equals("0")).count());
    }

    @Test
    public void testCollections() {
        ArrayList<String> list = new ArrayList<>();
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
        log(Arrays.toString(array1));
        log(Arrays.toString(array2));

        ImmutableMap<String, Integer> map = ImmutableMap.of(
            "1", 1,
            "2", 2,
            "3", 3
        );

        // Such an easy map operation over Map becomes rocket science!
        Map<Integer, String> transformedMap = map.entrySet().stream().map(entry ->
            new AbstractMap.SimpleEntry<>(Integer.valueOf(entry.getKey()), String.valueOf(entry.getValue()))
        ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        log(transformedMap);
    }
}
