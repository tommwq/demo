package com.tq.microservice.registryservice.adapter;

public class InstanceId {
  private Location location;
  private LocalId localId;

  public InstanceId(Location aLocation, LocalId aId) {
    location = aLocation;
    localId = aId;
  }

  public Location location() {
    return location;
  }

  public LocalId localId() {
    return localId;
  }
}
