package com.tq.microservice.gateway.nameresolver;

import com.ecwid.consul.v1.health.HealthServicesRequest;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.stream.Collectors;
import java.util.List;

public class ConsulNameResolver extends NameResolver {

  public static class Factory extends NameResolver.Factory {

    @Override
    public String getDefaultScheme() {
      return "";
    }

    @Override
    public NameResolver newNameResolver(URI uri, NameResolver.Helper helper) {
      return new ConsulNameResolver(getServiceName(uri));
    }

    private String  getServiceName(URI uri) {
      return uri.getPath();
    }
  }

  private NameResolver.Listener listener = null;
  private String serviceName = "";

  private ConsulNameResolver(String aServiceName) {
    serviceName = aServiceName;
  }

  @Override
  public String getServiceAuthority() {
    return "";
  }

  @Override
  public void start(NameResolver.Listener aListener) {
    listener = aListener;

    EquivalentAddressGroup group = new EquivalentAddressGroup(new InetSocketAddress("localhost", 12456));

    // TODO get consul address from environment
    ConsulClient consul = new ConsulClient("localhost");
    List<EquivalentAddressGroup> addressList = consul.getHealthServices(serviceName, HealthServicesRequest.newBuilder()
                                                                        .setPassing(true)
                                                                        .setQueryParams(QueryParams.DEFAULT)
                                                                        .build())
      .getValue()
      .stream()
      .map(healthService -> new EquivalentAddressGroup(new InetSocketAddress(healthService.getService().getAddress(),
                                                                             healthService.getService().getPort())))
      .collect(Collectors.toList());

    listener.onAddresses(addressList, Attributes.EMPTY);
  }

  @Override
  public void shutdown() {}
}
