package com.tq.applogcollect.http.codec;

public class LogRecordCodec {

  public static com.tq.applogcollect.AppLogCollectProto.LogRecord toProto(com.tq.applogcollect.http.LogRecord pojo) {
    return com.tq.applogcollect.AppLogCollectProto.LogRecord.newBuilder()
      .setSequence(pojo.getSequence())
      .setAssociatedSequence(pojo.getAssociatedSequence())
      .setLogLevel(com.tq.applogcollect.AppLogCollectProto.LogLevel.forNumber(pojo.getLogLevel()))
      .setLocalTime(pojo.getLocalTime())
      .setAppVersion(pojo.getAppVersion())
      //.addAllModuleVersions(com.tq.applogcollect.http.codec.ModuleVersionCodec.toProto(pojo.getModuleVersions()))
      .setSourceFile(pojo.getSourceFile())
      .setLineNumber(pojo.getLineNumber())
      .setPackageName(pojo.getPackageName())
      .setClassName(pojo.getClassName())
      .setMethodName(pojo.getMethodName())
      .addAllMethodParameters(pojo.getMethodParameters())
      .setMethodResult(pojo.getMethodResult())
      .setDeviceId(pojo.getDeviceId())
      .build();
  }

  public static com.tq.applogcollect.http.LogRecord toPojo(com.tq.applogcollect.AppLogCollectProto.LogRecord proto) {
    com.tq.applogcollect.http.LogRecord pojo = new com.tq.applogcollect.http.LogRecord();
    pojo.setSequence(proto.getSequence());
    pojo.setAssociatedSequence(proto.getAssociatedSequence());
    pojo.setLogLevel(proto.getLogLevel().getNumber());
    pojo.setLocalTime(proto.getLocalTime());
    pojo.setAppVersion(proto.getAppVersion());
    //pojo.setModuleVersions(proto.getModuleVersionsList());
    pojo.setSourceFile(proto.getSourceFile());
    pojo.setLineNumber(proto.getLineNumber());
    pojo.setPackageName(proto.getPackageName());
    pojo.setClassName(proto.getClassName());
    pojo.setMethodName(proto.getMethodName());
    pojo.setMethodParameters(proto.getMethodParametersList());
    pojo.setMethodResult(proto.getMethodResult());
    pojo.setDeviceId(proto.getDeviceId());
    return pojo;
  }
}
