package com.tq.applogmanagement;

import java.util.Map;
import java.util.TreeMap;

public class DeviceAndAppConfig {
  public String getDeviceId() {
    return deviceId;
  }

  public DeviceAndAppConfig setDeviceId(String deviceId) {
    this.deviceId = deviceId;
    return this;
  }

  public String getDeviceVersion() {
    return deviceVersion;
  }

  public DeviceAndAppConfig setDeviceVersion(String deviceVersion) {
    this.deviceVersion = deviceVersion;
    return this;
  }

  public String getBaseOsName() {
    return baseOsName;
  }

  public DeviceAndAppConfig setBaseOsName(String baseOsName) {
    this.baseOsName = baseOsName;
    return this;
  }

  public String getBaseOsVersion() {
    return baseOsVersion;
  }

  public DeviceAndAppConfig setBaseOsVersion(String baseOsVersion) {
    this.baseOsVersion = baseOsVersion;
    return this;
  }

  public String getOsName() {
    return osName;
  }

  public DeviceAndAppConfig setOsName(String osName) {
    this.osName = osName;
    return this;
  }

  public String getOsVersion() {
    return osVersion;
  }

  public DeviceAndAppConfig setOsVersion(String osVersion) {
    this.osVersion = osVersion;
    return this;
  }

  public String getAppVersion() {
    return appVersion;
  }

  public DeviceAndAppConfig setAppVersion(String appVersion) {
    this.appVersion = appVersion;
    return this;
  }

  private String deviceId = "";
  private String deviceVersion = "";
  private String baseOsName = "";
  private String baseOsVersion = "";
  private String osName = "";
  private String osVersion = "";
  private String appVersion = "";
  private TreeMap<String,String> moduleVersions = new TreeMap<>();

  public Map<String, String> getModuleVersions() {
    return moduleVersions;
  }

  public DeviceAndAppConfig setModuleVersion(String module, String version) {
    moduleVersions.put(module, version);
    return this;
  }

}
