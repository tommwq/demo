package com.example.demo;

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
    return (double) new ScoreRateTable<Double>(0.0)
      .addRate(200000000, 100.0)
      .addRate(100000000, 90.0)
      .addRate(50000000, 80.0)
      .addRate(30000000, 70.0)
      .addRate(10000000, 60.0)
      .rate(netAsset);
  }

  private double scoreDebtAssetRatio() {
    double base = industryDebtAssetRatio;
    return (double) new ScoreRateTable<Double>(100.0)
      .addRate(2.0 * base, 0.0)
      .addRate(1.5 * base, 60.0)
      .addRate(1.0 * base, 80.0)
      .addRate(0.5 * base, 90.0)
      .rate(debtAssetRatio);
  }

  private double scoreQuickRatio() {
    double base = industryQuickRatio;
    return (double) new ScoreRateTable<Double>(0.0)
      .addRate(1.5 * base, 100.0)
      .addRate(1.0 * base, 90.0)
      .addRate(0.8 * base, 80.0)
      .addRate(0.5 * base, 60.0)
      .rate(quickRatio);
  }

  private double scoreRoe() {
    double base = industryRoe;
    return (double) new ScoreRateTable<Double>(0.0)
      .addRate(1.5 * base, 100.0)
      .addRate(1.0 * base, 90.0)
      .addRate(0.8 * base, 80.0)
      .addRate(0.5 * base, 60.0)
      .rate(roe);
  }

  private double scoreCredit() {
    return (double) new ScoreRateTable<Double>(100.0)
      .addRate(2, 0.0)
      .addRate(1, 60.0)
      .rate(numberOfDefault);
  }

  private double scoreExperience() {
    return (double) new ScoreRateTable<Double>(60.0)
      .addRate(5, 100.0)
      .addRate(3, 90.0)
      .addRate(1, 80.0)
      .rate(yearOfInvestment);
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
    return (double) new ScoreRateTable<Double>(60.0)
      .addRate(100000000, 100.0)
      .addRate(50000000, 90.0)
      .addRate(20000000, 80.0)
      .rate(netAsset);
  }

  private double scoreNetValueIncreasment() {
    return (double) new ScoreRateTable<Double>(0.0)
      .addRate(0.2, 100.0)
      .addRate(0.1, 90.0)
      .addRate(0.05, 80.0)
      .addRate(0.0, 70.0)
      .addRate(-0.05, 50.0)
      .rate(netValueIncreasment);    
  }

  private double scoreStopLossRatio() {
    return (double) new ScoreRateTable<Double>(0.0)
      .addRate(0.9, 100.0)
      .addRate(0.8, 80.0)
      .addRate(0.7, 70.0)
      .addRate(0.5, 50.0)
      .rate(stopLossRatio);
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
    Rate rate = (Rate) new ScoreRateTable<Rate>(Rate.C).addRate(80, Rate.A)
      .addRate(60, Rate.B)
      .rate(score());
    
    lastRateInfo = new RateInfo(name, type, rate);
  }

  public RateInfo getRateInfo() {
    if (lastRateInfo == null) {
      lastRateInfo = new RateInfo(name, type);
    }

    return lastRateInfo;
  }
}
