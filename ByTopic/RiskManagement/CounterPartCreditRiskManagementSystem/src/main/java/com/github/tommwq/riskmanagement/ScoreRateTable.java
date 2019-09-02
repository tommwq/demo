package com.github.tommwq.riskmanagement;

import java.util.TreeMap;
import java.util.Map.Entry;

public class ScoreRateTable<T> {
  private TreeMap<Double,T> rateTable = new TreeMap<>();
  private T defaultRate;

  public ScoreRateTable addRate(double score, T rate) {
    rateTable.put(score, rate);
    return this;
  }

  public ScoreRateTable(T aRate) {
    defaultRate = aRate;
  }

  public T rate(double score) {
    Entry<Double,T> floor = rateTable.floorEntry(score);
    if (floor == null) {
      return defaultRate;
    }

    return floor.getValue();
  }
}
