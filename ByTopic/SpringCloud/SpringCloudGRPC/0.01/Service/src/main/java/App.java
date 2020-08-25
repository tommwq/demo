import helloworld.GreetGrpc;
import helloworld.HelloWorldProto;
import helloworld.Reply;
import helloworld.Request;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class App {

    static class GreetService extends GreetGrpc.GreetImplBase {
        @Override
        public void sayHello(Request request, StreamObserver<Reply> responseObserver) {
            Reply reply = Reply.newBuilder()
                    .setText("Welcome, " + request.getUserName() + "!")
                    .build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }

    static class GreetServer {
        private Server server;

        public void start() throws IOException {
            int port = 50051;
            server = ServerBuilder.forPort(port)
                    .addService(new GreetService())
                    .build()
                    .start();

            Runtime.getRuntime()
                    .addShutdownHook(new Thread() {
                        @Override
                        public void run() {
                            try {
                                GreetServer.this.stop();
                            } catch (InterruptedException e) {
                                // ignore
                            }
                        }
                    });
        }

        public void blockUntilShutdown() throws InterruptedException {
            if (server != null) {
                server.awaitTermination();
            }
        }

        public void stop() throws InterruptedException {
            if (server != null) {
                server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            }
        }
    }

    public static void main(String... args) throws Exception {
        final GreetServer server = new GreetServer();
        server.start();
        server.blockUntilShutdown();
    }
}
