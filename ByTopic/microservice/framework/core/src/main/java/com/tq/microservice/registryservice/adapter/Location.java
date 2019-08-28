package com.tq.microservice.registryservice.adapter;

public class Location {
  private String address;
  private int port;

  public Location(String aAddress, int aPort) {
    address = aAddress;
    port = aPort;
  }

  public String address() {
    return address;
  }

  public int port() {
    return port;
  }
}
