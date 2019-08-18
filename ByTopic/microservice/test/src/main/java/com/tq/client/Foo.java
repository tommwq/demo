package com.tq.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.List;

import io.grpc.NameResolver;
import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import java.net.URI;
import java.util.ArrayList;
import java.net.SocketAddress;

import java.net.InetSocketAddress;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.health.HealthServicesRequest;
import com.ecwid.consul.v1.health.model.HealthService;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.QueryParams;

public class Foo extends NameResolver.Factory {

  public Bar bar = new Bar();
  
  @Override
  public String getDefaultScheme() {
    return "";
  }

  @Override
  public NameResolver newNameResolver(URI uri, NameResolver.Helper helper) {
    System.err.println(uri.toString());

    return bar;
  }
}

class Bar extends NameResolver {

  public NameResolver.Listener listener;
  
  @Override
  public String getServiceAuthority() {
    return "";
  }

  @Override
  public void start(NameResolver.Listener aListener) {
    listener = aListener;

    ConsulClient client = new ConsulClient("localhost");
    Response<List<HealthService>> response = client.getHealthServices("web", HealthServicesRequest.newBuilder()
                             .setPassing(true)
                             .setQueryParams(QueryParams.DEFAULT)
                             .build());

    response.getValue().stream()
      .forEach(healthService -> {
          HealthService.Service service = healthService.getService();
          String address = service.getAddress();
          int port = service.getPort();
          System.err.println(address + ":" + port);
        });
    
    listener.onAddresses(new ArrayList<EquivalentAddressGroup>(), Attributes.EMPTY);
  }

  @Override
  public void refresh() {
    EquivalentAddressGroup group = new EquivalentAddressGroup(new InetSocketAddress("localhost", 12456));
    ArrayList<EquivalentAddressGroup> list = new ArrayList<EquivalentAddressGroup>();
    list.add(group);
    listener.onAddresses(list, Attributes.EMPTY);
  }

  @Override
  public void shutdown() {
  }
}
