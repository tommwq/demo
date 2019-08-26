package com.example.demo;

import java.util.TreeMap;
import java.util.Map.Entry;

class ValueScoreTable {
  TreeMap<Double,Double> table = new TreeMap<>();
  double defaultScore = 0.0;

  public ValueScoreTable add(double value, double score) {
    table.put(value, score);
    return this;
  }

  public ValueScoreTable setDefaultScore(double score) {
    defaultScore = score;
    return this;
  }

  public double score(double value) {
    Entry<Double,Double> floor = table.floorEntry(value);
    if (floor == null) {
      return defaultScore;
    }

    return floor.getValue();
  }
}

public class Counterpart {
  private String id;
  private CounterpartType type;
  private String name = null;
  private double netAsset;
  private double debtAssetRatio;
  private double quickRatio;
  private double roe;
  private int numberOfDefault;
  private double yearOfInvestment;
  private RateInfo lastRateInfo = null;

  private double netValueIncreasment;
  private double stopLossRatio;

  private double industryDebtAssetRatio = 0.0;
  private double industryQuickRatio = 0.0;
  private double industryRoe = 0.0;
  
  public Counterpart(String aId, String aName, CounterpartType aType) {
    id = aId;
    type = aType;
    name = aName;
  }

  public Counterpart(String aId, CounterpartType aType) {
    id = aId;
    type = aType;
  }

  public void setInitialName(String aName) {
    if (name != null) {
      throw new RuntimeException("cannot reassign initial name for counterpart");
    }

    name = aName;
  }

  public void changeNameTo(String aName) {
    name = aName;
  }

  public void changeIndustryDebtAssetRatio(double aValue) {
    industryDebtAssetRatio = 0.0;
  }

  public void changeIndustryQuickRatio(double aValue) {
    industryQuickRatio = aValue;
  }

  public void changeIndustryRoe(double aValue) {
    industryRoe = aValue;
  }

  public void changeDebtAssetRatio(double aValue) {
    debtAssetRatio = 0.0;
  }

  public void changeNetAsset(double aValue) {
    netAsset = aValue;
  }

  public void changeQuickRatio(double aValue) {
    quickRatio = aValue;
  }

  public void changeRoe(double aValue) {
    roe = aValue;
  }

  public void occurDefault() {
    numberOfDefault++;
  }

  public void increaseMonthOfInvestment() {
    yearOfInvestment += (1.0/12.0);
  }

  private double scoreNetAsset() {
    if (netAsset >= 200000000.0) {
      return 100.0;
    } else if (netAsset >= 100000000.0) {
      return 90.0;
    } else if (netAsset >= 50000000.0) {
      return 80.0;
    } else if (netAsset >= 30000000.0) {
      return 70.0;
    } else if (netAsset >= 10000000.0) {
      return 60.0;
    } else {
      return 0.0;
    }
  }

  private double scoreDebtAssetRatio() {
    double base = industryDebtAssetRatio;
    if (debtAssetRatio >= 2 * base) {
      return 0.0;
    } else if (debtAssetRatio >= 1.5 * base) {
      return 60.0;
    } else if (debtAssetRatio >= 1 * base) {
      return 80.0;
    } else if (debtAssetRatio >= 0.5 * base) {
      return 90.0;
    } else {
      return 100.0;
    }
  }

  private double scoreQuickRatio() {
    double base = industryQuickRatio;
    if (quickRatio >= 1.5 * base) {
      return 100.0;
    } else if (quickRatio >= base) {
      return 90.0;
    } else if (quickRatio >= 0.8 * base) {
      return 80.0;
    } else if (quickRatio >= 0.5 * base) {
      return 60.0;
    } else {
      return 0.0;
    }
  }

  private double scoreRoe() {
    double base = industryRoe;
    if (roe >= 1.5 * base) {
      return 100.0;
    } else if (roe >= base) {
      return 90.0;
    } else if (roe >= 0.8 * base) {
      return 80.0;
    } else if (roe >= 0.5 * base) {
      return 60.0;
    } else {
      return 0.0;
    }
  }

  private double scoreCredit() {
    if (numberOfDefault == 0) {
      return 100.0;
    } else if (numberOfDefault == 1) {
      return 60.0;
    }
    return 0.0;
  }

  private double scoreExperience() {
    double experience = 0.0;
    if (yearOfInvestment >= 5) {
      experience = 100.0;
    } else if (yearOfInvestment >= 3) {
      experience = 90.0;
    } else if (yearOfInvestment >= 1) {
      experience = 80.0;
    } else if (yearOfInvestment >= 0.5) {
      experience = 60.0;
    }

    return experience;
  }

  private double scoreFinancial() {
    return 0.4 * scoreNetAsset() + 0.2 * scoreDebtAssetRatio() + 0.3 * scoreQuickRatio() + 0.1 * scoreRoe();
  }

  private double scoreInstitution() {
    double financial = scoreFinancial();
    double credit = scoreCredit();    
    double experience = scoreExperience();

    return 0.5 * financial + 0.3 * credit + 0.2 * experience;
  }

  private double scoreProductNetAsset() {
    if (netAsset > 100000000.0) {
      return 100.0;
    } else if (netAsset > 50000000.0) {
      return 90.0;
    } else if (netAsset > 20000000.0) {
      return 80.0;
    } else {
      return 60.0;
    }
  }

  private double scoreNetValueIncreasment() {
    return new ValueScoreTable().setDefaultScore(0.0)
      .add(0.2, 100.0)
      .add(0.1, 90.0)
      .add(0.05, 80.0)
      .add(0.0, 70.0)
      .add(-0.05, 50.0)
      .score(netValueIncreasment);    
  }

  private double scoreStopLossRatio() {
    return new ValueScoreTable().setDefaultScore(0.0)
      .add(0.9, 100.0)
      .add(0.8, 80.0)
      .add(0.7, 70.0)
      .add(0.5, 50.0)
      .score(stopLossRatio);
  }

  private double scoreProductIndex() {
    return 0.7 * scoreProductNetAsset() + 0.2 * scoreNetValueIncreasment() + 0.1 * scoreStopLossRatio();
  }

  private double scoreManager() {
    return 0.5 * scoreCredit() + 0.5 * scoreExperience();
  }

  private double scoreProduct() {
    double productIndex = scoreProductIndex();
    double manager = scoreManager();

    return 0.7 * productIndex + 0.3 * manager;
  }

  private double score() {
    if (type == CounterpartType.Institution) {
      return scoreInstitution();
    } 

    return scoreProduct();
  }
  
  public void rate() {
    Rate rate;
    double result = score();
    if (result >= 80) {
      rate = Rate.A;
    } else if (result >= 60) {
      rate = Rate.B;
    } else {
      rate = Rate.C;
    }

    lastRateInfo = new RateInfo(name, type, rate);
  }

  public RateInfo getRateInfo() {
    if (lastRateInfo == null) {
      lastRateInfo = new RateInfo(name, type);
    }

    return lastRateInfo;
  }
}
