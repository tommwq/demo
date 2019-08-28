package com.tq.microservice.registryservice.adapter;

public class LocalId {
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
}
