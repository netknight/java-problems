import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.AbstractCollection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@SuppressWarnings({"WeakerAccess", "UnstableApiUsage", "unused"})
@RunWith(JUnit4.class)
public class Example5 {

    static void logRecord(String message) {
        System.out.println(message);
    }

    static ImmutableMap<String, String> getSearchEnginesToFind() {
        return ImmutableMap.of(
                "Google","http://www.google.com",
                "Yandex", "http://www.yandex.ru",
                "Yahoo", "http://www.yahoo.com"
        );
    }

    static CompletionStage<Optional<String>> flatten(CompletionStage<Optional<CompletionStage<Optional<Object>>>> future) {
        return future.thenCompose(result ->
            result.map(f ->
                f.thenApply(opt ->
                    opt.map(Object::toString)
                )
            ).orElse(CompletableFuture.completedFuture(Optional.empty()))
        );
    }

    static <T> CompletionStage<ImmutableList<T>> sequence(Stream<CompletionStage<T>> promises) {
        return CompletableFuture.supplyAsync(() -> promises.map(CompletionStage::toCompletableFuture).map(CompletableFuture::join).collect(ImmutableList.toImmutableList()));
    }

    static CompletionStage<String> doAsyncAction(String request) {
        return CompletableFuture.completedFuture("result");
    }

    static CompletionStage<ImmutableList<String>> batchOperations(CompletionStage<ImmutableList<String>> future) {
        return future.thenCompose(results ->
            sequence(
                Lists.partition(results, 10).stream().map(batch ->
                    batch.stream().map(item ->
                        doAsyncAction(item)
                    )
                ).map(Example5::sequence)
            ).thenApply(r ->
                r.stream().flatMap(ImmutableList::stream).collect(ImmutableList.toImmutableList())
            )
        );
    }

    static CompletionStage<ImmutableList<String>> doFindInSearchEngine(@SuppressWarnings("unused") String url) {
        return CompletableFuture.completedFuture(ImmutableList.of("Result1", "Result2"));
    }

    @Test
    public void concurrencyTest1() {

        ImmutableList<ImmutableList<String>> combinedSearchResult = sequence(getSearchEnginesToFind().entrySet().stream().map(entry ->
            doFindInSearchEngine(entry.getValue()).thenApply(result-> {
                logRecord(entry.getKey() + " search finished.");
                return result;
            })
        )).toCompletableFuture().join();

        logRecord("Completed with total results: " + combinedSearchResult.stream().mapToInt(AbstractCollection::size).sum());
    }

    @Test
    public void concurrencyTest2() {

        ImmutableList<Void> combinedSearchResult = sequence(getSearchEnginesToFind().entrySet().stream().map(entry ->
            doFindInSearchEngine(entry.getValue()).thenAccept(result-> logRecord(entry.getKey() + " search finished."))
        )).toCompletableFuture().join();

        logRecord("Completed with total searches: " + combinedSearchResult.size());
    }
}
