package com.tq.applogcollect;

import java.util.Map;
import java.util.TreeMap;

public class AppInfo {
  private String appVersion = "";
  private TreeMap<String,String> moduleVersions = new TreeMap<>();
  private String deviceId = "";

  public String getAppVersion() {
    return appVersion;
  }

  public void setAppVersion(String aVersion) {
    appVersion = aVersion;
  }

  public Map<String, String> getModuleVersions() {
    return moduleVersions;
  }

  public void setModuleVersion(String module, String version) {
    moduleVersions.put(module, version);
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String aDeviceId) {
    deviceId = aDeviceId;
  }
}
