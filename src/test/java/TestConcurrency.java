import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.AbstractCollection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@SuppressWarnings({"WeakerAccess", "UnstableApiUsage"})
@RunWith(JUnit4.class)
public class TestConcurrency {

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

    @SuppressWarnings("unused")
    static CompletionStage<Optional<String>> flatten(CompletionStage<Optional<CompletionStage<Optional<Object>>>> future) {
        return future.thenCompose(result ->
            result.map(f ->
                f.thenApply(opt ->
                    opt.map(Object::toString)
                )
            ).orElse(CompletableFuture.completedFuture(Optional.empty()))
        );
    }

    static CompletionStage<ImmutableList<String>> doFindInSearchEngine(@SuppressWarnings("unused") String url) {
        return CompletableFuture.completedFuture(ImmutableList.of("Result1", "Result2"));
    }

    static <T> CompletionStage<ImmutableList<T>> sequence(ImmutableList<CompletionStage<T>> promises) {
        return CompletableFuture.supplyAsync(() -> promises.stream().map(CompletionStage::toCompletableFuture).map(CompletableFuture::join).collect(ImmutableList.toImmutableList()));
    }

    @Test
    public void concurrencyTest1() {

        ImmutableList<ImmutableList<String>> combinedSearchResult = sequence(getSearchEnginesToFind().entrySet().stream()
                .map(entry -> doFindInSearchEngine(entry.getValue()).thenApply(result-> {
                    logRecord(entry.getKey() + " search finished.");
                    return result;
                })).collect(ImmutableList.toImmutableList())).toCompletableFuture().join();

        logRecord("Completed with total results: " + combinedSearchResult.stream().mapToInt(AbstractCollection::size).sum());
    }

    @Test
    public void concurrencyTest2() {

        ImmutableList<Void> combinedSearchResult = sequence(getSearchEnginesToFind().entrySet().stream()
            .map(entry -> doFindInSearchEngine(entry.getValue()).thenAccept(result-> logRecord(entry.getKey() + " search finished.")))
            .collect(ImmutableList.toImmutableList())).toCompletableFuture().join();

        logRecord("Completed with total searches: " + combinedSearchResult.size());
    }
}
