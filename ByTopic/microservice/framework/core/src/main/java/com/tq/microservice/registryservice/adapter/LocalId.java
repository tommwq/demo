package com.tq.microservice.registryservice.adapter;

public class LocalId implements Comparable<LocalId> {
  private int startTime;
  private int pid;

  public LocalId(int aTime, int aPid) {
    startTime = aTime;
    pid = aPid;
  }

  public int startTime() {
    return startTime;
  }

  public int pid() {
    return pid;
  }

  @Override
  public int compareTo(LocalId rhs) {
    if (startTime == rhs.startTime) {
      return pid - rhs.pid;
    }

    return startTime - rhs.startTime;
  }
}
