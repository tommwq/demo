package com.tq.applogcollect.http;
public class LogQueryCommand {
  private String PackageName;
  private long Sequence;
  private int Count;
  private boolean IncludeSubPackage;

  public String getPackageName() {
    return PackageName;
  }

  public void setPackageName(String value) {
    PackageName = value;
  }
  public long getSequence() {
    return Sequence;
  }

  public void setSequence(long value) {
    Sequence = value;
  }
  public int getCount() {
    return Count;
  }

  public void setCount(int value) {
    Count = value;
  }
  public boolean getIncludeSubPackage() {
    return IncludeSubPackage;
  }

  public void setIncludeSubPackage(boolean value) {
    IncludeSubPackage = value;
  }
}
