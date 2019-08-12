package ${package};

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class ${service} extends ${service}Grpc.${service}ImplBase {
  private final ManagedChannel channel;
  private final ${service}Grpc.${service}Stub stub;
  private final ${service}Grpc.${service}BlockingStub blockingStub;
  
  public ${service}() {
    // TODO
    channel = ManagedChannelBuilder.forAddress("localhost", 50052)
      .usePlaintext()
      .build();
    stub = ${service}Grpc.newStub(channel);
    blockingStub = ${service}Grpc.newBlockingStub(channel);
  }

  <#list methods as method>
${method.body?no_esc}
  </#list>
}
