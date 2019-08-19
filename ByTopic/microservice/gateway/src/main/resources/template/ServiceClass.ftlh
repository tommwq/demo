package ${package};

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolver.Factory;
import io.grpc.StatusRuntimeException;

public class ${service} extends ${service}Grpc.${service}ImplBase {
  private final ManagedChannel channel;
  private final ${service}Grpc.${service}Stub stub;
  private final ${service}Grpc.${service}BlockingStub blockingStub;
  private io.grpc.NameResolver.Factory nameResolverFactory;
  
  public ${service}(io.grpc.NameResolver.Factory aNameResolverFactory) {
    nameResolverFactory = aNameResolverFactory;
    channel = ManagedChannelBuilder.forTarget("${package}.${service}")
      .nameResolverFactory(nameResolverFactory)
      .usePlaintext()
      .build();
    stub = ${service}Grpc.newStub(channel);
    blockingStub = ${service}Grpc.newBlockingStub(channel);
  }

  <#list methods as method>
${method.body?no_esc}
  </#list>
}
