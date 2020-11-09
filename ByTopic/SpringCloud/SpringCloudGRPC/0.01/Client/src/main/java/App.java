import helloworld.GreetGrpc;
import helloworld.Request;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class App {

    private static Logger logger = Logger.getLogger("test");

    static class GreetClient {
        final GreetGrpc.GreetBlockingStub stub;


        public GreetClient(Channel channel) {
            stub = GreetGrpc.newBlockingStub(channel);
        }

        public void greet(String name) {
            Request request = Request.newBuilder()
                    .setUserName(name)
                    .build();

            String text = stub.sayHello(request).getText();
            logger.info(text);
        }
    }

    public static void main(String... args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:50051")
                .usePlaintext()
                .build();
        GreetClient client = new GreetClient(channel);
        client.greet("John");
        channel.shutdownNow().awaitTermination(30, TimeUnit.SECONDS);
    }
}
