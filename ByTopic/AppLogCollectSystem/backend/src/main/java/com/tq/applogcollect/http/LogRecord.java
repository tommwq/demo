package com.tq.applogcollect.http;
public class LogRecord {
  private long Sequence;
  private long AssociatedSequence;
  private int LogLevel;
  private long LocalTime;
  private String AppVersion;
  private java.util.List<com.tq.applogcollect.http.ModuleVersion> ModuleVersions;
  private String SourceFile;
  private int LineNumber;
  private String PackageName;
  private String ClassName;
  private String MethodName;
  private java.util.List<String> MethodParameters;
  private String MethodResult;
  private String DeviceId;

  public long getSequence() {
    return Sequence;
  }

  public void setSequence(long value) {
    Sequence = value;
  }
  public long getAssociatedSequence() {
    return AssociatedSequence;
  }

  public void setAssociatedSequence(long value) {
    AssociatedSequence = value;
  }
  public int getLogLevel() {
    return LogLevel;
  }

  public void setLogLevel(int value) {
    LogLevel = value;
  }
  public long getLocalTime() {
    return LocalTime;
  }

  public void setLocalTime(long value) {
    LocalTime = value;
  }
  public String getAppVersion() {
    return AppVersion;
  }

  public void setAppVersion(String value) {
    AppVersion = value;
  }
  public java.util.List<com.tq.applogcollect.http.ModuleVersion> getModuleVersions() {
    return ModuleVersions;
  }

  public void setModuleVersions(java.util.List<com.tq.applogcollect.http.ModuleVersion> value) {
    ModuleVersions = value;
  }
  public String getSourceFile() {
    return SourceFile;
  }

  public void setSourceFile(String value) {
    SourceFile = value;
  }
  public int getLineNumber() {
    return LineNumber;
  }

  public void setLineNumber(int value) {
    LineNumber = value;
  }
  public String getPackageName() {
    return PackageName;
  }

  public void setPackageName(String value) {
    PackageName = value;
  }
  public String getClassName() {
    return ClassName;
  }

  public void setClassName(String value) {
    ClassName = value;
  }
  public String getMethodName() {
    return MethodName;
  }

  public void setMethodName(String value) {
    MethodName = value;
  }
  public java.util.List<String> getMethodParameters() {
    return MethodParameters;
  }

  public void setMethodParameters(java.util.List<String> value) {
    MethodParameters = value;
  }
  public String getMethodResult() {
    return MethodResult;
  }

  public void setMethodResult(String value) {
    MethodResult = value;
  }
  public String getDeviceId() {
    return DeviceId;
  }

  public void setDeviceId(String value) {
    DeviceId = value;
  }
}
