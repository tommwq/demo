package com.tq.microservice.registryservice.adapter;

public class Location implements Comparable<Location> {
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

  @Override
  public int compareTo(Location rhs) {
    if (address.compareTo(rhs.address) == 0) {
      return port - rhs.port;
    }

    return address.compareTo(rhs.address);
  }
}
