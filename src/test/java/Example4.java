import lombok.Value;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;
import static utils.Utils.log;

@SuppressWarnings("WeakerAccess")
@RunWith(JUnit4.class)
public class Example4 {


    interface Request {

    }

    interface Response {
        String getResponse();
    }

    @Value
    static class RequestA implements Request {
        String request;
    }

    @Value
    static class RequestB implements Request {
        String request;
    }

    @Value
    static class RequestC implements Request {
        String request;
    }

    @Value
    static class RequestD implements Request {
        String request;
    }

    @Value
    static class RequestE implements Request {
        String request;
    }

    @Value
    static class RequestF implements Request {
        String request;
    }

    @Value
    static class RequestG implements Request {
        String request;
    }

    @Value
    static class RequestH implements Request {
        String request;
    }

    @Value
    static class ResponseText implements Response {
        String response;
    }

    static class NoResponse implements Response {

        @Override
        public String getResponse() {
            return "No Response";
        }
    }

    private static Response process(Request request) {
        return Match(request).of(
            Case($(instanceOf(RequestA.class)), r -> new ResponseText(r.request)),
            Case($(instanceOf(RequestB.class)), r -> new ResponseText(r.request)),
            Case($(instanceOf(RequestC.class)), r -> new ResponseText(r.request)),
            Case($(instanceOf(RequestD.class)), r -> new ResponseText(r.request)),
            Case($(instanceOf(RequestE.class)), r -> new ResponseText(r.request)),
            Case($(instanceOf(RequestF.class)), r -> new ResponseText(r.request)),
            Case($(instanceOf(RequestG.class)), r -> new ResponseText(r.request)), // TODO: Comment it to get compilation error
            Case($(instanceOf(RequestH.class)), r -> new NoResponse()),
            Case($(), r -> {
                throw new IllegalArgumentException("Unexpected request: " + r);
            })
        );
    }

    @Test
    public void test() {
        Request request = new RequestH("My request");
        Response response = process(request);
        log(response.getResponse());
    }

}
