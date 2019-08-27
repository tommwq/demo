package com.tq.microservice.gateway.nameresolver;

import com.tq.microservice.common.InstanceId;
import com.tq.microservice.common.InstanceLocalId;
import com.tq.microservice.common.InstanceLocation;
import com.tq.microservice.registryservice.RegistryServiceGrpc;
import com.tq.microservice.registryservice.QueryServiceRequest;
import com.tq.microservice.registryservice.QueryServiceResponse;
import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolver;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.stream.Collectors;
import java.util.List;

/**
 * resolve name from registry service.
 */
public class RegistryNameResolver extends NameResolver {

  private ManagedChannel channel;
  private RegistryServiceGrpc.RegistryServiceBlockingStub blockingStub;

  public static class Factory extends NameResolver.Factory {

    @Override
    public String getDefaultScheme() {
      return "";
    }

    @Override
    public NameResolver newNameResolver(URI uri, NameResolver.Helper helper) {
      return new RegistryNameResolver(getServiceName(uri));
    }

    private String  getServiceName(URI uri) {
      return uri.getPath();
    }
  }

  private NameResolver.Listener listener = null;
  private String serviceName = "";

  private RegistryNameResolver(String aServiceName) {
    serviceName = aServiceName;
  }

  @Override
  public String getServiceAuthority() {
    return "";
  }

  @Override
  public void start(NameResolver.Listener aListener) {
    listener = aListener;

    // TODO read configuration
    channel = ManagedChannelBuilder.forAddress("localhost", 12346)
      .usePlaintext()
      .build();
    blockingStub = RegistryServiceGrpc.newBlockingStub(channel);
    QueryServiceResponse response = blockingStub.queryService(QueryServiceRequest.newBuilder()
                                                              .setServiceName(serviceName)
                                                              .build());

    List<EquivalentAddressGroup> addressList = response.getInstanceList().stream()
      .map(instanceId -> new EquivalentAddressGroup(new InetSocketAddress(instanceId.getLocation().getHost(),
                                                                          instanceId.getLocation().getPort()))
        ).collect(Collectors.toList());
    
    listener.onAddresses(addressList, Attributes.EMPTY);
  }

  @Override
  public void shutdown() {}
}
