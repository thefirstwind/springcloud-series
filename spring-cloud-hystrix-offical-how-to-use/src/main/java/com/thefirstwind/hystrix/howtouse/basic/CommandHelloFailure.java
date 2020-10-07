package com.thefirstwind.hystrix.howtouse.basic;
import static org.junit.Assert.*;
import java.util.concurrent.Future;
import org.junit.Test;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class CommandHelloFailure extends HystrixCommand<String>{

    private final String name;

    public CommandHelloFailure(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() {
        throw new RuntimeException("this command always fails");
    }

    @Override
    protected String getFallback() {
        return "Hello Failure " + name + "!";
    }

    public static class UnitTest {

        @Test
        public void testSynchronous() {
            assertEquals("Hello Failure World!", new CommandHelloFailure("World").execute());
            assertEquals("Hello Failure Bob!", new CommandHelloFailure("Bob").execute());
        }

        @Test
        public void testAsynchronous1() throws Exception {
            assertEquals("Hello Failure World!", new CommandHelloFailure("World").queue().get());
            assertEquals("Hello Failure Bob!", new CommandHelloFailure("Bob").queue().get());
        }

        @Test
        public void testAsynchronous2() throws Exception {

            Future<String> fWorld = new CommandHelloFailure("World").queue();
            Future<String> fBob = new CommandHelloFailure("Bob").queue();

            assertEquals("Hello Failure World!", fWorld.get());
            assertEquals("Hello Failure Bob!", fBob.get());
        }
    }

}
