package com.github.tommwq.riskmanagement.domain;

public class Industry {
  private int id;
  private String name;
  private double debtAssetRatio;
  private double quickRatio;
  private double roe;

  public Industry(int aId) {
    id = aId;
  }

  public Industry(int aId, String aName, double aDebtAssetRatio, double aQuickRatio, double aRoe) {
    id = aId;
    name = aName;
    debtAssetRatio = aDebtAssetRatio;
    quickRatio = aQuickRatio;
    roe = aRoe;
  }

  public int id() {
    return id;
  }

  public String name() {
    return name;
  }

  public double debtAssetRatio() {
    return debtAssetRatio();
  }

  public double quickRatio() {
    return quickRatio();
  }

  public double roe() {
    return roe();
  }

  public Industry changeName(String aName) {
    name = aName;
    return this;
  }
  
  public Industry changeDebtAssetRatio(double aDebtAssetRatio) {
    debtAssetRatio = aDebtAssetRatio;
    return this;
  }

  public Industry changeQuickRatio(double aQuickRatio) {
    quickRatio = aQuickRatio;
    return this;
  }
  
  public Industry changeRoe(double aRoe) {
    roe = aRoe;
    return this;
  }
}
