package com.tq.applogcollect.http.codec;

public class ModuleVersionCodec {

  public static com.tq.applogcollect.AppLogCollectProto.ModuleVersion toProto(com.tq.applogcollect.http.ModuleVersion pojo) {
    return com.tq.applogcollect.AppLogCollectProto.ModuleVersion.newBuilder()
      .setModuleName(pojo.getModuleName())
      .setModuleVersion(pojo.getModuleVersion())
      .build();
  }

  public static com.tq.applogcollect.http.ModuleVersion toPojo(com.tq.applogcollect.AppLogCollectProto.ModuleVersion proto) {
    com.tq.applogcollect.http.ModuleVersion pojo = new com.tq.applogcollect.http.ModuleVersion();
    pojo.setModuleName(proto.getModuleName());
    pojo.setModuleVersion(proto.getModuleVersion());
    return pojo;
  }
}
