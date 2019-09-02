package com.github.tommwq.riskmanagement;

public class RateInfo {
  public String name;
  public CounterpartType type;
  public Rate rate;

  public RateInfo(String aName, CounterpartType aType, Rate aRate) {
    name = aName;
    type = aType;
    rate = aRate;
  }

  public RateInfo(String aName, CounterpartType aType) {
    name = aName;
    type = aType;
    rate = Rate.Unrated;
  }
}
