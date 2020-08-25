package helloworld;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.28.0)",
    comments = "Source: service.proto")
public final class GreetGrpc {

  private GreetGrpc() {}

  public static final String SERVICE_NAME = "helloworld.Greet";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<helloworld.Request,
      helloworld.Reply> getSayHelloMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SayHello",
      requestType = helloworld.Request.class,
      responseType = helloworld.Reply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<helloworld.Request,
      helloworld.Reply> getSayHelloMethod() {
    io.grpc.MethodDescriptor<helloworld.Request, helloworld.Reply> getSayHelloMethod;
    if ((getSayHelloMethod = GreetGrpc.getSayHelloMethod) == null) {
      synchronized (GreetGrpc.class) {
        if ((getSayHelloMethod = GreetGrpc.getSayHelloMethod) == null) {
          GreetGrpc.getSayHelloMethod = getSayHelloMethod =
              io.grpc.MethodDescriptor.<helloworld.Request, helloworld.Reply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SayHello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  helloworld.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  helloworld.Reply.getDefaultInstance()))
              .setSchemaDescriptor(new GreetMethodDescriptorSupplier("SayHello"))
              .build();
        }
      }
    }
    return getSayHelloMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GreetStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GreetStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GreetStub>() {
        @java.lang.Override
        public GreetStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GreetStub(channel, callOptions);
        }
      };
    return GreetStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GreetBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GreetBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GreetBlockingStub>() {
        @java.lang.Override
        public GreetBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GreetBlockingStub(channel, callOptions);
        }
      };
    return GreetBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GreetFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GreetFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GreetFutureStub>() {
        @java.lang.Override
        public GreetFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GreetFutureStub(channel, callOptions);
        }
      };
    return GreetFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class GreetImplBase implements io.grpc.BindableService {

    /**
     */
    public void sayHello(helloworld.Request request,
        io.grpc.stub.StreamObserver<helloworld.Reply> responseObserver) {
      asyncUnimplementedUnaryCall(getSayHelloMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSayHelloMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                helloworld.Request,
                helloworld.Reply>(
                  this, METHODID_SAY_HELLO)))
          .build();
    }
  }

  /**
   */
  public static final class GreetStub extends io.grpc.stub.AbstractAsyncStub<GreetStub> {
    private GreetStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreetStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GreetStub(channel, callOptions);
    }

    /**
     */
    public void sayHello(helloworld.Request request,
        io.grpc.stub.StreamObserver<helloworld.Reply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSayHelloMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class GreetBlockingStub extends io.grpc.stub.AbstractBlockingStub<GreetBlockingStub> {
    private GreetBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreetBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GreetBlockingStub(channel, callOptions);
    }

    /**
     */
    public helloworld.Reply sayHello(helloworld.Request request) {
      return blockingUnaryCall(
          getChannel(), getSayHelloMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class GreetFutureStub extends io.grpc.stub.AbstractFutureStub<GreetFutureStub> {
    private GreetFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GreetFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GreetFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<helloworld.Reply> sayHello(
        helloworld.Request request) {
      return futureUnaryCall(
          getChannel().newCall(getSayHelloMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAY_HELLO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GreetImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GreetImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAY_HELLO:
          serviceImpl.sayHello((helloworld.Request) request,
              (io.grpc.stub.StreamObserver<helloworld.Reply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class GreetBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GreetBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return helloworld.HelloWorldProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Greet");
    }
  }

  private static final class GreetFileDescriptorSupplier
      extends GreetBaseDescriptorSupplier {
    GreetFileDescriptorSupplier() {}
  }

  private static final class GreetMethodDescriptorSupplier
      extends GreetBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GreetMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GreetGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GreetFileDescriptorSupplier())
              .addMethod(getSayHelloMethod())
              .build();
        }
      }
    }
    return result;
  }
}
