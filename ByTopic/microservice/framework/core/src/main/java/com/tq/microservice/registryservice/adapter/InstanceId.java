package com.tq.microservice.registryservice.adapter;

public class InstanceId implements Comparable<InstanceId> {
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

  @Override
  public int compareTo(InstanceId rhs) {
    if (location.compareTo(rhs.location) == 0) {
      return localId.compareTo(rhs.localId);
    }

    return location.compareTo(rhs.location);
  }
}
